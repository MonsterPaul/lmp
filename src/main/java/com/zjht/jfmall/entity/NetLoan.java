package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 网贷大全表
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "t_net_loan")
public class NetLoan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private User user;

    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 属性
     */
    private String attribute;
    /**
     * 名称
     */
    private String name;
    /**
     * 额度
     */
    private Double quota;
    /**
     * 网址1
     */
    @Column(name = "link_url1")
    private String linkUrl1;
    /**
     * 类型
     */
    private String type;
    /**
     * 网址5
     */
    @Column(name = "link_url5")
    private String linkUrl5;
    /**
     * 网址
     */
    @Column(name = "link_url2")
    private String linkUrl2;
    /**
     * 网址3
     */
    @Column(name = "link_url3")
    private String linkUrl3;
    /**
     * 网址4
     */
    @Column(name = "link_url4")
    private String linkUrl4;
    /**
     * 操作人ID
     */
    @Column(name = "operation_id")
    private String operationId;
    /**
     * 操作时间
     */
    @Column(name = "operation_time")
    private Date operationTime;

    /**
     * 平台总放款额度
     */
    @Transient
    private int userAmount;
    /**
     * 平台总放款人数
     */
    @Transient
    private int userCount;

    @Transient
    private Double amountCount;//平台已收佣金

    @Transient
    private String dealStatus;//排序

    /**
     * 查询兑换开始时间
     */
    @Transient
    private String exchangeTimeBegin;

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getExchangeTimeBegin() {
        return exchangeTimeBegin;
    }

    public void setExchangeTimeBegin(String exchangeTimeBegin) {
        this.exchangeTimeBegin = exchangeTimeBegin;
    }

    public Double getAmountCount() {
        return amountCount;
    }

    public void setAmountCount(Double amountCount) {
        this.amountCount = amountCount;
    }

    public int getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(int userAmount) {
        this.userAmount = userAmount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuota() {
        return quota;
    }

    public void setQuota(Double quota) {
        this.quota = quota;
    }

    public String getLinkUrl1() {
        return linkUrl1;
    }

    public void setLinkUrl1(String linkUrl1) {
        this.linkUrl1 = linkUrl1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinkUrl5() {
        return linkUrl5;
    }

    public void setLinkUrl5(String linkUrl5) {
        this.linkUrl5 = linkUrl5;
    }

    public String getLinkUrl2() {
        return linkUrl2;
    }

    public void setLinkUrl2(String linkUrl2) {
        this.linkUrl2 = linkUrl2;
    }

    public String getLinkUrl3() {
        return linkUrl3;
    }

    public void setLinkUrl3(String linkUrl3) {
        this.linkUrl3 = linkUrl3;
    }

    public String getLinkUrl4() {
        return linkUrl4;
    }

    public void setLinkUrl4(String linkUrl4) {
        this.linkUrl4 = linkUrl4;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
