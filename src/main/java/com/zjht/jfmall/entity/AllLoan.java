package com.zjht.jfmall.entity;//

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;//

/**
 * 贷款大全
 * Created by vip on 2019/1/15.
 */
public class AllLoan {
    @Id
    private String id;//

    private String name;//平台名称

    private String logo;//logo

    private int type;//类型：1芝麻分贷,2小额贷,3大额贷,4信用贷,5信用卡贷,6公积金贷,7抵押贷

    private String introduction;//介绍

    private int status;//状态0上架，1下架

    private int amount;//额度：1:1000以下,2：1000-3000,3：3000-1万:4：1万-5万:5：5万以上

    private int deadline;//期限：1：7-14天:2：14-30天:3：30天以上

    private String passingRate;//通过率

    private String yearRate;//年利率

    private String raiders;//下款攻略

    private String applyCondition;//申请条件

    private String description;//特别说明

    private int applyNum;//已申请人数

    private String registLink;//注册链接

    private int top;//是否置顶 0置顶，1不置顶

    private String pillWay;//计费方式

    private Double price;//价格

    private String settlementWay;//结算方式

    private String person;//联系人

    private String phone;//联系方式

    private Date createTime;//创建时间

    private String createId;//创建人

    private String operationId;//最后操作人

    private Date operationTime;//最后操作时间

    private Date topTime;//置顶时候排序

    private Double balance;//余额

    private String furtherId;//运营人员ID

    @Transient
    private User user;//存储运营人员信息
    @Transient
    private int uv;//点击率
    @Transient
    private int countNum;//申请人数
    @Transient
    private double conversionRate;//转化率

    //1芝麻分贷,2小额贷,3大额贷,4信用贷,5信用卡贷,6公积金贷,7抵押贷
    public String getTypeStr() {
        switch (this.getType()) {
            case 1:
                return "芝麻分贷";
            case 2:
                return "小额贷";
            case 3:
                return "大额贷";
            case 4:
                return "信用贷";
            case 5:
                return "信用卡贷";
            case 6:
                return "公积金贷";
            case 7:
                return "抵押贷";
            default:
                return "未知";
        }
    }

    public String getDeadlineStr() {
        switch (this.getDeadline()) {
            case 1:
                return "7-14天";
            case 2:
                return "14-30天";
            case 3:
                return "30天以上";
            default:
                return "未知";
        }
    }

    public String getAmountStr() {
        switch (this.getAmount()) {
            case 1:
                return "1000以下";
            case 2:
                return "1000-3000";
            case 3:
                return "3000-1万";
            case 4:
                return "1万-5万";
            case 5:
                return "5万以上";
            default:
                return "未知";
        }
    }
    public String getTopStr() {
        switch (this.getTop()) {
            case 0:
                return "置顶";
            case 1:
                return "不置顶";
            default:
                return "未知";
        }
    }
    public String getStatusStr() {
        switch (this.getStatus()) {
            case 0:
                return "上架";
            case 1:
                return "下架";
            default:
                return "未知";
        }
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFurtherId() {
        return furtherId;
    }

    public void setFurtherId(String furtherId) {
        this.furtherId = furtherId;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public String getPassingRate() {
        return passingRate;
    }

    public void setPassingRate(String passingRate) {
        this.passingRate = passingRate;
    }

    public String getYearRate() {
        return yearRate;
    }

    public void setYearRate(String yearRate) {
        this.yearRate = yearRate;
    }

    public String getRaiders() {
        return raiders;
    }

    public void setRaiders(String raiders) {
        this.raiders = raiders;
    }

    public String getApplyCondition() {
        return applyCondition;
    }

    public void setApplyCondition(String applyCondition) {
        this.applyCondition = applyCondition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(int applyNum) {
        this.applyNum = applyNum;
    }

    public String getRegistLink() {
        return registLink;
    }

    public void setRegistLink(String registLink) {
        this.registLink = registLink;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getPillWay() {
        return pillWay;
    }

    public void setPillWay(String pillWay) {
        this.pillWay = pillWay;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSettlementWay() {
        return settlementWay;
    }

    public void setSettlementWay(String settlementWay) {
        this.settlementWay = settlementWay;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Date getTopTime() {
        return topTime;
    }

    public void setTopTime(Date topTime) {
        this.topTime = topTime;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
