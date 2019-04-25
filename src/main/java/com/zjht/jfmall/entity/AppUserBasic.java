package com.zjht.jfmall.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "app_user_basic")
public class AppUserBasic implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column(name = "app_user_id")
    private String appUserId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号码
     */
    @Column(name = "id_card")
    @JsonProperty(value = "id_card")
    private String idCard;
    /**
     * 性别
     */
    private String gender;
    /**
     * 年龄
     */
    private String age;
    /**
     * 星座
     */
    private String constellation;
    /**
     * 所属省
     */
    private String province;
    /**
     * 所属市
     */
    private String city;
    /**
     * 所属县
     */
    private String region;
    /**
     * 籍贯
     */
    @Column(name = "native_place")
    private String nativePlace;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户姓名
     */
    @JsonProperty(value ="carrier_name")
    private String carrierName;
    /**
     * 用户身份证号码
     */
    @JsonProperty(value ="carrier_idcard")
    private String carrierIdcard;
    /**
     * 开户时间
     */
    @JsonProperty(value ="reg_time")
    private String regTime;
    /**
     * 开户时长
     */
    @JsonProperty(value ="in_time")
    private String inTime;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 地址
     */
    private String address;
    /**
     * 是否实名
     */
    private String reliability;
    /**
     * 用户手机号码归属地
     */
    @Column(name = "phone_attribution")
    @JsonProperty(value ="phone_attribution")
    private String phoneAttribution;
    /**
     * 居住地址
     */
    @Column(name = "live_address")
    @JsonProperty(value ="live_address")
    private String liveAddress;
    /**
     * 余额
     */
    @Column(name = "available_balance")
    @JsonProperty(value ="available_balance")
    private String availableBalance;
    /**
     * 套餐
     */
    @Column(name = "package_name")
    @JsonProperty(value ="package_name")
    private String packageName;
    /**
     * 账单认证日期
     */
    @Column(name = "bill_certification_day")
    @JsonProperty(value ="bill_certification_day")
    private String billCertificationDay;

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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCarrierIdcard() {
        return carrierIdcard;
    }

    public void setCarrierIdcard(String carrierIdcard) {
        this.carrierIdcard = carrierIdcard;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    public String getPhoneAttribution() {
        return phoneAttribution;
    }

    public void setPhoneAttribution(String phoneAttribution) {
        this.phoneAttribution = phoneAttribution;
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getBillCertificationDay() {
        return billCertificationDay;
    }

    public void setBillCertificationDay(String billCertificationDay) {
        this.billCertificationDay = billCertificationDay;
    }
}