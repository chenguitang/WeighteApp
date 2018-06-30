package com.posin.weight.ui.adapter;

import android.graphics.Color;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.posin.weight.R;
import com.posin.weight.been.MenuDetail;

import java.util.List;

/**
 * FileName: RvMenuDetailAdapter
 * Author: Greetty
 * Time: 2018/6/14 11:10
 * Description: TODO
 */
public class RvMenuDetailAdapter extends RVAdapter<MenuDetail> {

    private List<MenuDetail> list_bean;
    private RvMenuDetailView mRvMenuDetailView;
    private boolean isZh;

    public RvMenuDetailAdapter(List<MenuDetail> list_bean, RvMenuDetailView rvMenuDetailView,
                               boolean isZh) {
        super(list_bean);
        this.list_bean = list_bean;
        this.mRvMenuDetailView = rvMenuDetailView;
        this.isZh = isZh;
    }

    @Override
    public void bindDataToView(RVViewHolder holder, int position, MenuDetail bean, boolean isSelected) {
        holder.setText(R.id.tv_menu_name, bean.getName());
        holder.setText(R.id.tv_menu_weight, "x" + bean.getWeight());
        holder.setText(R.id.tv_menu_prices, (isZh ? "￥" : "$") + bean.getPrices());
        holder.setText(R.id.tv_menu_subtotal, (isZh ? "￥" : "$") + bean.getSubtotal());

        if (isSelected) {
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
        mRvMenuDetailView.onMenuItemClick(position, bean);
    }

    public interface RvMenuDetailView {
        void onMenuItemClick(int position, MenuDetail menuDetail);
    }

}
