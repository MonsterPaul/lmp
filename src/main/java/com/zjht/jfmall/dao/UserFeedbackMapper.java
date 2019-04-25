package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.UserFeedback;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 * 用户意见反馈表 Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface UserFeedbackMapper extends Mapper<UserFeedback> {

    List<UserFeedback> findPage(@Param("bean") UserFeedback bean);

    UserFeedback findById(@Param("id") String id);

    void update(@Param("bean") UserFeedback bean);

    List<UserFeedback> getUserFeedBackList(@Param("id") String id, @Param("num") Integer pageNum, @Param("size") Integer pageSize);
}
