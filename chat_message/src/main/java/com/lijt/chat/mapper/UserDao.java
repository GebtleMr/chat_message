package com.lijt.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijt.chat.entity.ChatUser;
import org.apache.ibatis.annotations.Mapper;
/**
 * <p>类  名：com.lijt.chat.dao.UserDao</p>
 * <p>类描述：todo UserDao</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Mapper
public interface UserDao extends BaseMapper<ChatUser> {
}
