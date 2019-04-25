package com.zjht.jfmall.enums;

/**
 * 兑换订单处理状态
 * Created by vip on 2018/1/6.
 */
public enum OrderExchangeDealStatus {

    PENDING(0, "待处理"),
    PROCESSED(1, "已处理"),
    FAIl(2, "处理失败"),
    CHECKED(3, "状态已回查"),
    NOTKNOW(100, "未知");

    private OrderExchangeDealStatus(int status, String text) {
        this.status = status;
        this.text = text;
    }

    private int status;

    private String text;

    public int getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    /**
     * 获取订单处理枚举对象
     * @param status
     * @return
     */
    public static OrderExchangeDealStatus getInstance(int status) {
        switch(status) {
            case 0 : return PENDING;
            case 1 : return PROCESSED;
            case 2 : return FAIl;
            case 3 : return CHECKED;
            default : return NOTKNOW;
        }
    }

}
