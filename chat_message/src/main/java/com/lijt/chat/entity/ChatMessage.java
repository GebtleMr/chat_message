package com.lijt.chat.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lijt.chat.common.base.BaseEntify;
import lombok.Data;

import java.util.Date;

/**
 * <p>类  名：com.lijt.chat.model.ChatMessage</p>
 * <p>类描述：todo ChatMessage</p>
 * <p>创建时间：2022/8/26 11:02</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */
@Data
@TableName("chat_message")
public class ChatMessage extends BaseEntify {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 是否实现时间
     * 0：否 1：是
     */
    private Integer type;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 内容类型 0文字1图片2视频 3礼物
     */
    private Integer contentType;
    /**
     * 是否已读
     */
    private String isRead;
    /**
     * 发送人
     */
    private Long sender;
    /**
     * 接收人
     */
    private Long receiver;

    /**
     * 发送消息请求ID
     */
    private String requestId;

    private Boolean isLast;

    /**
     * 状态
     */
    private String status;
    /**
     * 备注
     */
    private String remarks;
}
