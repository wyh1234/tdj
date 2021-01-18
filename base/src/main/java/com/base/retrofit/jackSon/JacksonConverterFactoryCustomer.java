package com.base.retrofit.jackSon;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by yangkuo on 2018-09-29.
 */
public class JacksonConverterFactoryCustomer extends Converter.Factory {
    /**
     * Create an instance using a default {@link ObjectMapper} instance for conversion.
     */
    public static JacksonConverterFactoryCustomer create() {
        return create(new ObjectMapper());
    }

    /**
     * Create an instance using {@code mapper} for conversion.
     */
    public static JacksonConverterFactoryCustomer create(ObjectMapper mapper) {
        return new JacksonConverterFactoryCustomer(mapper);
    }

    private final ObjectMapper mapper;


    private JacksonConverterFactoryCustomer(ObjectMapper mapper) {
        if (mapper == null) throw new NullPointerException("mapper == null");
        this.mapper = mapper;
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectReader reader = mapper.readerFor(javaType);
//        return new JacksonResponseBodyConverter<>(reader);
        return new JacksonResponseBodyConverterCustomer<>(reader);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectWriter writer = mapper.writerFor(javaType);
//        return new JacksonRequestBodyConverter<>(writer);
        return new JacksonRequestBodyConverterCustomer<>(writer);
    }


}
