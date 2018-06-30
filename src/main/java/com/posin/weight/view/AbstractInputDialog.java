package com.posin.weight.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import com.posin.weight.R;

/**
 * FileName: AbstractInputDialog
 * Author: Greetty
 * Time: 2018/6/28 13:46
 * Description: TODO
 */
public abstract class AbstractInputDialog {

    protected final Context mContext;
    protected final AlertDialog mDlg;
    protected final View mView;

    protected abstract View createView(Context context);

    public AbstractInputDialog(Context context, String title) {
        mContext = context;
        mView = createView(context);
        mDlg = (new AlertDialog.Builder(context))
                .setTitle(title)
                .setView(mView)
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

            mDlg.setCanceledOnTouchOutside(false);
    }
}
