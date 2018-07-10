package com.colour.time.todo.NetRequest.vo;

/**
 * Created by mx on 2018/7/2.
 */

public class QueryRequestVo extends BaseRequestVo {
    public final static Integer ALL_TASKS = 0;
    public final static Integer ACTIVE_TASKS = 1;
    public final static Integer COMPLETED_TASKS = 2;

    private Integer type;
    private Long start_time;
    private Long end_time;
    private Integer user_id;



    public QueryRequestVo(Integer type, Long start_time, Long end_time, Integer user_id, String token, Long ts) {
        this.type = type;
        this.start_time = start_time;
        this.end_time = end_time;
        this.user_id = user_id;
        this.token = token;
        this.ts = ts;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


}
