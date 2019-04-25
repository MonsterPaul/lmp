package com.zjht.jfmall.action.common.strategy.util;


import com.zjht.jfmall.action.common.strategy.AbstractStrategy;
import com.zjht.jfmall.action.common.strategy.common.EmptyStrategy;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * 产品售卖策略工具类
 * @author chenxingwang
 *
 */
public class ActCommStrategyUtil {
    
    //策略缓存
    private static Map<String, AbstractStrategy> strategyMap = new HashMap<String, AbstractStrategy>();
    /**
     * 获取产品策略
     * @param fullStrategyName 策略全限定名
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static AbstractStrategy generateSaleStrategy(String fullStrategyName){
        //如果存在产品售卖策略，则构造反射生成售卖策略
    	AbstractStrategy saleStrategy = null;
        if(StringUtils.isNotBlank(fullStrategyName)){
        	if(strategyMap.containsKey(fullStrategyName)){
				try {
					//克隆对象，并重置为初始状态返回
					AbstractStrategy strategy = (AbstractStrategy)strategyMap.get(strategyMap).clone();
					return strategy;
				} catch (CloneNotSupportedException e) {
					 throw new IllegalArgumentException(fullStrategyName + "产品策略构造失败");
				}
        	}else{
                try {
                    Class clazz = Class.forName(fullStrategyName);
                    Constructor constructor = clazz.getConstructor();
                    saleStrategy = (AbstractStrategy)constructor.newInstance();
                } catch (Exception e) {
                	throw new IllegalArgumentException(fullStrategyName + "产品策略构造失败：", e);
                }
        	}
        }else{
            saleStrategy = new EmptyStrategy();
        }
        return saleStrategy;
    } 

}
