package com.zjht.jfmall.service.impl;

import com.zjht.jfmall.dao.ProductImgDao;
import com.zjht.jfmall.entity.ProductImg;
import com.zjht.jfmall.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 〈商品图片ServiceImpl〉
 *
 * @author wangpeng
 * @create 2018/1/11
 * @since 1.0.0
 */
@Service
@Transactional
public class ProductImgServiceImpl implements ProductImgService {
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    public List<ProductImg> getlist(ProductImg productImg) {
        return productImgDao.selectListByObject(productImg);
    }

    @Override
    public boolean insertProductImg(ProductImg productImg) {
        if(productImgDao.insertSelective(productImg)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateProductImg(ProductImg productImg) {
        if(productImgDao.updateByIdSelective(productImg)>0){
            return true;
        }
        return false;
    }
}