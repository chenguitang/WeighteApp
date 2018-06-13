package com.posin.weight.ui.activity;

import android.graphics.Color;
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
import android.widget.Toast;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.recyclerview.GridRecyclerView;
import com.cy.cyrvadapter.recyclerview.VerticalRecyclerView;
import com.posin.weight.R;
import com.posin.weight.base.BaseActivity;
import com.posin.weight.been.Food;
import com.posin.weight.been.MenuDetail;
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
public class MainActivity extends BaseActivity {


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


    private List<MenuDetail> menuDetailList;
    private RVAdapter<MenuDetail> rvMenuAdapter;
    private RVAdapter.RVViewHolder menuDetailHolder;
    private RVAdapter.RVViewHolder menuDetailHolder2;

    private List<String> foodTypeList;
    private RVAdapter<String> foodTypeAdapter;

    private List<Food> foodList;
    private RVAdapter<Food> foodsDetailAdapter;

    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {

        initMenuDetail();
        initFoodType();
        initFoodDetail();

        rlItemDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (menuDetailHolder != null) {
                            int position = menuDetailHolder.getLayoutPosition();
                            Log.e(TAG, "********************************************************");
                            Log.e(TAG, "position: " + position);
                            Log.e(TAG, "********************************************************\n");
                            menuDetailList.remove(position);
                            rvMenuAdapter.setList_bean(menuDetailList);
                            if (position-1>=0) {
                                rvMenuAdapter.setSelectedPosition(position - 1);
                            }
                            rvMenuAdapter.notifyDataSetChanged();
                            menuDetailHolder = null;
                        }
                    }
                }

        );
    }


    public void changeFoodDetail(String name, double prices, int size) {
        foodList.clear();
        for (int i = 0; i < size; i++) {
            foodList.add(new Food(name + i, prices + i));
        }
        foodsDetailAdapter.setList_bean(foodList);
        foodTypeAdapter.notifyDataSetChanged();
    }

    /**
     * 菜品明细（某一个类型的菜品所有种类）
     */
    private void initFoodDetail() {
        foodList = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            foodList.add(new Food("汉堡王" + i, 28.50 + i));
        }

        foodsDetailAdapter = new RVAdapter<Food>(foodList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, Food food, boolean isSelected) {
                FoodCardView foodCardView = (FoodCardView) holder.itemView.findViewById(R.id.fc_food_detail);
                foodCardView.setName(food.getName());
                foodCardView.setPrice("￥" + food.getPrices());

                if (isSelected) {
                    holder.getView(R.id.fc_food_detail).setBackgroundColor(Color.parseColor("#00ae7d"));
                } else {
                    holder.getView(R.id.fc_food_detail).setBackgroundColor(Color.parseColor("#E6E6E6"));
                }
            }

            @Override
            public int getItemLayoutID(int position, Food bean) {
                return R.layout.item_food_detail;
            }

            @Override
            public void onItemClick(int position, Food bean) {
                menuDetailList.add(new MenuDetail(bean.getName(), 1.25, bean.getPrices(), 9.86));
                rvMenuAdapter.setList_bean(menuDetailList);
                rvMenuAdapter.setSelectedPosition(menuDetailList.size() - 1);
                rvMenuAdapter.notifyDataSetChanged();

                vrvMenu.smoothScrollToPosition(menuDetailList.size() - 1);
//                vrvMenu.scrollToPosition(menuDetailList.size()-1);
//                vrvMenu.getLayoutManager().scrollToPosition(menuDetailList.size()-1);
//                vrvMenu.setSelected(true);
//                rvMenuAdapter.bindDataToView(menuDetailHolder2, menuDetailList.size() - 1,
//                        menuDetailList.get(menuDetailList.size()-1), true);


            }
        };
        grvFoodDetail.setAdapter(foodsDetailAdapter, 3, false, false);
    }

    /**
     * 菜品种类使用方法
     */
    private void initFoodType() {
        foodTypeList = new ArrayList<>();

        foodTypeList.add("水果");
        foodTypeList.add("水果1");
        foodTypeList.add("水果2");
        foodTypeList.add("水果3");
        foodTypeList.add("水果4");
        foodTypeList.add("水果5");
        foodTypeList.add("汉堡");
        foodTypeList.add("中餐");

        foodTypeAdapter = new RVAdapter<String>(foodTypeList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, String name, boolean isSelected) {
                holder.setText(R.id.tv_food_type_name, name);

                if (isSelected) {
                    holder.getView(R.id.tv_food_type_name).setBackgroundColor(Color.parseColor("#00ae7d"));
                } else {
                    holder.getView(R.id.tv_food_type_name).setBackgroundColor(0x00000000);
                }
            }

            @Override
            public int getItemLayoutID(int position, String bean) {
                return R.layout.item_food_type;
            }

            @Override
            public void onItemClick(int position, String bean) {
                changeFoodDetail(bean, 58.95, 22);
            }
        };
        hrvFoodType.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        hrvFoodType.setAdapter(foodTypeAdapter);
    }

    /**
     * 菜单明细使用方法
     */
    private void initMenuDetail() {
        menuDetailList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            menuDetailList.add(new MenuDetail(("黄焖鸡" + i), 1.25 + i, 79.5, (589.5 + i)));
        }

        rvMenuAdapter = new RVAdapter<MenuDetail>(menuDetailList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, MenuDetail bean, boolean isSelected) {


                holder.setText(R.id.tv_menu_name, bean.getName());
                holder.setText(R.id.tv_menu_weight, "x" + bean.getWeight());
                holder.setText(R.id.tv_menu_prices, "￥" + bean.getPrices());
                holder.setText(R.id.tv_menu_subtotal, "￥" + bean.getSubtotal());

                Log.e(TAG, "=============================================");
                Log.e(TAG, "isSelected: " + isSelected);
                Log.e(TAG, "position: " + position);
                Log.e(TAG, "=============================================\n");

                menuDetailHolder2 = holder;
                if (isSelected) {
                    menuDetailHolder = holder;
//                    holder.getView(R.id.rl_menu_item).setBackgroundColor(Color.parseColor("#E6E6E6"));
                    holder.getView(R.id.rl_menu_item).setBackgroundColor(Color.parseColor("#CBC2A1"));
                } else {
                    holder.getView(R.id.rl_menu_item).setBackgroundColor(0x00000000);
                }

            }

            @Override
            public int getItemLayoutID(int position, MenuDetail bean) {
                return R.layout.item_menu2;
            }

            @Override
            public void onItemClick(int position, MenuDetail bean) {
                Toast.makeText(mContext, "name: " + bean.getName(), Toast.LENGTH_SHORT).show();
            }
        };
        vrvMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        vrvMenu.setAdapter(rvMenuAdapter);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
