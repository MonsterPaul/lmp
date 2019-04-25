package com.zjht.jfmall.action.admin.lifeIntegral;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.action.common.strategy.AbstractStrategy;
import com.zjht.jfmall.action.common.strategy.util.ActCommStrategyUtil;
import com.zjht.jfmall.common.dto.*;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.entity.ApiChannel;
import com.zjht.jfmall.entity.OrderAssist;
import com.zjht.jfmall.entity.OrderExchange;
import com.zjht.jfmall.entity.Product;
import com.zjht.jfmall.enums.OrderExchangeDealStatus;
import com.zjht.jfmall.service.ChannelService;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.service.OrderAssistService;
import com.zjht.jfmall.service.OrderExchangeService;
import com.zjht.jfmall.service.ProductService;
import com.zjht.jfmall.util.DateTimeUtils;
import com.zjht.jfmall.util.ExcelUtils;
import com.zjht.jfmall.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 兑换订单ACTION
 * Created by vip on 2018/1/5.
 */
@Controller
@RequestMapping(value = "/lifeIntegral/*")
public class OrderExchangeAct {

    private static final Logger log = LoggerFactory.getLogger(OrderExchangeAct.class);

    @Autowired
    private OrderExchangeService orderExchangeService;
    @Autowired
    private OrderAssistService orderAssistService;
    @Autowired
    private ProductService productService;
    @Autowired
    private LogService logService;
    @Autowired
    private ChannelService channelService;

    /**
     * 跳转到兑换订单界面
     * @param oe
     * @return
     */
    @RequestMapping(value = "/orderExchange/v_list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_list(OrderExchange oe, ModelMap modelMap, HttpServletRequest request) {
        modelMap.put("oe", oe);
        WebSite.setParameters(request, modelMap);
        //查询渠道
        List<ApiChannel> channelList = channelService.getList(null);
        modelMap.addAttribute("channelList", channelList);
        return "orderExchange/list";
    }

    /**
     * 页面数据表格获取数据
     * @param oe
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/orderExchange/o_list.json", method = RequestMethod.POST)
    public LayuiResultDto o_list(OrderExchange oe, Integer pageNum, Integer pageSize) {
        PageInfo<OrderExchange> pageInfo = orderExchangeService.findPage(oe, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        return new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 查看订单详情
     * @param oe
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "orderExchange/v_detail.do", method = RequestMethod.GET)
    public String v_detail(OrderExchange oe, ModelMap modelMap) {
        OrderExchange orderExchange = orderExchangeService.get(oe);
        orderExchange.setProduct(productService.findById(orderExchange.getProductId()));
        if (orderExchange.getDealType() != null) {
            if (OrderExchange.FAMA == orderExchange.getDealType()) {//发码订单要查询
                //调用查码接口更新券码状态，等接口写好在加入
                orderExchange.setOrderAssists(orderAssistService.listByOrderAssist(newOrderAssist(oe.getId())));
            } else if (OrderExchange.HONGBAO == orderExchange.getDealType()) {//红包类型，解析红包发送接口返回的数据
                if (orderExchange.getRespMsg() != null) {
                    try {
                        Map parseMap = JSONObject.parseObject(orderExchange.getRespMsg(), HashMap.class);
                        modelMap.put("redpack", parseMap);
                    } catch (Exception e) {
                        log.info("查看兑换订单详情解析发送红包出现错误：", e);
                    }
                }
            }
        }
        modelMap.put("oe", orderExchange);
        return "orderExchange/detail";
    }

    /**
     * 创建OrderAssist对象并setOrderId
     * @param orderId
     * @return
     */
    private OrderAssist newOrderAssist(String orderId) {
        OrderAssist oa = new OrderAssist();
        oa.setOrderId(orderId);
        return oa;
    }
    /**
     * 修改页面
     * @param request
     * @param modelMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/orderExchange/o_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_edit(HttpServletRequest request, ModelMap modelMap, OrderExchange orderExchange) {
        WebSite.setParameters(request,modelMap);
        if(orderExchange.getId() == null){
            throw new IllegalArgumentException("编辑id不能为空");
        }
        ResultDto resultDto = orderExchangeService.updateSelective(orderExchange);
        return resultDto;
    }
    /**
     * 发码
     * @param oe
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/orderExchange/reSendCode.do", method = RequestMethod.POST)
    public ResultDto reSendCode(OrderExchange oe) {
        try {
            checkParam(oe);//校验请求参数
            OrderExchange orderExchange = orderExchangeService.get(oe);
            if (orderExchange == null) {
                return new ResultFailDto("订单不存在！");
            }
            if (orderExchange.getDealType() == null || orderExchange.getDealType() != OrderExchange.FAMA) {
                return new ResultFailDto("订单类型不属于发码！");
            }
            if (orderExchange.getDealStatus() != null && orderExchange.getDealStatus() == OrderExchangeDealStatus.PROCESSED.getStatus()) {
                return new ResultFailDto("订单已经处理成功，不可以再次操作！");
            }
            //调用发码接口处理逻辑
            Product product = productService.findById(orderExchange.getProductId());
            AbstractStrategy strategy = ActCommStrategyUtil.generateSaleStrategy(product.getStrategyName());
            strategy.execute(orderExchange);
            if(OrderExchangeDealStatus.PROCESSED.getStatus() != orderExchange.getDealStatus()){
                return new ResultFailDto(orderExchange.getRespMsg());
            }
            return new ResultSuccessDto("订单重新发码成功！");
        } catch (Exception e) {
            log.info("处理重新发码订单异常：", e);
            return new ResultFailDto(e.getMessage());
        }
    }

    /**
     * 发红包
     * @param oe
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/orderExchange/reSendRedPacket.do", method = RequestMethod.POST)
    public ResultDto reSendRedPacket(OrderExchange oe) {
        try {
            checkParam(oe);//校验请求参数
            OrderExchange orderExchange = orderExchangeService.get(oe);
            if (orderExchange == null) {
                return new ResultFailDto("订单不存在！");
            }
            if (orderExchange.getDealType() == null || orderExchange.getDealType() != OrderExchange.HONGBAO) {
                return new ResultFailDto("订单类型不属于发红包！");
            }
            if (orderExchange.getDealStatus() != null && orderExchange.getDealStatus() == OrderExchangeDealStatus.PROCESSED.getStatus()) {
                return new ResultFailDto("订单已经处理成功，不可以再次操作！");
            }
            //如果重发没有成功
            if(OrderExchangeDealStatus.PROCESSED.getStatus() != orderExchange.getDealStatus()){
                //如果接口返回的错误码是SEND_FAILED，需要更换订单号重发
                JSONObject respObj = JSONObject.parseObject(orderExchange.getRespMsg());
                if(respObj != null && "SEND_FAILED".equalsIgnoreCase(respObj.getString("err_code"))){
                    String orderNoNew = DateTimeUtils.genOrderNo(18);
                    log.info("订单" + orderExchange.getOrderNo() + "更换为订单号" + orderNoNew + "重发");
                    orderExchange.setRemark(StringUtils.trimToEmpty(orderExchange.getRemark()) + "原订单号：" + orderExchange.getOrderNo());
                    orderExchange.setOrderNo(orderNoNew);
                }
            }
            //调用微信红包接口处理逻辑
            Product product = productService.findById(orderExchange.getProductId());
            AbstractStrategy strategy = ActCommStrategyUtil.generateSaleStrategy(product.getStrategyName());
            strategy.execute(orderExchange);
            if(OrderExchangeDealStatus.PROCESSED.getStatus() == orderExchange.getDealStatus()){
                return new ResultSuccessDto("订单重新发红包成功！");
            }else{
                JSONObject respObj = JSONObject.parseObject(orderExchange.getRespMsg());
                return new ResultSuccessDto(respObj.getString("return_msg"));
            }
        } catch (Exception e) {
            log.info("处理重新发码订单异常：", e);
            return new ResultFailDto(e.getMessage());
        }
    }

    /**
     * 校验请求参数
     * @param oe
     */
    private void checkParam(OrderExchange oe) {
        if (oe == null || StringUtils.isBlank(oe.getId())) {
            throw new NullPointerException("请求参数错误");
        }
    }

    /**
     * 导出订单
     * @param oe
     */
    @RequestMapping(value = "/orderExchange/export.do")
    public void export(OrderExchange oe, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<OrderExchange> orderExchanges = orderExchangeService.listByOrderExchange(oe);
        //处理数据提取要导出的数据字段
        List<List<String>> collects = orderExchanges.stream().map(orderExchange -> extractOrderExchangeField(orderExchange)).collect(Collectors.toList());
        String[] heads    = {"项目名称", "订单号", "所属渠道", "手机号码", "产品名称", "兑换数量", "产品售价(元)", "总金额(元)", "兑换积分", "兑换时间", "订单状态", "处理状态", "红包订单号", "红包状态", "备注"};
        String   fileName = "兑换订单" + DateTimeUtils.formatDateStr(new Date(), "yyyyMMddHHmmss") + ".xls";
        ExcelUtils.exportExcel(fileName, req, resp, heads, collects);
    }

    /**
     * 提取属性
     * @param orderExchange
     * @return
     */
    public List<String> extractOrderExchangeField(OrderExchange orderExchange) {
        List<String> fields = new ArrayList<String>();
        fields.add("人寿积分兑换项目");
        fields.add(orderExchange.getOrderNo());
        fields.add(orderExchange.getUser()==null?"":orderExchange.getUser().getChannelName());
        fields.add(orderExchange.getPhone());
        Product product = productService.findById(orderExchange.getProductId());
        fields.add(product == null ? "未知" : product.getProductName());
        fields.add(String.valueOf(orderExchange.getTotal()));
        if(orderExchange.getProductPrice()!=null){
            fields.add(orderExchange.getProductPrice().setScale(2, BigDecimal.ROUND_FLOOR).toString());
            fields.add(orderExchange.getProductPrice().multiply(new BigDecimal(orderExchange.getTotal())).setScale(2,BigDecimal.ROUND_FLOOR).toString());
        }else{
            fields.add(product.getSalePrice().setScale(2, BigDecimal.ROUND_FLOOR).toString());
            fields.add(product.getSalePrice().multiply(new BigDecimal(orderExchange.getTotal())).setScale(2,BigDecimal.ROUND_FLOOR).toString());
        }
        fields.add(orderExchange.getIntegral());
        fields.add(DateTimeUtils.formatDateStr(orderExchange.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        fields.add(orderExchange.getStatusStr());
        fields.add(orderExchange.getDealStatusStr());
        try {
            JSONObject respObj = JSONObject.parseObject(orderExchange.getRespMsg());
            fields.add(respObj.getString("detail_id")==null?respObj.getString("send_listid"):respObj.getString("detail_id"));
            fields.add(orderExchange.getRedPackageStatusStr());
        } catch (Exception e) {
            fields.add("");
        }
        fields.add(orderExchange.getRemark());
        return fields;
    }

}
