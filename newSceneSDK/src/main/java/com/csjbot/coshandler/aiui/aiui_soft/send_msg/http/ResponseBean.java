package com.csjbot.coshandler.aiui.aiui_soft.send_msg.http;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/17.
 */

public class ResponseBean<T> implements Serializable {

    /**
     * code : 0
     * rows : T
     */

    private int code;
    private T rows;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
