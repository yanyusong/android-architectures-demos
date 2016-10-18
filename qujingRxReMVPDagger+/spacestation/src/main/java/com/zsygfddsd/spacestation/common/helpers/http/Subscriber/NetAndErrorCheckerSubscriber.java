package com.zsygfddsd.spacestation.common.helpers.http.Subscriber;

import android.content.Context;
import android.support.annotation.CallSuper;

import com.zsygfddsd.spacestation.base.module.network.BaseNetContract;
import com.zsygfddsd.spacestation.common.utils.DeviceUtils;
import com.zsygfddsd.spacestation.data.bean.ComRespInfo;

import rx.Subscriber;

/**
 * Created by mac on 16/7/27.
 */
public abstract class NetAndErrorCheckerSubscriber<T> extends Subscriber<ComRespInfo<T>> {

    private Context context;
    private BaseNetContract.INetView netView;

    public NetAndErrorCheckerSubscriber(Context context, BaseNetContract.INetView netView) {
        this.context = context;
        this.netView = netView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!new DeviceUtils(context).isHasNetWork()) {
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            netView.showNoNetWork();
        }
    }

    @CallSuper
    @Override
    public void onNext(ComRespInfo<T> tComRespInfo) {
        // TODO: 2016/10/17 公共errorCode处理
        //        if ((tComRespInfo.getResult() != 1) && tComRespInfo.getResultcode() == 99 || tComRespInfo.getResultcode() == 97) {
        //            netView.showToLoginDialog();
        //        }
    }


}
