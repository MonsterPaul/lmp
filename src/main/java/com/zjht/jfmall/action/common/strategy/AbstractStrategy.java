package com.zjht.jfmall.action.common.strategy;

import com.zjht.jfmall.entity.OrderExchange;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractStrategy implements Cloneable {

	/**
	 * 日志记录
	 */
	public final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 策略执行方法，交给子类实现
	 * @param order
	 */
	public abstract void execute(OrderExchange order);
	
    @SuppressWarnings("rawtypes")
	public AbstractStrategy() {
        WebApplicationContext webContent = ContextLoader.getCurrentWebApplicationContext();
        Field[] fields = this.getClass().getDeclaredFields();
        boolean hasFather = true;
        Class<?> clazz = this.getClass();
        //将this所有的注入属性赋值
        while(hasFather){
            clazz = clazz.getSuperclass();
            if(clazz==null){
                break;
            }
            Field[] fatherFields = clazz.getDeclaredFields();
            fields = ArrayUtils.addAll(fields, fatherFields);
            if(clazz.getName().equals(AbstractStrategy.class.getName())){
                hasFather = false;
            }
        }
        //对field进行赋值
        for (int i = 0; i < fields.length; i++) {
            if(fields[i].isAnnotationPresent(Autowired.class)){
                fields[i].setAccessible(true);
				try {
					Map beanMap = webContent.getBeansOfType(fields[i].getType());
					Iterator iter = beanMap.entrySet().iterator();
					Map.Entry entry = (Map.Entry)iter.next();
					fields[i].set(this, entry.getValue());
				} catch (Exception e) {
					log.error("初始化产品购买策略失败",e);
				}
            }
        }
    }


	@Override
	public AbstractStrategy clone() throws CloneNotSupportedException {
		AbstractStrategy strategy = (AbstractStrategy)super.clone();
		return strategy;
	}
}
