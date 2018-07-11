package com.colour.time.todo.NetRequest;


import android.support.annotation.NonNull;
import android.util.Log;

import com.colour.time.todo.NetRequest.Converter.MyConverterFactory;
import com.colour.time.todo.NetRequest.vo.BaseResponseVo;
import com.colour.time.todo.NetRequest.vo.DelRequestVo;
import com.colour.time.todo.NetRequest.vo.QueryByIdRequestVo;
import com.colour.time.todo.NetRequest.vo.QueryRequestVo;
import com.colour.time.todo.NetRequest.vo.TaskRemote;
import com.colour.time.todo.NetRequest.vo.TaskResponseVo;
import com.colour.time.todo.NetRequest.vo.TasksRequestVo;
import com.colour.time.todo.NetRequest.vo.TasksResponseVo;
import com.colour.time.todo.NetRequest.vo.Translation;
import com.colour.time.todo.data.Task;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RemoteApi {
    final private String TAG = "RemoteApi";
    final private String  URL = "http://192.168.1.9:8000";

    private static class SingleHolder{
        public static RemoteApi instance = new RemoteApi();
    }
    public static RemoteApi getInstance(){
        return SingleHolder.instance;
    }


    Retrofit mRetrofit ;
    NetApi mRequest;
    Gson mGson ;

    public void init(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URL) // 设置 网络请求 Url
                // .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addConverterFactory(MyConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mRequest= mRetrofit.create(NetApi.class);
        mGson = new Gson();
    }







    public void getTasks(Integer userId, final TaskCallback callback ){
        Long i =  System.currentTimeMillis();
        Long start =0L;
        //yyyy-MM-dd HH:mm:ss
        try {
            start = RemoteApi.dateToStamp("2018-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        QueryRequestVo queryRequestVo = new QueryRequestVo(QueryRequestVo.ALL_TASKS,start,i,1,"", i);
            this.getTasks(queryRequestVo, new Callback<TasksResponseVo>() {
                @Override
                public void onResponse(Call<TasksResponseVo> call, Response<TasksResponseVo> response) {
                    if(callback != null){
                        //TaskRemote[] taskRemotes = response.body().getTasks();

                        //callback.onSuccess();
                    }
                }

                @Override
                public void onFailure(Call<TasksResponseVo> call, Throwable throwable) {

                }
            });
    }


    //--------------
    /**
     *
     * @param queryRequestVo
     * @param callback
     */
    public void getTasks(QueryRequestVo queryRequestVo, Callback<TasksResponseVo> callback){
        if(mRetrofit == null){
            init();
        }
        String s = mGson.toJson(queryRequestVo);
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), s);
        Call<TasksResponseVo> call = mRequest.queryTasksCall(requestBody);
        call.enqueue(callback);
        // Request request =call.request();
    }

    public void getTaskById(QueryByIdRequestVo queryByIdRequestVo, Callback<TaskResponseVo> callback){
        if(mRetrofit == null){
            init();
        }
        String s = mGson.toJson(queryByIdRequestVo);
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), s);
        Call<TaskResponseVo> call = mRequest.queryTaskByIdCall(requestBody);
        call.enqueue(callback);
    }



    public void addTasks(TasksRequestVo tasksRequestVo, Callback<BaseResponseVo> callback){
        if(mRetrofit == null){
            init();
        }
        String s = mGson.toJson(tasksRequestVo);
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), s);
        Call<BaseResponseVo> call = mRequest.addTasksCall(requestBody);
        call.enqueue(callback);
    }

    public void delTasks(DelRequestVo delRequestVo, Callback<BaseResponseVo> callback){
        if(mRetrofit == null){
            init();
        }
        String s = mGson.toJson(delRequestVo);
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), s);
        Call<BaseResponseVo> call = mRequest.delTasksCall(requestBody);
        call.enqueue(callback);
    }

    public void updateTasks(TasksRequestVo tasksRequestVo, Callback<BaseResponseVo> callback){
        if(mRetrofit == null){
            init();
        }
        String s = mGson.toJson(tasksRequestVo);
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), s);
        Call<BaseResponseVo> call = mRequest.updateTasksCall(requestBody );
        call.enqueue(callback);
    }






    public void queryTasks(QueryRequestVo queryRequestVo) {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL) // 设置 网络请求 Url
               // .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addConverterFactory(MyConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        // 步骤5:创建 网络请求接口 的实例
        NetApi request = retrofit.create(NetApi.class);

        final Gson gson = new Gson();
        String s = gson.toJson(queryRequestVo);
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), s);


        //对 发送请求 进行封装
        Call<TasksResponseVo> call = request.queryTasksCall(requestBody);


        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<TasksResponseVo>() {

            @Override
            public void onResponse(Call<TasksResponseVo> call, Response<TasksResponseVo> response) {
                // 步骤7：处理返回的数据结果
                //response.body().show();

                TasksResponseVo tasksResponseVo =  response.body();
                Gson gson1 = new Gson();

                Log.e(TAG,"连接OK" + gson1.toJson(tasksResponseVo));
            }

            @Override
            public void onFailure(Call<TasksResponseVo> call, Throwable throwable) {
                System.out.println("连接失败");
                Log.e(TAG,"连接失败" + throwable.getMessage());
            }
        });
    }

    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        NetApi request = retrofit.create(NetApi.class);

        //对 发送请求 进行封装
        Call<Translation> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {

            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 步骤7：处理返回的数据结果
                response.body().show();
                Log.e(TAG,"连接OK" + response.body().toString());
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                System.out.println("连接失败");
                Log.d(TAG,"连接失败" + throwable.getMessage());
            }
        });
    }

    /**
     * 时间字符串转时间戳
     * @param s
     * @return
     * @throws ParseException
     */
    public static long dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        //res = String.valueOf(ts);
        return ts;
    }
}
