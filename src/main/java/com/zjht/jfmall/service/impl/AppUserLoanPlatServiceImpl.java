package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.dao.AppUserLoanPlatMapper;
import com.zjht.jfmall.entity.AppUserLoanPlat;
import com.zjht.jfmall.entity.ApplyRecord;
import com.zjht.jfmall.entity.NetLoan;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.AppUserLoanPlatService;
import com.zjht.jfmall.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * app用户贷款平台 服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
public class AppUserLoanPlatServiceImpl implements AppUserLoanPlatService {

    @Autowired
    private AppUserLoanPlatMapper dao;
    @Autowired
    private SessionProvider sessionProvider;

    @Override
    public Double countAmount(AppUserLoanPlat appUserLoanPlat) {
        return dao.countAmount(appUserLoanPlat);
    }

    @Override
    public Double brokerage(AppUserLoanPlat appUserLoanPlat) {
        return dao.brokerage(appUserLoanPlat);
    }

    @Override
    public Double kfComm(AppUserLoanPlat appUserLoanPlat) {
        return dao.kfComm(appUserLoanPlat);
    }

    @Override
    public PageInfo<AppUserLoanPlat> findPage(AppUserLoanPlat bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example          example  = new Example(AppUserLoanPlat.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("appUserId", bean.getAppUserId());
        criteria.andEqualTo("recordId", bean.getRecordId());
        List<AppUserLoanPlat> list = dao.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<AppUserLoanPlat> findPageCS(AppUserLoanPlat bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example          example  = new Example(AppUserLoanPlat.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("appUserId", bean.getAppUserId());
        criteria.andEqualTo("recordId", bean.getRecordId());
        criteria.andEqualTo("collStatus", "1");//只查询需要催收的
        List<AppUserLoanPlat> list = dao.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public Double loanSum(User user) {
        return dao.loanSum(user.getId());
    }

    @Override
    public Double loanSums(User user) {
        return dao.loanSums( user);
    }

    @Override
    public void updateBackLoanStatus(String id, String status, String collStatus) {
        dao.updateBackLoanStatus(id, status, sessionProvider.getUser().getId(), new Date(), collStatus);
}

    @Override
    public void updateCollection(String id, String collectionId) {
        dao.updateCollection(id, collectionId, sessionProvider.getUser().getId(), new Date());
    }

    @Override
    public AppUserLoanPlat findById(String id) {
        return dao.findById(id);
    }

    @Override
    public void updateComm(AppUserLoanPlat bean) {
        dao.updateComm(bean, sessionProvider.getUser().getId(), new Date());
    }


    @Override
    public Double commissionSum(User user) {
        return dao.commissionSum(user.getId());
    }

    @Override
    public Double commissionSums(User user) {
        return dao.commissionSums(user);
    }

    @Override
    public void insert(AppUserLoanPlat bean) {
        dao.insert(bean);
    }

    @Override
    public void update(AppUserLoanPlat bean) {
        dao.updateByPrimaryKeySelective(bean);
    }

    @Override
    public Integer userNum(User user) {
        return dao.userNum(user);
    }

    @Override
    public Double commissionSumCollection(User user) {
        return dao.commissionSumCollection(user.getId());
    }

    @Override
    public Double commissionSumCollections(User user) {
        if(StringUtils.isEmpty(user.getExchangeTimeBegin())){
            user.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }
        return dao.commissionSumCollections(user.getId(),user.getExchangeTimeBegin());
    }

    @Override
    public Double sumUseComm(String startTime, String endTime) {
        return dao.sumUseComm(startTime, endTime);
    }

    @Override
    public Double sumStopComm(String startTime, String endTime) {
        return dao.sumStopComm(startTime, endTime);
    }

    @Override
    public Double sumNotAmount(String startTime, String endTime) {
        return dao.sumNotAmount(startTime, endTime);
    }

    @Override
    public List<String> findUsePlat(ApplyRecord apply) {
        return dao.findUsePlat(apply);
    }

    @Override
    public List<String> findApplyRecord(ApplyRecord record) {
        return dao.findApplyRecord(record);
    }

    @Override
    public int count(String id) {
        return dao.count(id);
    }

    @Override
    public Double sumAll(String id) {
        return dao.sumAll(id);
    }

    @Override
    public Double sumUseCommByUserId(String id) {
        return dao.sumUseCommByUserId(id);
    }

    @Override
    public Double sumStopCommByUserId(String id) {
        return dao.sumStopCommByUserId(id);
    }

    @Override
    public Double sumUseCommByPlatId(String id) {
        return dao.sumUseCommByPlatId(id);
    }

    @Override
    public Double sumUseCommByPlatIds(NetLoan netLoan) {
        return dao.sumUseCommByPlatIds(netLoan);
    }

    @Override
    public Double sumStopCommByPlatId(String id) {
        return dao.sumStopCommByPlatId(id);
    }

    @Override
    public Double sumStopCommByPlatIds(NetLoan netLoan) {
        return dao.sumStopCommByPlatIds(netLoan);
    }

    @Override
    public Double sumUseCommByRecordId(String id) {
        return dao.sumUseCommByRecordId(id);
    }

    @Override
    public Double sumStopCommByRecordId(String id) {
        return dao.sumStopCommByRecordId(id);
    }

    @Override
    public int countSuccess(String id) {
        return dao.countSuccess(id).size();
    }

    @Override
    public int countSuccesss(User user) {
        return dao.countSuccesss(user).size();
    }

    @Override
    public Double sumApplyComm(User user) {
        return dao.sumApplyComm(user);
    }

    @Override
    public Double sumUseAmountByCollId(String collId) {
        return dao.sumUseAmountByCollId(collId);
    }

    @Override
    public Double sumApplyCommByCollId(String collId) {
        return dao.sumApplyCommByCollId(collId);
    }

    @Override
    public int getSqpts(User user) {
        return dao.getSqpts( user);
    }
}