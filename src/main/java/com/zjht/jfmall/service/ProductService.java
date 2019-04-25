package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-01-06 10:30
 */
public interface ProductService {
    /**
     * 通过产品编码校验产品是否存在
     * @param productCode
     * @return
     */
    ResultDto productCheck(String productCode);

    /**
     * 校验库存
     * @param productCode 产品编码
     * @param buyCount 此次购买数量
     * @return
     */
    ResultDto stockCheck(String productCode, int buyCount);
    /**
     * 通过id获取产品
     * @param id
     * @return
     */
    Product findById(String id);
    /**
     * 通过产品编码获取产品
     * @param productCode
     * @return
     */
    Product findByCode(String productCode);

    /**
     * 获取兑换商品数据
     * @param product
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfo<Product> getProductList(Product product, Integer pageSize, Integer pageNum);

    /**
     * 检查商品code是否重复
     * @param param
     * @return
     */
    boolean productCodeCheck(Map<String,Object> param);

    /**
     * 新增兑换商品数据
     * @param product
     */
    void insertProduct(Product product);

    /**
     * 更新兑换商品数据
     * @param product
     * @return
     */
    boolean updateProduct(Product product);

    /**
     * 删除兑换商品数据
     * @param id
     */
    void deleteById(String[] id);

}
