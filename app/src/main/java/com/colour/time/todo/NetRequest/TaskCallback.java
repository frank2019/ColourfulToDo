package com.colour.time.todo.NetRequest;


import com.colour.time.todo.data.Task;

import retrofit2.Call;

public interface TaskCallback<T> {
    void onSuccess(Task[] tasks);

    void onFailure(String var1, Throwable var2);

}

/*

    void onSuccess(Call<T> var1, Response<T> var2);

    void onFailure(Call<T> var1, Throwable var2);*/
