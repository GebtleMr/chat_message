package com.lijt.chat.common.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lijt.chat.common.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <p>类  名：com.lijt.chat.common.config.BizWebMvcConfigurer</p>
 * <p>类描述：todo 阿里云存储</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Configuration
public class BizWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fjc = new FastJsonConfig();
        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fjc.setSerializeConfig(serializeConfig);
        fastJsonConverter.setFastJsonConfig(fjc);
        converters.add(fastJsonConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有请求
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/chat/**");
    }

    @Bean
    public AuthInterceptor authenticationInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
