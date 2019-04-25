package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.AppUserLoanPlat;
import com.zjht.jfmall.entity.ApplyRecord;
import com.zjht.jfmall.entity.NetLoan;
import com.zjht.jfmall.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * app用户贷款平台 Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface AppUserLoanPlatMapper extends Mapper<AppUserLoanPlat> {

    Double countAmount(@Param("bean") AppUserLoanPlat appUserLoanPlat);

    Double brokerage(@Param("bean") AppUserLoanPlat appUserLoanPlat);
    Double kfComm(@Param("bean") AppUserLoanPlat appUserLoanPlat);

    Double loanSum(String serviceUserId);

    Double loanSums(@Param("bean") User user);

    Double commissionSums(@Param("user")User user);

    void updateBackLoanStatus(@Param("id") String id, @Param("status") String status, @Param("operId") String operId, @Param("operDate") Date operDate, @Param("collStatus") String collStatus);

    void updateCollection(@Param("id") String id, @Param("collectionId") String collectionId, @Param("operId") String operId, @Param("operDate") Date operDate);

    AppUserLoanPlat findById(@Param("id") String id);

    void updateComm(@Param("bean") AppUserLoanPlat bean, @Param("operId") String operId, @Param("operDate") Date operDate);

    Double commissionSum(String serviceUserId);

    Integer userNum(@Param("bean") User user);

    Double commissionSumCollection(@Param("id") String id);

    Double sumUseComm(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Double sumStopComm(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Double sumNotAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<String> findUsePlat(@Param("apply") ApplyRecord apply);

    List<String> findApplyRecord(@Param("apply") ApplyRecord record);

    int count(@Param("userId") String id);

    Double sumAll(@Param("userId") String id);

    Double sumUseCommByUserId(@Param("userId") String id);

    Double sumStopCommByUserId(@Param("userId") String id);

    Double sumUseCommByPlatId(@Param("platId") String id);

    Double sumUseCommByPlatIds(@Param("bean") NetLoan netLoan);

    Double sumStopCommByPlatId(@Param("platId") String id);

    Double sumStopCommByPlatIds(@Param("bean") NetLoan netLoan);

    Double sumUseCommByRecordId(@Param("recordId") String id);

    Double sumStopCommByRecordId(@Param("recordId") String id);

    List<String> countSuccess(@Param("userId") String id);

    List<String> countSuccesss(@Param("user") User user);

    Double commissionSumCollections(@Param("id") String id, @Param("beginTime") String beginTime);

    Double sumApplyComm(@Param("user") User user);

    Double sumUseAmountByCollId(@Param("collId") String collId);

    Double sumApplyCommByCollId(@Param("collId") String collId);

    int getSqpts(@Param("bean") User user);
}
