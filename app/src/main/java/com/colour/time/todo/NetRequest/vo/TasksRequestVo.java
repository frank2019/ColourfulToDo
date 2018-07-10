package com.colour.time.todo.NetRequest.vo;

/**
 * Created by mx on 2018/7/2.
 */

public class TasksRequestVo extends BaseRequestVo{
    protected  TaskRemote[]  tasks;

    public TaskRemote[] getTasks() {
        return tasks;
    }

    public void setTasks(TaskRemote[] tasks) {
        this.tasks = tasks;
    }
}
