package com.posin.weight.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

/**
 * FileName: AbstractInputDialog
 * Author: Greetty
 * Time: 2018/6/28 13:46
 * Desc: TODO
 */
public abstract class AbstractInputDialog {

    protected final Context mContext;
    protected final AlertDialog mDlg;
    protected final View mView;

    protected final Button mBtnOk;

    protected abstract void onOk();

    protected abstract View createView(Context context);

    public AbstractInputDialog(Context context, String title) {
        mContext = context;
        mView = createView(context);
        mDlg = (new AlertDialog.Builder(context))
                .setTitle(title)
                .setView(mView)
                .setInverseBackgroundForced(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onOk();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

            mDlg.setCanceledOnTouchOutside(false);
        mBtnOk = mDlg.getButton(AlertDialog.BUTTON_POSITIVE);
    }
}
