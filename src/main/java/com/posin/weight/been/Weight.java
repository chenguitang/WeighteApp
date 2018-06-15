package com.posin.weight.been;

import android.os.Parcel;
import android.os.Parcelable;


public class Weight implements Parcelable {

    private int tareWeight; //皮重
    private int netWeight;  //净重
    private int numberTareWeight; //数字皮重
    private int zeroOKMark;  //开机零点是否正常
    private int signMark;   //符号   ture 为正  false 负
    private int zeroMark;  //零位标志
    private int steadyMark; //稳定标志
    private int tareMark;   //去皮状态
    private int overLoadMark;  //超载状态
    private int openZeroHighMark;  //开机零点高标志
    private int openZeroLowMark;  //开机零点低标志
    private int pointnumber;  //小数点的位数
    private int maxRange;   //最大量程范围
    private int minDivision; //最小分度值
    private long currentTM; //当前的时间按毫秒计算; 最后一次读重量时的时间值


    public Weight(int tareWeight, int netWeight, int numberTareWeight, int zeroOKMark, int signMark, int zeroMark, int steadyMark, int tareMark
            , int overLoadMark, int openZeroHighMark, int openZeroLowMark, int pointnumber, int maxRange, int minDivision, long currentTM) {
        this.tareWeight = tareWeight;
        this.netWeight = netWeight;
        this.numberTareWeight = numberTareWeight;
        this.zeroOKMark = zeroOKMark;
        this.signMark = signMark;
        this.zeroMark = zeroMark;
        this.steadyMark = steadyMark;
        this.tareMark = tareMark;
        this.overLoadMark = overLoadMark;
        this.openZeroHighMark = openZeroHighMark;
        this.openZeroLowMark = openZeroLowMark;
        this.pointnumber = pointnumber;
        this.maxRange = maxRange;
        this.minDivision = minDivision;
        this.currentTM = currentTM;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public int getMinDivision() {
        return minDivision;
    }

    public void setMinDivision(int minDivision) {
        this.minDivision = minDivision;
    }

    public Weight() {

    }

    public int getPointnumber() {
        return pointnumber;
    }

    public void setPointnumber(int pointnumber) {
        this.pointnumber = pointnumber;
    }

    public int getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(int tareWeight) {
        this.tareWeight = tareWeight;
    }

    public int getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(int netWeight) {
        this.netWeight = netWeight;
    }

    public int getNumberTareWeight() {
        return numberTareWeight;
    }

    public void setNumberTareWeight(int numberTareWeight) {
        this.numberTareWeight = numberTareWeight;
    }

    public int getZeroOKMark() {
        return zeroOKMark;
    }

    public void setZeroOKMark(int zeroOKMark) {
        this.zeroOKMark = zeroOKMark;
    }

    public int getSignMark() {
        return signMark;
    }

    public void setSignMark(int signMark) {
        this.signMark = signMark;
    }

    public int getZeroMark() {
        return zeroMark;
    }

    public void setZeroMark(int zeroMark) {
        this.zeroMark = zeroMark;
    }

    public int getSteadyMark() {
        return steadyMark;
    }

    public void setSteadyMark(int steadyMark) {
        this.steadyMark = steadyMark;
    }

    public int getTareMark() {
        return tareMark;
    }

    public void setTareMark(int tareMark) {
        this.tareMark = tareMark;
    }

    public int getOverLoadMark() {
        return overLoadMark;
    }

    public void setOverLoadMark(int overLoadMark) {
        this.overLoadMark = overLoadMark;
    }

    public int getOpenZeroHighMark() {
        return openZeroHighMark;
    }

    public void setOpenZeroHighMark(int openZeroHighMark) {
        this.openZeroHighMark = openZeroHighMark;
    }

    public int getOpenZeroLowMark() {
        return openZeroLowMark;
    }

    public void setOpenZeroLowMark(int openZeroLowMark) {
        this.openZeroLowMark = openZeroLowMark;
    }

    public long getCurrentTM() {
        return currentTM;
    }

    public void setCurrentTM(long currentTM) {
        this.currentTM = currentTM;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub

        dest.writeInt(tareWeight);//皮重
        dest.writeInt(netWeight);//净重
        dest.writeInt(numberTareWeight);//数字皮重
        dest.writeInt(zeroOKMark);//开机零点是否正常
        dest.writeInt(signMark);//符号   ture 为正  false 负
        dest.writeInt(zeroMark);//零位标志
        dest.writeInt(steadyMark);//稳定标志
        dest.writeInt(tareMark); //去皮状态
        dest.writeInt(overLoadMark);//超载状态
        dest.writeInt(openZeroHighMark);//开机零点高标志
        dest.writeInt(openZeroLowMark);//开机零点低标志
        dest.writeInt(pointnumber);//小数点的位数
        dest.writeInt(maxRange);//最大量程范围
        dest.writeInt(minDivision);//最小分度值
        dest.writeLong(currentTM);//最后读取重量的时间

    }

    // 添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
    public static final Parcelable.Creator<Weight> CREATOR
            = new Parcelable.Creator<Weight>() //①
    {
        @Override
        public Weight createFromParcel(Parcel source) {
            // 从Parcel中读取数据，返回Weight对象
            return new Weight(source.readInt(), source.readInt(), source.readInt(), source.readInt(), source.readInt(), source.readInt(),
                    source.readInt(), source.readInt(), source.readInt(), source.readInt(), source.readInt(), source.readInt(),
                    source.readInt(), source.readInt(), source.readLong());
        }

        @Override
        public Weight[] newArray(int size) {
            return new Weight[size];
        }
    };
}
