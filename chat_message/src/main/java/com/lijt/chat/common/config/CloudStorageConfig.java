package com.lijt.chat.common.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * <p>类  名：com.lijt.chat.common.config.CloudStorageConfig</p>
 * <p>类描述：todo 云存储配置信息</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    //阿里云绑定的域名
    private String aliyunDomain;

    //阿里云路径前缀
    private String aliyunPrefix;

    //阿里云EndPoint
    private String aliyunEndPoint;

    //阿里云AccessKeyId
    private String aliyunAccessKeyId;

    //阿里云AccessKeySecret
    private String aliyunAccessKeySecret;

    //阿里云BucketName
    private String aliyunBucketName;
}
