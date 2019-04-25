package com.zjht.jfmall.action.common.strategy.common;

import com.zjht.jfmall.action.common.strategy.AbstractStrategy;
import com.zjht.jfmall.entity.OrderExchange;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 空策略
 * @author caozhaokui
 *
 */
public class EmptyStrategy extends AbstractStrategy {


	@Override
	public void execute(OrderExchange order) {
	}
}
