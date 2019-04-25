package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.UserMailList;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * app用户通讯录  Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface UserMailListMapper extends Mapper<UserMailList> {
    int insertData(List<UserMailList> userMailListList);


}
