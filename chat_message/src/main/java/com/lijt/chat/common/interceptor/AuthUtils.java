package com.lijt.chat.common.interceptor;

import com.lijt.chat.entity.ChatUser;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * <p>类  名：com.lijt.chat.common.interceptor.AuthUtils</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/20 10:21</p>
 *
 * @author junting.li
 * @version 1.0
 */
public class AuthUtils {
    private static final ThreadLocal<ChatUser> BASE_USER_THREAD_LOCAL = new ThreadLocal<>();


    /**
     * 获取当前线程的认证信息
     *
     * @return
     */
    public static ChatUser getBaseAuth() {
        return BASE_USER_THREAD_LOCAL.get();
    }


    public static String getOpenId() {
        ChatUser chatUser = getBaseAuth();
        if (Objects.nonNull(chatUser)) {
            return chatUser.getOpenId();
        }
        return StringUtils.EMPTY;
    }

    public static String getUsername() {
        ChatUser chatUser = getBaseAuth();
        if (Objects.nonNull(chatUser)) {
            return chatUser.getName();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 设置当前线程变量的认证信息
     *
     * @param chatUser
     */
    public static void setBaseAuth(ChatUser chatUser) {
        BASE_USER_THREAD_LOCAL.set(chatUser);
    }


    /**
     * 清除线程变量
     */
    public static void removeAppAccount() {
        BASE_USER_THREAD_LOCAL.remove();
    }
}
