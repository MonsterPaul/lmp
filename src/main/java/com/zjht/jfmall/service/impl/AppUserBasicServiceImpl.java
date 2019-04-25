package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.dao.AppUserBasicMapper;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.AppUserBasic;
import com.zjht.jfmall.service.AppUserBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * app用户信息表 服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-12-04
 */
@Service
public class AppUserBasicServiceImpl  implements AppUserBasicService {

    @Autowired
    private AppUserBasicMapper dao;

    @Override
    public AppUserBasic findByAppId(String id) {
        return dao.findByAppId(id);
    }

    @Override
    public PageInfo<AppUserBasic> findPage(AppUser bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询参数
        //2.1创建条件对象
        Example example  = new Example(AppUserBasic.class);
        Example.Criteria criteria = example.createCriteria();
        //2.2判断查询条件
        criteria.andEqualTo("appUserId", bean.getId());
        return new PageInfo<AppUserBasic>(dao.selectByExample(example));
    }
}
