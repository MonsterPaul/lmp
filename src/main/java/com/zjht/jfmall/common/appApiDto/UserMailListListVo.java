package com.zjht.jfmall.common.appApiDto;


import com.zjht.jfmall.entity.UserMailList;

import java.util.List;

public class UserMailListListVo {
    private List<UserMailList> userMailListList;

    public List<UserMailList> getUserMailListList() {
        return userMailListList;
    }

    public void setUserMailListList(List<UserMailList> userMailListList) {
        this.userMailListList = userMailListList;
    }
}