package com.posin.weight.module.printer;

/**
 * FileName: PrinterStyle
 * Author: Greetty
 * Time: 2018/6/21 20:04
 * Description: TODO
 */
public class PrinterStyle {

    /**
     * 选择字体加粗模式
     */
    public static final byte[] CMD_FONT_STYLE_BOLD_YES = {0x1b, 0x45, 1};
    /**
     * 取消字体加粗模式
     */
    public static final byte[] CMD_FONT_STYLE_BOLD_NO = {0x1b, 0x45, 0};

    /**
     * 初始化打印机
     */
    public static final byte[] CMD_INIT = {0x1B, 0x40};
    /**
     * 内容左对齐
     */
    public static final byte[] CMD_ALIGN_LEFT = {0x1B, 0x61, 0};
    /**
     * 内容居中
     */
    public static final byte[] CMD_ALIGN_CENTER = {0x1B, 0x61, 1};
    /**
     * 内容右对齐
     */
    public static final byte[] CMD_ALIGN_RIGHT = {0x1B, 0x61, 2};

    /**
     * 打印空行并切纸
     */
    public static final byte[] CMD_FEED_AND_CUT = {0x0A, 0x0A, 0x0A, 0x0A, 0x1D, 0x56, 0x01};

    /**
     * 打印空行
     */
    public static final byte[] CMD_FEED = {0x0A};

    /**
     * 字体大小，标准
     */
    public static final byte[] CMD_FONT_SIZE_STANDARD = new byte[]{0x1d, 0x21, 0x0};

    /**
     * 字体大小，宽高加倍
     */
    public static final byte[] CMD_FONT_SIZE_DOUBLE_ALL = new byte[]{0x1d, 0x21, 0x11};
}
