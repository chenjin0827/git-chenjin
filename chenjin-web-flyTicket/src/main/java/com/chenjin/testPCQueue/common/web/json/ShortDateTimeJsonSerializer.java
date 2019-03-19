package com.chenjin.testPCQueue.common.web.json;

import com.chenjin.testPCQueue.common.web.util.DateTime;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

public class ShortDateTimeJsonSerializer extends JsonSerializer<Date>
{
    public static final String SHORT_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException
    {
        if (value != null)
            jgen.writeString(DateTime.format(value, "yyyy-MM-dd HH:mm"));
    }

    public Class<Date> handledType()
    {
        return Date.class;
    }
}