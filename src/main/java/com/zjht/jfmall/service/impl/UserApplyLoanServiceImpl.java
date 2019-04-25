package com.zjht.jfmall.service.impl;

import com.zjht.jfmall.common.dto.JsonResult;
import com.zjht.jfmall.dao.AllLoanDao;
import com.zjht.jfmall.dao.UserApplyLoanDao;
import com.zjht.jfmall.entity.AllLoan;
import com.zjht.jfmall.entity.AllLoanChick;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.UserApplyLoan;
import com.zjht.jfmall.enums.BusiStatus;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.dao.UserApplyLoanDao;
import com.zjht.jfmall.entity.UserApplyLoan;
import com.zjht.jfmall.entity.param.LayuiAllLoanParam;
import com.zjht.jfmall.service.UserApplyLoanService;
import com.zjht.jfmall.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class UserApplyLoanServiceImpl implements UserApplyLoanService {
    @Autowired
    private UserApplyLoanDao userApplyLoanDao;
    @Autowired
    private AllLoanDao allLoanDao;



    /**
     * 根据平台ID统计申请数量
     *
     * @param allLoanId
     * @return
     */
    @Override
    public int countByAllLoanId(String allLoanId) {
        Example example = new Example(UserApplyLoan.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("allLoanId", allLoanId);
        return userApplyLoanDao.selectCountByExample(example);
    }

    /**
     * 查询
     *
     * @param param
     * @return
     */
    @Override
    public PageInfo<UserApplyLoan> findPage(LayuiAllLoanParam param) {
        return new PageInfo<>(userApplyLoanDao.findPage(param));
    }

    /**
     * 根据用户id获取用户申请的平台信息
     *
     * @param appUserId
     * @return
     */
    @Override
    public PageInfo<UserApplyLoan> list(String appUserId) {
        return new PageInfo<>(userApplyLoanDao.list(appUserId));
    }
    @Override
    public JsonResult saveUserApplyLoan(String id, String loanId) {
        if(StringUtils.isBlank(loanId)){
            return  new JsonResult(BusiStatus.PARAM_ERROR.toString(),BusiStatus.PARAM_ERROR.getReasonPhrase());
        }

        AllLoan allLoan=allLoanDao.findById(loanId);
        if(allLoan==null){
            return  new JsonResult(BusiStatus.PARAM_ERROR.toString(),BusiStatus.PARAM_ERROR.getReasonPhrase());
        }

        if (allLoan.getStatus()==1){
            return  new JsonResult(BusiStatus.NO_MONEY.toString(),BusiStatus.NO_MONEY.getReasonPhrase());
        }

        if(allLoan.getBalance()<allLoan.getPrice()){
            allLoan.setStatus(1);
            allLoanDao.update(allLoan);
            return  new JsonResult(BusiStatus.NO_MONEY.toString(),BusiStatus.NO_MONEY.getReasonPhrase());
        }

        allLoan.setBalance(allLoan.getBalance()-allLoan.getPrice());
        if(allLoan.getBalance()<allLoan.getPrice()||allLoan.getBalance()<=0){
            allLoan.setStatus(1);
        }
        allLoanDao.update(allLoan);

        UserApplyLoan userApplyLoan=new UserApplyLoan();
        userApplyLoan.setId(UUID.randomUUID().toString().replace("-",""));
        userApplyLoan.setAllLoanId(loanId);
        userApplyLoan.setAppUserId(id);
        userApplyLoan.setCreateTime(new Date());
        userApplyLoanDao.insertSelective(userApplyLoan);

        return  new JsonResult(BusiStatus.SUCCESS.toString(),BusiStatus.SUCCESS.getReasonPhrase());

    }
}