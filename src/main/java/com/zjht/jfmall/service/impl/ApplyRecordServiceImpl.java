package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.dao.AppUserMapper;
import com.zjht.jfmall.dao.ApplyRecordMapper;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.ApplyRecord;
import com.zjht.jfmall.service.ApplyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 申请贷款记录表  服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
@Transactional
public class ApplyRecordServiceImpl implements ApplyRecordService {
    @Autowired
    private ApplyRecordMapper applyRecordMapper;
    @Autowired
    private AppUserMapper     appUserMapper;
    @Autowired
    private SessionProvider sessionProvider;

    @Override
    public ApplyRecord insert(AppUser appUser, double loans) {
        ApplyRecord applyRecord = new ApplyRecord();
        applyRecord.setApplyDate(new Date());
        applyRecord.setApplyAmount(loans);
        applyRecord.setAppUserId(appUser.getId());
        applyRecord.setId(UUID.randomUUID().toString().replace("-", ""));
        applyRecord.setUserId(appUser.getServiceId());
        applyRecordMapper.insert(applyRecord);

        appUser.setLoansStatus("1");
        appUserMapper.updateByPrimaryKeySelective(appUser);

        return applyRecord;
    }

    @Override
    public List<ApplyRecord> getUserLoansList(String userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example          example  = new Example(ApplyRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("appUserId", userId);
        List<ApplyRecord>     list     = applyRecordMapper.selectByExample(example);
        PageInfo<ApplyRecord> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();
    }

    @Override
    public PageInfo<ApplyRecord> findPage(ApplyRecord bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<ApplyRecord>(applyRecordMapper.findPage(bean));
    }

    @Override
    public PageInfo<ApplyRecord> findCollectionPage(ApplyRecord bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<ApplyRecord>(applyRecordMapper.findCollectionPage(bean));
    }

    @Override
    public ApplyRecord find(ApplyRecord bean) {
        return applyRecordMapper.find(bean);
    }

    @Override
    public void updateOper(String recordId, String id, Date date) {
        applyRecordMapper.updateOper(recordId, id, date);
    }
}
