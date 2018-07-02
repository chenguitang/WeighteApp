package com.posin.weight.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.posin.weight.R;
import com.posin.weight.base.BaseDialog;
import com.posin.weight.been.MenuDetail;
import com.posin.weight.ui.presenter.WeightPresenter;
import com.posin.weight.utils.DensityUtils;
import com.posin.weight.utils.DoubleUtil;
import com.posin.weight.utils.LanguageUtils;
import com.posin.weight.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * FileName: WeightDialog
 * Author: Greetty
 * Time: 2018/6/21 15:20
 * Description: TODO
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
    @BindView(R.id.tv_get_weight_weight_tip)
    TextView tvWeightTip;

    private static final String TAG = "WeightDialog";
    /**
     * 获取并更新重量
     */
    private static final int CODE_GET_AND_UPDATE_WEIGHT = 101;

    private Context mContext;
    private String mFoodName;
    private double mPrices;
    private boolean mWeightFood;
    private WeightPresenter mWeightPresenter;
    private WeightDialogView mWeightDialogView;

    //重量
    private float mWeight = 0;
    //小计
    private double mSubtotal = 0;

    //是否为中文
    private boolean isZh = true;

    public WeightDialog(Context context, String foodName, double prices, boolean weightFood,
                        WeightPresenter weightPresenter, WeightDialogView weightDialogView) {
        super(context);
        this.mContext = context;
        this.mFoodName = foodName;
        this.mPrices = prices;
        this.mWeightFood = weightFood;
        this.mWeightPresenter = weightPresenter;
        this.mWeightDialogView = weightDialogView;
        isZh = LanguageUtils.isZh(context);
    }


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
        tvGetWeightPrinces.setText(isZh ? StringUtils.append(mPrices, " 元") :
                StringUtils.append("$", mPrices));

        if (mWeightFood) {
            try {
                tvWeightTip.setText(R.string.get_weight_weight_tip);
                mWeight = mWeightPresenter.getWeight();
                mSubtotal = DoubleUtil.round(mWeight * mPrices, 2);
                tvGetWeightWeight.setText(String.format("%.3f KG", mWeight));
                tvGetWeightMoney.setText(isZh ? StringUtils.append(mSubtotal, " 元") :
                        StringUtils.append("$", mSubtotal));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            tvWeightTip.setText(R.string.amount);
            tvGetWeightWeight.setText("1");
            tvGetWeightMoney.setText(isZh ? StringUtils.append(mPrices, " 元") :
                    StringUtils.append("$", mPrices));
        }
    }


    @OnClick({R.id.btn_weight_cancel, R.id.btn_weight_ok})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_weight_cancel:
                mWeightDialogView.getWeightCancel();
                break;
            case R.id.btn_weight_ok:
                MenuDetail menuDetail;
                if (mWeightFood) {
                    menuDetail = new MenuDetail(mFoodName, DoubleUtil.round(mWeight, 2),
                            mPrices, mSubtotal);
                } else {
                    menuDetail = new MenuDetail(mFoodName, 1, mPrices, mPrices);
                }
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
            tvGetWeightMoney.setText(isZh ? StringUtils.append(mSubtotal, " 元") :
                    StringUtils.append("$", mSubtotal));
        }
    }


    /**
     * 获取商品单价
     *
     * @return double
     */
    public double getFoodPrices() {
        return mPrices;
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
