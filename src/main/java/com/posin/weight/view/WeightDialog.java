package com.posin.weight.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.utils.DensityUtil;
import com.posin.weight.R;
import com.posin.weight.base.BaseDialog;
import com.posin.weight.been.MenuDetail;
import com.posin.weight.ui.presenter.WeightPresenter;
import com.posin.weight.utils.DensityUtils;
import com.posin.weight.utils.DoubleUtil;
import com.posin.weight.utils.StringUtils;
import com.posin.weight.utils.ThreadManage;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * FileName: WeightDialog
 * Author: Greetty
 * Time: 2018/6/21 15:20
 * Desc: TODO
 */
public class WeightDialog extends BaseDialog {

    @BindView(R.id.tv_weight_food_name)
    TextView tvWeightFoodName;
    @BindView(R.id.btn_weight_ok)
    Button btnWeightOk;
    @BindView(R.id.btn_weight_cancel)
    Button btnWeightCancel;
    @BindView(R.id.tv_get_weight_princes)
    TextView tvGetWeightPrinces;
    @BindView(R.id.tv_get_weight_weight)
    TextView tvGetWeightWeight;
    @BindView(R.id.tv_get_weight_money)
    TextView tvGetWeightMoney;

    private static final String TAG = "WeightDialog";
    /**
     * 获取并更新重量
     */
    private static final int CODE_GET_AND_UPDATE_WEIGHT = 101;

    private Context mContext;
    private String mFoodName;
    private double mPrices;
    private WeightPresenter mWeightPresenter;
    private WeightDialogView mWeightDialogView;

    //重量
    private float mWeight = 0;
    //小计
    private double mSubtotal = 0;

    public WeightDialog(Context context, String foodName, double prices,
                        WeightPresenter weightPresenter, WeightDialogView weightDialogView) {
        super(context);
        this.mContext = context;
        this.mFoodName = foodName;
        this.mPrices = prices;
        this.mWeightPresenter = weightPresenter;
        this.mWeightDialogView = weightDialogView;

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_GET_AND_UPDATE_WEIGHT:
                    try {
                        mWeight = mWeightPresenter.getWeight();
                        mSubtotal = DoubleUtil.round(mWeight * mPrices, 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tvGetWeightWeight.setText(StringUtils.append(mWeight, "KG"));
                    tvGetWeightMoney.setText(StringUtils.append(mSubtotal, " 元"));
                    Message message = new Message();
                    message.what = CODE_GET_AND_UPDATE_WEIGHT;
                    if (isShowing()) {
                        mHandler.sendMessageDelayed(message, 500);
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.alert_get_weight;
    }


    @Override
    public void initData() {
        //点击空白处不能取消dialog弹框
        setCanceledOnTouchOutside(false);

        //修改dialog弹框大小
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
//        lp.width = 900;//宽高可设置具体大小
//        lp.height = 550;
        //适配不同密度的机器屏幕
        lp.width = DensityUtils.dp2px(mContext, 600);
        lp.height = DensityUtils.dp2px(mContext, 367);
        this.getWindow().setAttributes(lp);

        //修改商品名称及商品单价
        tvWeightFoodName.setText(mFoodName);
        tvGetWeightPrinces.setText(StringUtils.append(mPrices, " 元"));


        //获取实时重量值，并更新重量
        // mHandler.sendEmptyMessage(CODE_GET_AND_UPDATE_WEIGHT);
    }


    @OnClick({R.id.btn_weight_cancel, R.id.btn_weight_ok})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_weight_cancel:
                mWeightDialogView.getWeightCancel();
                break;
            case R.id.btn_weight_ok:
                MenuDetail menuDetail = new MenuDetail(mFoodName, DoubleUtil.round(mWeight, 3),
                        mPrices, mSubtotal);
                mWeightDialogView.getWeightOk(menuDetail);
                break;
            default:
                break;
        }
    }

    /**
     * 更新重量值
     *
     * @param weight float
     */
    public void updateWeight(float weight) {
        if (isShowing()) {
            mWeight = weight;
            mSubtotal = DoubleUtil.round(mWeight * mPrices, 2);
            tvGetWeightWeight.setText(String.format("%.3f KG", mWeight));
            tvGetWeightMoney.setText(StringUtils.append(mSubtotal, " 元"));
            Message message = new Message();
            message.what = CODE_GET_AND_UPDATE_WEIGHT;
        }
    }


    /**
     * 获取重量dialog弹框接口方法
     */
    public interface WeightDialogView {
        /**
         * 获取该商品获取重量
         */
        void getWeightCancel();

        /**
         * 成功获取重量
         */
        void getWeightOk(MenuDetail menuDetail);
    }
}
