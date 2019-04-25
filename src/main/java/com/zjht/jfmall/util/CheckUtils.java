package com.zjht.jfmall.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckUtils {
    /**
     * 检查是否缺少请求参数或参数为空字符串
     *
     * @param mapField
     * @param params
     * @return
     */
    public static boolean checkParam(Map<String, String> mapField, String... params) {
        boolean flag = true;
        for (String param : params) {
            if (!mapField.containsKey(param)) {
                flag = false;
                break;
            } else {
                String value = mapField.get(param).toString();
                if (StringUtils.isEmpty(value)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 将json形式字符串转换为java实体类
     */
    public static <T> T parse(String jsonStr, Class<T> clazz) {
        ObjectMapper om        = new ObjectMapper();
        T            readValue = null;
        try {
            readValue = om.readValue(jsonStr, clazz);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return readValue;
    }

    /**
     * 将jsonArray装换为javabean对象
     *
     * @return
     */
    public static <T> T mapToBean(JSONArray jsonArray, Class<T> t) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            Object     o          = jsonArray.get(i);
            JSONObject jsonObject = JSONObject.parseObject(o.toString());
            map.put(underlineToCamel(jsonObject.getString("key")), jsonObject.getString("value"));
        }
        JSONObject json = new JSONObject(map);
        Gson       gson = new Gson();
        return gson.fromJson(json.toJSONString(), t);
    }


    private static final char UNDERLINE = '_';

    public static String camelToUnderline(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        StringBuilder sb  = new StringBuilder();
        int           len = param.length();
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String underlineToCamel(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        StringBuilder sb  = new StringBuilder();
        int           len = param.length();
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}