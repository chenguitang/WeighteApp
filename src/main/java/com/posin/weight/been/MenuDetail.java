package com.posin.weight.been;

/**
 * FileName: MenuDetail
 * Author: Greetty
 * Time: 2018/6/13 14:34
 * Description: TODO
 */
public class MenuDetail {

    //商品名字
    private String Name;
    //商品重量
    private double weight;
    //商品单价
    private double prices;
    //商品小计
    private double subtotal;

    public MenuDetail() {
    }

    public MenuDetail(String name, double weight, double prices, double subtotal) {
        Name = name;
        this.weight = weight;
        this.prices = prices;
        this.subtotal = subtotal;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "MenuDetail{" +
                "Name='" + Name + '\'' +
                ", weight=" + weight +
                ", prices=" + prices +
                ", subtotal=" + subtotal +
                '}';
    }
}
