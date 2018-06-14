package com.posin.weight.ui.adapter;

import android.graphics.Color;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.posin.weight.R;

import java.util.List;

/**
 * FileName: RvFoodTypeAdapter
 * Author: Greetty
 * Time: 2018/6/14 13:06
 * Desc: TODO
 */
public abstract class RvFoodTypeAdapter extends RVAdapter<String> {


    public RvFoodTypeAdapter(List<String> list_bean) {
        super(list_bean);
    }

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
        onTypeItemClick(position,bean);
    }

    public abstract void onTypeItemClick(int position,String name);
}
