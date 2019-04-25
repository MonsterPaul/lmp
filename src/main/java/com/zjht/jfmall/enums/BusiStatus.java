package com.zjht.jfmall.enums;

public enum BusiStatus {

    SUCCESS(200, "success"),            //成功
    NOT_EXISTS(404, "不存在"),
    NO_AUTHORITY(401, "没有权限"),
    SERVER_ERROR(500, "服务器错误"),
    SERVER_BUSY(503, "服务器被怪兽啃了，正在抢救"),
    UNKNOWN(999, "unknown"),//未知错误

    INVALID_SERVICE(199, "invalid service"),//服务不可用
    BUSIERROR(4000, "服务器维护中，请稍后"),
    SERVER_EXCEPTION(5000, "service exception"),

    PARAM_ERROR(1900, "参数错误"),
    PARAMETER_ILLEGAL(1901, "参数格式错误"),
    UPDATE_FAILED(1902, "更新失败"),
    DEL_FAILED(1903, "删除失败"),
    SAVE_FAILED(1904, "保存失败"),
    PASSWORD_ERROR(1905, "密码错误"),
    NO_DATA(1906, "没有数据"),
    UNLAWFUL_REQUEST(1907, "非法请求"),
    DATE_ERROR(1908, "签到日期必须小于签退日期"),
    CAN_NOT_ASK_FOR_LEAVE(1909, "已请全天或者存在相同的请假"),
    BALANCE_INSUFFICIENT(1910, "余额不足100"),
    TOO_MONEY(1911, "提现金额不得大于余额"),
    NO_BANK(1912, "未绑定银行卡"),
    NO_USER_DATA(1913, "个人资料未认证"),
    NO_IDEN(1914, "通讯录未认证"),
    NO_OPERATOR(1915, "运营商未认证"),
    PHONE_OR_PWD_ERROE(1916, "手机号或者密码错误"),
    WECHAT_EXISTS(1917, "微信号已存在"),
    QQ_EXISTS(1918, "qq已存在"),
    BANKNO_EXISTS(1919, "银行卡号已存在"),
    IDCARD_EXISTS(1920, "身份证号已存在"),
    USER_NOT_LOGIN(1921, "账号被冻结"),
    ID_ERROR(1922, "邀请人ID错误，请求非法"),
    INVITATION_TYPE(1924, "注册格式错误"),
    MOENY_TOO_SMALL(1925, "金额小于100"),
    TOO_LOANS(1926, "单次贷款金额不得大于个人额度"),
    ID_FROZEN(1927, "邀请人ID冻结,无法注册"),
    NO_MONEY(1928, "贷超已下架"),

    NO_RIGHT(1401, "权限不足"),
    USER_NOT_EXISTS(1404, "用户不存在"),
    NEED_LOGIN(1407, "需要登录"),
    PHONE_EXISTS(1408, "手机号码已注册过"),
    USER_UPDATE_FAILED(1409, "更新失败"),
    USER_PASSWORD_ERROR(1410, "密码错误"),
    IS_UNBANG_STATUS(1411, "已经是解绑状态"),
    USER_NOT_FIRST_TIME_LOGIN(1412, "非首次登陆"),

    SMS_SEND_ERROR(4001, "短信发送失败"),
    PHONE_INVALID(4002, "请输入正确的手机号码"),
    SMS_CODE_ERROR(4003, "短信验证码错误，请重新输入！"),
    SMS_IP_TOO_OFTEN(4004, "获取短信过于频繁"),
    VERSION_IS_NULL(80001, "版本为空，请检查版本号"),
    VERSION_TOO_LOW(80002, "版本过低，请升级版本号");


    private final int    value;
    private final String reasonPhrase;

    private BusiStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}