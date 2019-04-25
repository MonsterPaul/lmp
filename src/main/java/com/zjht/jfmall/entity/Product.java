package com.zjht.jfmall.entity;

import com.zjht.jfmall.entity.base.BaseProduct;

import javax.persistence.Transient;

public class Product extends BaseProduct{
    /**
     * 排序语句（例如：id desc）
     */
    protected String orderByClause;

    /**
     * 是否去重
     */
    protected boolean distinct;

    @Transient
    private Integer pageSize;

    @Transient
    private Integer pageNum;

    /**
     * Logo图片路径
     */
    @Transient
    private String productLogoPath;

    /**
     * 详情图片路径
     */
    @Transient
    private String productDetailPath;

    /**
     * Logo图片ID
     */
    @Transient
    private String productLogoImgId;

    /**
     * 详情图片ID
     */
    @Transient
    private String productDetailImgId;

    public Product() {
        
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getProductLogoPath() {
        return productLogoPath;
    }

    public void setProductLogoPath(String productLogoPath) {
        this.productLogoPath = productLogoPath;
    }

    public String getProductDetailPath() {
        return productDetailPath;
    }

    public void setProductDetailPath(String productDetailPath) {
        this.productDetailPath = productDetailPath;
    }

    public String getProductLogoImgId() {
        return productLogoImgId;
    }

    public void setProductLogoImgId(String productLogoImgId) {
        this.productLogoImgId = productLogoImgId;
    }

    public String getProductDetailImgId() {
        return productDetailImgId;
    }

    public void setProductDetailImgId(String productDetailImgId) {
        this.productDetailImgId = productDetailImgId;
    }


    /**
     * 状态
     */
    public static enum Status {

        NORMAL(0,"正常"),
        Disable(1,"已停用"),
        DELETED(2,"已删除");

        private Status(int status, String name) {
            this.status = status;
            this.name = name;
        }

        private String name;
        private int status;
        public static String getName(int status) {
            for (Product.Status c : Product.Status.values()) {
                if (c.getStatus() == status) {
                    return c.name;
                }
            }
            return null;
        }
        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
    }
    /**
     * 产品类型
     */
    public static enum Type {

        JY(0,"加油券"),
        SC(1,"商超券"),
        CASH(2,"现金券");
        private Type(int type, String name) {
            this.type = type;
            this.name = name;
        }

        private String name;
        private int type;
        public static String getName(int status) {
            for (Product.Type c : Product.Type.values()) {
                if (c.getType() == status) {
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