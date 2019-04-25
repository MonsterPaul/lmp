package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.Advertisement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by vip on 2018/11/29.
 */
@Repository
public interface AdvertisementDao extends Mapper<Advertisement> {

    void deleteById(@Param("id") String id);

    Advertisement findById(@Param("id") String id);

    void update(@Param("bean") Advertisement bean);
}
