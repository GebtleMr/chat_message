package com.lijt.chat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>类  名：com.lijt.chat.model.User</p>
 * <p>类描述：todo UserDao</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Data
@TableName(value = "chat_user")
public class ChatUser implements Serializable {

    private static final long serialVersionUID = -7470049602945129881L;
    private Long id;

    private String name;

    private String avatar;

    private String openId;
    private String unionId;
    private String gender;

    private String mySign;
}
