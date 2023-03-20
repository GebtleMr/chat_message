package com.lijt.chat.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lijt.chat.common.base.BaseResult;
import com.lijt.chat.entity.ChatUser;
import com.lijt.chat.service.ChatUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>类  名：com.lijt.chat.controller.RegisterController</p>
 * <p>类描述：todo 注册</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@RestController
@RequestMapping("/chat/register")
@Slf4j
public class RegisterController {

    @Autowired
    private ChatUserService chatUserService;

    @GetMapping("/verifyUser/{openId}")
    public BaseResult<ChatUser> verifyUser(@PathVariable String openId) {
        try {
            ChatUser chatUser = chatUserService.getOne(Wrappers.lambdaQuery(ChatUser.class)
                    .eq(ChatUser::getOpenId, openId));
            if (Objects.nonNull(chatUser)) {
                return BaseResult.ok(chatUser);
            }
            return BaseResult.error(HttpStatus.NOT_FOUND.value(), "用户不存在！");
        } catch (Exception e) {
            log.error("Error Message:" + e.getMessage());
            return BaseResult.error("注册失败");
        }
    }

    @PostMapping("/queryUserList")
    public BaseResult<List<ChatUser>> queryUserList(@RequestBody String openId) {
        try {
            List<ChatUser> chatUserList = chatUserService.list(Wrappers.lambdaQuery(ChatUser.class)
                    .eq(ChatUser::getOpenId, openId)
            );
            return BaseResult.ok(chatUserList);
        } catch (Exception e) {
            log.error("Error Message:" + e.getMessage());
            return BaseResult.error("注册失败");
        }
    }
}
