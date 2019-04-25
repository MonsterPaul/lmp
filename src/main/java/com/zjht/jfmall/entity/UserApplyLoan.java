package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "user_apply_loan")
public class UserApplyLoan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column(name = "app_user_id")
    private String appUserId;
    @Column(name = "all_loan_id")
    private String allLoanId;
    @Column(name = "create_time")
    private Date   createTime;

    @Transient
    private AppUser user;
    @Transient
    private AllLoan loan;

    public AllLoan getLoan() {
        return loan;
    }

    public void setLoan(AllLoan loan) {
        this.loan = loan;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
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

    public String getAllLoanId() {
        return allLoanId;
    }

    public void setAllLoanId(String allLoanId) {
        this.allLoanId = allLoanId;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}