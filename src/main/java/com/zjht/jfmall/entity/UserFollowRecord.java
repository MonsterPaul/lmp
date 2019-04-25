package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * <p>
 * APP用户跟进记录
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "user_follow_record")
public class UserFollowRecord {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column(name = "app_user_id")
    private String appUserId;
    /**
     * 跟进时间
     */
    @Column(name = "follow_date")
    private Date followDate;
    /**
     * 跟进内容
     */
    @Column(name = "follow_content")
    private String followContent;
    /**
     * 跟进人ID
     */
    @Column(name = "follow_user_id")
    private String followUserId;

    /**
     * 贷款关联ID
     */
    @Column(name = "record_id")
    private String recordId;

    /**
     * 贷款平台Id
     */
    @Column(name = "plat_id")
    private String platId;

    @Transient
    private User user;

    @Transient
    private int type;

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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

    public Date getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }

    public String getFollowContent() {
        return followContent;
    }

    public void setFollowContent(String followContent) {
        this.followContent = followContent;
    }

    public String getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }

}
