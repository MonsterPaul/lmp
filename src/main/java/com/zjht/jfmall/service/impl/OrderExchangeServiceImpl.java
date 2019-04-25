package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.action.common.strategy.AbstractStrategy;
import com.zjht.jfmall.action.common.strategy.util.ActCommStrategyUtil;
import com.zjht.jfmall.action.common.strategy.util.StrategyConstant;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.common.dto.ResultFailDto;
import com.zjht.jfmall.common.dto.ResultSuccessDto;
import com.zjht.jfmall.common.web.session.HttpSessionProvider;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.common.web.threadvariable.MemberThread;
import com.zjht.jfmall.common.web.threadvariable.RequestThread;
import com.zjht.jfmall.dao.ApiChannelDao;
import com.zjht.jfmall.dao.OrderExchangeDao;
import com.zjht.jfmall.dao.ProductDao;
import com.zjht.jfmall.dao.UserDao;
import com.zjht.jfmall.entity.ElectroniCode;
import com.zjht.jfmall.entity.OrderExchange;
import com.zjht.jfmall.entity.Product;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.enums.OrderExchangeDealStatus;
import com.zjht.jfmall.enums.OrderExchangeStatus;
import com.zjht.jfmall.service.OrderExchangeService;
import com.zjht.jfmall.service.ProductService;
import com.zjht.jfmall.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 兑换订单Service实现类
 * Created by vip on 2018/1/5.
 */
@Service
@Transactional
public class OrderExchangeServiceImpl implements OrderExchangeService {

    @Autowired
    private OrderExchangeDao oeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private HttpSessionProvider session;
    @Autowired
    private ApiChannelDao apiChannelDao;
    @Autowired
    private SessionProvider sessionProvider;

    //查看所有订单权限编码
    private final static String QUERYALL_BIZ_CODE = "m050108";
    /**
     * 分页查询
     *
     * @param oe
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<OrderExchange> findPage(OrderExchange oe, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        oe.setOrderByClause("id desc");
        //如果当前用户没有查看所有订单权限，只查询当前渠道订单
        if(!sessionProvider.getPermissions().contains(QUERYALL_BIZ_CODE)){
            if(oe.getUser() == null){
                oe.setUser(new User());
            }
            oe.getUser().setChannelId(sessionProvider.getUser().getChannelId());
        }

        List<OrderExchange> list = oeDao.selectListByObject(oe);
        for (OrderExchange oEx : list) {
            if(oEx.getUser()!=null && StringUtils.isNotBlank(oEx.getUser().getChannelId())){
                oEx.getUser().setChannelName(apiChannelDao.getNameById(oEx.getUser().getChannelId()));
            }
        }
        return new PageInfo<OrderExchange>(list);
    }

    /**
     * 根据条件获取
     *
     * @param oe
     * @return
     */
    @Override
    public OrderExchange get(OrderExchange oe) {
        return oeDao.selectById(oe.getId());
    }

    @Override
    public synchronized ResultDto<OrderExchange> exchange(Product product, Integer buyCount) {
        User user = MemberThread.get();
        //判断用户积分是否足够
        int remainPoint = user.getPoints() - product.getPoints()*buyCount;
        if(remainPoint<0){
            ResultDto resultDto = new ResultFailDto("对不起，您的礼包额不够");
            return resultDto;
        }
        //生成订单
        OrderExchange order = new OrderExchange();
        order.setTotal(buyCount);
        order.setProductId(product.getId());
        order.setProduct(product);
        order.setCreateTime(new Date());
        order.setOrderNo(DateTimeUtils.genOrderNo(18));
        order.setPhone(user.getMobile());
        order.setUserId(user.getId());
        order.setStatus(OrderExchangeStatus.PENDING.getStatus());
        order.setDealStatus(OrderExchangeDealStatus.PENDING.getStatus());
        order.setIntegral(String.valueOf(product.getPoints()*buyCount));
        if(product.getSalePrice() != null){
            order.setAmount(product.getSalePrice().multiply(new BigDecimal(buyCount)).setScale(2,BigDecimal.ROUND_HALF_UP));
            order.setProductPrice(product.getSalePrice().setScale(2,BigDecimal.ROUND_HALF_UP));
        }
        Object openIdObj = session.getAttribute(RequestThread.get(), StrategyConstant.OPENID_SESSION_KEY);
        String openId = openIdObj == null ? null : (String)openIdObj;
        order.setOpenid(openId);
        oeDao.insert(order);
        //用户积分扣减
        user.setPoints(remainPoint);
        userDao.updateByPrimaryKeySelective(user);
        product.setSaleCount(product.getSaleCount()+buyCount);
        productDao.updateByIdSelective(product);
        //异步调用业务处理策略
        new Thread(new Runnable() {
            @Override
            public void run() {
                AbstractStrategy strategy = ActCommStrategyUtil.generateSaleStrategy(product.getStrategyName());
                strategy.execute(order);
            }
        }).start();
        ResultDto resultDto = new ResultSuccessDto();
        resultDto.setData(order);
        return resultDto;
    }

    @Override
    public OrderExchange findByOrderNo(String orderNo) {
        return oeDao.findByOrderNo(orderNo);
    }

    /**
     * 不分页查询
     *
     * @param oe
     * @return
     */
    @Override
    public List<OrderExchange> listByOrderExchange(OrderExchange oe) {
        //如果当前用户没有查看所有订单权限，只查询当前渠道订单
        if(!sessionProvider.getPermissions().contains(QUERYALL_BIZ_CODE)){
            if(oe.getUser() == null){
                oe.setUser(new User());
            }
            oe.getUser().setChannelId(sessionProvider.getUser().getChannelId());
        }
        List<OrderExchange> list = oeDao.selectListByObject(oe);
        for (OrderExchange oEx : list) {
            if(oEx.getUser()!=null && StringUtils.isNotBlank(oEx.getUser().getChannelId())){
                oEx.getUser().setChannelName(apiChannelDao.getNameById(oEx.getUser().getChannelId()));
            }
        }
        return list;
    }

    @Override
    public ResultDto updateSelective(OrderExchange oe) {
        int updateCount = oeDao.updateByIdSelective(oe);
        if(updateCount == 1){
            return new ResultSuccessDto();
        }
        return new ResultFailDto("更新失败");
    }


}
