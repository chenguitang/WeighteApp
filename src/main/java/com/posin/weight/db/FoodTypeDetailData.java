package com.posin.weight.db;

import com.posin.weight.been.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: FoodTypeDetailData
 * Author: Greetty
 * Time: 2018/6/14 13:48
 * Desc: TODO
 */
public class FoodTypeDetailData {

    private static List<Food> foodList = new ArrayList<>();

    public static List<Food> getFoodTypeetail(String foodType) {

        switch (foodType) {
            case "水果":
                foodList.clear();
                foodList.add(new Food("妃子笑荔枝", 4.5));
                foodList.add(new Food("西瓜", 1.99));
                foodList.add(new Food("香蕉", 1.5));
                foodList.add(new Food("菠萝（凤梨）", 0.9));
                foodList.add(new Food("桂味荔枝", 7.5));
                foodList.add(new Food("哈密瓜", 3.99));
                foodList.add(new Food("芒果", 5.8));
                foodList.add(new Food("圣女果", 1.5));
                foodList.add(new Food("黄皮", 5));
                foodList.add(new Food("龙眼", 7));
                foodList.add(new Food("菠萝蜜", 15));
                break;
            case "前菜":
                foodList.add(new Food("开胃菜", 5));
                foodList.add(new Food("牛油果沙拉", 32));
                foodList.add(new Food("小菜", 15));
                foodList.add(new Food("土豆沙拉", 92));
                foodList.add(new Food("芒果蟹仔沙拉", 54.2));
                foodList.add(new Food("三文鱼沙拉", 41));
                foodList.add(new Food("凉拌青瓜", 12.5));
                foodList.add(new Food("凉拌木耳", 24));
                foodList.add(new Food("凉皮", 22));
                foodList.add(new Food("凉拌海带", 52));
                break;
            case "汉堡":
                foodList.add(new Food("鸡肉堡", 22));
                foodList.add(new Food("牛肉堡", 25));
                foodList.add(new Food("鲜虾堡", 21));
                foodList.add(new Food("巨无霸堡", 15.9));
                foodList.add(new Food("火腿汉堡", 18));
                foodList.add(new Food("芝士牛肉堡", 30));
                foodList.add(new Food("培根鸡蛋堡", 24));
                foodList.add(new Food("鳕鱼堡", 12));
                foodList.add(new Food("足尊牛堡", 88));
                foodList.add(new Food("麦香猪柳堡", 46));
                foodList.add(new Food("双层吉士堡", 64.5));
                break;
            case "烤物":
                foodList.add(new Food("火焰鹅肝", 48));
                foodList.add(new Food("烤蘑菇", 15));
                foodList.add(new Food("鹅肝", 8));
                foodList.add(new Food("烤三文鱼骨", 12));
                foodList.add(new Food("烤鸡腿", 7));
                foodList.add(new Food("烤鸭腿", 5.5));
                foodList.add(new Food("烧三文鱼", 45));
                foodList.add(new Food("海鲜蒸蛋", 20));
                foodList.add(new Food("香煎龙利鱼", 54));
                foodList.add(new Food("烤豆腐", 4.5));
                foodList.add(new Food("烤雪糕", 5.5));
                break;
            case "主食":
                foodList.add(new Food("普通米饭", 5));
                foodList.add(new Food("一级米饭", 45));
                foodList.add(new Food("二级米饭", 20));
                foodList.add(new Food("炒饭", 20));
                foodList.add(new Food("馒头", 10));
                foodList.add(new Food("意大利面", 60));
                foodList.add(new Food("香港公仔面", 12));
                foodList.add(new Food("挂面", 12));
                foodList.add(new Food("拉面", 14));
                foodList.add(new Food("担担面", 21));
                foodList.add(new Food("炸酱面", 18));
                break;
            case "刺身":
                foodList.add(new Food("三文鱼刺身", 5.5));
                foodList.add(new Food("北极贝刺身", 75.5));
                foodList.add(new Food("八爪鱼刺身", 85.5));
                foodList.add(new Food("赤贝刺身", 65.5));
                foodList.add(new Food("海螺刺身", 54));
                foodList.add(new Food("金仓鱼刺身", 55));
                foodList.add(new Food("黑胆", 56));
                foodList.add(new Food("黑爪子刺身", 23));
                foodList.add(new Food("活海胆刺身", 12.5));
                foodList.add(new Food("罗非鱼刺身", 125));
                break;
            default:
                for (int i = 0; i < 10; i++) {
                    if (i % 2 == 0)
                        foodList.add(new Food("测试菜品" + i, 21 + i));
                    else
                        foodList.add(new Food("测试菜品" + i, 2 * i + 1));
                }
                break;
        }
        return foodList;

    }

}
