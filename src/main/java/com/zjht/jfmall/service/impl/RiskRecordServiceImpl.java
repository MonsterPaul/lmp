package com.zjht.jfmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zjht.jfmall.dao.RiskRecordDao;
import com.zjht.jfmall.entity.RiskRecord;
import com.zjht.jfmall.service.RiskRecordService;


@Service
@Transactional
public class RiskRecordServiceImpl implements RiskRecordService {

    @Autowired
    private RiskRecordDao riskRecordDao;

    @Override
    public int insert(RiskRecord record) {
//    	Example example  = new Example(RiskRecord.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andCondition("id_no=", record.getIdNo());
//        
//    	List<RiskRecord> records =  riskRecordDao.selectByExample(record);
    	
//    	if(null!=records && records.size()>0 ) {
//    		riskRecordDao.deleteByPrimaryKey(((RiskRecord)records.get(0)).getId());
//    	}
    	
    	RiskRecord reqRecord = new RiskRecord();
    	reqRecord.setIdNo(record.getIdNo());
    	RiskRecord resRecord = riskRecordDao.selectOne(reqRecord);
    	if(null!=resRecord) {
    		riskRecordDao.deleteByPrimaryKey(resRecord.getId());
    	}
    	
        int result = riskRecordDao.insert(record);
        return result;
    }

    @Override
    public RiskRecord findByIdNo(String id_no) {
    	RiskRecord reqRecord = new RiskRecord();
    	reqRecord.setIdNo(id_no);
    	return riskRecordDao.selectOne(reqRecord);
    }

    @Override
    public RiskRecord findByEntity(RiskRecord record) {
    	return riskRecordDao.selectOne(record);
    }

}
