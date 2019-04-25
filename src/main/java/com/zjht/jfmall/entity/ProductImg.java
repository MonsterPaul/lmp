package com.zjht.jfmall.entity;

import com.zjht.jfmall.entity.base.BaseProductImg;

public class ProductImg extends BaseProductImg{
    /**
     * 排序语句（例如：id desc）
     */
    protected String orderByClause;

    /**
     * 是否去重
     */
    protected boolean distinct;

    public ProductImg() {
        
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

    /**
     * 图片类型
     */
    public static enum Type {

        LOGO(1,"logo"),
        DETAIL(2,"详情");


        private Type(int type, String name) {
            this.type = type;
            this.name = name;
        }

        private String name;
        private int type;
        public static String getName(int type) {
            for (Type c : Type.values()) {
                if (c.getType() == type) {
                    return c.name;
                }
            }
            return null;
        }
        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }
    }
}