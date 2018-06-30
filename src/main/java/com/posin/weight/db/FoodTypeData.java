package com.posin.weight.db;

import com.posin.weight.been.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: FoodTypeData
 * Author: Greetty
 * Time: 2018/6/14 13:42
 * Description: TODO
 */
public class FoodTypeData {

    /**
     * 虚拟商品种类
     *
     * @return List<String>
     */
    public static List<String> getFoodTypes(boolean is_zh) {
        List<String> foodTypeList = new ArrayList<>();
        if (is_zh) {
            foodTypeList.add("水果");
            foodTypeList.add("前菜");
            foodTypeList.add("汉堡");
            foodTypeList.add("烤物");
            foodTypeList.add("刺身");
            foodTypeList.add("主食");
        }else{
            foodTypeList.add("Fruits");
            foodTypeList.add("FormerDish");
            foodTypeList.add("Hamburger");
            foodTypeList.add("Roast");
            foodTypeList.add("Sashimi");
            foodTypeList.add("StapleFood");
        }
        return foodTypeList;
    }

}
