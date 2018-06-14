package com.posin.weight.secondary;


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
     * @param value String
     * @throws Exception
     */
    void displayPrice(String value) throws Exception;

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
     * @param value String
     * @throws Exception
     */
    void displayWeight(String value) throws Exception;
}
