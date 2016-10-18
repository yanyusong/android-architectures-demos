package net.zsygfddsd.qujing.base.module.network_refresh;


import net.zsygfddsd.qujing.base.common.ComRespInfo;
import net.zsygfddsd.qujing.base.module.base.BaseContract;
import net.zsygfddsd.qujing.base.module.network.BaseNetContract;

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
