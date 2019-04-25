package com.zjht.jfmall.weChat.request;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-01-07 10:24
 */
public class RedPackageRequest extends CommRequest {
    //商户订单号（每个订单号必须唯一）组成：mch_id+yyyymmdd+10位一天内不能重复的数字。接口根据商户订单号支持重入，如出现超时可再调用。
    private String billno;
    //商户名称
    private String name;
    //用户openid  接受红包的用户用户在wxappid下的openid
    private String openId;
    //付款金额 单位分
    private String amount;
    //红包发放总个数
    private String num;
    //红包祝福语
    private String wishing;
    //活动名称
    private String actName;
    //备注
    private String remark;

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
