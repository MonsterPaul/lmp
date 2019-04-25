package com.zjht.jfmall.entity.base;

import java.io.Serializable;
import java.util.Date;

public class BaseMobileVerify implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private String code;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * ip地址
     */
    private String ipaddr;

    /**
     * 当天发送次数
     */
    private Integer sendDayCount;

    /**
     * 每小时内发送次数
     */
    private Integer sendHourCount;

    /**
     * t_mobile_verify
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 手机号
     * @return mobile 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 验证码
     * @return code 验证码
     */
    public String getCode() {
        return code;
    }

    /**
     * 验证码
     * @param code 验证码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 内容
     * @return content 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 内容
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 状态
     * @return status 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 发送时间
     * @return sendTime 发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 发送时间
     * @param sendTime 发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * ip地址
     * @return ipAddr ip地址
     */
    public String getIpaddr() {
        return ipaddr;
    }

    /**
     * ip地址
     * @param ipaddr ip地址
     */
    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    /**
     * 当天发送次数
     * @return sendDay 当天发送次数
     */
    public Integer getSendDayCount() {
        return sendDayCount;
    }

    /**
     * 当天发送次数
     * @param sendDayCount 当天发送次数
     */
    public void setSendDayCount(Integer sendDayCount) {
        this.sendDayCount = sendDayCount;
    }

    /**
     * 每小时内发送次数
     * @return sendHour 每小时内发送次数
     */
    public Integer getSendHourCount() {
        return sendHourCount;
    }

    /**
     * 每小时内发送次数
     * @param sendHourCount 每小时内发送次数
     */
    public void setSendHourCount(Integer sendHourCount) {
        this.sendHourCount = sendHourCount;
    }
}