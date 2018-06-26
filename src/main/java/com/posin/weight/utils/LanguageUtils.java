package com.posin.weight.utils;

import android.content.Context;

import java.util.Locale;

/**
 * FileName: LanguageUtils
 * Author: Greetty
 * Time: 2018/6/25 13:29
 * Desc: TODO
 */
public class LanguageUtils {

    /**
     * 判断系统语言是否为中文
     *
     * @param context context
     * @return boolean
     */
    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

}
