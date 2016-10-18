package com.zsygfddsd.spacestation.base.module.network_refresh;


import com.zsygfddsd.spacestation.base.module.base.BaseContract;
import com.zsygfddsd.spacestation.base.module.network.BaseNetContract;
import com.zsygfddsd.spacestation.data.bean.ComRespInfo;

/**
 * Created by mac on 16/6/11.
 */
public class BaseRefreshContract {

    public interface IBaseRefreshView<T extends IBaseRefreshPresenter, DATA> extends BaseNetContract.IBaseNetView<T> {

        void onBindViewData(ComRespInfo<DATA> dataComRespInfo);

        void showRefreshIndication();

        void hideRefreshInfication();

    }

    public interface IBaseRefreshPresenter extends BaseContract.IBasePresenter {

        void onRefreshData();

    }

}
