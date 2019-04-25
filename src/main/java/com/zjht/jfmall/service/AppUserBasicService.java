package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.AppUserBasic;

/**
 * <p>
 * app用户信息表 服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-12-04
 */
public interface AppUserBasicService  {

    AppUserBasic findByAppId(String id);

    PageInfo<AppUserBasic> findPage(AppUser bean, int pageNum, int pageSize);

}
