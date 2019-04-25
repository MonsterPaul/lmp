package com.zjht.jfmall.service.impl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.dao.AppUserMapper;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.RiskRecord;
import com.zjht.jfmall.service.RiskRecordService;
import com.zjht.jfmall.service.RiskService;

import tk.mybatis.mapper.entity.Example;

/**
 * 风控管理
 * @author lqf
 *
 */
@Service
@Transactional
public class RiskServiceImpl implements RiskService {
    
    @Autowired
    private AppUserMapper  appUserMapper;
    
	@Autowired
	private RiskRecordService riskRecordService;
	
    @Override
    public PageInfo<AppUser> findPageForRiskAuth(AppUser bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询参数
        //2.1创建条件对象
        Example  example  = new Example(AppUser.class);
        example.setOrderByClause(" register_time desc");
        Example.Criteria criteria = example.createCriteria();
        
        //风控三认证:个人信息认证、通讯录认证、运营商认证
        criteria.andCondition("data_status=1");
        criteria.andCondition("iden_status=1");
        criteria.andCondition("operator_status=1");
        
        //2.2判断查询条件
        if (StringUtils.isNotBlank(bean.getName())) {
            criteria.andLike("name", "%" + bean.getName() + "%");
        }
        if (StringUtils.isNotBlank(bean.getQq())) {
            criteria.andLike("qq", "%" + bean.getQq() + "%");
        }
        if (StringUtils.isNotBlank(bean.getPhone())) {
            criteria.andLike("phone", "%" + bean.getPhone() + "%");
        }
        if (StringUtils.isNotBlank(bean.getWechat())) {
            criteria.andLike("wechat", "%" + bean.getWechat() + "%");
        }
        if (StringUtils.isNotBlank(bean.getExchangeTimeBegin())) {
            criteria.andCondition("register_time >= '" + bean.getExchangeTimeBegin() + " 00:00:00'");
        }
        if (StringUtils.isNotBlank(bean.getExchangeTimeEnd())) {
            criteria.andCondition("register_time <= '" + bean.getExchangeTimeEnd() + " 23:59:59'");
        }
        return new PageInfo<AppUser>(appUserMapper.selectByExample(example));
    }
    
    public RiskRecord findByEntity(RiskRecord record) {
    	return riskRecordService.findByEntity(record);
    }
    
    public RiskRecord findRisKRecord(String id_no) {
    	return riskRecordService.findByIdNo(id_no);
    }
    
    public int savaRisKRecord(RiskRecord record) {
    	return riskRecordService.insert(record);
    }
    
}
