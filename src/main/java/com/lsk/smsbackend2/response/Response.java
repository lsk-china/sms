package com.lsk.smsbackend2.response;

import com.google.gson.Gson;

import java.lang.annotation.Repeatable;

public class Response {
    private int code;
    private String message;
    private Object data;

    private Response (int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response ok (Object data) {
        return new Response(200, "Success.", data);
    }

    public static Response error (Throwable t) {
        if (t instanceof StatusCode) {
            StatusCode statusCode = (StatusCode) t;
            return new Response(statusCode.getCode(), statusCode.getMessage(), null);
        } else {
            return new Response(500, t.getMessage(), null);
        }
    }

    public String build () {
        return new Gson().toJson(this);
    }
}
