package com.colour.time.todo.NetRequest.Converter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.colour.time.todo.NetRequest.vo.TasksResponseVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;


public class MyResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private final Gson gson;


    MyResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    public T convert(ResponseBody value) throws IOException {

        JsonReader jsonReader = this.gson.newJsonReader(value.charStream());

        Object var4;
        try {
            Object result = this.adapter.read(jsonReader);
            if(jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            var4 = result;
        } finally {
            value.close();
        }
        return (T)var4;


     /* String data =  value.string();
        Log.e("TAG",data);*/

    /*    Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();

        TasksResponseVo t = gson.fromJson(data, TasksResponseVo.class);

        String mm =  gson.toJson(t);

        Log.e("TAG",t.toString());
        return (T)t;
        //return  adapter.fromJson(value.charStream());*/


    }
}