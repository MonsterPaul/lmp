package com.zjht.jfmall.entity;

import com.zjht.jfmall.entity.base.BaseAdvertisement;

import javax.persistence.Transient;

/**
 * Created by vip on 2018/11/29.
 */
public class Advertisement extends BaseAdvertisement {

    @Transient
    private User user;

    public String getStatusStr() {
        switch (this.getStatus()) {
            case 1 :
                return Status.USE.getText();
            case 2 :
                return Status.STOP.getText();
            default :
                return "未知";
        }
    }

    public enum Status {

        USE(1, "上架"), STOP(2, "下架");

        private Status(int status, String text) {
            this.status = status;
            this.text = text;
        };

        private int status;//状态值

        private String text;//状态对应的中文

        public String getText() {
            return text;
        }

        public int getStatus() {
            return status;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
