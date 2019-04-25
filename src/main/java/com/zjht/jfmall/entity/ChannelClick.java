package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 * 渠道商邀请链接点击表
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "t_channel_click")
public class ChannelClick {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 渠道商ID
     */
    @Column(name = "channel_business_id")
    private String channelBusinessId;
    /**
     * 点击ip地址
     */
    @Column(name = "click_ip")
    private String clickIp;
    /**
     * 点击mac地址
     */
    @Column(name = "click_mac")
    private String clickMac;
    /**
     * 点击时间
     */
    @Column(name = "click_time")
    private Date clickTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelBusinessId() {
        return channelBusinessId;
    }

    public void setChannelBusinessId(String channelBusinessId) {
        this.channelBusinessId = channelBusinessId;
    }

    public String getClickIp() {
        return clickIp;
    }

    public void setClickIp(String clickIp) {
        this.clickIp = clickIp;
    }

    public String getClickMac() {
        return clickMac;
    }

    public void setClickMac(String clickMac) {
        this.clickMac = clickMac;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

}
