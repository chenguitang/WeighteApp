package com.posin.svr;

interface IScaleService {

// **************************************************
// 基本功能
// **************************************************

 	// 获取重量值等等...
	int[] getWeight();

	// 去皮
	boolean setTare();

	// 置零
	boolean setZero();

	// 数字去皮
	boolean doNumberTare(int numbertare);

	// 秤复位
	boolean reset();

	// 获取错误代码
	int getErrorCode();

// **************************************************
// 高级功能
// **************************************************

	// 设置串口
	boolean setSerialPort(String port);
	// 获取串口
	String getSerialPort();

	// 获取当前AD值
	int getCurrentAd();

	// 获取精度值
	int getPrecision();
	// 设置精度值
	boolean setPrecision(int precision);

	// 获取校准表
	int[] getWeightMap();
	// 设置并保存校准表
	boolean setWeightMap(in int[] map);

}