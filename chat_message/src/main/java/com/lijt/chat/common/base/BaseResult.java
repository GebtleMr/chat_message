package com.lijt.chat.common.base;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lijt.chat.common.enums.ResultCodeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author junting.li
 * @Description 基础响应信息
 * @Date 2021-03-12 13:17
 **/
@Data
@Slf4j
public class BaseResult<T> implements Serializable {
    private static final long serialVersionUID = -3481403819715888457L;
    private Integer code = 400;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;
    private String msg;
    private T data;


    public BaseResult() {

    }

    private BaseResult(String msg) {
        this.timestamp = LocalDateTime.now();
        this.msg = msg;
    }

    private BaseResult(Integer status, String msg) {
        this.code = status;
        this.timestamp = LocalDateTime.now();
        this.msg = msg;
    }

    private BaseResult(Integer status, String msg, T data) {
        this.code = status;
        this.timestamp = LocalDateTime.now();
        this.msg = msg;
        this.data = data;
    }


    /**
     * 错误
     *
     * @param msg
     * @return
     */
    public static <T> BaseResult<T> error(String msg) {
        return new BaseResult<>(msg);
    }


    /**
     * 统一错误返回
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> commonError() {
        return new BaseResult<>(ResultCodeEnum.COMMON_ERROR.getCode(), ResultCodeEnum.COMMON_ERROR.getMsg());
    }


    /**
     * 其他
     *
     * @param status
     * @param msg
     * @return
     */
    public static <T> BaseResult<T> error(Integer status, String msg) {
        return new BaseResult<>(status, msg);
    }


    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> ok() {
        return new BaseResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg());
    }


    public static <V> BaseResult<Map<String, Object>> okToMap(String key, V value) {
        Map<String, Object> data = new HashMap<>(1);
        data.put(key, value);
        return ok(data);
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> ok(T data) {
        return new BaseResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), data);
    }

    /**
     * 成功
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> success(String message) {
        return new BaseResult<>(HttpStatus.OK.value(), message);
    }


    /**
     * Response输出Json格式
     *
     * @param response
     * @param data     返回数据
     */
    public static void responseJson(ServletResponse response, Object data) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(JSON.toJSONString(data));
            out.flush();
        } catch (Exception e) {
            System.out.println("Response输出Json异常：" + e);
        }
    }


    /**
     * Response 重定向
     *
     * @param response
     * @param redirectUrl 重定向页面
     */
    public static void responseSendRedirect(HttpServletResponse response, String redirectUrl) {
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setStatus(200);
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            log.error("Response重定向跳转页面异常：", e);
        }
    }

    /**
     * Response输出html重定向
     *
     * @param response
     * @param redirectUrl 返回数据
     */
    public static void sendRedirectHtml(HttpServletResponse response, String redirectUrl) {
        try {
            StringBuffer buffer = new StringBuffer("<html> <script>");
            buffer.append("window.open=('" + redirectUrl + "')");
            buffer.append("</script></html>");
            try (PrintWriter out = response.getWriter()) {
                out.println(buffer.toString());
                out.flush();
            } catch (Exception e) {
                log.error("Response输出Json异常：", e);
            }
        } catch (Exception e) {
            log.error("Response重定向跳转页面异常：", e);
        }
    }

}
