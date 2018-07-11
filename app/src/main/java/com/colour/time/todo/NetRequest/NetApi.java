package com.colour.time.todo.NetRequest;


import com.colour.time.todo.NetRequest.vo.BaseResponseVo;
import com.colour.time.todo.NetRequest.vo.QueryRequestVo;
import com.colour.time.todo.NetRequest.vo.TaskResponseVo;
import com.colour.time.todo.NetRequest.vo.TasksRequestVo;
import com.colour.time.todo.NetRequest.vo.TasksResponseVo;
import com.colour.time.todo.NetRequest.vo.Translation;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NetApi {

    //http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getCall();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("tasks_query")
    Call<TasksResponseVo> queryTasksCall(@Body RequestBody body);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("task_query")
    Call<TaskResponseVo> queryTaskByIdCall(@Body RequestBody body);

    /*@Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("tasks_query")
    Call<TasksResponseVo> queryTasksCall(QueryRequestVo queryRequestVo);*/

    @POST("tasks_add")
    Call<BaseResponseVo> addTasksCall(@Body RequestBody body);

    @POST("tasks_update")
    Call<BaseResponseVo> updateTasksCall(@Body RequestBody body);

    @POST("tasks_del")
    Call<BaseResponseVo> delTasksCall(@Body RequestBody body);






}