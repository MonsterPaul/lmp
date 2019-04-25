package com.zjht.jfmall.enums;

/**
 * 兑换订单枚举
 * Created by vip on 2018/1/6.
 */
public enum OrderExchangeStatus {

    PENDING(0, "待处理"),
    AUDIT(1, "审核中"),
    CANCEL(2, "取消"),
    WAIT_SHIPMENTS(3, "待发货"),
    SHIPPED(4, "已发货"),
    FINISH(5, "已完成"),
    LOCK(6, "锁定"),
    TIMEOUT_CANCEL(7, "超时取消"),
    DELETE(99, "删除"),
    NOTKNOW(100, "未知");

    private OrderExchangeStatus(int status, String text) {
        this.status = status;
        this.text = text;
    };

    private int status;//状态值

    private String text;//状态对应的中文

    public String getText() {
        return text;
    }

    public int getStatus() {
        return status;
    }

    /**
     *
     * 给OrderExchange的订单状态获取对应的枚举
     * @param status
     * @return
     */
    public static OrderExchangeStatus getInstance(int status) {
        switch (status) {
            case 0 : return PENDING;//待处理
            case 1 : return AUDIT;//审核中
            case 2 : return CANCEL;//取消
            case 3 : return WAIT_SHIPMENTS;//已发货
            case 4 : return SHIPPED;//已完成
            case 5 : return FINISH;//锁定
            case 6 : return LOCK;//超时取消
            case 7 : return TIMEOUT_CANCEL;//删除
            case 99 : return DELETE;//删除
            default: return NOTKNOW;//未知
        }



    }

}
