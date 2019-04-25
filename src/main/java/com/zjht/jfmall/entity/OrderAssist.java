package com.zjht.jfmall.entity;

import com.zjht.jfmall.entity.base.BaseOrderAssist;

public class OrderAssist extends BaseOrderAssist {
    /**
     * 排序语句（例如：id desc）
     */
    protected String orderByClause;

    /**
     * 是否去重
     */
    protected boolean distinct;

    public OrderAssist() {
        
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