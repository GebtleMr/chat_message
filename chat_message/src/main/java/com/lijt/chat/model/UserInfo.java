package com.lijt.chat.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>类  名：com.lijt.chat.model.UserInfo</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/14 9:58</p>
 *
 * @author junting.li
 * @version 1.0
 */
@Data
public class UserInfo implements Serializable {
    private String nickName;
    private Integer gender;
    private String language;
    private String city;
    private String province;
    private String country;

}
