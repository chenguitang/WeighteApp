package com.posin.weight.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.posin.svr.IScaleService;
import com.posin.weight.R;
import com.posin.weight.been.Weight;
import com.posin.weight.module.weight.ErrorCode;
import com.posin.weight.ui.presenter.WeightPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName: SetWeightPointDialog
 * Author: Greetty
 * Time: 2018/6/28 13:45
 * Desc: TODO
 */

public class SetWeightPointDialog extends AbstractInputDialog {

    private static final String TAG = "SetWeightPointDialog";
    private static final int MAX_WEIGHT_COUNT = 5;
    private static final HashMap<Integer, Integer> mWeightMap = new HashMap<Integer, Integer>();

    private EditText mAdValue;
    private EditText mMaxWeight;
    private AdWeightItem[] mWeight;
    private EditText mPrecision;
    private EditText mWeightValue;

    private Handler mHandler = new Handler();
    private static IScaleService iWeight;
    private Context mContext;
    private WeightPresenter mWeightPresenter;

    private Runnable mUpdateAd = new Runnable() {
        @Override
        public void run() {
            if (iWeight != null) {
                int ad, weight;
                try {
                    ad = iWeight.getCurrentAd();
                    weight = mWeightPresenter.getWeightInstance().getNetWeight();
                    //Log.d(TAG, "ad="+ad+", temp="+tmp);
                    mAdValue.setText(String.valueOf(ad));
                    mWeightValue.setText(String.valueOf(weight));
                } catch (Throwable e) {
                    e.printStackTrace();
                    iWeight = null;
                }
            }
            mHandler.postDelayed(mUpdateAd, 500);
        }
    };

    public SetWeightPointDialog(Context context, WeightPresenter weightPresenter) {
        super(context, "电子秤设置");

        this.mContext = context;
        this.iWeight = weightPresenter.getIScaleService();
        this.mWeightPresenter = weightPresenter;
        init();
        mDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface arg0) {
                mHandler.removeCallbacks(mUpdateAd);
            }
        });

        if (iWeight != null)
            mHandler.postDelayed(mUpdateAd, 500);
    }

    @Override
    protected void onOk() {
    }

    @Override
    protected View createView(Context context) {
        LayoutInflater inf = LayoutInflater.from(context);
        View v = inf.inflate(R.layout.view_escale_settings2, null);

        mAdValue = (EditText) v.findViewById(R.id.ed_ad_value);
        mMaxWeight = (EditText) v.findViewById(R.id.ed_max_weight);
        mPrecision = (EditText) v.findViewById(R.id.ed_precision);
        mWeightValue = (EditText) v.findViewById(R.id.ed_weight);

        mWeight = new AdWeightItem[MAX_WEIGHT_COUNT];
        mWeight[4] = new AdWeightItem(30, R.id.ed_weight4, R.id.ed_ad4, R.id.btn_set_weight4, v, null);
        mWeight[3] = new AdWeightItem(15, R.id.ed_weight3, R.id.ed_ad3, R.id.btn_set_weight3, v, mWeight[4]);
        mWeight[2] = new AdWeightItem(10, R.id.ed_weight2, R.id.ed_ad2, R.id.btn_set_weight2, v, mWeight[3]);
        mWeight[1] = new AdWeightItem(1, R.id.ed_weight1, R.id.ed_ad1, R.id.btn_set_weight1, v, mWeight[2]);
        mWeight[0] = new AdWeightItem(0, R.id.ed_weight0, R.id.ed_ad0, R.id.btn_set_weight0, v, mWeight[1]);

        mWeight[0].mSetWeight.setEnabled(true);

        Button btn;

        btn = (Button) v.findViewById(R.id.btn_save_config);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = mWeightMap.size();
                if (count < 2) {
                    Toast.makeText(mContext, "标定数量不足，不能保存", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (iWeight != null) {
                    try {
                        int p = Integer.valueOf(mPrecision.getText().toString());
                        iWeight.setPrecision(p);
                        if (iWeight.setWeightMap(getWeightMapArray())) {
                            showMsg("成功", "保存标定数据成功!");
                        } else {
                            showError("保存标定数据失败. 请重试!");
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "错误: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return v;
    }

    private void init() {
        if (iWeight == null)
            return;

        int ec;
        try {
            ec = iWeight.getErrorCode();
            if (ec != ErrorCode.ERR_OK && ec != ErrorCode.ERR_NO_WEIGHT_TABLE) {
                mBtnOk.setEnabled(false);
                //return;
            }
            //else {
            {
                Weight w;
                try {
                    w = mWeightPresenter.getWeightInstance();

                    mMaxWeight.setText(String.valueOf(w.getMaxRange() / 1000));
                    mPrecision.setText(String.valueOf(w.getMinDivision()));

                    int[] wmap = iWeight.getWeightMap();
                    if (wmap != null) {
                        int count = wmap.length / 2;
                        for (int i = 0; i < count && i < MAX_WEIGHT_COUNT; i++) {
                            int weight = wmap[i * 2];
                            int ad = wmap[i * 2 + 1];
                            Log.d(TAG, "load table item " + i + " : " + weight + ", " + ad);
                            mWeight[i].mWeight = weight;
                            mWeight[i].mAd = ad;
                            mWeight[i].mSetWeight.setEnabled(true);
                            mWeight[i].mEdWeight.setText(String.valueOf(weight));
                            mWeight[i].mEdAd.setText(String.valueOf(ad));
                            if (i < MAX_WEIGHT_COUNT - 1) {
                                mWeight[i + 1].mSetWeight.setEnabled(true);
                            }
                        }
                    } else {
                        Log.e(TAG, "no weight table");
                    }

                } catch (Throwable e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (RemoteException e1) {
            e1.printStackTrace();
            Toast.makeText(mContext, e1.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private static class AdWeightItem {
        public int mWeight;
        public int mAd = 0;
        public EditText mEdWeight;
        public EditText mEdAd;
        public Button mSetWeight;

        public AdWeightItem mNext;

        public AdWeightItem(int weight, int ed_weight, int ed_ad, int btn, View v, AdWeightItem next) {
            mWeight = weight;
            mEdWeight = (EditText) v.findViewById(ed_weight);
            mEdAd = (EditText) v.findViewById(ed_ad);
            mSetWeight = (Button) v.findViewById(btn);
            mSetWeight.setEnabled(false);
            mNext = next;

            mSetWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (iWeight != null) {
                        try {
                            mWeight = Integer.parseInt(mEdWeight.getText().toString());
                            if (mWeight == 0) {
                                mWeightMap.clear();
                                mAd = iWeight.getCurrentAd();
                            } else {
                                mAd = iWeight.getCurrentAd();
                            }

                            mWeightMap.put(mWeight, mAd);

                            if (mNext != null) {
                                mNext.mSetWeight.setEnabled(true);
                                mNext.mEdWeight.setEnabled(true);
                            }
                            mEdAd.setText(String.valueOf(mAd));
                            Log.d(TAG, "set weight " + mWeight + ", ad " + mAd);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Toast.makeText(mSetWeight.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private int[] getWeightMapArray() {
        int count = mWeightMap.size();
        if(count > 0) {
            int[] map = new int[count*2];
            int index = 0;
            for(Map.Entry<Integer,Integer> i : mWeightMap.entrySet()) {
                map[index*2] = i.getKey();
                map[index*2+1] = i.getValue();
            }
            return map;
        }
        return null;
    }

    // 显示错误, 并关闭Activity
    private void showError(String msg) {
        showMsg("错误", msg);
    }

    // 显示对话框
    private void showMsg(String title, String msg) {
        new AlertDialog.Builder(mContext).setTitle(title).setMessage(msg)
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
