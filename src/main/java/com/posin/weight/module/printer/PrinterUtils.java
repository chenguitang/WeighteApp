package com.posin.weight.module.printer;

import com.posin.device.Printer;
import com.posin.weight.been.MenuDetail;
import com.posin.weight.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * FileName: PrinterUtils
 * Author: Greetty
 * Time: 2018/6/21 19:43
 * Description: TODO
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
                                double changeMoney, double discountMoney, int flowNumber,
                                String oddNumber, boolean isZh) throws Throwable {
        if (mPrinter == null) {
            mPrinter = Printer.newInstance();
        }

        if (!mPrinter.ready()) {
            throw new Exception(isZh ? "打印机未准备就绪 ...." : "The printer is not ready ....");
        }

        mPrinter.print(PrinterStyle.CMD_INIT);

        mPrinter.print(PrinterStyle.CMD_ALIGN_CENTER);
        mPrinter.print(PrinterStyle.CMD_FONT_SIZE_DOUBLE_ALL);
        mPrinter.print(PrinterStyle.CMD_FONT_STYLE_BOLD_YES);

        if (isZh) {
            mPrinter.print("珠海宝盈\n");

            mPrinter.print(PrinterStyle.CMD_ALIGN_LEFT);
            mPrinter.print(PrinterStyle.CMD_FONT_SIZE_STANDARD);
            mPrinter.print(PrinterStyle.CMD_FONT_STYLE_BOLD_NO);
            mPrinter.print("================================\n");
            mPrinter.print(StringUtils.append("流水号：", flowNumber, "\n").getBytes("gbk"));
            mPrinter.print(StringUtils.append("单号：", oddNumber, "\n").getBytes("gbk"));
            mPrinter.print((StringUtils.append("制单日期：",
                    mSimpleDateFormat.format(System.currentTimeMillis()), "\n").getBytes("gbk")));
            mPrinter.print("================================\n");

            mPrinter.print(" 名称      单价    数量     小计\n".getBytes("gbk"));

            for (MenuDetail menuDetail : menuDetailList) {
                String foodName = menuDetail.getName();
                double foodPrices = menuDetail.getPrices();
                double foodWeight = menuDetail.getWeight();
                double subtotalMoney = menuDetail.getSubtotal();
                String menuLineData = StringUtils.append(foodName, spaceSize(11 - (foodName.length() * 2)),
                        foodPrices, spaceSize(7 - String.valueOf(foodPrices).length()),
                        foodWeight, spaceSize(12 - String.valueOf(foodWeight).length() -
                                String.valueOf(subtotalMoney).length()),
                        subtotalMoney, "\n");
                mPrinter.print(menuLineData.getBytes("gbk"));
            }
            mPrinter.print("--------------------------------\n");

            String alreadyPayStr = StringUtils.decimalFormat(alreadyPay, 2);
            String changeMoneyStr = StringUtils.decimalFormat(changeMoney, 2);
            changeMoneyStr = changeMoneyStr.indexOf(".") == 0 ? "0.00" : changeMoneyStr;

            mPrinter.print(StringUtils.append("应收：", sumMoney,
                    spaceSize(20 - String.valueOf(sumMoney).length() -
                            alreadyPayStr.length()), "实收：", alreadyPayStr, "\n").getBytes("gbk"));

            mPrinter.print(StringUtils.append("找零：", changeMoneyStr,
                    spaceSize(16 - changeMoneyStr.length()), "优惠：", "0.00", "\n").getBytes("gbk"));

            mPrinter.print("--------------------------------\n");
            mPrinter.print("操作员：李小明\n".getBytes("gbk"));
            mPrinter.print("地址：珠海宝盈商用设备有限公司\n".getBytes("gbk"));
            mPrinter.print("电话：07563965178\n".getBytes("gbk"));
            mPrinter.print("================================\n");
            mPrinter.print("温馨提醒：请妥善保留您的购物小票\n");
            mPrinter.print("欢迎再次光临\n");

        } else {
            mPrinter.print("ZHUHAI POS\n");

            mPrinter.print(PrinterStyle.CMD_ALIGN_LEFT);
            mPrinter.print(PrinterStyle.CMD_FONT_SIZE_STANDARD);
            mPrinter.print(PrinterStyle.CMD_FONT_STYLE_BOLD_NO);
            mPrinter.print("================================\n");
            mPrinter.print(StringUtils.append("Flow Number：", flowNumber, "\n").getBytes("gbk"));
            mPrinter.print(StringUtils.append("Order Number：", oddNumber, "\n").getBytes("gbk"));
            mPrinter.print((StringUtils.append("Date：",
                    mSimpleDateFormat.format(System.currentTimeMillis()), "\n").getBytes("gbk")));
            mPrinter.print("================================\n");

            mPrinter.print(" Name      Prices   Amount   Sub\n".getBytes("gbk"));

            for (MenuDetail menuDetail : menuDetailList) {
                String foodName = menuDetail.getName();
                double foodPrices = menuDetail.getPrices();
                double foodWeight = menuDetail.getWeight();
                double subtotalMoney = menuDetail.getSubtotal();
                String menuLineData = StringUtils.append(foodName, spaceSize(11 - (foodName.length() * 2)),
                        foodPrices, spaceSize(7 - String.valueOf(foodPrices).length()),
                        foodWeight, spaceSize(12 - String.valueOf(foodWeight).length() -
                                String.valueOf(subtotalMoney).length()),
                        subtotalMoney, "\n");
                mPrinter.print(menuLineData.getBytes("gbk"));
            }
            mPrinter.print("--------------------------------\n");

            String alreadyPayStr = StringUtils.decimalFormat(alreadyPay, 2);
            String changeMoneyStr = StringUtils.decimalFormat(changeMoney, 2);
            changeMoneyStr = changeMoneyStr.indexOf(".") == 0 ? "0.00" : changeMoneyStr;

            mPrinter.print(StringUtils.append("Total：", sumMoney,
                    spaceSize(15 - String.valueOf(sumMoney).length() -
                            alreadyPayStr.length()), "PayUp：", alreadyPayStr, "\n").getBytes("gbk"));

            mPrinter.print(StringUtils.append("Change：", changeMoneyStr,
                    spaceSize(15 - String.valueOf(changeMoney).length() -
                            changeMoneyStr.length()), "Discount：", "0.00", "\n").getBytes("gbk"));

            mPrinter.print("--------------------------------\n");
            mPrinter.print("Operator：Alis\n".getBytes("gbk"));
            mPrinter.print("Address：China,Zhuhai,Guangdong\n".getBytes("gbk"));
            mPrinter.print("Tel：07563965178\n".getBytes("gbk"));
            mPrinter.print("================================\n");
            mPrinter.print("Looking forward to your next visit\n");
        }
        for (int i = 0; i < 4; i++) {
            mPrinter.print(PrinterStyle.CMD_FEED);
        }

    }

    /**
     * 空格大小
     *
     * @param size int
     * @return String
     */
    private String spaceSize(int size) {
        if (size <= 0) {
            return " ";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(" ");
            }
            return sb.toString();
        }
    }
}
