package com.colour.time.todo.NetRequest.vo;

/**
 * Created by mx on 2018/7/2.
 */

public class RequestVoUser {
    protected Integer id;
    protected String imei;
    protected String uuid;
    protected String nick_name;
    protected String passwd;
    protected String email;
    protected Integer level;
    protected Integer action;
    protected Integer from_id;


    public RequestVoUser(Integer id, String imei, String uuid, String nick_name, String passwd, String email, Integer level, Integer action, Integer from_id) {
        this.id = id;
        this.imei = imei;
        this.uuid = uuid;
        this.nick_name = nick_name;
        this.passwd = passwd;
        this.email = email;
        this.level = level;
        this.action = action;
        this.from_id = from_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getFrom_id() {
        return from_id;
    }

    public void setFrom_id(Integer from_id) {
        this.from_id = from_id;
    }
}
