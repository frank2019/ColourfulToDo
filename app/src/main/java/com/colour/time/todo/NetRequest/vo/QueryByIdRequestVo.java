package com.colour.time.todo.NetRequest.vo;


public class QueryByIdRequestVo extends BaseRequestVo {

    String task_id;

    public QueryByIdRequestVo(String task_id,String token,Long ts) {
        this.task_id = task_id;
        this.token = token;
        this.ts =  ts;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }
}
