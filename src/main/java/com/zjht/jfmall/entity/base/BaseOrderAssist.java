package com.zjht.jfmall.entity.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BaseOrderAssist implements Serializable {
    /**
     * 主键id
     */
    private String id;

    /**
     * 主表订单id
     */
    private String orderId;

    /**
     * 辅助码
     */
    private String assistcode;

    /**
     * 发送手机号
     */
    private String mobile;

    /**
     * 返回码
     */
    private String respcode;

    /**
     * 返回消息
     */
    private String respmsg;

    /**
     * 关联系统编码
     */
    private String barcode;

    /**
     * 券码产品编号
     */
    private String couponcode;

    /**
     * 券码名称
     */
    private String couponname;

    /**
     * 发送时间
     */
    private Date sendtime;

    /**
     * 券码价格
     */
    private BigDecimal parprice;

    /**
     * 券码状态
     */
    private String status;

    /**
     * 券码失效时间
     */
    private Date endtime;

    /**
     * 退款记录ID
     */
    private String refundid;

    /**
     * t_order_assist
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     * @return id 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 主键id
     * @param id 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 主表订单id
     * @return orderId 主表订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 主表订单id
     * @param orderId 主表订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 辅助码
     * @return assistCode 辅助码
     */
    public String getAssistcode() {
        return assistcode;
    }

    /**
     * 辅助码
     * @param assistcode 辅助码
     */
    public void setAssistcode(String assistcode) {
        this.assistcode = assistcode;
    }

    /**
     * 发送手机号
     * @return mobile 发送手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 发送手机号
     * @param mobile 发送手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 返回码
     * @return respCode 返回码
     */
    public String getRespcode() {
        return respcode;
    }

    /**
     * 返回码
     * @param respcode 返回码
     */
    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    /**
     * 返回消息
     * @return respMsg 返回消息
     */
    public String getRespmsg() {
        return respmsg;
    }

    /**
     * 返回消息
     * @param respmsg 返回消息
     */
    public void setRespmsg(String respmsg) {
        this.respmsg = respmsg;
    }

    /**
     * 关联系统编码
     * @return barCode 关联系统编码
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 关联系统编码
     * @param barcode 关联系统编码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 券码产品编号
     * @return couponCode 券码产品编号
     */
    public String getCouponcode() {
        return couponcode;
    }

    /**
     * 券码产品编号
     * @param couponcode 券码产品编号
     */
    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }

    /**
     * 券码名称
     * @return couponName 券码名称
     */
    public String getCouponname() {
        return couponname;
    }

    /**
     * 券码名称
     * @param couponname 券码名称
     */
    public void setCouponname(String couponname) {
        this.couponname = couponname;
    }

    /**
     * 发送时间
     * @return sendTime 发送时间
     */
    public Date getSendtime() {
        return sendtime;
    }

    /**
     * 发送时间
     * @param sendtime 发送时间
     */
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    /**
     * 券码价格
     * @return parPrice 券码价格
     */
    public BigDecimal getParprice() {
        return parprice;
    }

    /**
     * 券码价格
     * @param parprice 券码价格
     */
    public void setParprice(BigDecimal parprice) {
        this.parprice = parprice;
    }

    /**
     * 券码状态
     * @return status 券码状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 券码状态
     * @param status 券码状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 券码失效时间
     * @return endTime 券码失效时间
     */
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * 退款记录ID
     * @return refundId 退款记录ID
     */
    public String getRefundid() {
        return refundid;
    }

    /**
     * 退款记录ID
     * @param refundid 退款记录ID
     */
    public void setRefundid(String refundid) {
        this.refundid = refundid;
    }
}