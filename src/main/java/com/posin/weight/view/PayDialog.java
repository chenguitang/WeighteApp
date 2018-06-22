package com.posin.weight.view;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.posin.weight.R;
import com.posin.weight.base.BaseDialog;
import com.posin.weight.utils.DensityUtils;

/**
 * FileName: PayDialog
 * Author: Greetty
 * Time: 2018/6/22 15:43
 * Desc: TODO
 */
public class PayDialog extends BaseDialog {

    private Context mContext;

    public PayDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.alert_menu_pay;
    }

    @Override
    public void initData() {
        //点击空白处不能取消dialog弹框
        setCanceledOnTouchOutside(false);

        //修改dialog弹框大小
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        //适配不同密度的机器屏幕
        lp.width = DensityUtils.dp2px(mContext, 650);
        lp.height = DensityUtils.dp2px(mContext, 500);
        this.getWindow().setAttributes(lp);




    }
}
