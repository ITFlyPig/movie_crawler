package com.wangyuelin.app.crawler.dylol.bean.app;

import java.util.Map;

/**
 * 描述:APP请求返回的格式
 *
 * @outhor wangyuelin
 * @create 2018-05-28 下午4:58
 */
public class RespBean {
    private int code;
    private String msg;
    private Object response;

    public RespBean() {
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public void setResponse(Map<String, Object> response) {
        this.response = response;
    }
}