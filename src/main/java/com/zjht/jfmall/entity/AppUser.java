package com.zjht.jfmall.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * <p>
 * app用户表
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "app_user")
public class AppUser {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    private String  id;
    private String  name;
    private String  age;
    private String  sex;
    private String  phone;
    private String  address;
    private String  wechat;
    private String  qq;
    /**
     * 身份证号码
     */
    @Column(name = "id_card")
    private String  idCard;
    /**
     * 身份证正面
     */
    @Column(name = "card_z_url")
    private String  cardZUrl;
    /**
     * 身份证反面
     */
    @Column(name = "card_f_url")
    private String  cardFUrl;
    /**
     * 手持身份证照片
     */
    @Column(name = "id_card_url")
    private String  idCardUrl;
    /**
     * 头像图片
     */
    @Column(name = "heard_url")
    private String  heardUrl;
    /**
     * 银行卡号
     */
    @Column(name = "bank_no")
    private String  bankNo;
    /**
     * 银行开户行
     */
    @Column(name = "bank_address")
    private String  bankAddress;
    /**
     * 持卡人
     */
    @Column(name = "bank_person")
    private String  bankPerson;
    /**
     * 银行预留手机号
     */
    @Column(name = "bank_phone")
    private String  bankPhone;
    /**
     * 个人余额
     */
    private Double  balance;
    /**
     * 邀请链接
     */
    @Column(name = "invite_link")
    private String  inviteLink;
    /**
     * 专属客服ID
     */
    @Column(name = "service_id")
    private String  serviceId;
    /**
     * 个人资料认证状态
     */
    @Column(name = "data_status")
    private String  dataStatus;
    /**
     * 通讯录认证状态
     */
    @Column(name = "iden_status")
    private String  idenStatus;
    /**
     * 运营商认证状态
     */
    @Column(name = "operator_status")
    private String  operatorStatus;
    /**
     * 银行卡认证状态
     */
    @Column(name = "bank_status")
    private String  bankStatus;
    /**
     * 贷款额度
     */
    private Integer     loans;
    /**
     * 是否申请贷款
     */
    @Column(name = "loans_status")
    private String  loansStatus;
    /**
     * 密码
     */
    private String  pwd;
    /**
     * 用户状态0正常1冻结
     */
    private String  status;
    /**
     * 注册时间
     */
    @Column(name = "register_time")
    private Date    registerTime;
    @Column(name = "sesame_credit")
    private Integer sesameCredit;
    /**
     * 信用卡额度
     */
    @Column(name = "credit_card")
    private Integer creditCard;
    /**
     * 是否被邀请0否1app用户邀请2渠道商邀请
     */
    @Column(name = "is_invited")
    private Integer isInvited;


        /**
     * 查询兑换开始时间
     */
    @Transient
    private String exchangeTimeBegin;
    /**
     * 查询兑换结束日期
     */
    @Transient
    private String exchangeTimeEnd;

    @Transient
    private Object obj;
    @Transient
    private String invitationMmoney;
    @Transient
    private String invitationName;
    @Transient
    private Double countAmount;
    /**
     * 公积金状态 是或者否
     */
    private String accumulation;

    private String backList;//央行証信黑名單 1是，2不是

    private String isLogin;//是否登录过0否1是

    public String getInvitationMmoney() {
        return invitationMmoney;
    }

    public void setInvitationMmoney(String invitationMmoney) {
        this.invitationMmoney = invitationMmoney;
    }

    @Transient
    private String keyWord;
    @Transient
    private Double sunLoan;
    @Transient
    private Double sunCommission;
    @Transient
    private Double applyCommSum;

    public Double getCountAmount() {
        return countAmount;
    }

    public void setCountAmount(Double countAmount) {
        this.countAmount = countAmount;
    }

    public Double getApplyCommSum() {
        return applyCommSum;
    }

    public void setApplyCommSum(Double applyCommSum) {
        this.applyCommSum = applyCommSum;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getAccumulation() {
        return accumulation;
    }

    public void setAccumulation(String accumulation) {
        this.accumulation = accumulation;
    }

    public String getBackList() {
        return backList;
    }

    public void setBackList(String backList) {
        this.backList = backList;
    }

    public String getInvitationName() {
        return invitationName;
    }

    public void setInvitationName(String invitationName) {
        this.invitationName = invitationName;
    }

    public String getBackListStr() {
        if (StringUtils.isBlank(this.getBackList())) {
            return "";
        }
        switch (this.getBackList()) {
            case "1" : return "是";
            case "2" : return "否";
            default: return "未知";
        }
    }

    public String getHidPhone() {
        if (StringUtils.isBlank(this.getPhone())) {
            return "---";
        }
        return this.getPhone().substring(0, 3) + "****" + this.getPhone().substring(7);
    }

    public String getAccumulationStr() {
        if (StringUtils.isBlank(this.getAccumulation())) {
            return "";
        }
        switch (this.getAccumulation()) {
            case "0" : return "否";
            case "1" : return "是";
            default: return "未知";
        }
    }

    public String getIsInvitedStr() {
        switch (this.getIsInvited()) {
            case 0 : return "自行注册";
            case 1 : return "app用户邀请";
            case 2 : return "渠道商邀请";
            default: return "未知";
        }
    }

    public String getStatusStr() {
        switch (this.getStatus()) {
            case "0" : return "启用";
            case "1" : return "禁用";
            default: return "未知";
        }
    }

    public String getDataStatusStr() {
        switch (this.getDataStatus()) {
            case "0" : return "未认证";
            case "1" : return "已认证";
            default: return "未知";
        }
    }

    public String getIdenStatusStr() {
        switch (this.getIdenStatus()) {
            case "0" : return "未认证";
            case "1" : return "已认证";
            default: return "未知";
        }
    }

    public String getOperatorStatusStr() {
        switch (this.getOperatorStatus()) {
            case "0" : return "未认证";
            case "1" : return "已认证";
            default: return "未知";
        }
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getIsLoginStr() {
        switch (this.getIsLogin()) {
            case "0" : return "没有";
            case "1" : return "有";
            default: return "未知";
        }
    }
    public String getExchangeTimeBegin() {
        return exchangeTimeBegin;
    }

    public void setExchangeTimeBegin(String exchangeTimeBegin) {
        this.exchangeTimeBegin = exchangeTimeBegin;
    }

    public String getExchangeTimeEnd() {
        return exchangeTimeEnd;
    }

    public void setExchangeTimeEnd(String exchangeTimeEnd) {
        this.exchangeTimeEnd = exchangeTimeEnd;
    }

    public Integer getSesameCredit() {
        return sesameCredit;
    }

    public void setSesameCredit(Integer sesameCredit) {
        this.sesameCredit = sesameCredit;
    }

    public Integer getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Integer creditCard) {
        this.creditCard = creditCard;
    }

    public Integer getIsInvited() {
        return isInvited;
    }

    public void setIsInvited(Integer isInvited) {
        this.isInvited = isInvited;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCardZUrl() {
        return cardZUrl;
    }

    public void setCardZUrl(String cardZUrl) {
        this.cardZUrl = cardZUrl;
    }

    public String getCardFUrl() {
        return cardFUrl;
    }

    public void setCardFUrl(String cardFUrl) {
        this.cardFUrl = cardFUrl;
    }

    public String getIdCardUrl() {
        return idCardUrl;
    }

    public void setIdCardUrl(String idCardUrl) {
        this.idCardUrl = idCardUrl;
    }

    public String getHeardUrl() {
        return heardUrl;
    }

    public void setHeardUrl(String heardUrl) {
        this.heardUrl = heardUrl;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankPerson() {
        return bankPerson;
    }

    public void setBankPerson(String bankPerson) {
        this.bankPerson = bankPerson;
    }

    public String getBankPhone() {
        return bankPhone;
    }

    public void setBankPhone(String bankPhone) {
        this.bankPhone = bankPhone;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getInviteLink() {
        return inviteLink;
    }

    public void setInviteLink(String inviteLink) {
        this.inviteLink = inviteLink;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public String getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus) {
        this.bankStatus = bankStatus;
    }


    public Integer getLoans() {
        return loans;
    }

    public void setLoans(Integer loans) {
        this.loans = loans;
    }

    public String getLoansStatus() {
        return loansStatus;
    }

    public void setLoansStatus(String loansStatus) {
        this.loansStatus = loansStatus;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Double getSunLoan() {
        return sunLoan;
    }

    public void setSunLoan(Double sunLoan) {
        this.sunLoan = sunLoan;
    }

    public Double getSunCommission() {
        return sunCommission;
    }

    public void setSunCommission(Double sunCommission) {
        this.sunCommission = sunCommission;
    }
}
