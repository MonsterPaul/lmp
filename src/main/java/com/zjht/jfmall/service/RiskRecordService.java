package com.zjht.jfmall.service;

import com.zjht.jfmall.entity.RiskRecord;

public interface RiskRecordService {

	int insert(RiskRecord record);

	RiskRecord findByIdNo(String id_no);
	
	RiskRecord findByEntity(RiskRecord record);

}
