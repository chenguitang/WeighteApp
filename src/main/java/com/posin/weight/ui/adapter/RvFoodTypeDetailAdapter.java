package com.posin.weight.ui.adapter;

import android.util.Log;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.posin.weight.R;
import com.posin.weight.been.Food;
import com.posin.weight.view.FoodCardView;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * FileName: RvFoodTypeDetailAdapter
 * Author: Greetty
 * Time: 2018/6/14 13:21
 * Description: TODO
 */
public class RvFoodTypeDetailAdapter extends RVAdapter<Food> {

    private static final String TAG = "RvFoodTypeDetailAdapter";
    private RvFoodTypeDetailView mRvFoodTypeDetailView;
    private boolean mIsZh;

    public RvFoodTypeDetailAdapter(List<Food> list_bean, RvFoodTypeDetailView rvFoodTypeDetailView, boolean isZh) {
        super(list_bean);
        this.mRvFoodTypeDetailView = rvFoodTypeDetailView;
        this.mIsZh = isZh;
    }

    @Override
    public void bindDataToView(RVViewHolder holder, int position, Food food, boolean isSelected) {
        FoodCardView foodCardView = (FoodCardView) holder.itemView.findViewById(R.id.fc_food_detail);
        foodCardView.setName(food.getName());
        foodCardView.setPrice(mIsZh ? "￥" + food.getPrices() : "$" + food.getPrices());

    }

    @Override
    public int getItemLayoutID(int position, Food bean) {
        return R.layout.item_food_detail;
    }

    @Override
    public void onItemClick(int position, Food bean) {
        Log.d(TAG, "Food detail: " + bean.toString());

        mRvFoodTypeDetailView.onTypeDetailItemClick(position, bean);
    }

    public interface RvFoodTypeDetailView {
        void onTypeDetailItemClick(int position, Food food);
    }

}
