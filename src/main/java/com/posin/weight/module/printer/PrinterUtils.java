package com.posin.weight.module.printer;

import android.util.Log;

import com.posin.device.Printer;
import com.posin.weight.been.MenuDetail;
import com.posin.weight.utils.StringUtils;

import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.os.Build.VERSION_CODES.M;

/**
 * FileName: PrinterUtils
 * Author: Greetty
 * Time: 2018/6/21 19:43
 * Desc: TODO
 */
public class PrinterUtils {

    private Printer mPrinter;

    private SimpleDateFormat mSimpleDateFormat;

    private static class PrinterUtilsHolder {
        private static final PrinterUtils Instance = new PrinterUtils();
    }

    public static final PrinterUtils getInstance() {
        return PrinterUtilsHolder.Instance;
    }

    public PrinterUtils() {
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:dd:ss");
    }

    public void printMenuDetail(List<MenuDetail> menuDetailList, double sumMoney, double alreadyPay,
                                double changeMoney, double discountMoney) throws Throwable {
        if (mPrinter == null) {
            mPrinter = Printer.newInstance();
        }

        if (!mPrinter.ready()) {
            throw new Exception("打印机未准备就绪 ....");
        }

        mPrinter.print(PrinterStyle.CMD_INIT);

        mPrinter.print(PrinterStyle.CMD_ALIGN_CENTER);
        mPrinter.print(PrinterStyle.CMD_FONT_SIZE_DOUBLE_ALL);
        mPrinter.print(PrinterStyle.CMD_FONT_STYLE_BOLD_YES);
        mPrinter.print("珠海宝盈\n");

        mPrinter.print(PrinterStyle.CMD_ALIGN_LEFT);
        mPrinter.print(PrinterStyle.CMD_FONT_SIZE_STANDARD);
        mPrinter.print(PrinterStyle.CMD_FONT_STYLE_BOLD_NO);
        mPrinter.print("================================\n");
        mPrinter.print("流水号：666\n".getBytes("gbk"));
        mPrinter.print("单号：2015464165487154\n".getBytes("gbk"));
        mPrinter.print((StringUtils.append("制单日期：",
                mSimpleDateFormat.format(System.currentTimeMillis()), "\n").getBytes("gbk")));
        mPrinter.print("================================\n");

        mPrinter.print(" 名称    单价   数量   小计\n".getBytes("gbk"));

        Log.e(TAG, "menuDetailList size: " + menuDetailList.size());

        for (MenuDetail menuDetail : menuDetailList) {
            mPrinter.print(StringUtils.append(menuDetail.getName(), "    ",
                    menuDetail.getPrices(), "    ",
                    menuDetail.getWeight(), "    ",
                    menuDetail.getSubtotal(), "    ",
                    "\n").getBytes("gbk"));
        }
        mPrinter.print("--------------------------------\n");

        mPrinter.print(StringUtils.append("应收：", sumMoney, "        ",
                "实收：", alreadyPay,"\n").getBytes("gbk"));
        mPrinter.print(StringUtils.append("找零：", changeMoney, "        ",
                "优惠：", discountMoney,"\n").getBytes("gbk"));

        mPrinter.print("--------------------------------\n");
        mPrinter.print("操作员：李小明\n".getBytes("gbk"));
        mPrinter.print("地址：珠海宝盈商用设备有限公司\n".getBytes("gbk"));
        mPrinter.print("电话：07563965178\n".getBytes("gbk"));
        mPrinter.print("================================\n");
        mPrinter.print("温馨提醒：请妥善保留您的购物小票\n");
        mPrinter.print("欢迎再次光临\n");

        for (int i = 0; i < 4; i++) {
            mPrinter.print(PrinterStyle.CMD_FEED);
        }

    }

}
