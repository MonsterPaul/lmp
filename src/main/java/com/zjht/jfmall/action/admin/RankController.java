package com.zjht.jfmall.action.admin;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.LayuiResultDto;
import com.zjht.jfmall.common.dto.LayuiResultSuccessDto;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.entity.NetLoan;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.*;
import com.zjht.jfmall.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈数据统计〉
 */
@Controller
@RequestMapping("/rank")
public class RankController {

    @Value("${generalize_id}")
    private String generalizeId;
    @Value("${service_id}")
    private String serviceId;
    @Value("${collection_id}")
    private String collectionId;
    @Value("${channel_id}")
    private String channelId;

    @Autowired
    private NetLoanService           netLoanService;
    @Autowired
    private UserService              userService;
    @Autowired
    private ChannelInvitationService invitationService;
    @Autowired
    private AppUserService           appUserService;
    @Autowired
    private AppUserLoanPlatService   appUserLoanPlatService;
    @Autowired
    private AppUserInvitationService appUserInvitationService;

    @RequestMapping(value = "/netLoanRank.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String loanRankingList(HttpServletRequest request, ModelMap modelMap, NetLoan netLoan) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", netLoan);

        if (StringUtils.isEmpty(netLoan.getExchangeTimeBegin())) {
            netLoan.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }

        PageInfo<NetLoan> pageInfo = netLoanService.getNetloanRank(netLoan, 1, 1000000);
        pageInfo.getList().stream().forEach(net -> {
            //统计平台的已收佣金
            net.setExchangeTimeBegin(netLoan.getExchangeTimeBegin());
            Double useComm = appUserLoanPlatService.sumUseCommByPlatIds(net);
            useComm = useComm == null ? 0D : useComm;
            Double stopComm = appUserLoanPlatService.sumStopCommByPlatIds(net);
            stopComm = stopComm == null ? 0D : stopComm;
            net.setAmountCount(useComm + stopComm);
        });

        List<NetLoan> netLoanList = pageInfo.getList();

        int    userAmounts  = netLoanList.stream().collect(Collectors.summingInt(NetLoan::getUserAmount));
        double amountCounts = netLoanList.stream().collect(Collectors.summingDouble(NetLoan::getAmountCount));
        int    userCounts   = netLoanList.stream().collect(Collectors.summingInt(NetLoan::getUserCount));
        modelMap.put("userAmounts", userAmounts);
        modelMap.put("amountCounts", amountCounts);
        modelMap.put("userCounts", userCounts);

        return "rank/netLoan/list";
    }

    @ResponseBody
    @RequestMapping(value = "/netLoanRank.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto loanRanking(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, NetLoan netLoan) {
        if (StringUtils.isEmpty(netLoan.getExchangeTimeBegin())) {
            netLoan.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }
        PageInfo<NetLoan> pageInfo = netLoanService.getNetloanRank(netLoan, pageNum, pageSize);
        pageInfo.getList().stream().forEach(net -> {
            //统计平台的已收佣金
            net.setExchangeTimeBegin(netLoan.getExchangeTimeBegin());
            Double useComm = appUserLoanPlatService.sumUseCommByPlatIds(net);
            useComm = useComm == null ? 0D : useComm;
            Double stopComm = appUserLoanPlatService.sumStopCommByPlatIds(net);
            stopComm = stopComm == null ? 0D : stopComm;
            net.setAmountCount(useComm + stopComm);
        });

        List<NetLoan> netLoanList = new ArrayList<>();

        switch (netLoan.getDealStatus()) {
            case "1":
                netLoanList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(NetLoan::getUserAmount).reversed()).collect(Collectors.toList()));
                break;
            case "2":
                netLoanList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(NetLoan::getUserCount).reversed()).collect(Collectors.toList()));
                break;
            case "3":
                netLoanList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(NetLoan::getAmountCount).reversed()).collect(Collectors.toList()));
                break;
            default:
                netLoanList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(NetLoan::getUserAmount).reversed()).collect(Collectors.toList()));

        }

        int    userAmounts  = netLoanList.stream().collect(Collectors.summingInt(NetLoan::getUserAmount));
        double amountCounts = netLoanList.stream().collect(Collectors.summingDouble(NetLoan::getAmountCount));
        int    userCounts   = netLoanList.stream().collect(Collectors.summingInt(NetLoan::getUserCount));
        modelMap.put("userAmounts", userAmounts);
        modelMap.put("amountCounts", amountCounts);
        modelMap.put("userCounts", userCounts);

        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, netLoanList, pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/generalize.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String generalizeList(HttpServletRequest request, ModelMap modelMap, User bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);

        if (StringUtils.isEmpty(bean.getExchangeTimeBegin())) {
            bean.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }
        PageInfo<User> pageInfo = userService.findUserByRoleId(bean, generalizeId, 1, 100000000);
        pageInfo.getList().stream().forEach(user -> {
            user.setExchangeTimeBegin(bean.getExchangeTimeBegin());
            user.setChannelNum(userService.countChannelNums(user));//渠道商数量
            user.setRegistNum(invitationService.countByChannelIds(user));//有效注册总量
            //有效注册量
            user.setNotRegister(invitationService.countAllByChannelIds(user));
            user.setAmountCount(appUserService.successCount(user));//已放款量
        });

        int    channelNums  = pageInfo.getList().stream().collect(Collectors.summingInt(User::getChannelNum));
        double registNums   = pageInfo.getList().stream().collect(Collectors.summingInt(User::getRegistNum));
        int    notRegisters = pageInfo.getList().stream().collect(Collectors.summingInt(User::getNotRegister));
        modelMap.put("channelNums", channelNums);
        modelMap.put("registNums", registNums);
        modelMap.put("notRegisters", notRegisters);

        return "rank/generalize/list";
    }

    @ResponseBody
    @RequestMapping(value = "/generalize.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto generalize(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, User bean) {
        if (StringUtils.isEmpty(bean.getExchangeTimeBegin())) {
            bean.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }
        PageInfo<User> pageInfo = userService.findUserByRoleId(bean, generalizeId, pageNum, pageSize);
        pageInfo.getList().stream().forEach(user -> {
            user.setExchangeTimeBegin(bean.getExchangeTimeBegin());
            user.setChannelNum(userService.countChannelNums(user));//渠道商数量
            user.setRegistNum(invitationService.countByChannelIds(user));//有效注册总量
            //有效注册量
            user.setNotRegister(invitationService.countAllByChannelIds(user));
            user.setAmountCount(appUserService.successCount(user));//已放款量
        });

        List<User> userList = new ArrayList<>();
        switch (bean.getDealStatus()) {
            case "1":
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getChannelNum).reversed()).collect(Collectors.toList()));
                break;
            case "2":
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getNotRegister).reversed()).collect(Collectors.toList()));
                break;
            case "3":
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getRegistNum).reversed()).collect(Collectors.toList()));
                break;
            default:
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getChannelNum).reversed()).collect(Collectors.toList()));

        }
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, userList, pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/service.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String service(HttpServletRequest request, ModelMap modelMap, User bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);

        if (StringUtils.isEmpty(bean.getExchangeTimeBegin())) {
            bean.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }
        PageInfo<User> pageInfo = userService.findUserByRoleId(bean, serviceId, 1, 10000000);
        pageInfo.getList().stream().forEach(user -> {
            user.setExchangeTimeBegin(bean.getExchangeTimeBegin());
            Double loanSum = appUserLoanPlatService.loanSums(user);
            user.setSunLoan(loanSum == null ? 0.00 : loanSum);//已放款总额
            Double commissionSum = appUserLoanPlatService.commissionSums(user);
            user.setSunCommission(commissionSum == null ? 0.00 : commissionSum);//已收佣金总和
            //维护客户数
            user.setRegister(appUserService.countByServiceIds(user));
            //已放款用户数
            user.setNotRegister(appUserLoanPlatService.countSuccesss(user));
            user.setFkpts(appUserLoanPlatService.getSqpts(user));
        });

        Double sunLoans       = pageInfo.getList().stream().collect(Collectors.summingDouble(User::getSunLoan));
        double sunCommissions = pageInfo.getList().stream().collect(Collectors.summingDouble(User::getSunCommission));
        int    registers      = pageInfo.getList().stream().collect(Collectors.summingInt(User::getRegister));
        int    notRegisters   = pageInfo.getList().stream().collect(Collectors.summingInt(User::getNotRegister));
        int sqpts=pageInfo.getList().stream().collect(Collectors.summingInt(User::getFkpts));
        modelMap.put("sunLoans", sunLoans);
        modelMap.put("sunCommissions", sunCommissions);
        modelMap.put("registers", registers);
        modelMap.put("notRegisters", notRegisters);
        modelMap.put("sqpts", sqpts);
        return "rank/service/list";
    }

    @ResponseBody
    @RequestMapping(value = "/service.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto service(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, User bean) {
        if (StringUtils.isEmpty(bean.getExchangeTimeBegin())) {
            bean.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }
        PageInfo<User> pageInfo = userService.findUserByRoleId(bean, serviceId, pageNum, pageSize);
        pageInfo.getList().stream().forEach(user -> {
            user.setExchangeTimeBegin(bean.getExchangeTimeBegin());
            Double loanSum = appUserLoanPlatService.loanSums(user);
            user.setSunLoan(loanSum == null ? 0.00 : loanSum);//已放款总额
            Double commissionSum = appUserLoanPlatService.commissionSums(user);
            user.setSunCommission(commissionSum == null ? 0.00 : commissionSum);//已收佣金总和
            //维护客户数
            user.setRegister(appUserService.countByServiceIds(user));
            //已放款用户数
            user.setNotRegister(appUserLoanPlatService.countSuccesss(user));
            user.setFkpts(appUserLoanPlatService.getSqpts(user));
        });

        List<User> userList = new ArrayList<>();
        switch (bean.getDealStatus()) {
            case "1":
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getRegister).reversed()).collect(Collectors.toList()));
                break;
            case "2":
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getNotRegister).reversed()).collect(Collectors.toList()));
                break;
            case "3":
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getSunCommission).reversed()).collect(Collectors.toList()));
                break;
            case "4":
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getFkpts).reversed()).collect(Collectors.toList()));
                break;
            default:
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getRegister).reversed()).collect(Collectors.toList()));

        }
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, userList, pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/collection.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String collection(HttpServletRequest request, ModelMap modelMap, User bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        if (StringUtils.isEmpty(bean.getExchangeTimeBegin())) {
            bean.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }
        PageInfo<User> pageInfo = userService.findUserByRoleId(bean, collectionId, 1, 10000000);
        pageInfo.getList().stream().forEach(user -> {
            user.setExchangeTimeBegin(bean.getExchangeTimeBegin());
            Integer userNum = appUserLoanPlatService.userNum(user);
            user.setUserNum(userNum == null ? 0 : userNum);//用户数量
            Double commissionSum = appUserLoanPlatService.commissionSumCollections(user);
            user.setSunCommission(commissionSum == null ? 0.00 : commissionSum);//已收佣金总数
            Double useAmount = appUserLoanPlatService.sumUseAmountByCollId(user.getId());
            user.setSunLoan(useAmount == null ? 0 : useAmount);//总放款金额
            Double applySum = appUserLoanPlatService.sumApplyCommByCollId(user.getId());
            user.setApplyAmount(applySum == null ? 0 : applySum);
        });
        double sunCommissions = pageInfo.getList().stream().collect(Collectors.summingDouble(User::getSunCommission));
        modelMap.put("sunCommissions", sunCommissions);

        int userSum = pageInfo.getList().stream().collect(Collectors.summingInt(User::getUserNum));
        modelMap.put("userSum", userSum);

        double loanSum = pageInfo.getList().stream().collect(Collectors.summingDouble(User::getSunLoan));
        modelMap.put("loanSum", loanSum);

        double applySum = pageInfo.getList().stream().collect(Collectors.summingDouble(User::getApplyAmount));
        modelMap.put("applySum", applySum);

        return "rank/collection/list";
    }

    @ResponseBody
    @RequestMapping(value = "/collection.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto collection(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, User bean) {
        PageInfo<User> pageInfo = userService.findUserByRoleId(bean, collectionId, pageNum, pageSize);
        if (StringUtils.isEmpty(bean.getExchangeTimeBegin())) {
            bean.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }
        pageInfo.getList().stream().forEach(user -> {
            user.setExchangeTimeBegin(bean.getExchangeTimeBegin());
            Integer userNum = appUserLoanPlatService.userNum(user);
            user.setUserNum(userNum == null ? 0 : userNum);//用户数量
            Double commissionSum = appUserLoanPlatService.commissionSumCollections(user);
            user.setSunCommission(commissionSum == null ? 0.00 : commissionSum);//已收佣金总数
        });
        List<User> userList = new ArrayList<>();
        switch (bean.getDealStatus()) {
            case "1":
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getUserNum).reversed()).collect(Collectors.toList()));
                break;
            case "2":
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getSunCommission).reversed()).collect(Collectors.toList()));
                break;
            default:
                userList.addAll(pageInfo.getList().stream().sorted(Comparator.comparing(User::getSunCommission).reversed()).collect(Collectors.toList()));

        }
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, userList, pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/countdate.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String countdate(HttpServletRequest request, ModelMap modelMap, String time) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("time", time == null ? "" : time);
        String startTime = "";
        String endTime   = "";
        if (StringUtils.isNotBlank(time)) {
            startTime = time + "-01 00:00:00";
            endTime = time + "-31 23:59:59";
        }
        //渠道数量
        Integer qdsl = userService.countByTime(channelId, startTime, endTime);
        modelMap.put("qdsl", qdsl != null ? qdsl : 0);
        //客户注册量
        Integer khzcl = appUserService.countByTime(startTime, endTime);
        modelMap.put("khzcl", khzcl != null ? khzcl : 0);
        //已放款数量
        Integer yfksl = appUserService.countNumByTime(startTime, endTime);
        modelMap.put("yfksl", yfksl != null ? yfksl : 0);
        //客服已收佣金量
        Double kfysyj = appUserLoanPlatService.sumUseComm(startTime, endTime);
        modelMap.put("kfysyj", kfysyj != null ? kfysyj : 0);
        //催收员收佣金量
        Double csysyj = appUserLoanPlatService.sumStopComm(startTime, endTime);
        modelMap.put("csysyj", csysyj != null ? csysyj : 0);
        //收入（佣金）
        modelMap.put("sr", (kfysyj != null ? kfysyj : 0) + (csysyj != null ? csysyj : 0));
        //未收佣金
        Double wsrj = (appUserLoanPlatService.sumNotAmount(startTime, endTime)!=null?appUserLoanPlatService.sumNotAmount(startTime, endTime):0)-(kfysyj != null ? kfysyj : 0) - (csysyj != null ? csysyj : 0);
        modelMap.put("wsrj", wsrj != null ? wsrj : 0);
        //支出
        Double zc = appUserInvitationService.sumMoney(startTime, endTime);
        modelMap.put("zc", zc != null ? zc : 0);
        return "rank/data/list";
    }
}