package com.lijt.chat.websocket.config;

/**
 * <p>类  名：com.lijt.chat.websocket.config.RedisPushConfig</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/11 14:49</p>
 *
 * @author junting.li
 * @version 1.0
 */
public class WebSocketSessionConfig {

    public static final String CHANNEL_NAME = "chat.message.redis.topic";

    public static final String ONLINE_USER_COUNT = "chat.online.user.count";
}
