package com.zjht.jfmall.weChat.response;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-01-07 10:21
 */
public abstract class CommResponse<T> {
    //接口业务处理状态
    private String status;
    //接口业务处理结果
    private String msg;
    //data
    private T data;

    protected abstract boolean isSuccess();

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
