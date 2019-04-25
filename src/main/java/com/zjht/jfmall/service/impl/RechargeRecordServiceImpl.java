package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.dao.RechargeRecordDao;
import com.zjht.jfmall.entity.RechargeRecord;
import com.zjht.jfmall.service.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by vip on 2019/1/17.
 */
@Service
public class RechargeRecordServiceImpl implements RechargeRecordService {

    @Autowired
    private RechargeRecordDao rechargeRecordDao;

    /**
     * 查询
     *
     * @param loanId
     * @return
     */
    @Override
    public PageInfo<RechargeRecord> find(String loanId) {
        Example example = new Example(RechargeRecord.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loanId", loanId);
        return new PageInfo<>(rechargeRecordDao.selectByExample(example));
    }
}
