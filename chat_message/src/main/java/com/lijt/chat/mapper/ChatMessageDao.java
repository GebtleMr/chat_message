package com.lijt.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijt.chat.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
/**
 * <p>类  名：com.lijt.chat.dao.MessageDao</p>
 * <p>类描述：todo MessageDao</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Mapper
public interface ChatMessageDao extends BaseMapper<ChatMessage> {
}
