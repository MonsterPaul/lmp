package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.CollectionRecord;

/**
 * <p>
 * 催收记录表 服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
public interface CollectionRecordService {

    PageInfo<CollectionRecord> findPage(AppUser bean, int pageNum, int pageSize);
}
