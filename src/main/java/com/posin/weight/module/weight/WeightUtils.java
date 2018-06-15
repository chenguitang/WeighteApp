package com.posin.weight.module.weight;

import android.os.RemoteException;
import android.os.SystemClock;

import com.posin.svr.IScaleService;
import com.posin.weight.been.Weight;

/**
 * FileName: WeightUtils
 * Author: Greetty
 * Time: 2018/6/15 11:19
 * Desc: TODO
 */
public class WeightUtils {


    /**
     * 获取weight对象
     *
     * @param iWeight IScaleService
     * @return Weight
     * @throws RemoteException
     */
    public static Weight getWeight(IScaleService iWeight) throws RemoteException {
        if (iWeight == null) {
            return null;
        }

        int[] d = iWeight.getWeight();
        long tick = d[14] & 0xFFFFFFFFL;
        tick = System.currentTimeMillis() - (SystemClock.uptimeMillis() - tick);
        return new Weight(
                d[0], //皮重
                d[1],  //净重
                d[2], //数字皮重
                d[3],  //开机零点是否正常
                d[4],   //符号   ture 为正  false 负
                d[5],  //零位标志
                d[6], //稳定标志
                d[7],   //去皮状态
                d[8],  //超载状态
                d[9],  //开机零点高标志
                d[10],  //开机零点低标志
                d[11],  //小数点的位数
                d[12],   //最大量程范围
                d[13], //最小分度值
                tick); //当前的时间按毫秒计算; 最后一次读重量时的时间值
    }


}
