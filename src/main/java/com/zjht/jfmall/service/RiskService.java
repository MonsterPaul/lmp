package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.RiskRecord;

/**
 * 风控管理
 * @author lqf
 *
 */
public interface RiskService {
	
	/**
	   *      风控认证
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<AppUser> findPageForRiskAuth(AppUser bean, int pageNum, int pageSize);
	
    public RiskRecord findRisKRecord(String id_no) ;

    public RiskRecord findByEntity(RiskRecord record) ;
	    
    public int savaRisKRecord(RiskRecord record) ;
	
}
