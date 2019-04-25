package com.zjht.jfmall.entity.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BaseProduct implements Serializable {
    /**
     *
     */
    private String id;

    /**
     * 商品编码
     */
    private String productCode;

    /**
     * 所属类目编码
     */
    private String categoryCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 状态：0-正常；1-停用；2-删除
     */
    private Byte status;

    /**
     * 生成时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 库存总数
     */
    private Integer stockCount;

    /**
     * 已卖总数
     */
    private Integer saleCount;

    /**
     * 商品类型：0-实体；1-虚拟
     */
    private Byte productType;

    /**
     * 产品所需积分
     */
    private Integer points;

    /**
     * 产品策略名字
     */
    private String strategyName;

    /**
     * 产品外部编码
     */
    private String outCode;

    /**
     * 产品兑换页
     */
    private String pageExchange;

    /**
     *
     */
    private String pageResult;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 销售价
     */
    private BigDecimal salePrice;

    /**
     * 商品详情介绍
     */
    private String introduction;
    /**
     * 前端排序字段，越大越靠前
     */
    private Integer sort;
    /**
     * 日库存
     */
    private Integer dayStock;
    /**
     * 每日用户购买限制
     */
    private Integer dayUserLimit;


    /**
     * t_product
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
     * 商品编码
     * @return productCode 商品编码
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * 商品编码
     * @param productCode 商品编码
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * 所属类目编码
     * @return categoryCode 所属类目编码
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * 所属类目编码
     * @param categoryCode 所属类目编码
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * 商品名称
     * @return productName 商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 商品名称
     * @param productName 商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 状态：0-正常；1-停用；2-删除
     * @return status 状态：0-正常；1-停用；2-删除
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 状态：0-正常；1-停用；2-删除
     * @param status 状态：0-正常；1-停用；2-删除
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 生成时间
     * @return gmtCreate 生成时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 生成时间
     * @param gmtCreate 生成时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 修改时间
     * @return gmtModified 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 修改时间
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 库存总数
     * @return stockCount 库存总数
     */
    public Integer getStockCount() {
        return stockCount;
    }

    /**
     * 库存总数
     * @param stockCount 库存总数
     */
    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    /**
     * 已卖总数
     * @return saleCount 已卖总数
     */
    public Integer getSaleCount() {
        return saleCount;
    }

    /**
     * 已卖总数
     * @param saleCount 已卖总数
     */
    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    /**
     * 商品类型：0-实体；1-虚拟
     * @return productType 商品类型：0-实体；1-虚拟
     */
    public Byte getProductType() {
        return productType;
    }

    /**
     * 商品类型：0-实体；1-虚拟
     * @param productType 商品类型：0-实体；1-虚拟
     */
    public void setProductType(Byte productType) {
        this.productType = productType;
    }

    /**
     * 产品所需积分
     * @return points 产品所需积分
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * 产品所需积分
     * @param points 产品所需积分
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * 产品策略名字
     * @return strategyName 产品策略名字
     */
    public String getStrategyName() {
        return strategyName;
    }

    /**
     * 产品策略名字
     * @param strategyName 产品策略名字
     */
    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    /**
     * 产品外部编码
     * @return outCode 产品外部编码
     */
    public String getOutCode() {
        return outCode;
    }

    /**
     * 产品外部编码
     * @param outCode 产品外部编码
     */
    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }

    /**
     * 产品兑换页
     * @return pageExchange 产品兑换页
     */
    public String getPageExchange() {
        return pageExchange;
    }

    /**
     * 产品兑换页
     * @param pageExchange 产品兑换页
     */
    public void setPageExchange(String pageExchange) {
        this.pageExchange = pageExchange;
    }

    /**
     *
     * @return pageResult
     */
    public String getPageResult() {
        return pageResult;
    }

    /**
     *
     * @param pageResult
     */
    public void setPageResult(String pageResult) {
        this.pageResult = pageResult;
    }

    /**
     * 市场价
     * @return marketPrice 市场价
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * 市场价
     * @param marketPrice 市场价
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 销售价
     * @return salePrice 销售价
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * 销售价
     * @param salePrice 销售价
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * 商品详情介绍
     * @return introduction 商品详情介绍
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 商品详情介绍
     * @param introduction 商品详情介绍
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getDayUserLimit() {
        return dayUserLimit;
    }

    public void setDayUserLimit(Integer dayUserLimit) {
        this.dayUserLimit = dayUserLimit;
    }

    public Integer getDayStock() {
        return dayStock;
    }

    public void setDayStock(Integer dayStock) {
        this.dayStock = dayStock;
    }
}