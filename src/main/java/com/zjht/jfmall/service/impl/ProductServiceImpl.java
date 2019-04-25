package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.common.dto.ResultFailDto;
import com.zjht.jfmall.common.dto.ResultSuccessDto;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.common.web.threadvariable.MemberThread;
import com.zjht.jfmall.dao.OrderExchangeDao;
import com.zjht.jfmall.dao.ProductDao;
import com.zjht.jfmall.dao.ProductImgDao;
import com.zjht.jfmall.entity.OrderExchange;
import com.zjht.jfmall.entity.Product;
import com.zjht.jfmall.entity.ProductImg;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.ProductService;
import com.zjht.jfmall.util.DateTimeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-01-06 10:32
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao      productDao;
    @Autowired
    private ProductImgDao   productImgDao;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private OrderExchangeDao orderExchangeDao;

    @Override
    public ResultDto productCheck(String productCode) {
        Product product = productDao.findByCode(productCode);
        if(product==null){
            return new ResultFailDto("产品不存在，请勿非法操作");
        }
        List<ProductImg> detailImgs = productImgDao.findByProductAndType(product.getId(),String.valueOf(ProductImg.Type.DETAIL.getType()));
        if(CollectionUtils.isNotEmpty(detailImgs)){
            product.setProductDetailPath(detailImgs.get(0).getPath());
        }
        ResultDto resultDto = new ResultSuccessDto();
        resultDto.setData(product);
        return resultDto;
    }

    @Override
    public ResultDto stockCheck(String productCode, int buyCount) {
        User user = MemberThread.get();
        Product product = productDao.findByCode(productCode);
        if(product == null){
            return new ResultFailDto("产品不存在，请勿非法操作");
        }
        if (product.getStatus() == Product.Status.Disable.getStatus()) {
            String msg = "您要购买的产品【" + product.getProductName() + "】已经下架！";
            return new ResultFailDto(msg);
        }
        //如果有配置活动时间，验证
        Date now = new Date();

        //如果有配置库存数，则验证
        if(product.getStockCount()!=null && product.getStockCount()!=0){
            // 库数小于已售数
            if (product.getStockCount() <= product.getSaleCount()) {
                String msg = "您要购买的产品【" + product.getProductName() + "】已经售完！";
                return new ResultFailDto(msg);
            }
            // 库数小于已售数+现有数量
            if (product.getStockCount() < (buyCount + product.getSaleCount())) {
                String msg = "您要购买的产品【" + product.getProductName() + "】当前库存数为" + (product.getStockCount() - product.getSaleCount()) + "件！不能满足此次交易！！";
                return new ResultFailDto(msg);
            }
        }

        //如果有配置日库存,则验证
        OrderExchange orderExchange = new OrderExchange();
        orderExchange.setExchangeTimeBegin(DateTimeUtils.getCurrentDateStr());
        orderExchange.setProductId(product.getId());
        if(product.getDayStock()!=null ){
            int allExchgeOrderCount = orderExchangeDao.selectExchangeOrder(orderExchange);
           //当日库存已经兑换完毕
            if(product.getDayStock()<=allExchgeOrderCount){
                String msg = "您要购买的产品【" + product.getProductName() + "】当日可兑换总数已经兑换完毕！不能满足此次交易！！";
                return new ResultFailDto(msg);
            }
        }

        //如果有配置每日用户购买限制,则验证
        if(product.getDayUserLimit()!=null){
            orderExchange.setPhone(user.getMobile());
            int exchgeOrderCount = orderExchangeDao.selectExchangeOrder(orderExchange);
            //用户兑换此类商品数已经达到每日购买限制
            if(product.getDayUserLimit()<=exchgeOrderCount){
                String msg = "您要购买的产品【" + product.getProductName() + "】每日单人仅可兑换"+product.getDayUserLimit()+"个！";
                return new ResultFailDto(msg);
            }
        }
        ResultDto resultDto = new ResultSuccessDto();
        resultDto.setData(product);
        return resultDto;
    }

    @Override
    public Product findById(String id) {
        return productDao.selectById(id);
    }

    @Override
    public Product findByCode(String productCode) {
        return productDao.findByCode(productCode);
    }

    @Override
    public PageInfo<Product> getProductList(Product product, Integer pageSize, Integer pageNum) {
        if(product == null){
            product = new Product();
        }
        if(StringUtils.isBlank(product.getOrderByClause())){
            product.setOrderByClause("id desc");
        }
        product.setPageSize(pageSize);
        product.setPageNum(pageNum);


        List<Product> productList = productDao.selectListByObject(product);
        for(Iterator<Product> iterator = productList.iterator();iterator.hasNext();){
            Product product1 = iterator.next();
            List<ProductImg> logoimgs = productImgDao.findByProductAndType(product1.getId(),String.valueOf(ProductImg.Type.LOGO.getType()));
            if(CollectionUtils.isNotEmpty(logoimgs)){
                product1.setProductLogoPath(logoimgs.get(0).getPath());
            }
            List<ProductImg> detailImgs = productImgDao.findByProductAndType(product1.getId(),String.valueOf(ProductImg.Type.DETAIL.getType()));
            if(CollectionUtils.isNotEmpty(detailImgs)){
                product1.setProductDetailPath(detailImgs.get(0).getPath());
            }
        }
        return new PageInfo<Product>(productList);
    }

    @Override
    public boolean productCodeCheck(Map<String, Object> param) {
        if(productDao.productCodeCheck(param)>0){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public void insertProduct(Product product) {
        productDao.insertSelective(product);
        String id =productDao.findByCode(product.getProductCode()).getId();
        User user = sessionProvider.getUser();
        //新增商品logo图片
        if (StringUtils.isNotBlank(product.getProductLogoPath())) {
            insertProductImg(id, String.valueOf(ProductImg.Type.LOGO.getType()), product.getProductLogoPath(),user.getId());
        }

        //新增商品详情图片
        if (StringUtils.isNotBlank(product.getProductDetailPath())) {
            insertProductImg(id, String.valueOf(ProductImg.Type.DETAIL.getType()), product.getProductDetailPath(),user.getId());
        }


    }

    @Override
    public boolean updateProduct(Product product) {
        User user = sessionProvider.getUser();
        product.setGmtModified(new Timestamp(System.currentTimeMillis()));
        if (StringUtils.isNotBlank(product.getProductDetailPath())) {
            if (StringUtils.isNotBlank(product.getProductDetailImgId())) {
                //更新商品详情图片
                updateProductImg(product.getProductDetailImgId(), product.getProductDetailPath(),user.getId());
            } else {
                //新增商品详情图片
                insertProductImg(product.getId(), String.valueOf(ProductImg.Type.DETAIL.getType()), product.getProductDetailPath(),user.getId());
            }

        }

        if (StringUtils.isNotBlank(product.getProductLogoPath())) {
            if (StringUtils.isNotBlank(product.getProductLogoImgId())) {
                //更新商品logo图片
                updateProductImg(product.getProductLogoImgId(), product.getProductLogoPath(),user.getId());
            } else {
                //新增商品logo图片
                insertProductImg(product.getId(), String.valueOf(ProductImg.Type.LOGO.getType()), product.getProductLogoPath(),user.getId());
            }
        }
          if(productDao.updateByIdSelective(product)>0){
              return  true;
          }else{
              return  false;
          }
    }

    @Override
    public void deleteById(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            productDao.deleteById(ids[i]);
        }
    }

    private void updateProductImg(String id, String path,String userId) {
        ProductImg productImg = new ProductImg();
        productImg.setId(id);
        productImg.setPath(path);
        productImg.setUserId(userId);
        productImgDao.updateByIdSelective(productImg);
    }

    private void insertProductImg(String id, String type, String path,String userId) {
        ProductImg productImg = new ProductImg();
        productImg.setProductId(id);
        productImg.setType(type);
        productImg.setPath(path);
        productImg.setCreateTime(new Timestamp(System.currentTimeMillis()));
        productImg.setUserId(userId);
        productImgDao.insertSelective(productImg);
    }
}
