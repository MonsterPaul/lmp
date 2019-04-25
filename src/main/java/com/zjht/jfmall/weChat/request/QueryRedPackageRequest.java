package com.zjht.jfmall.weChat.request;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-03-01 9:55
 */
public class QueryRedPackageRequest extends CommRequest {
    //商户订单号（每个订单号必须唯一）组成：mch_id+yyyymmdd+10位一天内不能重复的数字。接口根据商户订单号支持重入，如出现超时可再调用。
    private String mchBillno;

    public String getMchBillno() {
        return mchBillno;
    }

    public void setMchBillno(String mchBillno) {
        this.mchBillno = mchBillno;
    }

    @Override
    public String toString() {
        return "QueryRedPackageRequest{" +
                "mchBillno='" + mchBillno + '\'' +
                ", redpackAccount='" + redpackAccount + '\'' +
                '}';
    }
}
