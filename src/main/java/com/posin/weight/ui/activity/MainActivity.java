package com.posin.weight.ui.activity;

import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.recyclerview.GridRecyclerView;
import com.cy.cyrvadapter.recyclerview.VerticalRecyclerView;
import com.posin.weight.R;
import com.posin.weight.base.BaseActivity;
import com.posin.weight.been.Food;
import com.posin.weight.been.MenuDetail;
import com.posin.weight.db.FoodTypeData;
import com.posin.weight.db.FoodTypeDetailData;
import com.posin.weight.ui.adapter.RvFoodTypeAdapter;
import com.posin.weight.ui.adapter.RvFoodTypeDetailAdapter;
import com.posin.weight.ui.adapter.RvMenuDetailAdapter;
import com.posin.weight.view.FoodCardView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * FileName: MainActivity
 * Author: Greetty
 * Time: 2018/5/23 20:06
 * Desc: 在线更新系统主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";

    @BindView(R.id.common_toolbar)
    Toolbar commonToolbar;
    @BindView(R.id.tv_weight_tip)
    TextView tvWeightTip;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.rb_stable)
    RadioButton rbStable;
    @BindView(R.id.rb_zero)
    RadioButton rbZero;
    @BindView(R.id.rb_peel)
    RadioButton rbPeel;
    @BindView(R.id.rg_root)
    RadioGroup rgRoot;
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
    @BindView(R.id.rl_bottom_root)
    RelativeLayout rlBottomRoot;
    @BindView(R.id.hrv_food_type)
    RecyclerView hrvFoodType;
    @BindView(R.id.grv_food_detail)
    GridRecyclerView grvFoodDetail;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.tv_subtotal_title)
    TextView tvSubtotalTitle;
    @BindView(R.id.tv_prices_title)
    TextView tvPricesTitle;
    @BindView(R.id.iv_item_delete)
    ImageView ivItemDelete;
    @BindView(R.id.rl_item_delete)
    RelativeLayout rlItemDelete;
    @BindView(R.id.tv_pay_tip)
    TextView tvPayTip;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.rl_pay_root)
    RelativeLayout rlPayRoot;


    //菜单列表
    private List<MenuDetail> menuDetailList;
    //菜品种类
//    private List<String> foodTypeList;
    //菜品种类下所有菜式
    private List<Food> foodList;
    //菜单列表适配器
    private RvMenuDetailAdapter rvMenuDetailAdapter;
    //菜品种类适配器
    private RvFoodTypeAdapter rvFoodTypeAdapter;
    //菜品种类所有菜式适配器
    private RvFoodTypeDetailAdapter rvFoodTypeDetailAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {

        initMenuDetail();
        initFoodType();
        initFoodDetail();
        rlItemDelete.setOnClickListener(this);
    }


    /**
     * 菜品明细（某一个类型的菜品所有种类）
     */
    private void initFoodDetail() {
        foodList = FoodTypeDetailData.getFoodTypeetail("水果");
        rvFoodTypeDetailAdapter = new RvFoodTypeDetailAdapter(foodList) {
            @Override
            public void onTypeDetailItemClick(int position, Food food) {
                menuDetailList.add(new MenuDetail(food.getName(), 1.25, food.getPrices(), 9.86));
                rvMenuDetailAdapter.setList_bean(menuDetailList);
                rvMenuDetailAdapter.setSelectedPosition(menuDetailList.size() - 1);
                rvMenuDetailAdapter.notifyDataSetChanged();

                vrvMenu.smoothScrollToPosition(menuDetailList.size() - 1);
            }
        };
        grvFoodDetail.setAdapter(rvFoodTypeDetailAdapter, 3, false, false);
    }

    /**
     * 菜品种类使用方法
     */
    private void initFoodType() {
        rvFoodTypeAdapter = new RvFoodTypeAdapter(FoodTypeData.getFoodTypes()) {
            @Override
            public void onTypeItemClick(int position, String name) {
                foodList.clear();
                foodList = FoodTypeDetailData.getFoodTypeetail(name);

                rvFoodTypeDetailAdapter.setList_bean(foodList);
                rvFoodTypeDetailAdapter.notifyDataSetChanged();
            }
        };

        hrvFoodType.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        hrvFoodType.setAdapter(rvFoodTypeAdapter);
    }

    /**
     * 菜单明细使用方法
     */
    private void initMenuDetail() {
        menuDetailList = new ArrayList<>();
        rvMenuDetailAdapter = new RvMenuDetailAdapter(menuDetailList);
        vrvMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        vrvMenu.setAdapter(rvMenuDetailAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_item_delete:
                int position = rvMenuDetailAdapter.getSelectedPosition();
                Log.e(TAG, "********************************************************");
                Log.e(TAG, "position: " + position);
                Log.e(TAG, "********************************************************\n");
                if (position >= 0) {
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
                break;
            default:
                break;
        }
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setLogo(R.mipmap.ic_launcher);
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
}
