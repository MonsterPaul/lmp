package com.zjht.jfmall.entity.base;

import com.zjht.jfmall.entity.User;

import java.io.Serializable;
import java.util.Date;

public class BaseElectroniCode implements Serializable {

    private static final long serialVersionUID = 3494814075169661971L;
    /**
     * 
     */
    private String id;

    /**
     * 电子码
     */
    private String code;

    /**
     * 电子码积分数
     */
    private Integer points;

    /**
     * 电子码状态（1-已使用，0-未使用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建者
     */
    private User creator;

    /**
     * 兑换时间
     */
    private Date exchangeTime;
    /**
     * 兑换手机号
     */
    private String mobile;
    /**
     * 发送者
     */
    private User sender;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 备注
     */
    private String remark;

    /**
     * 导入者
     */
    private User importor;
    /**
     * 导入时间
     */
    private Date importTime;

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
     * 电子码
     * @return code 电子码
     */
    public String getCode() {
        return code;
    }

    /**
     * 电子码
     * @param code 电子码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 电子码积分数
     * @return points 电子码积分数
     */
    public Integer getPoints() {
        return points;
    }
    /**
     * 电子码积分数
     * @param points 电子码积分数
     */
    public void setPoints(Integer points) {
        this.points = points;
    }
    /**
     * 电子码状态（1-已使用，0-未使用）
     * @return status 电子码状态（1-已使用，0-未使用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 电子码状态（1-已使用，0-未使用）
     * @param status 电子码状态（1-已使用，0-未使用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 创建时间
     * @return createTime 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 兑换时间
     * @return exchangeTime 兑换时间
     */
    public Date getExchangeTime() {
        return exchangeTime;
    }

    /**
     * 兑换时间
     * @param exchangeTime 兑换时间
     */
    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getImportor() {
        return importor;
    }

    public void setImportor(User importor) {
        this.importor = importor;
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}