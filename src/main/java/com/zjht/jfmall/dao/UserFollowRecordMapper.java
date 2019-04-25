package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.UserFollowRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * APP用户跟进记录 Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface UserFollowRecordMapper extends Mapper<UserFollowRecord> {

    int count(@Param("userId") String userId, @Param("recordId") String recordId, @Param("platId") String platId);
}
