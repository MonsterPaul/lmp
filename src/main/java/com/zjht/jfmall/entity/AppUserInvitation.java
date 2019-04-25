package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * <p>
 * APP用户邀请记录
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "t_app_user_invitation")
public class AppUserInvitation {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    /**
     * 邀请人ID
     */
    @Column(name = "invitation_id")
    private String invitationId;
    /**
     * 被邀请人ID
     */
    @Column(name = "beinvited_id")
    private String beinvitedId;
    /**
     * 邀请成功时间
     */
    @Column(name = "invitation_time")
    private Date   invitationTime;
    /**
     * 奖励金额
     */
    @Column(name = "invitation_money")
    private int    invitationMoney;

    /**
     * 被邀请人名称
     */
    @Transient
    private String beinvitedName;
    /**
     * 被邀请人手机号
     */
    @Transient
    private String beinvitedMobile;
    /**
     * 被邀请人头像
     */
    @Transient
    private String beinvitedUrl;

    /**
     * 个人资料认证状态
     */
    @Column(name = "data_status")
    private String dataStatus;
    /**
     * 通讯录认证状态
     */
    @Column(name = "iden_status")
    private String idenStatus;
    /**
     * 运营商认证状态
     */
    @Column(name = "operator_status")
    private String operatorStatus;

    public String getBeinvitedUrl() {
        return beinvitedUrl;
    }

    public void setBeinvitedUrl(String beinvitedUrl) {
        this.beinvitedUrl = beinvitedUrl;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getIdenStatus() {
        return idenStatus;
    }

    public void setIdenStatus(String idenStatus) {
        this.idenStatus = idenStatus;
    }

    public String getOperatorStatus() {
        return operatorStatus;
    }

    public void setOperatorStatus(String operatorStatus) {
        this.operatorStatus = operatorStatus;
    }

    public String getBeinvitedName() {
        return beinvitedName;
    }

    public void setBeinvitedName(String beinvitedName) {
        this.beinvitedName = beinvitedName;
    }

    public String getBeinvitedMobile() {
        return beinvitedMobile;
    }

    public void setBeinvitedMobile(String beinvitedMobile) {
        this.beinvitedMobile = beinvitedMobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(String invitationId) {
        this.invitationId = invitationId;
    }

    public String getBeinvitedId() {
        return beinvitedId;
    }

    public void setBeinvitedId(String beinvitedId) {
        this.beinvitedId = beinvitedId;
    }

    public Date getInvitationTime() {
        return invitationTime;
    }

    public void setInvitationTime(Date invitationTime) {
        this.invitationTime = invitationTime;
    }


    public int getInvitationMoney() {
        return invitationMoney;
    }

    public void setInvitationMoney(int invitationMoney) {
        this.invitationMoney = invitationMoney;
    }
}
