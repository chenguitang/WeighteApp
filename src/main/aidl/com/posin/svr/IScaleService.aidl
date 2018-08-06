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
	// 获取精度值
	boolean getAutoPrecision();
	// 获取校准表
	int[] getWeightMap();

	// 设置并保存校准表和精度值
	boolean setWeightConfig(in int[] weightMap, int precision, boolean autoPrecision);

	// 获取一个稳定的AD值，在timeout时间内完成，返回0为失败
	int getSteadyAd(long timeout);

    /* 读一次AD数据，如1秒钟没有数据来，返回错误。
              这是阻塞方法，不能在UI线程调用。

             返回数据格式: 偶数序列为ID号,奇数序列为该ID的值
		  int[] result = readValues(MAX_ID_COUNT);
		  // result[0] : id0
		  // result[1] : id0 对应的 value
		  // result[2] : id1
		  // result[3] : id1 对应的 value
		  // result[4] : id2
		  // result[5] : id2 对应的 value
		  ...
	*/
	int[] readValues(int maxId);
}
