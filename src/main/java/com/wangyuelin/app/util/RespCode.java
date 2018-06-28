package com.wangyuelin.app.util;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-28 下午5:16
 */
public enum  RespCode {
    SUCCESS(100, "数据请求成功"),
    ERROR(101, "数据请求出错");

    RespCode(int index, String msg) {
        this.index = index;
        this.msg = msg;
    }

    private int index;
    private String msg;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}