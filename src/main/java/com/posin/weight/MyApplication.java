package com.posin.weight;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * FileName: MyApplication
 * Author: Greetty
 * Time: 2018/5/23 20:06
 * Description: TODO
 */
public class MyApplication extends Application {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mApplicationContext = this.getApplicationContext();
    }

    public static Context getContext() {

        return mApplicationContext;
    }
}
