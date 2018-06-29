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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.posin.svr.IScaleService;
import com.posin.weight.R;
import com.posin.weight.been.Weight;
import com.posin.weight.ui.presenter.WeightPresenter;

import java.util.HashMap;

/**
 * FileName: SetWeightPointDialog
 * Author: Greetty
 * Time: 2018/6/28 13:45
 * Desc: TODO
 */

public class SetWeightPointDialog extends AbstractInputDialog {

    private static final String TAG = "SetWeightPointDialog";
    private static final int MAX_WEIGHT_COUNT = 5;

    private EditText mAdValue;
    private EditText mMaxWeight;
    private AdWeightItem[] mWeightItems;
    private EditText mPrecision;
    private EditText mWeightValue;
    private CheckBox mAutoPrecision;
    private Button mBtnSave;

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


    class AdWeightItem {
        public final int mIndex;
        public int mWeight;
        public int mAd = 0;
        public final EditText mEdWeight;
        public final EditText mEdAd;
        public final Button mBtnSetWeight;

        public void clear() {
            mAd = 0;
            mEdAd.getText().clear();
            mEdWeight.setEnabled(false);
            mBtnSetWeight.setEnabled(mWeight == 0);
        }

        public void enable() {
            mBtnSetWeight.setEnabled(true);
            mEdWeight.setEnabled(true);
        }

        public AdWeightItem(int index, int weight, int ed_weight, int ed_ad, int btn, View v) {
            mIndex = index;
            mWeight = weight;
            mEdWeight = (EditText) v.findViewById(ed_weight);
            mEdAd = (EditText) v.findViewById(ed_ad);
            mBtnSetWeight = (Button) v.findViewById(btn);
            mBtnSetWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClick();
                }
            });
            clear();
        }

        public void onButtonClick() {
            if (iWeight == null)
                return;

            if (mWeight == 0) {
                // 标定零点，清空之前标定的数据
                for (int i = 0; i < mWeightItems.length; i++)
                    mWeightItems[i].clear();
                mBtnSave.setEnabled(false);
            }

            try {
                mWeight = Integer.parseInt(mEdWeight.getText().toString());
            } catch (Throwable e) {
                e.printStackTrace();
                Toast.makeText(mContext, "标定重量值不正确，请重新输入!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mIndex != 0) {
                if (mWeight <= mWeightItems[mIndex - 1].mWeight) {
                    Toast.makeText(mContext, "错误: 标定重量值不能比前一个值小!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            try {
                int ad = iWeight.getSteadyAd(2000);
                if (ad <= 0) {
                    Toast.makeText(mContext, "无法获取稳定值，请重试!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAd = ad;
                mEdAd.setText(String.valueOf(mAd));

                if (mIndex < MAX_WEIGHT_COUNT - 1)
                    mWeightItems[mIndex + 1].enable();

                if (mWeight > 0)
                    mBtnSave.setEnabled(true);

                Log.d(TAG, "set weight " + mWeight + ", ad " + mAd);
            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(mBtnSetWeight.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    ;


    @Override
    protected View createView(Context context) {
        LayoutInflater inf = LayoutInflater.from(context);
        View v = inf.inflate(R.layout.view_escale_settings2, null);

        mAdValue = (EditText) v.findViewById(R.id.ed_ad_value);
        mMaxWeight = (EditText) v.findViewById(R.id.ed_max_weight);
        mPrecision = (EditText) v.findViewById(R.id.ed_precision);
        mWeightValue = (EditText) v.findViewById(R.id.ed_weight);
        mAutoPrecision = (CheckBox) v.findViewById(R.id.cb_auto_precision);

        mWeightItems = new AdWeightItem[MAX_WEIGHT_COUNT];
        mWeightItems[4] = new AdWeightItem(4, 30, R.id.ed_weight4, R.id.ed_ad4, R.id.btn_set_weight4, v);
        mWeightItems[3] = new AdWeightItem(3, 15, R.id.ed_weight3, R.id.ed_ad3, R.id.btn_set_weight3, v);
        mWeightItems[2] = new AdWeightItem(2, 10, R.id.ed_weight2, R.id.ed_ad2, R.id.btn_set_weight2, v);
        mWeightItems[1] = new AdWeightItem(1, 1, R.id.ed_weight1, R.id.ed_ad1, R.id.btn_set_weight1, v);
        mWeightItems[0] = new AdWeightItem(0, 0, R.id.ed_weight0, R.id.ed_ad0, R.id.btn_set_weight0, v);

        mWeightItems[0].mBtnSetWeight.setEnabled(true);

        mBtnSave = (Button) v.findViewById(R.id.btn_save_config);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] weightMap = getWeightMapArray();
                int count = weightMap != null ? weightMap.length / 2 : 0;
                if (count < 2) {
                    Toast.makeText(mContext, "标定数量不足，不能保存", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (iWeight != null) {
                    try {
                        int p = Integer.valueOf(mPrecision.getText().toString());
                        boolean autop = mAutoPrecision.isChecked();
                        Log.d(TAG, "p=" + p + ", autop=" + autop + ", count=" + count);
                        for (int i = 0; i < count; i++) {
                            Log.d(TAG, "W=" + weightMap[i * 2] + ", AD=" + weightMap[i * 2 + 1]);
                        }
                        if (iWeight.setWeightConfig(weightMap, p, autop)) {
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

        mAutoPrecision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean checked) {
                mPrecision.setEnabled(!checked);
            }
        });

        return v;
    }

    private void init() {

        try {
            mBtnSave.setEnabled(false);

            Weight w;

            w = mWeightPresenter.getWeightInstance();

            int pointnumber = w.getPointnumber();
            if (pointnumber == 1)
                mMaxWeight.setText(String.valueOf(w.getMaxRange()));
            else if (pointnumber == 3)
                mMaxWeight.setText(String.valueOf(w.getMaxRange() / 1000));
            else
                mMaxWeight.setText("错误");

            //mPrecision.setText(String.valueOf(w.getMinDivision())); // 如果是自动选择模式，getMinDivision()是自动生成的值，不是用户设定的值
            mPrecision.setText(String.valueOf(iWeight.getPrecision()));
            boolean autoPrecision = iWeight.getAutoPrecision();
            mAutoPrecision.setChecked(autoPrecision);
            mPrecision.setEnabled(!autoPrecision);

            //mWeightMap.clear();

            int[] wmap = iWeight.getWeightMap();
            if (wmap != null) {
                int count = wmap.length / 2;
                for (int i = 0; i < count && i < MAX_WEIGHT_COUNT; i++) {
                    int weight = wmap[i * 2];
                    int ad = wmap[i * 2 + 1];
                    Log.d(TAG, "load table item " + i + " : " + weight + ", " + ad);
                    //mWeightMap.put(weight, ad);
                    mWeightItems[i].mWeight = weight;
                    mWeightItems[i].mAd = ad;
                    mWeightItems[i].mBtnSetWeight.setEnabled(true);
                    mWeightItems[i].mEdWeight.setText(String.valueOf(weight));
                    mWeightItems[i].mEdAd.setText(String.valueOf(ad));
                    if (i < MAX_WEIGHT_COUNT - 1) {
                        mWeightItems[i + 1].mBtnSetWeight.setEnabled(true);
                    }
                }
                if (count >= 2)
                    mBtnSave.setEnabled(true);
            } else {
                Log.e(TAG, "no weight table");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            Toast.makeText(mContext, e1.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private int[] getWeightMapArray() {
        int count = 0;
        for (int i = 0; i < MAX_WEIGHT_COUNT; i++) {
            AdWeightItem wi = mWeightItems[i];
            if (wi.mAd > 0) {
                count++;
            } else {
                break;
            }
        }
        if (count == 0)
            return null;

        int[] result = new int[count * 2];
        for (int i = 0; i < count; i++) {
            AdWeightItem wi = mWeightItems[i];
            result[i * 2] = wi.mWeight;
            result[i * 2 + 1] = wi.mAd;
        }
        return result;
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
