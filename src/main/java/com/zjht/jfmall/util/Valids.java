package com.zjht.jfmall.util;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * 校验工具类
 */
public class Valids {

    //public static final String PHONE_REGEX = "^(0|86|17951)?(13[0-9]|15[012356789]|16[0-9]|17[0-9]|18[0-9]|14[57]|19[0-9])[0-9]{8}$"; //含区号
    public static final String PHONE_REGEX = "^1(3[0-9]|4[57]|5[0-9]|8[0-9]|7[0-9])\\d{8}$"; //不含区号
    private static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 生成uuid
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 手机号判断
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {
        return isBlank(str) ? false : str.matches(PHONE_REGEX);
    }

    /**
     * 邮箱判断
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        return isBlank(str) ? false : str.matches(EMAIL_REGEX);
    }

    /**
     * 空判断
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (isEmpty(str)) {
            return true;
        }
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 空判断
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isEmpty(Object[] arrs) {
        return arrs == null || arrs.length == 0;
    }

    /**
     * 判断字符串数组是否有空字符串
     *
     * @param arrs
     * @return
     */
    public static boolean hasEmpty(String... arrs) {
        if (arrs == null || arrs.length == 0) {
            return true;
        }
        for (String str : arrs) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }


    /**
     * md5加密
     * @param param 明文密码或者是明文md5之后的结果
     * @return
     */
    public static String MD5Hex(String param) {
//        if(32 == param.length()){//这是明文md5之后的结果
//            return genEncryptPwdByMd5pwd(param);
//        }else {
//            String md5Pwd = DigestUtils.md5DigestAsHex(param.getBytes());//先md5
//            return genEncryptPwdByMd5pwd(md5Pwd);
//        }
        return DigestUtils.md5DigestAsHex(param.getBytes());
    }

    /**
     @param md5Pwd: 明文md5之后的字符串
     */
    public static String genEncryptPwdByMd5pwd(String md5Pwd){
        return new StringBuilder()
                .append(md5Pwd).reverse()
                .delete(2, 4).delete(8, 11).delete(3, 9).toString()
                .toUpperCase();
    }

    public static boolean isDouble(String str){
        if(isBlank(str)){
            return false;
        }

        try{
            Double.parseDouble(str);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }

}
