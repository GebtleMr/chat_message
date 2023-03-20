package com.lijt.chat.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijt.chat.entity.ChatMessage;
import com.lijt.chat.mapper.ChatMessageDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>类  名：com.lijt.chat.service.ChatMessageService</p>
 * <p>类描述：todo ChatMessageService</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Service
@Slf4j
public class ChatMessageService extends ServiceImpl<ChatMessageDao, ChatMessage> {

    @Transactional(rollbackFor = Exception.class)
    public int sendMessage(ChatMessage chatMessage) {
        UpdateWrapper<ChatMessage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_last", 0);
        updateWrapper.eq("del_flag", 0);
        updateWrapper.and(wrapper -> wrapper.and(item -> item.eq("sender", chatMessage.getSender()).eq("receiver", chatMessage.getReceiver()))
                .or(item -> item.eq("receiver", chatMessage.getSender()).eq("sender", chatMessage.getReceiver()))
        );
        this.update(updateWrapper);
        // 保存消息
        chatMessage.setIsLast(true);
        chatMessage.setDelFlag("0");
        this.save(chatMessage);
        return 1;
    }
}
