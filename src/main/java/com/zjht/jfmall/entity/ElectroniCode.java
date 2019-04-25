package com.zjht.jfmall.entity;

import com.zjht.jfmall.entity.base.BaseElectroniCode;

import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ElectroniCode extends BaseElectroniCode {
    /**
     * 排序语句（例如：id desc）
     */
    protected String orderByClause;

    /**
     * 是否去重
     */
    protected boolean distinct;
    /**
     * 当前用户id
     */
    protected String selfUserId;
    /**
     * 创建者用户名查询
     */
    protected String creatorName;
    /**
     * 发送者用户名查询
     */
    protected String senderName;
    /**
     * 导入者用户名查询
     */
    protected String importorName;
    /**
     * 所属渠道查询
     */
    protected String channelId;

    public ElectroniCode() {
    }

    /**
     * 创建开始时间
     */
    @Transient
    private String createBeginTime;

    /**
     * 创建结束时间
     */
    @Transient
    private String createEndTime;

    /**
     * 兑换开始时间
     */
    @Transient
    private String exchangeBeginTime;

    /**
     * 兑换结束时间
     */
    @Transient
    private String exchangeEndTime;

    /**
     * 发送开始时间
     */
    @Transient
    private String sendBeginTime;

    /**
     * 发送结束时间
     */
    @Transient
    private String sendEndTime;

    /**
     * 页码数
     */
    @Transient
    private Integer pageIndex;

    /**
     * 每页条数
     */
    @Transient
    private Integer pageSize;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getExchangeBeginTime() {
        return exchangeBeginTime;
    }

    public void setExchangeBeginTime(String exchangeBeginTime) {
        this.exchangeBeginTime = exchangeBeginTime;
    }

    public String getExchangeEndTime() {
        return exchangeEndTime;
    }

    public void setExchangeEndTime(String exchangeEndTime) {
        this.exchangeEndTime = exchangeEndTime;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public String getCreateBeginTime() {
        return createBeginTime;
    }

    public void setCreateBeginTime(String createBeginTime) {
        this.createBeginTime = createBeginTime;
    }


    /**
     * 状态
     */
    public static enum Status {
        EXPORTED(1, "已导出"),
        SENDED(2,"已发送"),
        ALREADY_USED(3, "已兑换"),
        ABANDONED(4, "已作废"),
        SEND_FAIL(5, "发送失败");


        private Status(int status, String name) {
            this.status = status;
            this.name = name;
        }

        private String name;
        private int    status;

        public static String getName(int status) {
            for (ElectroniCode.Status c : ElectroniCode.Status.values()) {
                if (c.getStatus() == status) {
                    return c.name;
                }
            }
            return null;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static Map<String, String> StatusMap() {
            Map<String, String> map = new HashMap<>();
            map.put(String.valueOf(Status.EXPORTED.status), Status.EXPORTED.name);
            map.put(String.valueOf(Status.SENDED.status), Status.SENDED.name);
            map.put(String.valueOf(Status.ALREADY_USED.status), Status.ALREADY_USED.name);
            map.put(String.valueOf(Status.ABANDONED.status), Status.ABANDONED.name);
            map.put(String.valueOf(Status.SEND_FAIL.status), Status.SEND_FAIL.name);
            return map;
        }
    }
    public String getStatusStr() {
        return Status.getName(this.getStatus());
    }
    /**
     * 电子码积分
     */
        public static Map<Integer, String> PointsMap() {
            Map<Integer, String> map = new TreeMap<>();
            int point =0;
            for(int i=0;i<60;i++){
                point+=50;
                map.put(Integer.valueOf(point),String.valueOf(point));
            }
            return map;
        }

    public String getSendBeginTime() {
        return sendBeginTime;
    }

    public void setSendBeginTime(String sendBeginTime) {
        this.sendBeginTime = sendBeginTime;
    }

    public String getSendEndTime() {
        return sendEndTime;
    }

    public void setSendEndTime(String sendEndTime) {
        this.sendEndTime = sendEndTime;
    }

    public String getSelfUserId() {
        return selfUserId;
    }

    public void setSelfUserId(String selfUserId) {
        this.selfUserId = selfUserId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getImportorName() {
        return importorName;
    }

    public void setImportorName(String importorName) {
        this.importorName = importorName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}