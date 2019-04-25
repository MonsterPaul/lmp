package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.AppUserDeposit;

import java.util.List;

/**
 * <p>
 * app用户提现表  服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
public interface AppUserDepositService {
    /**
     * 新增用户提现
     * @param appUser
     * @param money 提现金额
     * @return
     */
    int insert(AppUser appUser,double money);

    /**
     * 获取用户提现信息表
     * @param id
     * @return
     */
    List<AppUserDeposit> getCashWithdrawal(String id,Integer pageNum,Integer pageSize);

    /**
     * 分页查询
     * @param bean
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<AppUserDeposit> getPage(AppUserDeposit bean, int pageNum, int pageSize);

    /**
     * 修改申请记录状态
     * @param id
     * @param status
     */
    void updateStatus(String id, String status);

    int countUser(String appUserId);

    Double sumAmount(String appUserId);

    List<AppUserDeposit> find(AppUserDeposit bean, int pageNum, int pageSize);
}
