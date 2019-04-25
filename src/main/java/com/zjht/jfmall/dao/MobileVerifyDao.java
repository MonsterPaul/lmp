package com.zjht.jfmall.dao;

import com.zjht.jfmall.dao.common.BaseDao;
import com.zjht.jfmall.entity.MobileVerify;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileVerifyDao extends BaseDao<MobileVerify> {
    MobileVerify findByMobile(String mobile);

    MobileVerify findByIp(String ipaddr);
}