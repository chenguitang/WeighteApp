package com.posin.weight.module.secondary;

import android.text.TextUtils;

import com.posin.weight.MyApplication;
import com.posin.weight.module.secondary.lcd.LcdCustomerDisplay;
import com.posin.weight.module.secondary.led.LedCustomerDisplay;
import com.posin.weight.utils.LanguageUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * FileName: SecDisplayUtils
 * Author: Greetty
 * Time: 2018/6/14 14:54
 * Desc: 客显控制类
 */
public class SecDisplayUtils implements ICustomerDisplay {

    private String mCustomerDspType;
    private String mLedPort;
    private LedCustomerDisplay mLedCustomerDisplay;
    private LcdCustomerDisplay mLcdCustomerDisplay;


    private static class SecDisplayUtilsHolder {
        private static final SecDisplayUtils Instance = new SecDisplayUtils();
    }

    /**
     * 获取实例对象
     *
     * @return SecDisplayUtils
     */
    public static SecDisplayUtils getInstance() {
        return SecDisplayUtilsHolder.Instance;
    }

    /**
     * 构造函数
     */
    private SecDisplayUtils() {
        try {
            loadSystemProperties();
            if (isLcd()) {
                mLcdCustomerDisplay = new LcdCustomerDisplay(LanguageUtils.isZh(
                        MyApplication.getContext()));
            } else {
                mLedCustomerDisplay = new LedCustomerDisplay(mLedPort);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.close();
        } else {
            mLedCustomerDisplay.close();
        }

    }

    @Override
    public void clear() throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.clear();
        } else {
            mLedCustomerDisplay.clear();
        }
    }

    @Override
    public void reset() throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.reset();
        } else {
            mLedCustomerDisplay.reset();
        }
    }

    @Override
    public void displayPrice(String name, String value, String weight,
                             String subtotal) throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.setCursorPos(0, 0);
            mLcdCustomerDisplay.displayName(name);

            mLcdCustomerDisplay.setCursorPos(1, 0);
            mLcdCustomerDisplay.displayWeight(weight);

            mLcdCustomerDisplay.setCursorPos(2, 0);
            mLcdCustomerDisplay.displayPrice(value);

            mLcdCustomerDisplay.setCursorPos(3, 0);
            mLcdCustomerDisplay.displaySubtotal(subtotal);

        } else {
            mLedCustomerDisplay.displayPrice(value);
        }
    }

    /**
     * LED客显更新单价
     *
     * @param value 单价
     * @throws IOException
     */
    public void displayLedPrice(String value) throws IOException {
        if (!isLcd())
            mLedCustomerDisplay.displayPrice(value);
    }

    @Override
    public void displayTotal(String value) throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.setCursorPos(1, 0);
            mLcdCustomerDisplay.displayTotal(value);
        } else {
            mLedCustomerDisplay.displayTotal(value);
        }
    }

    @Override
    public void displayPayment(String value) throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.setCursorPos(1, 0);
            mLcdCustomerDisplay.displayPayment(value);
        } else {
            mLedCustomerDisplay.displayPayment(value);
        }
    }

    @Override
    public void displayChange(String value) throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.setCursorPos(1, 0);
            mLcdCustomerDisplay.displayChange(value);
        } else {
            mLedCustomerDisplay.displayChange(value);
        }
    }

    @Override
    public void displayWeight(String value) throws Exception {
        if (isLcd()) {
            mLcdCustomerDisplay.setCursorPos(1, 0);
            mLcdCustomerDisplay.displayWeight(value);
        } else {
            mLedCustomerDisplay.displayWeight(value);
        }
    }

    public void displayWightUnClear(String value) throws Exception {
        if (isLcd()) {
            mLcdCustomerDisplay.setCursorPos(0, 0);
            mLcdCustomerDisplay.displayWeight(value);
        } else {
            mLedCustomerDisplay.displayWeight(value);
        }
    }

    /**
     * 更新商品名称，重量使用动态值
     *
     * @param name     商品名称
     * @param prices   商品单价
     * @param subtotal 小计
     * @throws IOException
     */
    public void displayUpdateFood(String name, String prices,
                                  String subtotal) throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.setCursorPos(0, 0);
            mLcdCustomerDisplay.displayName(name);

            mLcdCustomerDisplay.setCursorPos(2, 0);
            mLcdCustomerDisplay.displayPrice(prices);

            mLcdCustomerDisplay.setCursorPos(3, 0);
            mLcdCustomerDisplay.displaySubtotal(subtotal);

        }
    }


    /**
     * 显示收银明细
     *
     * @param sumMoney    总金额
     * @param payUp       支付金额
     * @param changeMoney 找零金额
     * @param discount    优惠金额
     */
    public void displayPayMessage(String sumMoney, String payUp, String changeMoney,
                                  String discount) throws Exception {
        if (isLcd()) {
//            mLcdCustomerDisplay.clear();

            mLcdCustomerDisplay.setCursorPos(0, 0);
            mLcdCustomerDisplay.displayTotal(TextUtils.isEmpty(sumMoney) ? "0.0" : sumMoney);

            mLcdCustomerDisplay.setCursorPos(1, 0);
            mLcdCustomerDisplay.displayPayment(TextUtils.isEmpty(payUp) ? "0.0" : payUp);

            mLcdCustomerDisplay.setCursorPos(2, 0);
            if (TextUtils.isEmpty(changeMoney)) {
                mLcdCustomerDisplay.displayChange("0.0");
            } else {
                changeMoney = changeMoney.indexOf(".") == 0 ? "0.0" : changeMoney;
                mLcdCustomerDisplay.displayChange(changeMoney);
            }

            mLcdCustomerDisplay.setCursorPos(3, 0);
            mLcdCustomerDisplay.displayDiscount(TextUtils.isEmpty(discount) ? "0.0" : discount);

        }
    }

    /**
     * 判断是否为LED客显
     *
     * @return boolean
     */
    public boolean isLcd() {
        return mCustomerDspType.equals("lcd");
    }

    /**
     * 加载系统配置
     *
     * @return Properties
     */
    private Properties loadSystemProperties() throws Exception {
        Properties p = new Properties();
        FileInputStream is = null;

        try {
            is = new FileInputStream("/system/build.prop");
            p.load(is);
            mCustomerDspType = p.getProperty("ro.customerdisplay.type", "lcd");
            mLedPort = p.getProperty("ro.customerdisplay.port", "/dev/ttyACM0");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return p;
    }

}
