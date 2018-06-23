/**
 * Copyright 2015 LittleBall Inc.
 */
package cn.tties.maint.util;

import java.net.URLDecoder;
import java.util.List;

/**
 * 字符串处理工具类.
 * @author Justin
 *
 */
public class StringUtil {

    /**
     * 判断字符串是否为空.
     * @param str 需要判断的字符串
     * @return 返回判断结果
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str) || "null".equals(str) ? true : false;
    }

    /**
     * 处理中文转码.
     * @param str String
     * @return String
     */
    public static String getChineseStr(String str) {
        if (isEmpty(str)) {
            return null;
        }
        String url = null;
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return URLDecoder.decode(str, "UTF-8");
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return new String((str).getBytes("ISO-8859-1"), "utf-8");
            }
        } catch (Exception exception1) {
        }
        return url;
    }

    public static String listToString(List<String> list) {
        String temp = "";
        for (String strl : list) {
            temp += strl + ",";
        }
        return temp;
    }
    /**
     *
     * @param ss String[]
     */
    public  static void main(String[] ss) {
        System.out.println(getChineseStr("ÓÃ»§1"));
        System.out.println(getChineseStr("管一枝花"));
        System.out.println(getChineseStr("%E5%AD%A4%E5%B4%96%E4%B8%80%E6%9E%9D%E8%8A%B1"));
        System.out.println(getChineseStr("æµç§°"));
    }
    public static String[] split(String value,String value2){
        String[] values={"null",value};
        if(value!=null&&!value.equals("")){
            String sub=new String(value);
            String[] split = sub.split(value2);
            return split;
        }
        return values;
    }
    public static String substring(String value,int start,int end){
        if(value!=null&&!value.equals("")){
            String sub=new String(value);
            String split = sub.substring(start,end);
            return split;
        }
        return "";
    }
}
