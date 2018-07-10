package com.colour.time.todo.NetRequest.vo;

import android.util.Log;

/**
 * Created by mx on 2018/6/21.
 */

public class Translation {
    static final String TAG =  "Test";



    private int status;
    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;

        @Override
        public String toString() {
            return "content{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", vendor='" + vendor + '\'' +
                    ", out='" + out + '\'' +
                    ", errNo=" + errNo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Translation{" +
                "status=" + status +
                ", content=" + content.toString() +
                '}';
    }

    //定义 输出返回数据 的方法
    public void show() {

        Log.d(TAG,this.toString());

        //System.out.println(status);

    }
}