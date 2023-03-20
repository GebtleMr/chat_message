package com.lijt.chat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijt.chat.entity.ChatUser;
import com.lijt.chat.mapper.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>类  名：com.lijt.chat.service.UserService</p>
 * <p>类描述：todo UserService</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Service
@Slf4j
public class ChatUserService extends ServiceImpl<UserDao, ChatUser> {
}
