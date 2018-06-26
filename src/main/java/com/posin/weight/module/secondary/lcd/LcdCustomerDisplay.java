package com.posin.weight.module.secondary.lcd;

import com.posin.device.CustomerDisplay;
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
    private boolean isZh = true;

    public LcdCustomerDisplay(boolean isZh) throws Throwable {
        mCustomerDisplay = CustomerDisplay.newInstance();
        this.isZh = isZh;
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
     * 显示商品名称
     *
     * @param value String
     * @throws IOException
     */
    public void displayName(String value) throws IOException {
        mCustomerDisplay.write(StringUtils.append(isZh ? "品名：" : "  Name:", value));
    }

    /**
     * 显示单价
     *
     * @param value String
     * @throws IOException
     */
    public void displayPrice(String value) throws IOException {
        if (isZh) {
            mCustomerDisplay.write(StringUtils.append("单价：", value, " 元"));
        } else {
            mCustomerDisplay.write(StringUtils.append("Prices:$", value));
        }
    }

    /**
     * 显示总计
     *
     * @param value String
     * @throws IOException
     */
    public void displayTotal(String value) throws IOException {
        if (isZh) {
            mCustomerDisplay.write(StringUtils.append("总计：", value, " 元"));
        } else {
            mCustomerDisplay.write(StringUtils.append(" Total:$", value));
        }
    }

    /**
     * 显示付款
     *
     * @param value String
     * @throws IOException
     */

    public void displayPayment(String value) throws IOException {
        if (isZh) {
            mCustomerDisplay.write(StringUtils.append("收款：", value, " 元"));
        } else {
            mCustomerDisplay.write(StringUtils.append("  Pays:$", value));
        }
    }

    /**
     * 显示找零
     *
     * @param value String
     * @throws IOException
     */
    public void displayChange(String value) throws IOException {
        if (isZh) {
            mCustomerDisplay.write(StringUtils.append("找零：", value, " 元"));
        } else {
            mCustomerDisplay.write(StringUtils.append("Change:$", value));
        }
    }

    /**
     * 显示小计
     *
     * @param value String
     * @throws IOException
     */
    public void displaySubtotal(String value) throws IOException {
        if (isZh) {
            mCustomerDisplay.write(StringUtils.append("小计：", value, " 元"));
        } else {
            mCustomerDisplay.write(StringUtils.append("   Sub:$", value));
        }
    }

    /**
     * 显示重量
     *
     * @param value String
     * @throws IOException
     */
    public void displayWeight(String value) throws IOException {
        if (isZh) {
            mCustomerDisplay.write(StringUtils.append("重量：", value, " KG"));
        } else {
            mCustomerDisplay.write(StringUtils.append("Weight:", value, " KG"));

        }
    }

    /**
     * 显示优惠金额
     *
     * @param value String
     * @throws IOException
     */
    public void displayDiscount(String value) throws IOException {
        if (isZh) {
            mCustomerDisplay.write(StringUtils.append("优惠：", value, " 元"));
        } else {
            mCustomerDisplay.write(StringUtils.append(" Discd:$", value));

        }
    }

    /**
     * 设置显示开始位置光标
     *
     * @param x x坐标
     * @param y y坐标
     * @throws IOException
     */
    public void setCursorPos(int x, int y) throws IOException {
        mCustomerDisplay.setCursorPos(x, y);
    }

}
