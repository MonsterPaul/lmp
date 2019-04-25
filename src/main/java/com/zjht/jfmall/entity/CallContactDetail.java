package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "call_contact_detail")
public class CallContactDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    /**
     * 联系人姓名
     */
    private String name;
    /**
     * app用户ID
     */
    @Column(name = "app_user_id")
    private String appUserId;
    /**
     * 联系人号码
     */
    @Column(name = "peer_num")
    private String peerNum;
    /**
     * 号码类型
     */
    @Column(name = "group_name")
    private String groupName;
    /**
     * 号码标识
     */
    @Column(name = "company_name")
    private String companyName;
    /**
     * 通话地
     */
    private String city;
    /**
     * 与联系人关系
     */
    @Column(name = "p_relation")
    private String pRelation;
    /**
     * 近1周（最近7天）通话次数
     */
    @Column(name = "call_cnt_1w")
    private Long   callCnt1w;
    /**
     * 近1月（最近30天）通话次数
     */
    @Column(name = "call_cnt_1m")
    private Long   callCnt1m;
    /**
     * 近3月（最近0-90天）通话次数
     */
    @Column(name = "call_cnt_3m")
    private Long   callCnt3m;
    /**
     * 近6月（最近0-180天）通话次数
     */
    @Column(name = "call_cnt_6m")
    private Long   callCnt6m;
    /**
     * 近3月通话时长
     */
    @Column(name = "call_time_3m")
    private Long   callTime3m;
    /**
     * 近6月通话时长
     */
    @Column(name = "call_time_6m")
    private Long   callTime6m;
    /**
     * 第一次通话时间
     */
    @Column(name = "trans_start")
    private String transStart;
    /**
     * 最后一次通话时间m
     */
    @Column(name = "trans_end")
    private String transEnd;

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

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getPeerNum() {
        return peerNum;
    }

    public void setPeerNum(String peerNum) {
        this.peerNum = peerNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getpRelation() {
        return pRelation;
    }

    public void setpRelation(String pRelation) {
        this.pRelation = pRelation;
    }

    public Long getCallCnt1w() {
        return callCnt1w;
    }

    public void setCallCnt1w(Long callCnt1w) {
        this.callCnt1w = callCnt1w;
    }

    public Long getCallCnt1m() {
        return callCnt1m;
    }

    public void setCallCnt1m(Long callCnt1m) {
        this.callCnt1m = callCnt1m;
    }

    public Long getCallCnt3m() {
        return callCnt3m;
    }

    public void setCallCnt3m(Long callCnt3m) {
        this.callCnt3m = callCnt3m;
    }

    public Long getCallCnt6m() {
        return callCnt6m;
    }

    public void setCallCnt6m(Long callCnt6m) {
        this.callCnt6m = callCnt6m;
    }

    public Long getCallTime3m() {
        return callTime3m;
    }

    public void setCallTime3m(Long callTime3m) {
        this.callTime3m = callTime3m;
    }

    public Long getCallTime6m() {
        return callTime6m;
    }

    public void setCallTime6m(Long callTime6m) {
        this.callTime6m = callTime6m;
    }

    public String getTransStart() {
        return transStart;
    }

    public void setTransStart(String transStart) {
        this.transStart = transStart;
    }

    public String getTransEnd() {
        return transEnd;
    }

    public void setTransEnd(String transEnd) {
        this.transEnd = transEnd;
    }
}