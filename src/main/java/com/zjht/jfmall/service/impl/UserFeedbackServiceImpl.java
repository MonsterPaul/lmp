package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.dao.UserFeedbackMapper;
import com.zjht.jfmall.entity.Advertisement;
import com.zjht.jfmall.entity.ApiChannel;
import com.zjht.jfmall.entity.AppUserDeposit;
import com.zjht.jfmall.entity.UserFeedback;
import com.zjht.jfmall.service.AppUserService;
import com.zjht.jfmall.service.UserFeedbackService;
import com.zjht.jfmall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户意见反馈表 服务实现类
 * </p>
 */
@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {
    @Autowired
    private UserFeedbackMapper userFeedbackMapper;
    @Autowired
    private UserService        userService;
    @Autowired
    private AppUserService     appUserService;
    @Autowired
    private SessionProvider    sessionProvider;

    @Override
    public int insert(UserFeedback userFeedback) {
        return userFeedbackMapper.insertSelective(userFeedback);
    }

    @Override
    public List<UserFeedback> getUserFeedBackList(String id, Integer pageNum, Integer pageSize) {
        return userFeedbackMapper.getUserFeedBackList(id, pageNum, pageSize);
    }

    @Override
    public PageInfo<UserFeedback> getPage(UserFeedback bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserFeedback> list = userFeedbackMapper.findPage(bean);
        list.stream().forEach(userFeedback -> {
            userFeedback.setUser(userService.findById(userFeedback.getCustomerId()));
            userFeedback.setAppUser(appUserService.findById(userFeedback.getUserId()));
        });
        return new PageInfo<UserFeedback>(list);
    }

    @Override
    public UserFeedback findById(String id) {
        UserFeedback bean = userFeedbackMapper.findById(id);
        bean.setUser(userService.findById(bean.getCustomerId()));
        bean.setAppUser(appUserService.findById(bean.getUserId()));
        bean.setImgs(StringUtils.isNotBlank(bean.getFeedImageUrl())?bean.getFeedImageUrl().split(","):null);
        return bean;
    }

    @Override
    public void update(UserFeedback bean) {
        bean.setOperatorId(sessionProvider.getUser().getId());
        bean.setOperatorTime(new Date());
        userFeedbackMapper.update(bean);
    }
}
