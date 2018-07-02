package com.posin.weight.been;

/**
 * FileName: Food
 * Author: Greetty
 * Time: 2018/6/13 16:15
 * Description: TODO
 */
public class Food {

    //菜品名字
    private String name;
    //菜品单价
    private double prices;
    //是否为称重商品
    private boolean weightFood;


    public Food(String name, double prices, boolean weightFood) {
        this.name = name;
        this.prices = prices;
        this.weightFood = weightFood;
    }

    public Food() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }

    public boolean isWeightFood() {
        return weightFood;
    }

    public void setWeightFood(boolean weightFood) {
        this.weightFood = weightFood;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                "weightFood='" + weightFood + '\'' +
                ", prices=" + prices +
                '}';
    }
}
