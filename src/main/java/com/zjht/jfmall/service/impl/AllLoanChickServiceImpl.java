package com.zjht.jfmall.service.impl;

import com.zjht.jfmall.dao.AllLoanChickDao;
import com.zjht.jfmall.entity.AllLoanChick;
import com.zjht.jfmall.service.AllLoanChickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.UUID;

@Service
public class AllLoanChickServiceImpl implements AllLoanChickService {
    @Autowired
    private AllLoanChickDao allLoanChickDao;
    @Override
    public void insert(String id, String loanId) {
         AllLoanChick allLoanChick=new AllLoanChick();
         allLoanChick.setId(UUID.randomUUID().toString().replace("-",""));
         allLoanChick.setCreateTime(new Date());
         allLoanChick.setAllLoanId(loanId);
         allLoanChickDao.insertSelective(allLoanChick);
    }


    /**
     * 统计单个平台的点击率
     *
     * @param allLoanId
     * @return
     */
    @Override
    public int countByAllLoanId(String allLoanId) {
        Example example = new Example(AllLoanChick.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("allLoanId", allLoanId);
        return allLoanChickDao.selectCountByExample(example);
    }
}