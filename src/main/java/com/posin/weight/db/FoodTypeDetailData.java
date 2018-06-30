package com.posin.weight.db;

import com.posin.weight.been.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: FoodTypeDetailData
 * Author: Greetty
 * Time: 2018/6/14 13:48
 * Description: TODO
 */
public class FoodTypeDetailData {

    private static List<Food> foodList = new ArrayList<>();

    public static List<Food> getFoodTypeDetail(String foodType) {

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
            case "Fruits":
                foodList.clear();
                foodList.add(new Food("Persimmon", 4.5));
                foodList.add(new Food("Watermelon", 1.99));
                foodList.add(new Food("Banana", 1.5));
                foodList.add(new Food("Pineapple", 0.9));
                foodList.add(new Food("Sydney", 7.5));
                foodList.add(new Food("Hami melon", 3.99));
                foodList.add(new Food("Mango", 5.8));
                foodList.add(new Food("Cherry Tomatoes", 1.5));
                foodList.add(new Food("Wampee", 5));
                foodList.add(new Food("Longan", 7));
                foodList.add(new Food("Jackfruit", 15));
                break;
            case "前菜":
                foodList.clear();
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
            case "FormerDish":
                foodList.clear();
                foodList.add(new Food("Appetizer", 5));
                foodList.add(new Food("Avocado salad", 32));
                foodList.add(new Food("Side dishes", 15));
                foodList.add(new Food("Potato salad", 92));
                foodList.add(new Food("Mango Crab Salad", 54.2));
                foodList.add(new Food("Salmon salad", 41));
                foodList.add(new Food("Cucumber salad", 12.5));
                foodList.add(new Food("Cold fungus", 24));
                foodList.add(new Food("Liang Pi", 22));
                foodList.add(new Food("Seaweed salad", 52));
                break;
            case "汉堡":
                foodList.clear();
                foodList.add(new Food("鸡肉堡", 22));
                foodList.add(new Food("牛肉堡", 25));
                foodList.add(new Food("鲜虾堡", 21));
                foodList.add(new Food("巨无霸堡", 15.9));
                foodList.add(new Food("火腿汉堡", 18));
                foodList.add(new Food("芝士牛肉堡", 30));
                foodList.add(new Food("培根鸡蛋堡", 24));
                foodList.add(new Food("鳕鱼堡", 12));
                foodList.add(new Food("至尊牛肉堡", 88));
                foodList.add(new Food("麦香猪柳堡", 46));
                foodList.add(new Food("双层吉士堡", 64.5));
                break;

            case "Hamburger":
                foodList.clear();
                foodList.add(new Food("Chicken burger", 22));
                foodList.add(new Food("Beef fort", 25));
                foodList.add(new Food("Fresh shrimp burger", 21));
                foodList.add(new Food("Big mac", 15.9));
                foodList.add(new Food("Ham burger", 18));
                foodList.add(new Food("Beef burger cheese", 30));
                foodList.add(new Food("Bacon's egg Burger", 24));
                foodList.add(new Food("Cod burger", 12));
                foodList.add(new Food("Prime Beef Burger", 88));
                foodList.add(new Food("Mixiang pig willows", 46));
                foodList.add(new Food("Double cheeseburger", 64.5));
                break;
            case "烤物":
                foodList.clear();
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
            case "Roast":
                foodList.clear();
                foodList.add(new Food("Foie goose liver", 48));
                foodList.add(new Food("Mushroom", 15));
                foodList.add(new Food("Goose liver", 8));
                foodList.add(new Food("Roasted salmon bone", 12));
                foodList.add(new Food("Roast chicken leg", 7));
                foodList.add(new Food("Roast duck leg", 5.5));
                foodList.add(new Food("Burning salmon", 45));
                foodList.add(new Food("Steamed egg with seafood", 20));
                foodList.add(new Food("Roasted sweet potato", 54));
                foodList.add(new Food("Baked bean curd", 4.5));
                foodList.add(new Food("Roasted ice cream", 5.5));
                break;
            case "主食":
                foodList.clear();
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

            case "StapleFood":
                foodList.clear();
                foodList.add(new Food("Ordinary rice", 5));
                foodList.add(new Food("First grade rice", 45));
                foodList.add(new Food("Two grade rice", 20));
                foodList.add(new Food("Fried rice", 20));
                foodList.add(new Food("Steamed buns", 10));
                foodList.add(new Food("Pasta", 60));
                foodList.add(new Food("Hongkong boy face", 12));
                foodList.add(new Food("Noodles", 12));
                foodList.add(new Food("Lamian Noodles", 14));
                foodList.add(new Food("Dandan", 21));
                foodList.add(new Food("Fried bean sauce noodles", 18));
                break;
            case "刺身":
                foodList.clear();
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
            case "Sashimi":
                foodList.clear();
                foodList.add(new Food("Salmon Sashimi", 5.5));
                foodList.add(new Food("Scallops Sashimi", 75.5));
                foodList.add(new Food("Octopus", 85.5));
                foodList.add(new Food("Scallop", 65.5));
                foodList.add(new Food("Snail tattoo", 54));
                foodList.add(new Food("Golden fish sashimi", 55));
                foodList.add(new Food("Black gall", 56));
                foodList.add(new Food("Sashimi Platter", 23));
                foodList.add(new Food("Black paw sashimi", 12.5));
                foodList.add(new Food("Tilapia sashimi", 125));
                break;
            default:
                foodList.clear();
                for (int i = 0; i < 10; i++) {
                    if (i % 2 == 0)
                        foodList.add(new Food("Test Food" + i, 21 + i));
                    else
                        foodList.add(new Food("Test Food" + i, 2 * i + 1));
                }
                break;
        }
        return foodList;

    }

}
