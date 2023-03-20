package com.lijt.chat.websocket.config;

import com.alibaba.fastjson.JSONObject;
import com.lijt.chat.websocket.model.MessageDto;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.Writer;

/**
 * <p>类  名：com.lijt.chat.websocket.config.ConsumerEncoder</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/11 15:34</p>
 *
 * @author ruffian_lee
 * @version 1.0
 */

public class ConsumerEncoder implements Encoder.TextStream<MessageDto> {

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void encode(MessageDto payload, Writer writer) {
        JSONObject.writeJSONString(writer, payload);
    }

    @Override
    public void destroy() {
    }
}
