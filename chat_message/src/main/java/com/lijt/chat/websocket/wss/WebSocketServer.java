package com.lijt.chat.websocket.wss;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.lijt.chat.common.config.RedisUtils;
import com.lijt.chat.entity.ChatMessage;
import com.lijt.chat.service.ChatMessageService;
import com.lijt.chat.util.SpringContextHolder;
import com.lijt.chat.websocket.config.ConsumerEncoder;
import com.lijt.chat.websocket.config.JSONObjectEncoder;
import com.lijt.chat.websocket.config.WebSocketSessionConfig;
import com.lijt.chat.websocket.model.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>类  名：com.lijt.chat.websocket.wss.WebSocketServer</p>
 * <p>类描述：todo 启动</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Slf4j
@AutoConfigureAfter(RedisUtils.class)
@Component
@ServerEndpoint(value = "/websocket/chat/{userId}", encoders = {ConsumerEncoder.class, JSONObjectEncoder.class})
public class WebSocketServer<V> {


    private static RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);
    private static RedisTemplate<String, Object> redisTemplate = redisUtils.redisTemplate;


    /**
     * 实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key为用户标识
     */
    public static final ConcurrentHashMap<Long, Session> USER_SESSIONS = new ConcurrentHashMap<>();


    private final ChatMessageService chatMessageService = SpringContextHolder.getBean(ChatMessageService.class);


    /**
     * 建立连接
     *
     * @param userId  用户id
     * @param session session
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        log.info("开始连接");
        USER_SESSIONS.put(Long.parseLong(userId), session);
        addOnlineCount();
        log.info("userId:{}=====>roomId:{}", userId);
        log.info("有新连接加入！新用户：{},当前在线人数为:{}", userId, getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     *
     * @param userId 用户id
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        USER_SESSIONS.remove(Long.parseLong(userId));
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为{}", getOnlineCount());
    }

    /**
     * 消息处理器
     *
     * @param message 客户端发送过来的消息
     * @param session session
     */
    @OnMessage
    public void onMessage(@PathParam("userId") String userId, String message, Session session) {
        try {
            System.out.println(userId + "来自客户端的消息:" + message);
            MessageDto messageDto = JSON.parseObject(message, MessageDto.class);
            ChatMessage chatMessage = sendMessage(messageDto);
            session.getBasicRemote().sendText(JSON.toJSONString(chatMessage));
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("消息通知异常:", e);
        }
    }

    /**
     * 出现错误时
     *
     * @param session session
     * @param error   异常信息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误", error);
    }


    /**
     * @param messageDto 发送消息内容
     * @return ChatMessage
     */
    public ChatMessage sendMessage(MessageDto messageDto) {
        ChatMessage message = new ChatMessage();
        message.setContent(messageDto.getContent());
        Date now = new Date();
        message.setCreatedTime(now);
        message.setReceiver(messageDto.getReceiverId());
        message.setSender(messageDto.getSendId());
        message.setContentType(messageDto.getMessageType());
        message.setIsRead("0");
        message.setRequestId(messageDto.getReqId());
        message.setType(0);
        Date lastTime = messageDto.getLastTime();
        if (Objects.nonNull(lastTime)) {
            long minute = (now.getTime() - lastTime.getTime()) / 1000 / 60;
            log.info("距离上一次发送时间相差：{}", minute);
            if (minute > 5) {
                message.setType(1);
            }
        }
        try {
            int res = chatMessageService.sendMessage(message);
            if (res == 1) {
                message.setStatus("-1");
            }
            //发送
            Session session = USER_SESSIONS.get(messageDto.getReceiverId());
            if (Objects.nonNull(session)) {
                session.getBasicRemote().sendText(JSON.toJSONString(message));
                new LambdaUpdateChainWrapper<>(this.chatMessageService.getBaseMapper())
                        .set(ChatMessage::getIsRead, 1)
                        .eq(ChatMessage::getId, message.getId());
            } else {
                // 发布消息
                redisTemplate.convertAndSend(WebSocketSessionConfig.CHANNEL_NAME, messageDto);
            }

        } catch (IOException e) {
            log.error("发送消息异常:", e);
        }
        return message;
    }

    public Integer getOnlineCount() {
        return (Integer) redisTemplate.opsForValue().get(WebSocketSessionConfig.ONLINE_USER_COUNT);
    }

    public void addOnlineCount() {
        redisTemplate.opsForValue().increment(WebSocketSessionConfig.ONLINE_USER_COUNT);
    }

    public void subOnlineCount() {
        redisTemplate.opsForValue().decrement(WebSocketSessionConfig.ONLINE_USER_COUNT);
    }
}
