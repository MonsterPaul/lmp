package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.dao.CallContactDetailMapper;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.CallContactDetail;
import com.zjht.jfmall.service.CallContactDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * app用户通话记录表 服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-12-04
 */
@Service
public class CallContactDetailServiceImpl  implements CallContactDetailService {

    @Autowired
    private CallContactDetailMapper dao;

    @Override
    public PageInfo<CallContactDetail> findPage(AppUser bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询参数
        //2.1创建条件对象
        Example example  = new Example(CallContactDetail.class);
        example.setOrderByClause(" call_cnt_6m desc");
        Example.Criteria criteria = example.createCriteria();
        //2.2判断查询条件
        criteria.andEqualTo("appUserId", bean.getId());
        return new PageInfo<CallContactDetail>(dao.selectByExample(example));
    }

}
