package com.lijt.chat.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * <p>类  名：com.lijt.chat.common.config.ObjLongSerializer</p>
 * <p>类描述：todo</p>
 * <p>创建时间：2023/3/11 17:06</p>
 *
 * @author junting.li
 * @version 1.0
 */
public class ObjLongSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(Long.toString(aLong));
    }
}
