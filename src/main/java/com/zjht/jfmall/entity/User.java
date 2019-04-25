package com.zjht.jfmall.entity;

import com.zjht.jfmall.entity.base.BaseUser;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_user")
public class User extends BaseUser {

    private static final long serialVersionUID = -5328049940610750041L;

    @Transient
    private String[] roleIds;//角色Id

    @Transient
    private String channelName;//扩展字段渠道名称

    @Transient
    private int uv;//点击率
    @Transient
    private int register;//注册量
    @Transient
    private double rate;//注册转换率
    @Transient
    private int successCount;//成功贷款用户
    @Transient
    private double rateSuccess;//成功贷款转换率
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
    private Double sunLoan;
    @Transient
    private Double sunCommission;
    @Transient
    private int channelNum;//渠道总数量
    @Transient
    private int registNum;//有效注册总量
    @Transient
    private int amountCount;//放款总数量
    @Transient
    private Integer userNum;//催收人员客户数量
    @Transient
    private int notRegister;//总量
    @Transient
    private String dealStatus;
    @Transient
    private Double applyAmount;//应收佣金
    @Transient
    private int fkpts;
    @Transient
    private String tgyName;
    @Transient
    private String roleName;
    @Transient
    private int client;//分发客户数
    @Transient
    private int distribution;//分发数
    
    public String getTgyName() {
        return tgyName;
    }

    public void setTgyName(String tgyName) {
        this.tgyName = tgyName;
    }

    public int getFkpts() {
        return fkpts;
    }

    public void setFkpts(int fkpts) {
        this.fkpts = fkpts;
    }

    public Double getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(Double applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public int getNotRegister() {
        return notRegister;
    }

    public void setNotRegister(int notRegister) {
        this.notRegister = notRegister;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public int getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }

    public int getRegistNum() {
        return registNum;
    }

    public void setRegistNum(int registNum) {
        this.registNum = registNum;
    }

    public int getAmountCount() {
        return amountCount;
    }

    public void setAmountCount(int amountCount) {
        this.amountCount = amountCount;
    }

    /**
     * 用户类型
     */
    public static enum Type {

        BACK(1,"后台用户"),
        FRONT(2,"前端用户");

        private Type(int type, String name) {
            this.type = type;
            this.name = name;
        }

        private String name;
        private int type;
        public static String getName(int status) {
            for (User.Type c : User.Type.values()) {
                if (c.getType() == status) {
                    return c.name;
                }
            }
            return null;
        }
        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }
    }

    public String getStatusStr() {
        switch (this.getStatus()) {
            case "1" : return "禁用";
            case "0" : return "启用";
            default : return "未知";
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

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public double getRateSuccess() {
        return rateSuccess;
    }

    public void setRateSuccess(double rateSuccess) {
        this.rateSuccess = rateSuccess;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
	}

	public int getDistribution() {
		return distribution;
	}

	public void setDistribution(int distribution) {
		this.distribution = distribution;
	}
    
}