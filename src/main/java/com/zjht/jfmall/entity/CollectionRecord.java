package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 * 催收记录表
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "t_collection_record")
public class CollectionRecord {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    private String id;
    /**
     * 催收员ID
     */
    @Column(name = "collection_id")
    private String collectionId;
    /**
     * 客服ID
     */
    @Column(name = "customer_id")
    private String customerId;
    /**
     * APP用户ID
     */
    @Column(name = "app_user_id")
    private String appUserId;
    /**
     * app用户贷款平台ID
     */
    @Column(name = "platform_id")
    private String platformId;
    /**
     * 催收金额
     */
    @Column(name = "collection_money")
    private String collectionMoney;
    /**
     * 操作人
     */
    @Column(name = "operation_id")
    private String operationId;
    /**
     * 操作时间
     */
    @Column(name = "operation_time")
    private Date operationTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getCollectionMoney() {
        return collectionMoney;
    }

    public void setCollectionMoney(String collectionMoney) {
        this.collectionMoney = collectionMoney;
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

}
