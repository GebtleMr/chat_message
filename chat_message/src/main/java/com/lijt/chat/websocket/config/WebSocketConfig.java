package com.lijt.chat.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * <p>类  名：com.lijt.chat.websocket.config.WebSocketConfig</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/11 15:20</p>
 *
 * @author junting.li
 * @version 1.0
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpoint() {
        return new ServerEndpointExporter();
    }

    /**
     * 通信文本消息和二进制缓存区大小
     * 避免对接 第三方 报文过大时，Websocket 1009 错误
     *
     * @return
     */

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        // 在此处设置bufferSize
        container.setMaxTextMessageBufferSize(10240000);
        container.setMaxBinaryMessageBufferSize(10240000);
        container.setMaxSessionIdleTimeout(15 * 60000L);
        return container;
    }

}
