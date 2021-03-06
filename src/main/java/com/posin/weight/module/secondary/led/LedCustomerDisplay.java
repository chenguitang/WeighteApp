package com.posin.weight.module.secondary.led;

import java.io.IOException;

import com.posin.device.SerialPort;
import com.posin.device.SerialPortConfiguration;

/**
 * FileName: LedCustomerDisplay
 * Author: Greetty
 * Time: 2018/6/14 14:54
 * Description: LED客显
 */
public class LedCustomerDisplay {

    /**
     * 显示类型控制指令
     *
     * 30 全部清空类型
     * 31 单价
     * 32 总计
     * 33 付款
     * 34 找零
     * 35 全亮
     */

    /*
     * 用串口发送指令
     */
    private final SerialPort mSerialPort;

    /**
     * @param port 串口路径(/dev/ttS??等)
     * @throws Throwable
     */
    public LedCustomerDisplay(String port) throws Throwable {
        SerialPortConfiguration cfg = new SerialPortConfiguration();
        cfg.port = port;
        cfg.baudrate = 2400;
        cfg.databits = SerialPort.DATABITS_8;
        cfg.stopbits = SerialPort.STOPBITS_1;
        cfg.parity = SerialPort.PARITY_NONE;
        cfg.flowControl = SerialPort.FLOWCONTROL_NONE;
        mSerialPort = SerialPort.open(cfg, true);
    }

    /**
     * 关闭客显（串口）
     * 用完都有记得关闭，否则会造成系统资源浪费
     */
    public void close() {
        mSerialPort.close();
    }

    /**
     * 清屏
     *
     * @throws IOException
     */
    public void clear() throws IOException {
        mSerialPort.getOutputStream().write(0x0C);
    }

    /**
     * 复位
     *
     * @throws IOException
     */
    public void reset() throws IOException {
        byte[] cmd = {0x1B, 0x40};
        mSerialPort.getOutputStream().write(cmd);
    }

    /**
     * 显示单价
     *
     * @param value String
     * @throws IOException
     */
    public void displayPrice(String value) throws IOException {
        displayValue(value, 0x31);
    }

    /**
     * 显示总计
     *
     * @param value String
     * @throws IOException
     */
    public void displayTotal(String value) throws IOException {
        displayValue(value, 0x32);
    }

    /**
     * 显示付款
     *
     * @param value String
     * @throws IOException
     */
    public void displayPayment(String value) throws IOException {
        displayValue(value, 0x33);
    }

    /**
     * 显示找零
     *
     * @param value String
     * @throws IOException
     */
    public void displayChange(String value) throws IOException {
        displayValue(value, 0x34);
    }

    /**
     * 显示重量
     *
     * @param value String
     * @throws IOException
     */
    public void displayWeight(String value) throws IOException {
        value += "\r";
        mSerialPort.getOutputStream().write(new byte[]{0x1B, 0x73, 0x30});
        mSerialPort.getOutputStream().write(value.getBytes());
    }

    /**
     * 发送指令到LED客显
     *
     * @param value 指令命令
     * @param type  指令类别
     * @throws IOException
     */
    public void displayValue(String value, int type) throws IOException {
        value += "\r";
        mSerialPort.getOutputStream().write(value.getBytes());

        byte[] cmd = {0x1B, 0x73, (byte) type};
        mSerialPort.getOutputStream().write(cmd);
    }

}
