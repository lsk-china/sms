package com.lsk.smsbackend2.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.lsk.smsbackend2.util.ReflectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    private int code;
    private String message;
    private Object data;

    /*
        In fact, I never want to create this method.But if I don't, I have to change too many codes
        of frontend, and I don't have enough time to do that.
        I may remove this method in newer version. Hope I will do that. :-)
     */
    private Object processPage(Object data) {
        try {
            if (!(data instanceof Page)) {
                return data;
            }
            Page<?> page = (Page<?>) data;
            List<?> records = page.getRecords();
            List<Object> resultPages = null;
            if (shouldIChangeItToMap(records)) {
                resultPages = new ArrayList<>();
                for (Object record : records) {
                    resultPages.add((Object) ReflectionUtil.objectToMap(record));
                }
            } else {
                resultPages = (List<Object>) records;
            }
            Long total = page.getPages();
            Map<String, Object> result = new HashMap<>();
            result.put("paged", resultPages);
            result.put("totalPages", total);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean shouldIChangeItToMap(List<?> data) {
        if (data.size() == 0) {
            return false;
        }
        Object anElement = data.get(0);
        Class<?> clazz = anElement.getClass();
        return clazz.isAnnotationPresent(Bean.class);
    }

    private Response (int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = processPage(data);
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
