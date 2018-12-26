package com.chenjin.common.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.chenjin.common.web.util.DateTime;
import java.io.IOException;
import java.util.Date;

public class DateTimeJsonSerializer extends JsonSerializer<Date>
{
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException
    {
        if (value != null)
            jgen.writeString(DateTime.format(value));
    }

    public Class<Date> handledType()
    {
        return Date.class;
    }
}