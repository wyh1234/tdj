package com.base.retrofit.jackSon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.math.BigDecimal;

import okhttp3.ResponseBody;

/**
 * Created by yangkuo on 2018-09-17.
 */
public class JacksonUtils {
    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            //设置找不到的属性则不序列化
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            //通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
            //Include.Include.ALWAYS 默认
            //Include.NON_DEFAULT 属性为默认值不序列化
            //Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化
            //Include.NON_NULL 属性为NULL 不序列化
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


            SimpleModule module = new SimpleModule();

            module.addDeserializer(String.class, new CustomStringDeserializer());
            module.addDeserializer(Integer.class, new CustomIntegerDeserializer());
            module.addDeserializer(Long.class, new CustomLongDeserializer());
            module.addDeserializer(Float.class, new CustomFloatDeserializer());
            module.addDeserializer(Double.class, new CustomDoubleDeserializer());
            module.addDeserializer(BigDecimal.class, new CustomBigDecimalDeserializer());
            module.addDeserializer(Boolean.class, new CustomBooleanDeserializer());

            objectMapper.registerModule(module);

        }

        return objectMapper;
    }


    private static class CustomStringDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
            return jsonparser.getText();
        }

        @Override
        public String getNullValue() {
            return "";
        }
    }

    private static class CustomIntegerDeserializer extends JsonDeserializer<Integer> {


        @Override
        public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return p.getIntValue();
        }

        @Override
        public Integer getNullValue() {
            return 0;
        }

    }

    private static class CustomLongDeserializer extends JsonDeserializer<Long> {

        @Override
        public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return p.getLongValue();
        }

        @Override
        public Long getNullValue() {
            return 0L;
        }
    }

    private static class CustomFloatDeserializer extends JsonDeserializer<Float> {


        @Override
        public Float deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return p.getFloatValue();
        }

        @Override
        public Float getNullValue() {
            return 0f;
        }
    }

    private static class CustomDoubleDeserializer extends JsonDeserializer<Double> {


        @Override
        public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return p.getDoubleValue();
        }

        @Override
        public Double getNullValue() {
            return 0d;
        }
    }

    private static class CustomBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {


        @Override
        public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            BigDecimal bigDecimal;
            if (p.getCurrentToken() == JsonToken.VALUE_STRING)
                bigDecimal = new BigDecimal(p.getText());
            else bigDecimal = p.getDecimalValue();

            int index = bigDecimal.toString().indexOf(".") + 1;
            int size = bigDecimal.toString().length();

            if (index > 0 && size - index > 2) {
                return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            return bigDecimal;

        }

        @Override
        public BigDecimal getNullValue() {
            return BigDecimal.ZERO;
        }
    }

    private static class CustomBooleanDeserializer extends JsonDeserializer<Boolean> {


        @Override
        public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
                return Boolean.valueOf(p.getText());
            }
            return p.getBooleanValue();
        }
        @Override
        public Boolean getNullValue() {
            return false;
        }
    }


}
