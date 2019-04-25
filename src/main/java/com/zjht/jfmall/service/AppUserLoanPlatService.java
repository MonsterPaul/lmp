package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.AppUserLoanPlat;
import com.zjht.jfmall.entity.ApplyRecord;
import com.zjht.jfmall.entity.NetLoan;
import com.zjht.jfmall.entity.User;

import java.util.List;

/**
 * <p>
 * app用户贷款平台 服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
public interface AppUserLoanPlatService {

    Double countAmount(AppUserLoanPlat appUserLoanPlat);

    Double brokerage(AppUserLoanPlat appUserLoanPlat);
    Double kfComm(AppUserLoanPlat appUserLoanPlat);

    PageInfo<AppUserLoanPlat> findPage(AppUserLoanPlat bean, int pageNum, int pageSize);
    PageInfo<AppUserLoanPlat> findPageCS(AppUserLoanPlat bean, int pageNum, int pageSize);

    Double loanSum(User user);
    Double loanSums(User user);

    Double commissionSum(User user);
    Double commissionSums(User user);


    void updateBackLoanStatus(String id, String status, String collStatus);

    void updateCollection(String id, String collectionId);

    AppUserLoanPlat findById(String id);

    void updateComm(AppUserLoanPlat bean);

    void insert(AppUserLoanPlat bean);

    void update(AppUserLoanPlat bean);

    Integer userNum(User user);

    Double commissionSumCollection(User user);

    Double commissionSumCollections(User user);

    Double sumUseComm(String startTime, String endTime);

    Double sumStopComm(String startTime, String endTime);

    Double sumNotAmount(String startTime, String endTime);

    List<String> findUsePlat(ApplyRecord apply);

    List<String> findApplyRecord(ApplyRecord record);

    int count(String id);

    Double sumAll(String id);


    Double sumUseCommByUserId(String id);

    Double sumStopCommByUserId(String id);

    Double sumUseCommByPlatId(String id);

    Double sumUseCommByPlatIds(NetLoan netLoan);

    Double sumStopCommByPlatId(String id);

    Double sumStopCommByPlatIds(NetLoan netLoan);

    Double sumUseCommByRecordId(String id);

    Double sumStopCommByRecordId(String id);

    int countSuccess(String id);

    int countSuccesss(User user);

    Double sumApplyComm(User user);

    Double sumUseAmountByCollId(String collId);

    Double sumApplyCommByCollId(String collId);

    int getSqpts(User user);
}
