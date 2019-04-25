package com.zjht.jfmall.common.dto;


public class JsonResult {
    private boolean success;
    private String  status;
    private String  msg;
    private Object  data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonResult(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public JsonResult() {

    }
}
