package com.base.retrofit.jackSon;

import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by yangkuo on 2018-09-29.
 */
public class JacksonResponseBodyConverterCustomer<T> implements Converter<ResponseBody, T> {
    private final ObjectReader adapter;

    JacksonResponseBodyConverterCustomer(ObjectReader adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {

//            if (BuildConfig.DEBUG) {
//                String ss = value.source().readUtf8();
//                Log.d("reader", ss);
//                return adapter.readValue(ss);
//            }

            return adapter.readValue(value.charStream());
        } finally {
            value.close();
        }
    }

}
