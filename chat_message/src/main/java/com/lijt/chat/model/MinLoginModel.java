package com.lijt.chat.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>类  名：com.lijt.chat.model.MinLoginModel</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/13 17:37</p>
 *
 * @author junting.li
 * @version 1.0
 */
@Data
public class MinLoginModel implements Serializable {
    private UserInfo userInfo;
    private String rawData;
    private String signature;
    private String encryptedData;
    private String iv;
    private String code;
}
