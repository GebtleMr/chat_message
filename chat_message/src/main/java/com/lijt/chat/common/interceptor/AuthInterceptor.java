package com.lijt.chat.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>类  名：com.lijt.chat.common.interceptor.AuthInterceptor</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/20 10:20</p>
 *
 * @author junting.li
 * @version 1.0
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从 http token
        final String token = httpServletRequest.getHeader("token");
        // 从redis中获取token信息

        //放入到ThreadLocal 中
        return Boolean.TRUE;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        AuthUtils.removeAppAccount();
    }
}
