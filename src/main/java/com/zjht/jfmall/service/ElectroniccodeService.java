package com.zjht.jfmall.service;

import com.zjht.jfmall.common.dto.LayuiResultDto;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.entity.ElectroniCode;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 〈人寿积分电子码Service〉
 *
 * @author wangpeng
 * @create 2018/1/4
 * @since 1.0.0
 */
public interface ElectroniccodeService {

    /**
     * 获取电子码列表数据
     * @param electroniccode
      @param pageNum
     * @param pageSize
     * @return
     */
    List<ElectroniCode> getPage(ElectroniCode electroniccode, Integer pageNum, Integer pageSize);

    /**
     * 生成指定数量以及指定积分数的电子码
     * @param num
     * @param points
     * @return 生成的电子码集合
     */
    Set<String> insertElectroniccodeByNumAndPoints(int num, Integer points);

    /**
     * 获取电子码条数
     * @param electroniCode
     * @return
     */
    int getPageCount(ElectroniCode electroniCode);

    /**
     * 兑换电子码
     * @param mobile
     * @param code
     * @return
     */
    ResultDto exchangeCode(String mobile, String code);

    /**
     * 导入电子码文件
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    LayuiResultDto importFile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap);

    /**
     * 生成并发送电子码
     * @param pinots 积分
     * @param expireTime 有效期
     * @param electroniCodes
     * @return
     */
    LayuiResultDto createAndSendCode(String pinots,String expireTime,List<ElectroniCode> electroniCodes);

    /**
     * 重发记录为id的电子码短信
     * @param id
     * @return
     */
    ResultDto reSendCode(String id);

    /**
     * 作废记录为id的电子码
     * @param ids
     * @return
     */
    ResultDto abandonedCode(String[] ids);

}