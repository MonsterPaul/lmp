package com.zjht.jfmall.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>
 * APP用户奖励表
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Table(name = "t_user_reward")
public class UserReward {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    private String id;
    /**
     * 奖励类型1邀请奖励
     */
    private String type;
    /**
     * 奖励金额
     */
    private String money;
    /**
     * 操作人ID
     */
    @Column(name = "operation_id")
    private String operationId;
    /**
     * 操作时间
     */
    @Column(name = "operation_time")
    private Date operationTime;
    /**
     * 贷款关联ID
     */
    @Column(name = "record_id")
    private String recordId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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
