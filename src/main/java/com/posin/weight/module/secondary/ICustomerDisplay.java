package com.posin.weight.module.secondary;


import java.io.IOException;

/**
 * FileName: ICustomerDisplay
 * Author: Greetty
 * Time: 2018/6/14 15:22
 * Description: 客显操作接口
 */
public interface ICustomerDisplay {

    /**
     * 关闭客显
     *
     * @throws Exception
     */
    void close() throws Exception;

    /**
     * 清屏
     *
     * @throws Exception
     */
    void clear() throws Exception;

    /**
     * 复位
     *
     * @throws Exception
     */
    void reset() throws Exception;

    /**
     * 单价
     *
     * @param name     名字
     * @param value    单价
     * @param weight   重量
     * @param subtotal 小计
     * @throws Exception
     */
    void displayLcdFoodByWeight(String name, String value, String weight, String subtotal) throws Exception;

    /**
     * 单价
     *
     * @param name     名字
     * @param value    单价
     * @param weight   重量
     * @param subtotal 小计
     * @throws Exception
     */
    void displayLcdFoodByAmount(String name, String value, String weight, String subtotal) throws Exception;

    /**
     * LED客显显示单价
     *
     * @param value 单价
     * @throws IOException
     */
    void displayLedPrice(String value) throws IOException;

    /**
     * 总计
     *
     * @param value String
     * @throws Exception
     */
    void displayTotal(String value) throws Exception;

    /**
     * 支付
     *
     * @param value String
     * @throws Exception
     */
    void displayPayment(String value) throws Exception;

    /**
     * 找零
     *
     * @param value String
     * @throws Exception
     */
    void displayChange(String value) throws Exception;

    /**
     * 重量
     *
     * @param value String
     * @throws Exception
     */
    void displayWeight(String value) throws Exception;

    /**
     * LCD客显更新商品名称
     *
     * @param name     品名
     * @param prices   单价
     * @param subtotal 小计
     * @throws IOException
     */
    void displayUpdateFood(String name, String prices,
                           String subtotal) throws IOException;


    /**
     * 更新LCD显示商品名字
     *
     * @param name 品名
     * @throws IOException
     */
    void displayLcdUpdateName(String name) throws IOException;


    /**
     * 更新LCD显示小计金额
     *
     * @param subtotal 小计
     * @throws IOException
     */
    void displayLcdUpdateSubtotal(String subtotal) throws IOException;

    /**
     * 更新收银明细
     *
     * @param sumMoney    总计金额
     * @param payUp       支付金额
     * @param changeMoney 找零金额
     * @param discount    优惠金额
     * @throws Exception
     */
    void displayPayMessage(String sumMoney, String payUp, String changeMoney,
                           String discount) throws Exception;
}
