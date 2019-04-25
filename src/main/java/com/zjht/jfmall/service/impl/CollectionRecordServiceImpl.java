package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.dao.CollectionRecordMapper;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.CollectionRecord;
import com.zjht.jfmall.service.CollectionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * 催收记录表 服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
public class CollectionRecordServiceImpl implements CollectionRecordService {

    @Autowired
    private CollectionRecordMapper dao;

    @Override
    public PageInfo<CollectionRecord> findPage(AppUser bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询参数
        //2.1创建条件对象
        Example example  = new Example(CollectionRecord.class);
        Example.Criteria criteria = example.createCriteria();
        //2.2判断查询条件
        criteria.andEqualTo("appUserId", bean.getId());
        return new PageInfo<CollectionRecord>(dao.selectByExample(example));
    }

}
