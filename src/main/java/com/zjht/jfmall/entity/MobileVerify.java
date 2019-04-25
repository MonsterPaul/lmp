package com.zjht.jfmall.entity;

import com.zjht.jfmall.entity.base.BaseMobileVerify;

public class MobileVerify extends BaseMobileVerify{

    /**
     * 发送成功
     */
    public static final int SENDSTATUS_SUCCESS = 1;
    /**
     * 发送失败
     */
    public static final int SENDSTATUS_FAILED = 2;

    /**
     * 排序语句（例如：id desc）
     */
    protected String orderByClause;

    /**
     * 是否去重
     */
    protected boolean distinct;

    public MobileVerify() {
        
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }
}