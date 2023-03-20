package com.lijt.chat.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>类  名：com.lijt.chat.common.config.RsaConfig</p>
 * <p>类描述：todo ras 加密key信息</p>
 * <p>创建时间：2023/3/14 9:20</p>
 *
 * @author junting.li
 * @version 1.0
 */

@Data
@Component
@ConfigurationProperties(prefix = "rsa")
public class RsaConfig {

    private String privateKey;
    private String publicKey;

}
