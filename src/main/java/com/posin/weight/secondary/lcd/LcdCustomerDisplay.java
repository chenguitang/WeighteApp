package com.posin.weight.secondary.lcd;

import com.posin.device.CustomerDisplay;
import com.posin.device.SerialPort;
import com.posin.device.SerialPortConfiguration;
import com.posin.weight.utils.StringUtils;

import java.io.IOException;

/**
 * FileName: LcdCustomerDisplay
 * Author: Greetty
 * Time: 2018/6/14 14:54
 * Desc: LCD客显
 */
public class LcdCustomerDisplay {

    private CustomerDisplay mCustomerDisplay;

    public LcdCustomerDisplay() throws Throwable {
        mCustomerDisplay = CustomerDisplay.newInstance();
    }

    /**
     * 关闭客显
     * 用完都有记得关闭，否则会造成系统资源浪费
     */
    public void close() {
        mCustomerDisplay.close();
    }

    /**
     * 清屏
     *
     * @throws IOException
     */
    public void clear() throws IOException {
        mCustomerDisplay.clear();
    }

    /**
     * 复位
     *
     * @throws IOException
     */
    public void reset() throws IOException {
        mCustomerDisplay.reset();
    }

    /**
     * 显示单价
     *
     * @param value String
     * @throws IOException
     */
    public void displayPrice(String value) throws IOException {
        mCustomerDisplay.clear();
        mCustomerDisplay.setCursorPos(1,1);
        mCustomerDisplay.write(StringUtils.append("单价：",value," 元"));
    }

    /**
     * 显示总计
     *
     * @param value String
     * @throws IOException
     */
    public void displayTotal(String value) throws IOException {
        mCustomerDisplay.clear();
        mCustomerDisplay.setCursorPos(1,1);
        mCustomerDisplay.write(StringUtils.append("总计：",value," 元"));
    }

    /**
     * 显示付款
     *
     * @param value String
     * @throws IOException
     */
    public void displayPayment(String value) throws IOException {
        mCustomerDisplay.clear();
        mCustomerDisplay.setCursorPos(1,1);
        mCustomerDisplay.write(StringUtils.append("收款：",value," 元"));
    }

    /**
     * 显示找零
     *
     * @param value String
     * @throws IOException
     */
    public void displayChange(String value) throws IOException {
        mCustomerDisplay.clear();
        mCustomerDisplay.setCursorPos(1,1);
        mCustomerDisplay.write(StringUtils.append("找零：",value," 元"));
    }

    /**
     * 显示重量
     *
     * @param value String
     * @throws IOException
     */
    public void displayWeight(String value) throws IOException {
        mCustomerDisplay.clear();
        mCustomerDisplay.setCursorPos(1,1);
        mCustomerDisplay.write(StringUtils.append("重量：",value," KG"));
    }

}
