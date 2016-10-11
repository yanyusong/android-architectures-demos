package net.zsygfddsd.qujing.common.helpers.Subscriber;

import android.content.Context;
import android.support.annotation.CallSuper;

import net.zsygfddsd.qujing.base.module.network.BaseNetContract;
import net.zsygfddsd.qujing.common.utils.DeviceUtils;
import net.zsygfddsd.qujing.data.bean.ComRespInfo;

import rx.Subscriber;

/**
 * Created by mac on 16/7/27.
 */
public abstract class NetCheckerSubscriber<T> extends Subscriber<ComRespInfo<T>> {

    private Context context;
    private BaseNetContract.INetView netView;

    public NetCheckerSubscriber(Context context, BaseNetContract.INetView netView) {
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
        //        if ((tComRespInfo.getResult() != 1) && tComRespInfo.getResultcode() == 99 || tComRespInfo.getResultcode() == 97) {
        //            netView.showToLoginDialog();
        //        }
    }
}
