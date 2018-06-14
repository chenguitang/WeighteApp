package com.posin.weight.utils;

/**
 * FileName: StringUtils
 * Author: Greetty
 * Time: 2018/6/14 15:48
 * Desc: TODO
 */
public class StringUtils {

    /**
     * 拼接字符串
     *
     * @param message 字符串
     * @return 拼接后字符串
     */
    public static String append(Object ... message) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : message) {
            sb.append(obj);
        }
        return sb.toString();
    }
}
