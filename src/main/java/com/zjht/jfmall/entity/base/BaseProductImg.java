package com.zjht.jfmall.entity.base;

import java.io.Serializable;
import java.util.Date;

public class BaseProductImg implements Serializable {
    /**
     * 
     */
    private String id;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 图片类型：1-logo；2-详情
     */
    private String type;

    /**
     * 图片路径
     */
    private String path;

    /**
     * 上传时间
     */
    private Date createTime;

    /**
     * 排序（数值越大越靠前）
     */
    private Integer sort;

    /**
     * 操作管理员标识
     */
    private String userId;

    /**
     * t_product_img
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 产品id
     * @return productId 产品id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 产品id
     * @param productId 产品id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 图片类型：1-logo；2-详情
     * @return type 图片类型：1-logo；2-详情
     */
    public String getType() {
        return type;
    }

    /**
     * 图片类型：1-logo；2-详情
     * @param type 图片类型：1-logo；2-详情
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 图片路径
     * @return path 图片路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 图片路径
     * @param path 图片路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 上传时间
     * @return createTime 上传时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 上传时间
     * @param createTime 上传时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 排序（数值越大越靠前）
     * @return sort 排序（数值越大越靠前）
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序（数值越大越靠前）
     * @param sort 排序（数值越大越靠前）
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 操作管理员标识
     * @return userId 操作管理员标识
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 操作管理员标识
     * @param userId 操作管理员标识
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}