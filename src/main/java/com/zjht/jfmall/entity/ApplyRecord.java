package com.zjht.jfmall.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 申请贷款记录表 
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "apply_record")
public class ApplyRecord {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    /**
     * 用户ID
     */
    @Column(name = "app_user_id")
    private String appUserId;
    /**
     * 申请贷款金额 
     */
    @Column(name = "apply_amount")
    private double applyAmount;
    /**
     * 申请时间
     */
    @Column(name = "apply_date")
    private Date applyDate;
    /**
     * 客服id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 最后操作人
     */
    @Column(name = "operation_id")
    private String operationId;
    /**
     * 最后操作时间 （保存不展示）
     */
    @Column(name = "operation_time")
    private Date operationTime;

    @Transient
    private String username;
    @Transient
    private String userphone;
    @Transient
    private String platId;
    @Transient
    private String collStatus;
    @Transient
    private String backLoanStatus;
    @Transient
    private AppUser appUser;
    @Transient
    private User user;
    @Transient
    private User operUser;//最后操作人信息
    @Transient
    private Double countAmount;//总放款金额
    @Transient
    private Double brokerage;//已收佣金
    @Transient
    private String collId;//已收佣金
    @Transient
    private List<String> usePlat;//已放款平台
    @Transient
    private NetLoan net;
    @Transient
    private AppUserLoanPlat loanPlat;
    @Transient
    private int commNum;//催收次数
    @Transient
    private Double csComm;
    @Transient
    private Double kfComm;

    public Double getKfComm() {
        return kfComm;
    }

    public void setKfComm(Double kfComm) {
        this.kfComm = kfComm;
    }

    public Double getCsComm() {
        return csComm;
    }

    public void setCsComm(Double csComm) {
        this.csComm = csComm;
    }

    public int getCommNum() {
        return commNum;
    }

    public void setCommNum(int commNum) {
        this.commNum = commNum;
    }

    public AppUserLoanPlat getLoanPlat() {
        return loanPlat;
    }

    public void setLoanPlat(AppUserLoanPlat loanPlat) {
        this.loanPlat = loanPlat;
    }

    public NetLoan getNet() {
        return net;
    }

    public void setNet(NetLoan net) {
        this.net = net;
    }

    public String getUsePlatStr() {
        if (this.getUsePlat() == null || this.getUsePlat().isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        this.getUsePlat().stream().forEach(plat -> {
            sb.append(plat).append(",");
        });
        return sb.toString();
    }

    public int getUsePlatNum() {
        if (this.getUsePlat() == null || this.getUsePlat().isEmpty()) {
            return 0;
        }
        return this.getUsePlat().size();
    }

    public List<String> getUsePlat() {
        return usePlat;
    }

    public void setUsePlat(List<String> usePlat) {
        this.usePlat = usePlat;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getCollStatus() {
        return collStatus;
    }

    public void setCollStatus(String collStatus) {
        this.collStatus = collStatus;
    }

    public String getBackLoanStatus() {
        return backLoanStatus;
    }

    public void setBackLoanStatus(String backLoanStatus) {
        this.backLoanStatus = backLoanStatus;
    }

    public String getCollId() {
        return collId;
    }

    public void setCollId(String collId) {
        this.collId = collId;
    }

    public User getOperUser() {
        return operUser;
    }

    public void setOperUser(User operUser) {
        this.operUser = operUser;
    }
    public String getBackLoanStatusStr() {
        if (StringUtils.isBlank(this.getBackLoanStatus())) {
            return "";
        }
        switch (this.getBackLoanStatus()) {
            case "0" : return "未结清";
            case "1" : return "已结清";
            default: return "未知";
        }
    }

    public String getCollStatusStr() {
        if (StringUtils.isBlank(this.getCollStatus())) {
            return "";
        }
        //0无需催收1需要催收2催收完成',
        switch (this.getCollStatus()) {
            case "0" : return "无需催收";
            case "1" : return "需要催收";
            case "2" : return "催收完成";
            default: return "未知";
        }
    }
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

    public Double getCountAmount() {
        return countAmount;
    }

    public void setCountAmount(Double countAmount) {
        this.countAmount = countAmount;
    }

    public Double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Double brokerage) {
        this.brokerage = brokerage;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public double getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(double applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
