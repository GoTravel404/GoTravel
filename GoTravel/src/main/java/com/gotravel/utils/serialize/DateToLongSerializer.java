package com.gotravel.utils.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * @Name: DateToLongSerializer
 * @Description:TODO
 * @Author:chenyx
 * @Date: 2020/3/20 21:13
 **/
public class DateToLongSerializer extends JsonSerializer {

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        Date date = (Date) o;
        jsonGenerator.writeNumber(date.getTime());
    }
}
