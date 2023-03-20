package com.lijt.chat.common.exception;

import java.text.MessageFormat;


/**
 * <p>类  名：com.lijt.chat.common.exception.BizException</p>
 * <p>类描述：todo 自动响应话术异常</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public BizException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * 重写fillInStackTrace()方法 不打印堆栈信息
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}[{1}]", this.code, this.message);
    }

}
