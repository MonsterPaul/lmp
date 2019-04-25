package com.zjht.jfmall.service;

import java.util.List;

/**
 * Created by vip on 2017/9/7.
 */
public interface UserRoleService {

    /**
     * 根据用户Id查询角色
     * @param userId
     * @return
     */
    List<String> listRoleIdByUserId(String userId);

}
