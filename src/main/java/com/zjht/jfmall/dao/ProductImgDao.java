package com.zjht.jfmall.dao;

import com.zjht.jfmall.dao.common.BaseDao;
import com.zjht.jfmall.entity.ProductImg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgDao extends BaseDao<ProductImg> {
    List<ProductImg> findByProductAndType(@Param("productId") String productId, @Param("type") String type);
}