/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.colour.time.todo.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.colour.time.todo.NetRequest.RemoteApi;
import com.colour.time.todo.NetRequest.vo.BaseResponseVo;
import com.colour.time.todo.NetRequest.vo.QueryByIdRequestVo;
import com.colour.time.todo.NetRequest.vo.QueryRequestVo;
import com.colour.time.todo.NetRequest.vo.TaskRemote;
import com.colour.time.todo.NetRequest.vo.TaskResponseVo;
import com.colour.time.todo.NetRequest.vo.TasksRequestVo;
import com.colour.time.todo.NetRequest.vo.TasksResponseVo;
import com.colour.time.todo.data.Task;
import com.colour.time.todo.data.source.TasksDataSource;
import com.colour.time.todo.util.AppConstants;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class TasksRemoteAirDataSource implements TasksDataSource {

    final String TAG = "TasksRemoteAir";

    private static TasksRemoteAirDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;


  /*  private final static Map<String, Task> TASKS_SERVICE_DATA;

    static {
        TASKS_SERVICE_DATA = new LinkedHashMap<>(2);
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!");
    }*/

    public static TasksRemoteAirDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TasksRemoteAirDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private TasksRemoteAirDataSource() {}

  /*  private static void addTask(String title, String description) {
        Task newTask = new Task(title, description);
        TASKS_SERVICE_DATA.put(newTask.getId(), newTask);

        TasksRequestVo tasksRequestVo =  new TasksRequestVo();
        //RemoteApi.getInstance().addTasks(tasksRequestVo,null);

    }*/

    /**
     * Note: {@link LoadTasksCallback#onDataNotAvailable()} is never fired. In a real remote data
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getTasks(final @NonNull LoadTasksCallback callback) {

        Long i =  System.currentTimeMillis();
        Long start =0L;
        //yyyy-MM-dd HH:mm:ss
        try {
            start = RemoteApi.dateToStamp("2018-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        QueryRequestVo queryRequestVo = new QueryRequestVo(QueryRequestVo.ACTIVE_TASKS,start,i,1,AppConstants.TOKEN, i);


        RemoteApi.getInstance().getTasks(queryRequestVo, new Callback<TasksResponseVo>() {
            @Override
            public void onResponse(Call<TasksResponseVo> call, Response<TasksResponseVo> response) {

                Log.e(TAG,"onResponse " + response.raw());
                //TaskRemote[] taskRemotes = response.body().getTasks();
                Task[]  tasksArry = response.body().getTasks();

                List<Task> tasks = new ArrayList<Task>();
                if(tasksArry == null || tasksArry.length == 0){

                }else {
                    for(int i = 0; i< tasksArry.length; i++){
                        tasks.add(tasksArry[i]);
                    }
                }
                callback.onTasksLoaded(tasks);
            }

            @Override
            public void onFailure(Call<TasksResponseVo> call, Throwable throwable) {
                Log.e(TAG,"onFailure " + throwable.getMessage());
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * Note: {@link GetTaskCallback#onDataNotAvailable()} is never fired. In a real remote data
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     */
    @Override
    public void getTask(@NonNull String taskId, final @NonNull GetTaskCallback callback) {
        RemoteApi.getInstance().getTaskById(new QueryByIdRequestVo(taskId, AppConstants.TOKEN,System.currentTimeMillis()), new Callback<TaskResponseVo>() {
            @Override
            public void onResponse(Call<TaskResponseVo> call, Response<TaskResponseVo> response) {
                callback.onTaskLoaded(response.body().getTask());
            }

            @Override
            public void onFailure(Call<TaskResponseVo> call, Throwable throwable) {
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void saveTask(@NonNull Task task) {
        TasksRequestVo tasksRequestVo =  new TasksRequestVo();
        tasksRequestVo.setToken(AppConstants.TOKEN);
        tasksRequestVo.setTs(System.currentTimeMillis());
        Task[] tasks = new  Task[1];
        tasks[0] = task;
        tasksRequestVo.setTasks(tasks);

        RemoteApi.getInstance().addTasks(tasksRequestVo, new Callback<BaseResponseVo>() {
            @Override
            public void onResponse(Call<BaseResponseVo> call, Response<BaseResponseVo> response) {
                Log.d(TAG,"onResponse addTasks");
            }

            @Override
            public void onFailure(Call<BaseResponseVo> call, Throwable throwable) {
                Log.d(TAG,"onFailure addTasks");
            }
        });

    }

    public void updateTask(@NonNull Task task){

        TasksRequestVo tasksRequestVo =  new TasksRequestVo();
        tasksRequestVo.setToken(AppConstants.TOKEN);
        tasksRequestVo.setTs(System.currentTimeMillis());
        Task[] tasks = new  Task[1];
        tasks[0] =  task;
        tasksRequestVo.setTasks(tasks);


        RemoteApi.getInstance().updateTasks(tasksRequestVo, new Callback<BaseResponseVo>() {
            @Override
            public void onResponse(Call<BaseResponseVo> call, Response<BaseResponseVo> response) {

            }

            @Override
            public void onFailure(Call<BaseResponseVo> call, Throwable throwable) {

            }
        });

    }

    @Override
    public void completeTask(@NonNull Task task) {
        /*Task completedTask = new Task(task.getTitle(), task.getDescription(), task.getId(), true);
        TASKS_SERVICE_DATA.put(task.getId(), completedTask);*/
        task.setCompleted(true);
        updateTask(task);
    }

    @Override
    public void completeTask(@NonNull String taskId) {

        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    @Override
    public void activateTask(@NonNull Task task) {
        task.setCompleted(false);
        updateTask(task);

       /* Task activeTask = new Task(task.getTitle(), task.getDescription(), task.getId());
        TASKS_SERVICE_DATA.put(task.getId(), activeTask);*/
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        // Not required for the remote data source because the {@link TasksRepository} handles
        // converting from a {@code taskId} to a {@link task} using its cached data.
    }

    @Override
    public void clearCompletedTasks() {
        /*Iterator<Map.Entry<String, Task>> it = TASKS_SERVICE_DATA.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Task> entry = it.next();
            if (entry.getValue().isCompleted()) {
                it.remove();
            }
        }*/
    }

    @Override
    public void refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllTasks() {
        //TASKS_SERVICE_DATA.clear();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
       // TASKS_SERVICE_DATA.remove(taskId);
    }
}
