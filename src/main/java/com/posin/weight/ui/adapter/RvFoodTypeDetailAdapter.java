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
public abstract class RvFoodTypeDetailAdapter extends RVAdapter<Food> {

    public RvFoodTypeDetailAdapter(List<Food> list_bean) {
        super(list_bean);
    }

    @Override
    public void bindDataToView(RVViewHolder holder, int position, Food food, boolean isSelected) {
        FoodCardView foodCardView = (FoodCardView) holder.itemView.findViewById(R.id.fc_food_detail);
        foodCardView.setName(food.getName());
        foodCardView.setPrice("￥" + food.getPrices());

    }

    @Override
    public int getItemLayoutID(int position, Food bean) {
        return R.layout.item_food_detail;
    }

    @Override
    public void onItemClick(int position, Food bean) {
        onTypeDetailItemClick(position,bean);
    }

    public abstract void onTypeDetailItemClick(int position, Food food);
}
