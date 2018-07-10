package com.colour.time.todo.NetRequest.vo;

/**
 * Created by mx on 2018/7/2.
 */

public class BaseRequestVo {
    protected String token;

    protected Long ts;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
