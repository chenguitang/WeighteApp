package com.posin.weight.ui.adapter;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.posin.weight.R;
import com.posin.weight.been.Food;
import com.posin.weight.view.FoodCardView;

import java.util.List;

/**
 * FileName: RvFoodTypeDetailAdapter
 * Author: Greetty
 * Time: 2018/6/14 13:21
 * Desc: TODO
 */
public class RvFoodTypeDetailAdapter extends RVAdapter<Food> {

    private RvFoodTypeDetailView mRvFoodTypeDetailView;
    private boolean mIsZh;

    public RvFoodTypeDetailAdapter(List<Food> list_bean, RvFoodTypeDetailView rvFoodTypeDetailView, boolean iszh) {
        super(list_bean);
        this.mRvFoodTypeDetailView = rvFoodTypeDetailView;
        this.mIsZh = iszh;
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
        mRvFoodTypeDetailView.onTypeDetailItemClick(position, bean);
    }

    public interface RvFoodTypeDetailView {
        void onTypeDetailItemClick(int position, Food food);
    }

}
