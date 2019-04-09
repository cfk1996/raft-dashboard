package com.github.chenfeikun.raftdashboard.http;

/**
 * @desciption: Response
 * @CreateTime: 2019-04-08
 * @author: chenfeikun
 */
public class Response<T> {

    private int code;
    private String msg;
    private T response;

    public Response() {}

    public Response(int code, String msg, T response) {
        this.code = code;
        this.msg = msg;
        this.response = response;
    }

    public static Response errorResponse(int code, String msg) {
        return new Response(code, msg, null);
    }

    public static <R> Response<R> succResponse(R response) {
        return new Response<>(200, "success", response);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
