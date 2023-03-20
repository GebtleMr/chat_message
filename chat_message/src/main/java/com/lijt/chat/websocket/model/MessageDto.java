package com.lijt.chat.websocket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lijt.chat.common.config.ObjLongSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>类  名：com.lijt.chat.websocket.model.MessageModel</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/11 13:29</p>
 *
 * @author junting.li
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {

    private static final long serialVersionUID = -1L;
    private String reqId;
    private Integer sendType;
    @JsonSerialize(using = ObjLongSerializer.class)
    private Long sendId;
    @JsonSerialize(using = ObjLongSerializer.class)
    private Long receiverId;
    private String content;
    private Integer messageType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;
}










