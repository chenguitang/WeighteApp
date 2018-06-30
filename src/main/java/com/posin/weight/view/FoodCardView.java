package com.posin.weight.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.posin.weight.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.posin.weight.R.id.tv_food_name;
import static com.posin.weight.R.id.tv_food_price;

/**
 * FileName: FoodCardView
 * Author: Greetty
 * Time: 2018/6/12 19:27
 * Description: TODO
 */
public class FoodCardView extends RelativeLayout {

    @BindView(R.id.iv_food_image)
    ImageView ivFoodImage;
    @BindView(tv_food_name)
    TextView tvFoodName;
    @BindView(R.id.tv_food_prices)
    TextView tvFoodPrices;
    @BindView(R.id.rl_food_message)
    RelativeLayout rlFoodMessage;


    private String foodName;
    private String foodPrices;
    private ImageView foodImage;

    public FoodCardView(Context context) {
        super(context);
        initView(context);
    }

    public FoodCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FoodCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    /**
     * 初始化View
     *
     * @param context Context
     */
    private void initView(Context context) {
        View view = View.inflate(context, R.layout.view_food_card2, null);
        this.addView(view);
        ButterKnife.bind(this, view);
    }

    /**
     * 得到属性值
     *
     * @param context context
     * @param attrs   attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.FoodCardView);
        foodName = ta.getString(R.styleable.FoodCardView_name);
        foodPrices = ta.getString(R.styleable.FoodCardView_price);
        ta.recycle();
    }

    /**
     * 设置菜名
     *
     * @param foodName name
     */
    public void setName(String foodName) {
        if (!TextUtils.isEmpty(foodName))
            tvFoodName.setText(foodName);
    }

    /**
     * 设置单价
     *
     * @param foodPrice price
     */
    public void setPrice(String foodPrice) {
        if (!TextUtils.isEmpty(foodPrice))
            tvFoodPrices.setText(foodPrice);
    }


    /**
     * 获取商品名字
     */
    public String getName() {
        return foodName;
    }

    /**
     * 获取商品单价
     */
    public String getPrice() {
        return foodPrices;
    }

}
