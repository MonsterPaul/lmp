package com.zjht.jfmall.service;

import com.zjht.jfmall.entity.ProductImg;

import java.util.List;

/**
 * 〈商品图片Service〉
 *
 * @author wangpeng
 * @create 2018/1/11
 * @since 1.0.0
 */
public interface ProductImgService {

    /**
     * 获取商品图片数据
     * @param productImg
     * @return
     */
    List<ProductImg> getlist(ProductImg productImg);

    /**
     * 新增商品图片数据
     * @param productImg
     * @return
     */
    boolean insertProductImg(ProductImg productImg);

    /**
     * 更新商品图片数据
     * @param productImg
     * @return
     */
    boolean  updateProductImg(ProductImg productImg);
}