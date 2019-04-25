package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.dao.UserFollowRecordMapper;
import com.zjht.jfmall.entity.UserFollowRecord;
import com.zjht.jfmall.service.UserFollowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * APP用户跟进记录 服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
public class UserFollowRecordServiceImpl implements UserFollowRecordService {

    @Autowired
    private UserFollowRecordMapper dao;
    @Autowired
    private SessionProvider sessionProvider;

    @Override
    public void insert(UserFollowRecord bean) {
        bean.setId(UUID.randomUUID().toString().replace("-", ""));
        bean.setFollowDate(new Date());
        bean.setFollowUserId(sessionProvider.getUser().getId());
        dao.insert(bean);
    }

    @Override
    public PageInfo<UserFollowRecord> findPage(UserFollowRecord bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(UserFollowRecord.class);
        example.setOrderByClause("follow_date desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("recordId", bean.getRecordId());
        criteria.andEqualTo("appUserId", bean.getAppUserId());
        List<UserFollowRecord> list = dao.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public int count(String userId, String recordId, String platId) {
        return dao.count(userId, recordId, platId);
    }
}
