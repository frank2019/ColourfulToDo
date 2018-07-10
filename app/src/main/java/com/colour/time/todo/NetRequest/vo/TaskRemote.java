package com.colour.time.todo.NetRequest.vo;

import com.colour.time.todo.data.Task;

import java.util.Arrays;

/**
 * Created by mx on 2018/7/2.
 */

public class TaskRemote  {
    private  Integer id;
    private String title;
    private String description;
    private Boolean completed;
    private Integer ticks_expect;
    private Integer ticks_consume;
    private Integer father_id;
    private Boolean selected;
    private int[] sub_ids;
    private Long create_time;
    private Integer user_id;


    public TaskRemote(Integer id, String title, String description, Boolean completed, Integer ticks_expect, Integer ticks_consume, Integer father_id, Boolean selected, int[] sub_ids, Long create_time, Integer user_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.ticks_expect = ticks_expect;
        this.ticks_consume = ticks_consume;
        this.father_id = father_id;
        this.selected = selected;
        this.sub_ids = sub_ids;
        this.create_time = create_time;
        this.user_id = user_id;
    }

    public TaskRemote() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getTicks_expect() {
        return ticks_expect;
    }

    public void setTicks_expect(Integer ticks_expect) {
        this.ticks_expect = ticks_expect;
    }

    public Integer getTicks_consume() {
        return ticks_consume;
    }

    public void setTicks_consume(Integer ticks_consume) {
        this.ticks_consume = ticks_consume;
    }

    public Integer getFather_id() {
        return father_id;
    }

    public void setFather_id(Integer father_id) {
        this.father_id = father_id;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public int[] getSub_ids() {
        return sub_ids;
    }

    public void setSub_ids(int[] sub_ids) {
        this.sub_ids = sub_ids;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


    @Override
    public String toString() {
        return "TaskRemote{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", ticks_expect=" + ticks_expect +
                ", ticks_consume=" + ticks_consume +
                ", father_id=" + father_id +
                ", selected=" + selected +
                ", sub_ids=" + Arrays.toString(sub_ids) +
                ", create_time=" + create_time +
                ", user_id=" + user_id +
                '}';
    }
}
