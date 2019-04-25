package com.zjht.jfmall.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * <p>
 * 用户意见反馈表
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "t_user_feedback")
public class UserFeedback {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 反馈内容
     */
    @Column(name = "feed_back")
    private String feedBack;
    /**
     * 反馈图片(多张,隔开)
     */
    @Column(name = "feed_image_url")
    private String feedImageUrl;
    /**
     * 处理状态 1处理中2处理完成
     */
    @Column(name = "processing_state")
    private String processingState;
    /**
     * 处理内容
     */
    @Column(name = "process_remark")
    private String processRemark;
    /**
     * 客服ID
     */
    @Column(name = "customer_id")
    private String customerId;
    /**
     * 操作人ID
     */
    @Column(name = "operator_id")
    private String operatorId;
    /**
     * 操作时间
     */
    @Column(name = "operator_time")
    private Date   operatorTime;
    @Column(name = "create_time")
    private Date   createTime;

    @Transient
    private User user;
    @Transient
    private AppUser appUser;
    @Transient
    private String userName;
    @Transient
    private String appUserName;
    @Transient
    private String[] imgs;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public String getProcessingStateStr() {
        if (StringUtils.isBlank(this.getProcessingState())) {
            return "未知";
        }
        switch (this.getProcessingState()) {
            case "1" : return "处理中";
            case "2" : return "处理完成";
            default : return "未知";
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getFeedImageUrl() {
        return feedImageUrl;
    }

    public void setFeedImageUrl(String feedImageUrl) {
        this.feedImageUrl = feedImageUrl;
    }

    public String getProcessingState() {
        return processingState;
    }

    public void setProcessingState(String processingState) {
        this.processingState = processingState;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public String getProcessRemark() {
        return processRemark;
    }

    public void setProcessRemark(String processRemark) {
        this.processRemark = processRemark;
    }
}
