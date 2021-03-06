package com.posin.weight.module.secondary;

import android.text.TextUtils;

import com.posin.weight.MyApplication;
import com.posin.weight.module.secondary.lcd.LcdCustomerDisplay;
import com.posin.weight.module.secondary.led.LedCustomerDisplay;
import com.posin.weight.utils.LanguageUtils;
import com.posin.weight.utils.ThreadManage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * FileName: SecDisplayUtils
 * Author: Greetty
 * Time: 2018/6/14 14:54
 * Description: 客显控制类
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
    public static SecDisplayUtils getInstance() throws Exception {
        if (SecDisplayUtilsHolder.Instance == null) {
            throw new Exception("SecDisplayUtils Instance is null ...");
        }
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

        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                if (isLcd()) {
                    mLcdCustomerDisplay.close();
                } else {
                    mLedCustomerDisplay.close();
                }
            }
        });

    }

    @Override
    public void clear() throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.clear();
                    } else {
                        mLedCustomerDisplay.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void reset() throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.reset();
                    } else {
                        mLedCustomerDisplay.reset();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void displayLcdFoodByWeight(final String name, final String value, final String weight,
                                       final String subtotal) throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.setCursorPos(0, 0);
                        mLcdCustomerDisplay.displayName(name);

                        mLcdCustomerDisplay.setCursorPos(1, 0);
                        mLcdCustomerDisplay.displayWeight(weight);

                        mLcdCustomerDisplay.setCursorPos(2, 0);
                        mLcdCustomerDisplay.displayPrice(value);

                        mLcdCustomerDisplay.setCursorPos(3, 0);
                        mLcdCustomerDisplay.displaySubtotal(subtotal);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void displayLcdFoodByAmount(final String name, final String value, final String weight,
                                       final String subtotal) throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.setCursorPos(0, 0);
                        mLcdCustomerDisplay.displayName(name);

                        mLcdCustomerDisplay.setCursorPos(1, 0);
                        mLcdCustomerDisplay.displayAmount(weight);

                        mLcdCustomerDisplay.setCursorPos(2, 0);
                        mLcdCustomerDisplay.displayPrice(value);

                        mLcdCustomerDisplay.setCursorPos(3, 0);
                        mLcdCustomerDisplay.displaySubtotal(subtotal);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * LED客显更新单价
     *
     * @param value 单价
     * @throws IOException
     */
    @Override
    public void displayLedPrice(final String value) throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!isLcd()) {
                        mLedCustomerDisplay.displayPrice(value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void displayTotal(final String value) throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.setCursorPos(1, 0);
                        mLcdCustomerDisplay.displayTotal(value);
                    } else {
                        mLedCustomerDisplay.displayTotal(value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void displayPayment(final String value) throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.setCursorPos(1, 0);
                        mLcdCustomerDisplay.displayPayment(value);
                    } else {
                        mLedCustomerDisplay.displayPayment(value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void displayChange(final String value) throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.setCursorPos(1, 0);
                        mLcdCustomerDisplay.displayChange(value);
                    } else {
                        mLedCustomerDisplay.displayChange(value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void displayWeight(final String value) throws Exception {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.setCursorPos(1, 0);
                        mLcdCustomerDisplay.displayWeight(value);
                    } else {
                        mLedCustomerDisplay.displayWeight(value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * 更新商品名称，重量使用动态值
     *
     * @param name     商品名称
     * @param prices   商品单价
     * @param subtotal 小计
     * @throws IOException
     */
    @Override
    public void displayUpdateFood(final String name, final String prices,
                                  final String subtotal) throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.setCursorPos(0, 0);
                        mLcdCustomerDisplay.displayName(name);

                        mLcdCustomerDisplay.setCursorPos(2, 0);
                        mLcdCustomerDisplay.displayPrice(prices);

                        mLcdCustomerDisplay.setCursorPos(3, 0);
                        mLcdCustomerDisplay.displaySubtotal(subtotal);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void displayLcdUpdateName(final String name) throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.setCursorPos(0, 0);
                        mLcdCustomerDisplay.displayName(name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void displayLcdUpdateSubtotal(final String subtotal) throws IOException {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.setCursorPos(3, 0);
                        mLcdCustomerDisplay.displaySubtotal(subtotal);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * 显示收银明细
     *
     * @param sumMoney    总金额
     * @param payUp       支付金额
     * @param changeMoney 找零金额
     * @param discount    优惠金额
     */
    @Override
    public void displayPayMessage(final String sumMoney, final String payUp, final String changeMoney,
                                  final String discount) throws Exception {
        ThreadManage.getShortPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLcd()) {
                        mLcdCustomerDisplay.setCursorPos(0, 0);
                        mLcdCustomerDisplay.displayTotal(TextUtils.isEmpty(sumMoney) ? "0.0" : sumMoney);

                        mLcdCustomerDisplay.setCursorPos(1, 0);
                        mLcdCustomerDisplay.displayPayment(TextUtils.isEmpty(payUp) ? "0.0" : payUp);

                        mLcdCustomerDisplay.setCursorPos(2, 0);
                        if (TextUtils.isEmpty(changeMoney)) {
                            mLcdCustomerDisplay.displayChange("0.0");
                        } else {
//                            changeMoney = changeMoney.indexOf(".") == 0 ? "0.0" : changeMoney;
                            mLcdCustomerDisplay.displayChange(changeMoney.indexOf(".") == 0 ? "0.0" : changeMoney);
                        }

                        mLcdCustomerDisplay.setCursorPos(3, 0);
                        mLcdCustomerDisplay.displayDiscount(TextUtils.isEmpty(discount) ? "0.0" : discount);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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
