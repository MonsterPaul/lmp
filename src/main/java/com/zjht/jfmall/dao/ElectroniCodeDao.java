package com.zjht.jfmall.dao;

import com.zjht.jfmall.dao.common.BaseDao;
import com.zjht.jfmall.entity.ElectroniCode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectroniCodeDao extends BaseDao<ElectroniCode> {
    List<String> selectAllCode();

    ElectroniCode findByCode(String code);

}