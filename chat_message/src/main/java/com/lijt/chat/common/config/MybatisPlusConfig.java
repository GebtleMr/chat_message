package com.lijt.chat.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lijt.chat.common.interceptor.AuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * <p>类  名：com.lijt.chat.common.config.MybatisPlusConfig</p>
 * <p>类描述：todo mybatis-plus</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Configuration
public class MybatisPlusConfig implements MetaObjectHandler {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor();
        innerInterceptor.setDbType(DbType.MYSQL);
        innerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(innerInterceptor);
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.setCacheEnabled(true);
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setCallSettersOnNulls(true);
            configuration.setJdbcTypeForNull(JdbcType.NULL);
        };
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        try {
            final String username = AuthUtils.getUsername();
            this.setFieldValByName("createdTime", date, metaObject);
            this.setFieldValByName("updatedTime", date, metaObject);
            if (StringUtils.isNotBlank(username)) {
                this.setFieldValByName("createdBy", username, metaObject);
                this.setFieldValByName("updatedBy", username, metaObject);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            final String username = AuthUtils.getUsername();
            this.setFieldValByName("updatedTime", new Date(), metaObject);
            if (StringUtils.isNotBlank(username)) {
                this.setFieldValByName("updatedBy", username, metaObject);
            }
        } catch (Exception e) {
        }
    }
}
