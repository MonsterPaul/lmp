package com.zjht.jfmall.entity;

import java.util.Date;

/**
 * 平台充值记录
 * Created by vip on 2019/1/16.
 */
public class RechargeRecord {

    private String id;

    private String loanId;//平台ID

    private Double balance;//平台当前余额

    private Double rechargeAmount;//平台充值金额

    private String createId;//充值人员ID

    private Date createTime;//充值时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
