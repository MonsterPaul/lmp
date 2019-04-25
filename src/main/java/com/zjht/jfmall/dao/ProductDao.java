package com.zjht.jfmall.dao;

import com.zjht.jfmall.dao.common.BaseDao;
import com.zjht.jfmall.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ProductDao extends BaseDao<Product> {
    Product findByCode(String productCode);

    int productCodeCheck(@Param(value = "param")Map<String,Object> param);

    int deleteById(@Param(value="id")String id);
}