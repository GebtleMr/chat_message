package com.lijt.chat.websocket.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSON;
import com.lijt.chat.entity.ChatMessage;
import com.lijt.chat.websocket.wss.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * <p>类  名：com.lijt.chat.websocket.config.RedisMessageListener</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/11 13:33</p>
 *
 * @author junting.li
 * @version 1.0
 */

@Slf4j
@Configuration
public class RedisMessageListener implements MessageListener {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        // 获取消息
        byte[] messageBody = message.getBody();
        ChatMessage chatMessage = Convert.convert(new TypeReference<ChatMessage>() {
        }, redisTemplate.getValueSerializer().deserialize(messageBody));
        Map<Long, Session> onlineSessionMap = WebSocketServer.USER_SESSIONS;
        if (onlineSessionMap.containsKey(chatMessage.getReceiver())) {
            try {
                onlineSessionMap.get(chatMessage.getReceiver()).getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (IOException e) {
                log.error("发送消息异常:", e);
            }
        }
    }
}
