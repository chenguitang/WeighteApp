package com.posin.weight.module.secondary;

import com.posin.weight.module.secondary.lcd.LcdCustomerDisplay;
import com.posin.weight.module.secondary.led.LedCustomerDisplay;

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
                mLcdCustomerDisplay = new LcdCustomerDisplay();
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
    public void displayPrice(String value) throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.displayPrice(value);
        } else {
            mLedCustomerDisplay.displayPrice(value);
        }
    }

    @Override
    public void displayTotal(String value) throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.displayTotal(value);
        } else {
            mLedCustomerDisplay.displayTotal(value);
        }
    }

    @Override
    public void displayPayment(String value) throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.displayPayment(value);
        } else {
            mLedCustomerDisplay.displayPayment(value);
        }
    }

    @Override
    public void displayChange(String value) throws IOException {
        if (isLcd()) {
            mLcdCustomerDisplay.displayChange(value);
        } else {
            mLedCustomerDisplay.displayChange(value);
        }
    }

    @Override
    public void displayWeight(String value) throws Exception {
        if (isLcd()) {
            mLcdCustomerDisplay.displayWeight(value);
        } else {
            mLedCustomerDisplay.displayPrice(value);
        }
    }

    /**
     * 判断是否为LED客显
     *
     * @return boolean
     */
    private boolean isLcd() {
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
