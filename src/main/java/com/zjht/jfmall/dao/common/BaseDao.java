package com.zjht.jfmall.dao.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基础DAO，所有dao接口继承该类
 *
 * @author caozhaokui
 * @create 2017-11-24 17:21
 */
public interface BaseDao<T> {

    int deleteById(String id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectListByObject(@Param(value = "param") T param);

    T selectById(String id);

    int updateByIdSelective(T record);

    int updateById(T record);

    long countByObject(@Param(value = "param") T param);
}
