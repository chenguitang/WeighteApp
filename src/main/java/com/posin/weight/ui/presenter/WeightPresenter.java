package com.posin.weight.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;

import com.posin.svr.IScaleService;
import com.posin.weight.been.Weight;
import com.posin.weight.module.weight.ErrorCode;
import com.posin.weight.module.weight.WeightUtils;
import com.posin.weight.ui.contract.WeightContract;

/**
 * FileName: WeightPresenter
 * Author: Greetty
 * Time: 2018/6/15 14:31
 * Desc: TODO
 */
public class WeightPresenter implements WeightContract.IWeightPresenter {

    private static IScaleService iWeight = null;
    private static final String SVR_NAME = "minipos.escale";
    private static final int UPDATE_INTERVAL = 500;
    private static final int BIND_INTERVAL = 5000;

    private Context mContext;
    private Handler mHandler;
    //    private int mBindInterval;
    private WeightContract.IWeightView mWeightView;
    private Weight mWeight;

    public WeightPresenter(Context context, WeightContract.IWeightView weightView, Handler handler) {
        this.mContext = context;
        this.mWeightView = weightView;
        this.mHandler = handler;
//        this.mBindInterval = bindInterval;
    }

    protected void bindWeightService() {
        if (iWeight == null) {
            try {
                IBinder b = ServiceManager.getService(SVR_NAME);
                if (b == null)
                    throw new RemoteException("failed to connect to " + SVR_NAME);
                iWeight = IScaleService.Stub.asInterface(b);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private final Runnable mBindService = new Runnable() {
        @Override
        public void run() {
            bindWeightService();
            mHandler.postDelayed(this, BIND_INTERVAL);
        }
    };

    private final Runnable mUpdateWeight = new Runnable() {
        @Override
        public void run() {
            if (iWeight != null) {
                try {
                    doUpdateWeight();
                } catch (Throwable e) {
                    iWeight = null;
                    mWeightView.showError(e);
                    e.printStackTrace();
                }
            } else {
                updateErrorCode(ErrorCode.ERR_SERVICE_DISCONNECTED);
            }
            mHandler.postDelayed(this, UPDATE_INTERVAL);
        }
    };


    @Override
    public void bindService() throws Exception {
        mHandler.post(mBindService);
        mHandler.post(mUpdateWeight);
    }

    @Override
    public float getWeight() throws Exception {
        if (mWeight == null) {
            return 0;
        } else {
            return mWeight.getNetWeight() / 1000.0f;
        }
    }

    @Override
    public boolean getStable() throws Exception {
        if (mWeight == null) {
            return false;
        } else {
            return mWeight.getSteadyMark() > 0;
        }
    }

    @Override
    public Weight getWeightInstance() throws Exception {
        if (mWeight == null) {
            throw new Exception("Weight services is null, please bindService");
        }
        return mWeight;
    }

    @Override
    public void setZero() throws Exception {
        if (iWeight == null) {
            throw new Exception("IScaleService services is null, please bindService");
        }
        iWeight.setZero();
    }

    @Override
    public void rePeel() throws Exception {
        if (iWeight == null) {
            throw new Exception("IScaleService services is null, please bindService");
        }
        iWeight.setTare();
    }

    /**
     * 获取IScaleService服务
     *
     * @return IScaleService
     */
    public IScaleService getIScaleService() {
        return iWeight;
    }

    private void doUpdateWeight() throws Exception {
        if (iWeight == null) {
            throw new Exception("IScaleService services is null, please bindService");
        }

        int ec = iWeight.getErrorCode();
        if (ec != ErrorCode.ERR_OK) {
            updateErrorCode(ec);
            mWeight = null;
        } else {
            mWeight = WeightUtils.getWeight(iWeight);

            //重量
            mWeightView.updateWeight(mWeight.getNetWeight());

            //是否稳定
            mWeightView.isStable(mWeight.getSteadyMark() > 0);

            //是否零位
            mWeightView.isZero(mWeight.getZeroMark() > 0);

            //是否去皮
            mWeightView.isPeel(mWeight.getTareMark() > 0);

        }
    }

    private void updateErrorCode(int ec) {
        if ((ec & ErrorCode.ERR_SP_FAILED) != 0) {
            mWeightView.weightError("打开串口失败");
        } else if ((ec & ErrorCode.ERR_CONN_FAILED) != 0) {
            mWeightView.weightError("AD通信失败");
        } else if ((ec & ErrorCode.ERR_NO_WEIGHT_TABLE) != 0) {
            mWeightView.weightError("未校准或\n通信故障");
        } else if ((ec & ErrorCode.ERR_INCORRECT_AD) != 0) {
            mWeightView.weightError("AD错误");
        } else if ((ec & ErrorCode.ERR_SERVICE_DISCONNECTED) != 0) {
            mWeightView.weightError("无法连接\n称重服务程序");
        } else {
            mWeightView.weightError("未知错误!");
        }
    }
}
