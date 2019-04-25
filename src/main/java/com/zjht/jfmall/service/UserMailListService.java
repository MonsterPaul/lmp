package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.UserMailList;

import java.util.List;

/**
 * <p>
 * app用户通讯录  服务类
 */
public interface UserMailListService {
    /**
     * 通讯录上传
     *
     * @param userId           用户ID
     * @param userMailListList 通讯录信息
     * @return
     */
    int insert(String userId, List<UserMailList> userMailListList);

    /**
     * 分页查询
     * @param bean
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UserMailList> findPage(AppUser bean, int pageNum, int pageSize);
}
