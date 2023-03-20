package com.lijt.chat.common.enums;

import com.lijt.chat.common.constants.ErrorConstants;

/**
 * <p>类  名：com.lijt.chat.common.enums.ResultCodeEnum</p>
 * <p>类描述：todo 响应结果枚举</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
public enum ResultCodeEnum {
    SUCCESS(200, ErrorConstants.ALL_SUCCESS),
    COMMON_ERROR(500, ErrorConstants.ALL_ERROR_MESSAGE),
    ;
    private int code;

    private String msg;

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCodeEnum getByCode(int code) {
        for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
            if (code == resultCodeEnum.getCode()) {
                return resultCodeEnum;
            }
        }
        return SUCCESS;
    }
}
