package com.zjht.jfmall.entity.param;

import java.util.Date;

/**
 * 贷款大全参数传递
 * Created by vip on 2019/1/16.
 */
public class LayuiAllLoanParam {

    private String id;

    private String name;

    private String status;

    private String operationId;//最后操作人

    private Date operationTime;//最后操作时间

    private String top;//置顶状态

    private Date topTime;//置顶时候处理时间

    private Double recharge;//充值金额

    private Double balance;//余额

    private String furtherId;//分配给谁

    public String getFurtherId() {
        return furtherId;
    }

    public void setFurtherId(String furtherId) {
        this.furtherId = furtherId;
    }

    /**
     * 查询兑换开始时间
     */
    private String exchangeTimeBegin;
    /**
     * 查询兑换结束日期
     */
    private String exchangeTimeEnd;

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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getRecharge() {
        return recharge;
    }

    public void setRecharge(Double recharge) {
        this.recharge = recharge;
    }

    public Date getTopTime() {
        return topTime;
    }

    public void setTopTime(Date topTime) {
        this.topTime = topTime;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
