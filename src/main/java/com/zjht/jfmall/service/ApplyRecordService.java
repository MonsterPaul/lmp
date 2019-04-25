package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.ApplyRecord;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 申请贷款记录表  服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
public interface ApplyRecordService {

    /**
     * 新增用户申请贷款记录
     *
     * @param appUser 用户信息
     * @param loans   贷款金额
     * @return
     */
    ApplyRecord insert(AppUser appUser, double loans);

    /**
     * 获取用户贷款记录
     *
     * @param userId   用户ID
     * @param pageNum  页数
     * @param pageSize 条数
     * @return
     */
    List<ApplyRecord> getUserLoansList(String userId, Integer pageNum, Integer pageSize);

    PageInfo<ApplyRecord> findPage(ApplyRecord bean, int pageNum, int pageSize);

    ApplyRecord find(ApplyRecord bean);

    void updateOper(String recordId, String id, Date date);

    PageInfo<ApplyRecord> findCollectionPage(ApplyRecord bean, int pageNum, int pageSize);
}
