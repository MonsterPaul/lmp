package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.CallContactDetail;

/**
 * <p>
 * app用户通话记录表 服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-12-04
 */
public interface CallContactDetailService  {

    PageInfo<CallContactDetail> findPage(AppUser bean, int pageNum, int pageSize);
}
