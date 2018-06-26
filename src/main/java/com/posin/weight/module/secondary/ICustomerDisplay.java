package com.posin.weight.module.secondary;


import java.io.IOException;

/**
 * FileName: ICustomerDisplay
 * Author: Greetty
 * Time: 2018/6/14 15:22
 * Desc: 客显操作接口
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
    void displayPrice(String name, String value, String weight, String subtotal) throws Exception;

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

}
