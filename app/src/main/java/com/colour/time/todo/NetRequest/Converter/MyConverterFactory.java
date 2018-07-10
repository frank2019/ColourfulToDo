package com.colour.time.todo.NetRequest.Converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public final class MyConverterFactory extends Converter.Factory {
    private final Gson gson;

    public static MyConverterFactory create() {
        return create(new Gson());
    }

    public static MyConverterFactory create(Gson gson) {
        if(gson == null) {
            throw new NullPointerException("gson == null");
        } else {
            return new MyConverterFactory(gson);
        }
    }

    private MyConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if(type == String.class){

        }else {

        }
        TypeAdapter adapter = this.gson.getAdapter(TypeToken.get(type));
        return new MyResponseBodyConverter(this.gson,adapter);

    }

    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter adapter = this.gson.getAdapter(TypeToken.get(type));
        return new MyRequestBodyConverter(this.gson, adapter);
    }
}

