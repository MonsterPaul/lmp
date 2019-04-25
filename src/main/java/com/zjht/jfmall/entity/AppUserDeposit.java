package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * app用户提现表 
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "app_user_deposit")
public class AppUserDeposit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private AppUser appUser;

    @Transient
    private User user;

    @Id
    private String id;
    /**
     * 用户ID
     */
    @Column(name = "app_user_id")
    private String appUserId;
    /**
     * 提现金额
     */
    private Double amount;
    /**
     * 申请时间
     */
    @Column(name = "apply_date")
    private Date applyDate;
    /**
     * 提现时间
     */
    @Column(name = "deposit_date")
    private Date depositDate;
    @Column(name = "opertion_id")
    private String opertionId;
    /**
     * 提现进度  0处理中 1已处理
     */
    private String status;

    /**
     * 查询兑换开始时间
     */
    @Transient
    private String exchangeTimeBegin;
    /**
     * 查询兑换结束日期
     */
    @Transient
    private String exchangeTimeEnd;

    @Transient
    private int count;
    @Transient
    private Double countAmount;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getCountAmount() {
        return countAmount;
    }

    public void setCountAmount(Double countAmount) {
        this.countAmount = countAmount;
    }

    public String getStatusStr() {
        switch (this.getStatus()) {
            case "0" : return "处理中";
            case "1" : return "已处理";
            default : return "未知";
        }
    }

    public String getExchangeTimeBegin() {
        return exchangeTimeBegin;
    }

    public void setExchangeTimeBegin(String exchangeTimeBegin) {
        this.exchangeTimeBegin = exchangeTimeBegin;
    }

    public String getExchangeTimeEnd() {
        return exchangeTimeEnd;
    }

    public void setExchangeTimeEnd(String exchangeTimeEnd) {
        this.exchangeTimeEnd = exchangeTimeEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(Date depositDate) {
        this.depositDate = depositDate;
    }

    public String getOpertionId() {
        return opertionId;
    }

    public void setOpertionId(String opertionId) {
        this.opertionId = opertionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
