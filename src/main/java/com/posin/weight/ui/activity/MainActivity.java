package com.posin.weight.ui.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cy.cyrvadapter.recyclerview.GridRecyclerView;
import com.cy.cyrvadapter.recyclerview.VerticalRecyclerView;
import com.posin.device.SDK;
import com.posin.weight.R;
import com.posin.weight.base.BaseActivity;
import com.posin.weight.been.Food;
import com.posin.weight.been.MenuDetail;
import com.posin.weight.db.FoodTypeData;
import com.posin.weight.db.FoodTypeDetailData;
import com.posin.weight.module.printer.PrinterUtils;
import com.posin.weight.module.secondary.SecDisplayUtils;
import com.posin.weight.ui.adapter.RvFoodTypeAdapter;
import com.posin.weight.ui.adapter.RvFoodTypeDetailAdapter;
import com.posin.weight.ui.adapter.RvMenuDetailAdapter;
import com.posin.weight.ui.contract.WeightContract;
import com.posin.weight.ui.presenter.WeightPresenter;
import com.posin.weight.utils.DoubleUtil;
import com.posin.weight.utils.LanguageUtils;
import com.posin.weight.utils.StringUtils;
import com.posin.weight.view.PayDialog;
import com.posin.weight.view.SetWeightPointDialog;
import com.posin.weight.view.WeightDialog;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * FileName: MainActivity
 * Author: Greetty
 * Time: 2018/5/23 20:06
 * Description: 在线更新系统主界面
 */
public class MainActivity extends BaseActivity implements WeightContract.IWeightView,
        WeightDialog.WeightDialogView, RvFoodTypeDetailAdapter.RvFoodTypeDetailView,
        RvFoodTypeAdapter.RvFoodTypeView, PayDialog.IPayView, RvMenuDetailAdapter.RvMenuDetailView, View.OnTouchListener {

    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.rb_stable)
    RadioButton rbStable;
    @BindView(R.id.rb_zero)
    RadioButton rbZero;
    @BindView(R.id.rb_peel)
    RadioButton rbPeel;
    @BindView(R.id.rl_weight_root)
    RelativeLayout rlWeightRoot;
    @BindView(R.id.vrv_menu)
    VerticalRecyclerView vrvMenu;
    @BindView(R.id.iv_zero)
    ImageView ivZero;
    @BindView(R.id.rl_zero_root)
    RelativeLayout rlZeroRoot;
    @BindView(R.id.iv_peel)
    ImageView ivPeel;
    @BindView(R.id.rl_peel_root)
    RelativeLayout rlPeelRoot;
    @BindView(R.id.hrv_food_type)
    RecyclerView hrvFoodType;
    @BindView(R.id.grv_food_detail)
    GridRecyclerView grvFoodDetail;
    @BindView(R.id.rl_item_delete)
    RelativeLayout rlItemDelete;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_open_setting)
    View tvOpenSetting;

    private static final String TAG = "MainActivity";

    /**
     * LED客显显示非重量值时，显示时长
     * 单位：毫秒
     */
    private static final int SECONDARY_LED_DISPLAY_SHOW_NOT_WEIGHT_TIME = 3650;

    /**
     * LCD客显显示非重量值时，显示时长
     * 单位：毫秒
     */
    private static final int SECONDARY_LCD_DISPLAY_SHOW_NOT_WEIGHT_TIME = 2000;
    //菜单列表
    private List<MenuDetail> menuDetailList;
    //菜品种类下所有菜式
    private List<Food> foodList;
    //菜单列表适配器
    private RvMenuDetailAdapter rvMenuDetailAdapter;
    //菜品种类适配器
    private RvFoodTypeAdapter rvFoodTypeAdapter;
    //菜品种类所有菜式适配器
    private RvFoodTypeDetailAdapter rvFoodTypeDetailAdapter;

    private int flowNumber = 666;
    private int orderNumber = 87154;

    private WeightPresenter mWeightPresenter;
    private WeightDialog mWeightDialog;
    private PayDialog mPayDialog;
    private Handler mHandler = new Handler();
    //客显显示非重量值的时间值
    private long mSecShowOthersTime;

    private boolean mLcdShowInit = true;
    private boolean mPressed = false;
    private long mDownTime = 0;

    private double mSum;
    private boolean isZh = true;
    private boolean isLcd = false;
    private boolean isUpdateWeight = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initData() {

        try {
            SDK.init(this);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        isZh = LanguageUtils.isZh(this);
        try {
            isLcd = SecDisplayUtils.getInstance().isLcd();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        mWeightPresenter = new WeightPresenter(this, this, mHandler);
        initAdapter();

//        commonToolbar.setOnTouchListener(this);
        tvOpenSetting.setOnTouchListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            mWeightPresenter.bindService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化各种Adapter适配器
     */
    private void initAdapter() {
        //菜品种类使用方法
        rvFoodTypeAdapter = new RvFoodTypeAdapter(FoodTypeData.getFoodTypes(isZh), this);
        rvFoodTypeAdapter.setSelectedPosition(0);
        hrvFoodType.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        hrvFoodType.setAdapter(rvFoodTypeAdapter);

        //菜品明细（某一个类型的菜品所有种类）
        foodList = FoodTypeDetailData.getFoodTypeDetail(isZh ? "水果" : "Fruits");
        rvFoodTypeDetailAdapter = new RvFoodTypeDetailAdapter(foodList, this, isZh);
        grvFoodDetail.setAdapter(rvFoodTypeDetailAdapter, 3, false, false);

        //菜单明细使用方法
        menuDetailList = new ArrayList<>();
        rvMenuDetailAdapter = new RvMenuDetailAdapter(menuDetailList, this, isZh);
        vrvMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        vrvMenu.setAdapter(rvMenuDetailAdapter);
    }

    /**
     * 点击选择具体某个商品
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onTypeDetailItemClick(int position, Food food) {
        if (mWeightDialog == null) {
            mWeightDialog = new WeightDialog(MainActivity.this, food.getName(), food.getPrices(),
                    food.isWeightFood(), mWeightPresenter, MainActivity.this);
            mWeightDialog.show();
            isUpdateWeight = food.isWeightFood();
            mLcdShowInit = false;
            if (isLcd) {
                if (food.isWeightFood()) {
                    //控制客显显示内容
                    try {
                        float weight = mWeightPresenter.getWeight();
                        SecDisplayUtils.getInstance().displayLcdFoodByWeight(
                                String.valueOf(food.getName())
                                , String.valueOf(food.getPrices())
                                , String.format("%.3f", weight)
                                , String.valueOf(DoubleUtil.round(weight * food.getPrices(), 2))
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //控制客显显示内容
                    try {
                        SecDisplayUtils.getInstance().displayLcdFoodByAmount(
                                String.valueOf(food.getName())
                                , String.valueOf(food.getPrices())
                                , String.valueOf(1)
                                , String.valueOf(DoubleUtil.round(food.getPrices(), 2))
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (!food.isWeightFood()) {
                    try {
                        SecDisplayUtils.getInstance().displayLedPrice(String.valueOf(food.getPrices()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Log.e(TAG, "mWeightDialog !=null ,Please close the Dialog that is being displayed");
        }
    }

    /**
     * 选择某个商品分类
     */
    @Override
    public void onTypeItemClick(int position, String name) {
        foodList.clear();
        foodList = FoodTypeDetailData.getFoodTypeDetail(name);

        rvFoodTypeDetailAdapter.setList_bean(foodList);
        rvFoodTypeDetailAdapter.notifyDataSetChanged();
    }

    /**
     * 点击菜单栏
     */
    @Override
    public void onMenuItemClick(int position, MenuDetail menuDetail) {

        //更新客显显示的商品数据
        try {
            if (isLcd) {
//                SecDisplayUtils.getInstance().displayUpdateFood(menuDetail.getName(),
//                        String.valueOf(menuDetail.getPrices()),
//                        String.valueOf(menuDetail.getSubtotal()));
            } else {
                mSecShowOthersTime = System.currentTimeMillis();
                SecDisplayUtils.getInstance().displayLedPrice(String.valueOf(menuDetail.getPrices()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.rl_item_delete, R.id.rl_pay_root, R.id.rl_peel_root, R.id.rl_zero_root})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_item_delete:
                if (menuDetailList.size() <= 0) {
                    return;
                }

                int position = rvMenuDetailAdapter.getSelectedPosition();
                if (position >= 0) {
                    mSum = DoubleUtil.sub(mSum, menuDetailList.get(position).getSubtotal());
                    tvPay.setText(StringUtils.append((isZh ? "￥" : "$") + mSum));
                    menuDetailList.remove(position);
                }
                rvMenuDetailAdapter.setList_bean(menuDetailList);
                if (position < rvMenuDetailAdapter.getItemCount()) {
                    rvMenuDetailAdapter.setSelectedPosition(position);
                } else if (position - 1 >= 0) {
                    rvMenuDetailAdapter.setSelectedPosition(position - 1);
                } else {
                    rvMenuDetailAdapter.setSelectedPosition(-1);
                }
                rvMenuDetailAdapter.notifyDataSetChanged();

                //更新客显
                try {
                    isUpdateWeight = true;
                    if (isLcd) {
//                        SecDisplayUtils.getInstance().displayUpdateFood(isZh ? "欢迎光临" :
//                                "Welcome", "0.00", "0.00");
                        float weight = mWeightPresenter.getWeight();
                        SecDisplayUtils.getInstance().displayLcdFoodByWeight(isZh ? "欢迎光临" : "Welcome",
                                "0.00", String.format("%.3f", weight), "0.00");
                        mLcdShowInit = false;

                    } else {
                        SecDisplayUtils.getInstance().displayTotal(String.valueOf(mSum));
                        mSecShowOthersTime = System.currentTimeMillis();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_pay_root:

                //菜单中没有数据，点击支付不显示内容
                if (menuDetailList.size() <= 0) {
                    return;
                }

                try {
                    if (mPayDialog == null) {
                        mPayDialog = new PayDialog(this, mSum, isLcd, this);
                        mPayDialog.show();
                    } else {
                        Log.e(TAG, "PayDialog !=null,please close pay dialog");
                    }

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    Toast.makeText(mContext, isZh ? "出错了：" : "Error:" + throwable.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_peel_root:
                try {
                    mWeightPresenter.rePeel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_zero_root:
                try {
                    mWeightPresenter.setZero();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setLogo(R.mipmap.scalen);
//        mCommonToolbar.setNavigationIcon(R.mipmap.scalen);
        mCommonToolbar.setTitle(R.string.app_name);

    }

    /**
     * 加载菜单按钮
     *
     * @param menu Menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 菜单按钮Item点击事件
     *
     * @param item 菜单Item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.serial_setting:
                Toast.makeText(mContext, "串口设置正在开发中 ....", Toast.LENGTH_SHORT).show();
                break;
            case R.id.system_exit:
                MainActivity.this.finish();
                System.exit(0);
                break;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示菜单Item中的图片
     *
     * @param featureId 图片ID
     * @param view      View
     * @param menu      Menu
     * @return boolean
     */
    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",
                            Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public void weightError(String errorMessage) {
        tvWeight.setText(errorMessage);
    }

    @Override
    public void overLoadMark() {
        tvWeight.setText(isZh ? "过载" : "Overload");
    }

    @Override
    public void openZeroHighMark() {
        tvWeight.setText(isZh ? "零点高" : "Zero point high");
    }

    @Override
    public void openZeroLowMark() {
        tvWeight.setText(isZh ? "零点低" : "Zero point low");
    }

    @Override
    public void updateWeight(float weight) {
        //修改显示重量
        float weightFloat = weight / 1000.0f;
        tvWeight.setText(String.format("%.3f KG", weightFloat));


        if (!isUpdateWeight) {
            return;
        }

        //如果有dialog弹框，同步dialog中显示的重量
        if (mWeightDialog != null) {
            mWeightDialog.updateWeight(weightFloat);
        }

        //客显显示实时重量
        try {
            String secFormatWight = String.format("%.3f", weightFloat);


            if (isLcd) {
//                Log.e(TAG, System.currentTimeMillis() + " - " + mSecShowOthersTime + " = " +
//                        (System.currentTimeMillis() - mSecShowOthersTime));
                if (mLcdShowInit) {
                    if (System.currentTimeMillis() - mSecShowOthersTime >=
                            SECONDARY_LCD_DISPLAY_SHOW_NOT_WEIGHT_TIME) {
                        SecDisplayUtils.getInstance().displayLcdFoodByWeight(isZh ? "欢迎光临" : "Welcome",
                                "0.00", secFormatWight, "0.00");
                        mLcdShowInit = false;
                    }
                } else {
                    if (mPayDialog == null) {
                        SecDisplayUtils.getInstance().displayWeight(secFormatWight);
                    }

                    if (mWeightDialog != null) {
                        SecDisplayUtils.getInstance().displayLcdUpdateSubtotal(
                                String.valueOf(DoubleUtil.round(weightFloat *
                                        mWeightDialog.getFoodPrices(), 2)));
                    }
                }
            } else {
                if (System.currentTimeMillis() - mSecShowOthersTime >=
                        SECONDARY_LED_DISPLAY_SHOW_NOT_WEIGHT_TIME) {  //不显示其他数据时，动态显示重量
                    SecDisplayUtils.getInstance().displayWeight(secFormatWight);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void isStable(boolean stable) {
        rbStable.setChecked(stable);
    }

    @Override
    public void isPeel(boolean Peel) {
        rbPeel.setChecked(Peel);
    }

    @Override
    public void isZero(boolean Zero) {
        rbZero.setChecked(Zero);
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(mContext, "Error: " + throwable.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getWeightCancel() {
        if (mWeightDialog != null) {
            if (mWeightDialog.isShowing()) {
                mWeightDialog.dismiss();
            }
            mWeightDialog = null;
        }

        try {
            if (isLcd) {
                SecDisplayUtils.getInstance().displayUpdateFood(isZh ? "欢迎光临" :
                        "Welcome", "0.00", "0.00");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            isUpdateWeight = true;
        }

    }

    @Override
    public void getWeightOk(MenuDetail menuDetail) {

        //隐藏Dialog弹框
        if (mWeightDialog != null) {
            if (mWeightDialog.isShowing()) {
                mWeightDialog.dismiss();
            }
            mWeightDialog = null;
        }

        //显示单价，更新客显显示非重量的时间
        mSecShowOthersTime = System.currentTimeMillis();
        if (!isUpdateWeight) {
            mLcdShowInit = true;
        }
        isUpdateWeight = true;

        try {
            SecDisplayUtils.getInstance().displayLedPrice(String.valueOf(menuDetail.getPrices()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //修改总金额
        mSum = DoubleUtil.add(mSum, menuDetail.getSubtotal());
        tvPay.setText(StringUtils.append((isZh ? "￥" : "$") + mSum));

        //增加菜品到菜单栏
        menuDetailList.add(menuDetail);
        rvMenuDetailAdapter.setList_bean(menuDetailList);
        rvMenuDetailAdapter.setSelectedPosition(menuDetailList.size() - 1);
        rvMenuDetailAdapter.notifyDataSetChanged();

        vrvMenu.smoothScrollToPosition(menuDetailList.size() - 1);
    }


    @Override
    public void payCancel() {
        if (mPayDialog != null) {
            if (mPayDialog.isShowing()) {
                mPayDialog.dismiss();
            }
            mPayDialog = null;
        }

        if (isLcd) {
            try {
                float weight = mWeightPresenter.getWeight();
                SecDisplayUtils.getInstance().displayLcdFoodByWeight(isZh ? "欢迎光临" : "Welcome",
                        "0.00", String.format("%.3f", weight), "0.00");
                mLcdShowInit = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void paySuccess(double payUp, double changeMoney) {
        if (mPayDialog != null) {
            if (mPayDialog.isShowing()) {
                mPayDialog.dismiss();
            }
            mPayDialog = null;
        }
        mLcdShowInit = true;
        tvPay.setText(isZh ? "￥0.0" : "$0.0");

        try {
            PrinterUtils.getInstance().printMenuDetail(menuDetailList, mSum,
                    payUp, changeMoney, 0.0, flowNumber, StringUtils.append("20154641654",
                            orderNumber), isZh);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Toast.makeText(mContext, (isZh ? "出错了：" : "Error:") + throwable.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        mSum = 0;
        menuDetailList.clear();
        rvMenuDetailAdapter.setList_bean(menuDetailList);
        rvMenuDetailAdapter.notifyDataSetChanged();

        orderNumber++;
        flowNumber++;
        Toast.makeText(mContext, isZh ? "支付成功 " : "pay success",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayTotal(String value) {
        try {
            SecDisplayUtils.getInstance().displayTotal(value);
            //显示总计，更新客显显示非重量的时间
            mSecShowOthersTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayPayment(String value) {
        try {
            if (TextUtils.isEmpty(value)) {
                SecDisplayUtils.getInstance().displayPayment("0.0");
            } else {
                SecDisplayUtils.getInstance().displayPayment(value);
            }

            //显示支付，更新客显显示非重量的时间
            mSecShowOthersTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayChange(String value) {
        try {
            SecDisplayUtils.getInstance().displayChange(value.indexOf(".") == 0 ? "0.00" : value);
            //显示找零，更新客显显示非重量的时间
            mSecShowOthersTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayPayMessage(String sumMoney, String payUp, String changeMoney, String discount) {
        try {
            SecDisplayUtils.getInstance().displayPayMessage(sumMoney, payUp, changeMoney, discount);
            //显示收银信息，更新客显显示非重量的时间
            mSecShowOthersTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.common_toolbar:
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    Log.d(TAG, "************* pressed");
                    if (!mPressed) {
                        mPressed = true;
                        mDownTime = SystemClock.uptimeMillis();
                    }
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    Log.d(TAG, "************* release");
                    if (mPressed) {
                        mPressed = false;
                        long interval = SystemClock.uptimeMillis() - mDownTime;
                        Log.d(TAG, "interval time: " + interval);
                        if (interval > 5000 && interval < 10000) {
                            if (mWeightPresenter.getIScaleService() == null) {
                                Toast.makeText(MainActivity.this, "错误: 无法连接称重服务程序",
                                        Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            new SetWeightPointDialog(MainActivity.this, mWeightPresenter);
                        }
                    }
                }

            case R.id.tv_open_setting:
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    Log.d(TAG, "************* pressed");
                    if (!mPressed) {
                        mPressed = true;
                        mDownTime = SystemClock.uptimeMillis();
                    }
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    Log.d(TAG, "************* release");
                    if (mPressed) {
                        mPressed = false;
                        long interval = SystemClock.uptimeMillis() - mDownTime;
                        Log.d(TAG, "interval time: " + interval);
                        if (interval > 5000 && interval < 10000) {
                            if (mWeightPresenter.getIScaleService() == null) {
                                Toast.makeText(MainActivity.this, "错误: 无法连接称重服务程序",
                                        Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            new SetWeightPointDialog(MainActivity.this, mWeightPresenter);
                        }
                    }
                }
                return true;
            default:
                return false;

        }
    }
}
