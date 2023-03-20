package com.lijt.chat.common.config;

import com.lijt.chat.util.SpringContextHolder;

/**
 * <p>类  名：com.lijt.chat.common.config.OSSFactory</p>
 * <p>类描述：todo OSSFactory</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
public final class OSSFactory {


    public static CloudStorageService build() {
        //获取云存储配置信息
        CloudStorageConfig config = SpringContextHolder.getBean(CloudStorageConfig.class);
        return new AliyunCloudStorageService(config);
    }

}
