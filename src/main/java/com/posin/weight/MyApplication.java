package com.posin.weight;

import android.app.Application;
import android.support.multidex.MultiDex;

/**
 * FileName: MyApplication
 * Author: Greetty
 * Time: 2018/5/23 20:06
 * Desc: TODO
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
