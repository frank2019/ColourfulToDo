package com.colour.time.todo.NetRequest.vo;

import com.colour.time.todo.data.Task;


public class TaskResponseVo extends BaseResponseVo {
    public TaskResponseVo(String status, String message) {
        super(status, message);
    }
    Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
