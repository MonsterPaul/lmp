package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "all_loan_chick")
public class AllLoanChick implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column(name = "app_user_id")
    private String appUserId;
    @Column(name = "all_loan_id")
    private String allLoanId;
    private String ip;
    @Column(name = "create_time")
    private Date   createTime;


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

    public String getAllLoanId() {
        return allLoanId;
    }

    public void setAllLoanId(String allLoanId) {
        this.allLoanId = allLoanId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}