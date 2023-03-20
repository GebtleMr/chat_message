package com.lijt.chat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lijt.chat.common.base.BaseResult;
import com.lijt.chat.entity.ChatMessage;
import com.lijt.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>类  名：com.lijt.chat.controller.MessageController</p>
 * <p>类描述：todo 历史记录</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@RestController
@RequestMapping("/chat/message")
public class MessageController {

    @Autowired
    private ChatMessageService chatMessageService;


    /**
     * 分页获取我与某人的历史聊天记录
     *
     * @param myMemberId
     * @param youMemberId
     * @return
     */
    @GetMapping("/getMessageHistory")
    public BaseResult getMessageHistory(Long myMemberId, Long youMemberId, int pageNo, int pageSize) {
        Page page = new Page<>(pageNo, pageSize);
        //查询历史聊天记录
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper();
        queryWrapper.eq("del_flag", 0);
        queryWrapper.and(wrapper -> wrapper.and(item -> item.eq("sender", myMemberId).eq("receiver", youMemberId))
                .or(item -> item.eq("receiver", myMemberId).eq("sender", youMemberId))
        );
        queryWrapper.orderByDesc("created_time");
        Page pageData = chatMessageService.page(page, queryWrapper);
        //第一次获取，将此人与对方的聊天消息，全部置为已读
        if (pageNo == 1) {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("del_flag", 0);
            updateWrapper.eq("sender", youMemberId);
            updateWrapper.eq("receiver", myMemberId);
            updateWrapper.set("is_read", 1);
            chatMessageService.update(updateWrapper);
        }
        return BaseResult.okToMap("datas", pageData);
    }
}
