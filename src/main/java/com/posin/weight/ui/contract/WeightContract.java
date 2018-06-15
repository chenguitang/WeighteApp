package com.posin.weight.ui.contract;

import com.posin.weight.base.BaseContract;
import com.posin.weight.been.Weight;

/**
 * FileName: FoodTypeContract
 * Author: Greetty
 * Time: 2018/6/14 11:17
 * Desc: TODO
 */
public interface WeightContract {

    interface IWeightView extends BaseContract.BaseView {

        /**
         * 显示秤错误信息
         *
         * @param errorMessage String
         */
        void weightError(String errorMessage);

        /**
         * 显示秤重量
         *
         * @param weight String
         */
        void updateWeight(String weight);

        /**
         * 是否稳定
         *
         * @param stable
         */
        void isStable(boolean stable);

        /**
         * 是否去皮
         *
         * @param Peel
         */
        void isPeel(boolean Peel);

        /**
         * 是否零位
         *
         * @param Zero
         */
        void isZero(boolean Zero);

        /**
         * 显示程序异常
         *
         * @param throwable Throwable
         */
        void showError(Throwable throwable);
    }

    interface IWeightPresenter extends BaseContract.BasePresenter {
        /**
         * 连接秤服务
         *
         */
        void bindService() throws Exception;


        /**
         * 获取秤重量值
         *
         * @return float
         */
        float getWeight() throws Exception;

        /**
         * 是否稳定
         *
         * @return boolean
         */
        boolean getStable() throws Exception;

        /**
         * 获取Weight实例对象
         *
         * @return Weight
         */
        Weight getWeightInstance()  throws Exception;

        /**
         * 置零
         */
        void setZero() throws Exception;

        /**
         * 去皮
         */
        void rePeel() throws Exception;
    }
}
