package com.colour.time.todo.NetRequest.vo;

import com.colour.time.todo.data.Task;

/**
 * Created by mx on 2018/7/2.
 */

public class TasksRequestVo extends BaseRequestVo{
    protected  Task[]  tasks;

    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }
}
