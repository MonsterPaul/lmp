package com.zjht.jfmall.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class BaseUser implements Serializable {

    private static final long serialVersionUID = 3764508436244930981L;
    /**
     * 用户id
     */
    @Id
    private String id;

    /**
     * 昵称，渠道名称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 用户密码密文形式
     */
    private String password;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户姓名，渠道登录账号
     */
    private String username;

    /**
     * 用户类型1-后台管理用户,2-前台用户
     */
    private Integer type;

    /**
     * 用户电子邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 渠道ID
     */
    @Column(name = "channel_id")
    private String channelId;
    /**
     * 用户积分
     */
    private Integer points;
    /**
     * 地区
     */
    private String address;
    /**
     * 微信号
     */
    private String wechat;
    /**
     * 联系人
     */
    @Column(name = "link_man")
    private String linkMan;
    /**
     * 计算方式
     */
    private String way;
    /**
     * 价格
     */
    private String price;
    /**
     * 二维码
     */
    @Column(name = "qr_code")
    private String qrCode;
    /**
     * 渠道链接
     */
    @Column(name = "channel_link")
    private String channelLink;
    /**
     * 渠道专员Id
     */
    @Column(name = "channel_per_id")
    private String channelPerId;

    /**
     * 是否禁用0否1是
     */
    @Column(name = "status")
    private String status;

    /**
     * 上次登录IP
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 登录次数
     */
    @Column(name = "login_count")
    private int loginCount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getChannelLink() {
        return channelLink;
    }

    public void setChannelLink(String channelLink) {
        this.channelLink = channelLink;
    }

    public String getChannelPerId() {
        return channelPerId;
    }

    public void setChannelPerId(String channelPerId) {
        this.channelPerId = channelPerId;
    }

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取昵称
     *
     * @return nick_name - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取用户密码密文形式
     *
     * @return password - 用户密码密文形式
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码密文形式
     *
     * @param password 用户密码密文形式
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户手机号
     *
     * @return mobile - 用户手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置用户手机号
     *
     * @param mobile 用户手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取用户姓名
     *
     * @return username - 用户姓名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户姓名
     *
     * @param username 用户姓名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户类型1-后台管理用户
     *
     * @return type - 用户类型1-后台管理用户
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置用户类型1-后台管理用户
     *
     * @param type 用户类型1-后台管理用户
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取用户电子邮箱
     *
     * @return email - 用户电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置用户电子邮箱
     *
     * @param email 用户电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取渠道ID
     * @return
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 设置渠道ID
     * @param channelId
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
