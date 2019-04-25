package com.zjht.jfmall.entity.base;

import com.zjht.jfmall.entity.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BaseOrderExchange implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 订单完成时间
     */
    private Date endTime;

    /**
     * 用户ID暂时没有可以为空
     */
    private String userId;

    /**
     * 兑换登录手机号码
     */
    private String phone;

    /**
     * 使用积分
     */
    private String integral;
    /**
     * 订单购买产品的售价
     */
    private BigDecimal productPrice;
    /**
     * 订单总金额，产品的售价*数量
     */
    private BigDecimal amount;

    /**
     * 订单状态(0待处理 1审核中 2取消 3待发货 4已发货 5完成 6锁定 7-超时取消 99删除)
     */
    private Integer status;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 用户微信OPENID
     */
    private String openid;

    /**
     * 订单处理类型
     */
    private Integer dealType;

    /**
     * 处理结果
     */
    private Integer dealStatus;

    /**
     * 请求响应码
     */
    private String respCode;

    /**
     * 请求响应描述
     */
    private String respMsg;

    /**
     * 数量
     */
    private Integer total;

    /**
     * t_order_exchange
     */
    private static final long serialVersionUID = 1L;
    /**
     * 订单所属用户
     */
    private User user;
    /**
     * ID
     * @return id ID
     */
    public String getId() {
        return id;
    }

    /**
     * ID
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 订单号
     * @return orderNo 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 订单号
     * @param orderNo 订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 商品ID
     * @return productId 商品ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 商品ID
     * @param productId 商品ID
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 下单时间
     * @return createTime 下单时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 下单时间
     * @param createTime 下单时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 订单完成时间
     * @return endTime 订单完成时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 订单完成时间
     * @param endTime 订单完成时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 用户ID暂时没有可以为空
     * @return userId 用户ID暂时没有可以为空
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户ID暂时没有可以为空
     * @param userId 用户ID暂时没有可以为空
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 兑换登录手机号码
     * @return phone 兑换登录手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 兑换登录手机号码
     * @param phone 兑换登录手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 使用积分
     * @return integral 使用积分
     */
    public String getIntegral() {
        return integral;
    }

    /**
     * 使用积分
     * @param integral 使用积分
     */
    public void setIntegral(String integral) {
        this.integral = integral;
    }

    /**
     * 订单状态
     * @return status 订单状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 订单状态
     * @param status 订单状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 订单备注
     * @return remark 订单备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 订单备注
     * @param remark 订单备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 用户微信OPENID
     * @return openId 用户微信OPENID
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 用户微信OPENID
     * @param openid 用户微信OPENID
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getDealType() {
        return dealType;
    }

    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}