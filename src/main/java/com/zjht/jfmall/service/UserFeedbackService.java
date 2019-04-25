package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.Advertisement;
import com.zjht.jfmall.entity.UserFeedback;

import java.util.List;

/**
 * <p>
 * 用户意见反馈表 服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
public interface UserFeedbackService {
    /**
     * 添加用户反馈意见
     *
     * @param userFeedback
     * @return
     */
    int insert(UserFeedback userFeedback);

    /**
     * 获取用户反馈信息
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<UserFeedback> getUserFeedBackList(String id, Integer pageNum, Integer pageSize);

    /**
     * 分页查询
     * @param bean
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UserFeedback> getPage(UserFeedback bean, int pageNum, int pageSize);

    UserFeedback findById(String id);

    void update(UserFeedback bean);
}
