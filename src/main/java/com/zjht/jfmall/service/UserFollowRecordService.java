package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.UserFollowRecord;

/**
 * <p>
 * APP用户跟进记录 服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
public interface UserFollowRecordService {

    void insert(UserFollowRecord bean);

    PageInfo<UserFollowRecord> findPage(UserFollowRecord bean, int pageNum, int pageSize);

    int count(String userId, String recordId, String platId);
}
