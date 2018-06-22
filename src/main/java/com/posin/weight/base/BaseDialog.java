package com.posin.weight.base;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * FileName: BaseDialog
 * Author: Greetty
 * Time: 2018/6/21 18:21
 * Desc: TODO
 */
public abstract class BaseDialog extends AlertDialog {

    public BaseDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initData();
    }

    public abstract int getLayoutId();

    public abstract void initData();

}
