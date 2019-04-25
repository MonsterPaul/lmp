package com.zjht.jfmall.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * <p>
 * app用户贷款平台
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "app_user_loan_plat")
public class AppUserLoanPlat {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column(name = "app_user_id")
    private String appUserId;
    /**
     * 平台ID 关联网贷大全ID
     */
    @Column(name = "platform_id")
    private String platformId;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 申请放款数额
     */
    @Column(name = "apply_amount")
    private String applyAmount;
    /**
     * 已放款数额
     */
    @Column(name = "use_amount")
    private String useAmount;

    /**
     * 应收佣金(放款数额的百分之十五，配置文件配置)
     */
    @Column(name = "apply_comm")
    private String applyComm;
    /**
     * 客服已收佣金
     */
    @Column(name = "use_comm")
    private String useComm;
    /**
     * 催收已收佣金
     */
    @Column(name = "stop_comm")
    private String stopComm;
    /**
     * 结清状态0未结清1已结清
     */
    @Column(name = "back_loan_status")
    private String backLoanStatus;
    /**
     * 催收状态 0无需催收1需要催收2催收完成
     */
    @Column(name = "coll_status")
    private String collStatus;
    /**
     * 催收人ID （催收角色列表选择）
     */
    @Column(name = "coll_user_id")
    private String collUserId;
    /**
     * 跟进客服ID
     */
    @Column(name = "service_user_id")
    private String serviceUserId;
    /**
     * 转催收时间时间
     */
    @Column(name = "stop_time")
    private Date stopTime;
    /**
     * 结清时间
     */
    @Column(name = "coll_date")
    private Date collDate;
    /**
     * 最后操作人
     */
    @Column(name = "opertion_id")
    private String opertionId;
    /**
     * 最后操作时间 （保存不展示）
     */
    @Column(name = "opertion_date")
    private Date opertionDate;
    /**
     * 贷款关联ID
     */
    @Column(name = "record_id")
    private String recordId;

    @Transient
    private NetLoan netLoan;

    @Column(name = "create_time")
    private Date createTime;

    @Transient
    private User user;
    @Transient
    private String collUser;


    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getCollUser() {
        return collUser;
    }

    public void setCollUser(String collUser) {
        this.collUser = collUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getNotCount() {
        double comm = 0D;
        if (StringUtils.isNotBlank(this.getApplyComm())) {
            comm = Double.valueOf(this.getApplyComm()).doubleValue();
        } else {
            return 0D;
        }

        double useComm = 0D;
        if (StringUtils.isNotBlank(this.getUseComm())) {
            useComm = Double.valueOf(this.getUseComm()).doubleValue();
        }
        double stopComm = 0D;
        if (StringUtils.isNotBlank(this.getStopComm())) {
            stopComm = Double.valueOf(this.getStopComm()).doubleValue();
        }

        return comm - (useComm);
    }

    public double getCount() {
        double useComm = 0.00;
        if (StringUtils.isNotBlank(this.getUseComm())) {
            useComm = Double.valueOf(this.getUseComm()).doubleValue();
        }
        double stopComm = 0.00;
        if (StringUtils.isNotBlank(this.getStopComm())) {
            stopComm = Double.valueOf(this.getStopComm()).doubleValue();
        }
        return useComm + stopComm;
    }

    public String getBackLoanStatusStr() {
        switch (this.getBackLoanStatus()) {
            case "0" : return "未结清";
            case "1" : return "已结清";
            default: return "未知";
        }
    }

    public String getCollStatusStr() {
        //0无需催收1需要催收2催收完成',
        switch (this.getCollStatus()) {
            case "0" : return "无需催收";
            case "1" : return "跑单";
            case "2" : return "催收完成";
            case "3" : return "已申请";
            case "4" : return "拒绝";
            case "5" : return "客户放弃";
            default: return "未知";
        }
    }


    public NetLoan getNetLoan() {
        return netLoan;
    }

    public void setNetLoan(NetLoan netLoan) {
        this.netLoan = netLoan;
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

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getUseAmount() {
        return useAmount;
    }

    public void setUseAmount(String useAmount) {
        this.useAmount = useAmount;
    }

    public String getApplyComm() {
        return applyComm;
    }

    public void setApplyComm(String applyComm) {
        this.applyComm = applyComm;
    }

    public String getUseComm() {
        return useComm;
    }

    public void setUseComm(String useComm) {
        this.useComm = useComm;
    }

    public String getStopComm() {
        return stopComm;
    }

    public void setStopComm(String stopComm) {
        this.stopComm = stopComm;
    }

    public String getBackLoanStatus() {
        return backLoanStatus;
    }

    public void setBackLoanStatus(String backLoanStatus) {
        this.backLoanStatus = backLoanStatus;
    }

    public String getCollStatus() {
        return collStatus;
    }

    public void setCollStatus(String collStatus) {
        this.collStatus = collStatus;
    }

    public String getCollUserId() {
        return collUserId;
    }

    public void setCollUserId(String collUserId) {
        this.collUserId = collUserId;
    }

    public String getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(String serviceUserId) {
        this.serviceUserId = serviceUserId;
    }

    public Date getCollDate() {
        return collDate;
    }

    public void setCollDate(Date collDate) {
        this.collDate = collDate;
    }

    public String getOpertionId() {
        return opertionId;
    }

    public void setOpertionId(String opertionId) {
        this.opertionId = opertionId;
    }

    public Date getOpertionDate() {
        return opertionDate;
    }

    public void setOpertionDate(Date opertionDate) {
        this.opertionDate = opertionDate;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
