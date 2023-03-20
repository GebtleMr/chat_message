package com.lijt.chat.common.exception;

import com.lijt.chat.common.base.BaseResult;
import com.lijt.chat.common.constants.ErrorConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;


/**
 * <p>类  名：com.lijt.chat.common.exception.GlobalExceptionHandler</p>
 * <p>类描述：todo 全局异常处理</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public BaseResult handleException(Throwable e) {
        // 打印堆栈信息
        log.error("系统出现异常：", e);
        return buildBaseResult(ErrorConstants.ALL_ERROR_MESSAGE);
    }

    /**
     * 自定义校验话术响应
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public BaseResult bizException(Exception e) {
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            log.error("自定义话术：{}", bizException.getMessage());
            if (bizException.getCode() != null) {
                return buildBaseResult(bizException.getCode(), bizException.getMessage());
            } else {
                return buildBaseResult(bizException.getMessage());
            }
        }
        return buildBaseResult(ErrorConstants.ALL_ERROR_MESSAGE);
    }


    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if (msg.equals(message)) {
            message = str[1] + ":" + message;
        }
        log.error("接口数据验证异常：{}", message);
        return buildBaseResult(100, message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public BaseResult validatedBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        log.error("自定义验证异常：{}", message);
        return buildBaseResult(100, message);
    }


    /**
     * 统一返回
     */
    private BaseResult buildBaseResult(String message) {
        return BaseResult.error(message);
    }

    /**
     * 统一返回
     *
     * @param status
     * @param message
     * @return
     */
    private BaseResult buildBaseResult(Integer status, String message) {
        return BaseResult.error(status, message);
    }


}
