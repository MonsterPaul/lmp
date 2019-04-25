package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.ApplyRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 申请贷款记录表  Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface ApplyRecordMapper extends Mapper<ApplyRecord> {

    List<ApplyRecord> findPage(@Param("bean") ApplyRecord bean);

    ApplyRecord find(@Param("bean") ApplyRecord bean);

    void updateOper(@Param("id") String recordId, @Param("operId") String id, @Param("operDate") Date date);

    List<ApplyRecord> findCollectionPage(@Param("bean") ApplyRecord bean);
}
