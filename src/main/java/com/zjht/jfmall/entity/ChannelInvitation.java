package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 * 渠道商邀请注册表
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "t_channel_invitation")
public class ChannelInvitation {

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
     * 邀请注册用户ID
     */
    @Column(name = "invitation_user_id")
    private String invitationUserId;
    /**
     * 邀请成功时间
     */
    @Column(name = "invitation_time")
    private Date invitationTime;


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

    public String getInvitationUserId() {
        return invitationUserId;
    }

    public void setInvitationUserId(String invitationUserId) {
        this.invitationUserId = invitationUserId;
    }

    public Date getInvitationTime() {
        return invitationTime;
    }

    public void setInvitationTime(Date invitationTime) {
        this.invitationTime = invitationTime;
    }


}
