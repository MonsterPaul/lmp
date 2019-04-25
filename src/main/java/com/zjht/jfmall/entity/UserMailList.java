package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 * app用户通讯录 
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "t_user_mail_list")
public class UserMailList {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * app用户ID
     */
    @Column(name = "app_user_id")
    private String appUserId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 备注
     */
    private String remark;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UserMailList() {

    }

    @Override
    public String toString() {
        return "UserMailList{" +
                "id='" + id + '\'' +
                ", appUserId='" + appUserId + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
