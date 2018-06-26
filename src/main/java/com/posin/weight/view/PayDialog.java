package com.posin.weight.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.posin.weight.R;
import com.posin.weight.base.BaseDialog;
import com.posin.weight.utils.DensityUtils;
import com.posin.weight.utils.DoubleUtil;
import com.posin.weight.utils.LanguageUtils;
import com.posin.weight.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * FileName: PayDialog
 * Author: Greetty
 * Time: 2018/6/22 15:43
 * Desc: TODO
 */
public class PayDialog extends BaseDialog implements View.OnLongClickListener, TextWatcher {

    private static final String TAG = "PayDialog";
    //支付金额最大一种占十个字符串
    private static final int MAX_PAY_UP_SIZE = 10;

    @BindView(R.id.tv_pay_sum)
    TextView tvPaySum;
    @BindView(R.id.et_pay_up)
    EditText etPayUp;
    @BindView(R.id.tv_pay_change)
    TextView tvPayChange;
    @BindView(R.id.btn_keyboard_delete)
    Button btnKeyboardDelete;


    private Context mContext;
    private String mPayUp;
    private double mMenuSumMoney;
    private double mChangeMoney;
    private boolean mIsLcd = false;
    private IPayView mIPayView;

    public PayDialog(Context context, double sum, boolean isLcd, IPayView iPayView) {
        super(context);
        this.mContext = context;
        this.mMenuSumMoney = sum;
        this.mIsLcd = isLcd;
        this.mIPayView = iPayView;
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

        tvPaySum.setText(String.valueOf(mMenuSumMoney));
        etPayUp.setText(String.valueOf(mMenuSumMoney));
        etPayUp.selectAll();

        if (mIsLcd) {
            mIPayView.displayPayMessage(String.valueOf(mMenuSumMoney),
                    String.valueOf(mMenuSumMoney), "0.0", "0.0");
        } else {
            mIPayView.displayTotal(String.valueOf(mMenuSumMoney));
        }
        btnKeyboardDelete.setOnLongClickListener(this);
        etPayUp.addTextChangedListener(this);
    }

    @OnClick({R.id.btn_keyboard_1, R.id.btn_keyboard_2, R.id.btn_keyboard_3, R.id.btn_keyboard_4,
            R.id.btn_keyboard_5, R.id.btn_keyboard_6, R.id.btn_keyboard_7, R.id.btn_keyboard_8,
            R.id.btn_keyboard_9, R.id.btn_keyboard_spot, R.id.btn_keyboard_zero,
            R.id.btn_keyboard_delete, R.id.btn_pay_ok, R.id.btn_pay_cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_keyboard_1:
                keyboardClick("1");
                break;
            case R.id.btn_keyboard_2:
                keyboardClick("2");
                break;
            case R.id.btn_keyboard_3:
                keyboardClick("3");
                break;
            case R.id.btn_keyboard_4:
                keyboardClick("4");
                break;
            case R.id.btn_keyboard_5:
                keyboardClick("5");
                break;
            case R.id.btn_keyboard_6:
                keyboardClick("6");
                break;
            case R.id.btn_keyboard_7:
                keyboardClick("7");
                break;
            case R.id.btn_keyboard_8:
                keyboardClick("8");
                break;
            case R.id.btn_keyboard_9:
                keyboardClick("9");
                break;
            case R.id.btn_keyboard_spot:
                mPayUp = etPayUp.getText().toString().trim();
                if (!mPayUp.contains(".") && mPayUp.length() > 0) {
                    keyboardClick(".");
                } else {
                    Log.e(TAG, "money have spot ... ");
                }

                break;
            case R.id.btn_keyboard_zero:
                mPayUp = etPayUp.getText().toString().trim();
                if (mPayUp.length() > 0) {
                    keyboardClick("0");
                }
                break;
            case R.id.btn_keyboard_delete:
                mPayUp = etPayUp.getText().toString().trim();
                if (mPayUp.length() > 0) {
                    if (etPayUp.getSelectionEnd() == etPayUp.getSelectionStart()) {
                        mPayUp = mPayUp.substring(0, mPayUp.length() - 1);
                        etPayUp.setText(mPayUp);
                    } else {
                        etPayUp.setText("");
                    }
                } else {
                    Log.e(TAG, "payUp is null or payUp size is zero ...");
                }
                break;
            case R.id.btn_pay_ok:
                mPayUp = etPayUp.getText().toString().trim();
                if (mChangeMoney < 0) {
                    Toast.makeText(mContext, LanguageUtils.isZh(mContext) ?
                                    "支付金额不能小于总金额..." :
                                    "The amount of payment should not be less than the total amount ...",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mIPayView.paySuccess(Double.parseDouble(mPayUp), mChangeMoney);
                if (mIsLcd) {
                    mIPayView.displayPayMessage(String.valueOf(mMenuSumMoney),
                            mPayUp, StringUtils.decimalFormat(mChangeMoney, 2), "0.0");
                } else {
                    mIPayView.displayChange(StringUtils.decimalFormat(mChangeMoney, 2));
                }
                break;
            case R.id.btn_pay_cancel:
                mIPayView.payCancel();
                mIPayView.displayTotal(StringUtils.decimalFormat(mMenuSumMoney, 2));
                break;
            default:
                break;
        }
    }

    @OnLongClick({R.id.btn_keyboard_delete})
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.btn_keyboard_delete:
                etPayUp.setText("");
                return true;
            default:
                return false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        etPayUp.setSelection(count);
        if (TextUtils.isEmpty(s)) {
            updateChangeMoney(0, mMenuSumMoney);
        } else {
            updateChangeMoney(Double.parseDouble(String.valueOf(
                    Float.parseFloat(etPayUp.getText().toString().trim()))), mMenuSumMoney);
        }

        if (mIsLcd) {
            mIPayView.displayPayMessage(String.valueOf(mMenuSumMoney),
                    String.valueOf(s), StringUtils.decimalFormat(mChangeMoney, 2), "0.0");
        }else{
            mIPayView.displayPayment(String.valueOf(s));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 键盘按键点击
     *
     * @param keyboard String
     */
    public void keyboardClick(String keyboard) {
        if (etPayUp.getSelectionEnd() == etPayUp.getSelectionStart()) {
            mPayUp = etPayUp.getText().toString().trim();
            if (mPayUp.length() < MAX_PAY_UP_SIZE) {
                mPayUp = StringUtils.append(mPayUp, keyboard);
                etPayUp.setText(mPayUp);
            } else {
                Toast.makeText(mContext, "实收金额最多为" + MAX_PAY_UP_SIZE + "个字符...",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            etPayUp.setText(keyboard);
        }
    }

    /**
     * 更新找零金额
     *
     * @param payUp 已支付
     * @param sum   总金额
     */
    private void updateChangeMoney(double payUp, double sum) {
        mChangeMoney = DoubleUtil.sub(payUp, sum);
        double roundChangeMoney = DoubleUtil.round(mChangeMoney, 2);
        tvPayChange.setText(StringUtils.decimalFormat(roundChangeMoney, 2));
    }


    public interface IPayView {
        /**
         * 取消支付
         */
        void payCancel();

        /**
         * 支付成功
         *
         * @param payUp       double
         * @param changeMoney double
         */
        void paySuccess(double payUp, double changeMoney);

        /**
         * 显示总计
         *
         * @param value String
         */
        void displayTotal(String value);

        /**
         * 显示支付金额
         *
         * @param value String
         */
        void displayPayment(String value);

        /**
         * 显示找零金额
         *
         * @param value String
         */
        void displayChange(String value);


        /**
         * 显示收银明细
         *
         * @param sumMoney    总金额
         * @param payUp       支付金额
         * @param changeMoney 找零金额
         * @param discount    优惠金额
         */
        void displayPayMessage(String sumMoney, String payUp, String changeMoney, String discount);
    }


}
