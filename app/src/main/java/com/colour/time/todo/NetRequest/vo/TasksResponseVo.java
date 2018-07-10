package com.colour.time.todo.NetRequest.vo;

import com.colour.time.todo.data.Task;

import java.util.Arrays;

/**
 * Created by mx on 2018/7/2.
 */

public class TasksResponseVo extends BaseResponseVo {
    public TasksResponseVo(String status, String message) {
        super(status, message);
    }

    public Task[] tasks;


    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "TasksResponseVo{" +
                "tasks=" + Arrays.toString(tasks) +
                ",message=" + getMessage() +
                '}';
    }
}
