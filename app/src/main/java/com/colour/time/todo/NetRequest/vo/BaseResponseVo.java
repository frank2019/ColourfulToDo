package com.colour.time.todo.NetRequest.vo;

/**
 * Created by mx on 2018/7/2.
 */

public class BaseResponseVo {
    final public static int CODE_SUCCESS = 0 ;
    final public static int CODE_VALID_PARGS = 10001 ;

    final public static String MSG_SUCCESS = "success";
    final public static String MSG_VALID_PARGS = "error parameter ";



    public String status;
    public String message;

    public BaseResponseVo(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "BaseResponseVo{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
