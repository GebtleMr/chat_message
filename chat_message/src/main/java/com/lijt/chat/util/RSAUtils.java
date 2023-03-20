package com.lijt.chat.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
/**
 * <p>类  名：com.lijt.chat.util.RSAUtils</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2022/7/19 11:23</p>
 *
 * @author junting.li
 * @version 1.0
 */
@Slf4j
public class RSAUtils {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);//公钥
        keyMap.put(PRIVATE_KEY, privateKey);//私钥
        return keyMap;
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param encryptData 已加密数据
     * @param privateKey  私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(String encryptData, String privateKey) throws Exception {
        byte[] data = encryptData.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }
    /** */
    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param encryptData 已加密数据
     * @param publicKey   公钥(BASE64编码)
     * @param sign        数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(String encryptData, String publicKey, String sign)
            throws Exception {
        byte[] data = encryptData.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }
    /** */
    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        byte[] encryptedData = Base64Utils.decode(data);
        int inputLen = encryptedData.length;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            return new String(decryptedData);
        } catch (Exception e) {
            log.error("私钥解密出现异常：", e);
            return null;
        }

    }
    /** */
    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        byte[] encryptedData = Base64Utils.decode(data);
        int inputLen = encryptedData.length;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            return new String(decryptedData);
        } catch (Exception e) {
            log.error("公钥解密异常：", e);
            return null;
        }
    }
    /** */
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param source    源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String source, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        byte[] data = source.getBytes(StandardCharsets.UTF_8);
        int inputLen = data.length;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            return Base64Utils.encode(encryptedData);
        } catch (Exception e) {
            log.error("公钥加密出现异常：", e);
            return null;
        }

    }
    /** */
    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param target     源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String target, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        byte[] data = target.getBytes(StandardCharsets.UTF_8);
        int inputLen = data.length;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            return Base64Utils.encode(encryptedData);
        } catch (Exception e) {
            log.error("私钥异常：", e);
            return null;
        }
    }
    /** */
    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }
    /** */
    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }


    public static void main(String[] args) throws Exception {
        Map<String, Object> map = genKeyPair();
        String publicKey = getPublicKey(map);
        String privateKey = getPrivateKey(map);
        System.out.println(publicKey);
        System.out.println("------------------------------");
        System.out.println(privateKey);
        System.out.println("------------------------------");




//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmeKR0mBctE2cRly8du1U+KXnIVXk8GTVQxzXA3GeJpgL2moWiQeij92RiokDVqaBniIbYMa0vDgvxIALUy6sxYKF2fTDeVjN1DBnX2Y6MMLYAUgjAv1iaHAFG1sN45cfzSU3mZxPtiOm2XquFH4TkpnODhQLlOuAgaGWf87YI/QIDAQAB";
//        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKZ4pHSYFy0TZxGXLx27VT4pechVeTwZNVDHNcDcZ4mmAvaahaJB6KP3ZGKiQNWpoGeIhtgxrS8OC/EgAtTLqzFgoXZ9MN5WM3UMGdfZjowwtgBSCMC/WJocAUbWw3jlx/NJTeZnE+2I6bZeq4UfhOSmc4OFAuU64CBoZZ/ztgj9AgMBAAECgYBu1IMo3NkROYm1PGIx+TaXXCWoKr+8M0w7q2UuMY79JXlzlQzDKf4oq9w5jqx7k6MnSs37wZb7bO6mahl4cuBVufT/aGSieGH61mTWJLCrGVRFwIgTwycfh4/bdqf8mZPEl9NLLQlwOX/s6UKnda164mSgZTtw0Yk4bFcjc3/4QQJBAOzXjUkFQZ3i5iAFeUqemmy8WsdO6h7HyAWTNiTr2u9ccG31s7AABHxN1vb+MJFe6WZvpM20T/k5v3zPQl976gsCQQCz7+A0/dSwBwjRa7HFQGW82CNmyL1PBHXQrwJ7L6dXTELb9JP4A09zZvubl9QVfRhaNZuhdVF+5F9Kr7aa30YXAkEAyeKTlILeg+89ql4AHhNEHxwPZYVnHAXAAsCRjrddUFvDmneuS4A4Up0neDMEw2XcHm1cVdw1r4QCLimdsja2MQJBAIaijMN5zgOsL8fq6ggqqDVGEC8fMq8GKlVsLdIYlQC3+ir0dScU6wTxYIBKeZFembMd+4Yy7zigDkEm+t4lBB8CQQCAZMBDyVVXBlp2VGZ6nYkjg43RHNW2Fuzluhg4mr5h+xtzt1vKnq3JA/bAEdYF8k57G1uX5CAZfBBT9M33zoXP";
//        /**
//         * 公钥加密 私钥解密
//         */
//        String data = encryptByPublicKey("希奥信息", publicKey);
//        System.out.println(data);
//        System.out.println("=======================================");
//        //生成签名
//        String sign = sign(data, privateKey);
//        System.out.println("=======================================");
//        //验签
//        boolean isVerify = verify(data, publicKey, sign);
//
//        String r = "inuc3Eypsj3+iUQX01ldMlSrxW05c+EYYSQJfmn+07E77Xw6MGIacLSUBFRLBdEKeDILIQaesHDDYnfmfdvUgV/HcNClvYnSRHmptTVeOgNSRTKha2ZxpNxcWIQu0Y9Sn+i1QMn7RyQRx03Q7WqLf0V/0xQZgPb/pG6r2saB+Dg=";
//        System.out.println("boolean:" + isVerify);
//        data = decryptByPrivateKey(r, privateKey);
//        System.out.println(data);


        /**
         * 私钥加密 公钥解密
         */
 /*       data = encryptByPrivateKey(data, privateKey);
        data = decryptByPublicKey(data, publicKey);
        System.out.println(data);*/
    }


}
