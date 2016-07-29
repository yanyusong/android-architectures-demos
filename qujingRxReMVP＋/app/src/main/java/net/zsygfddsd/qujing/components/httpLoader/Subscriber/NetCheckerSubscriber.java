package net.zsygfddsd.qujing.components.httpLoader.Subscriber;

import android.content.Context;

import net.zsygfddsd.qujing.base.module.network.BaseNetContract;
import net.zsygfddsd.qujing.common.utils.DeviceUtils;

import rx.Subscriber;

/**
 * Created by mac on 16/7/27.
 */
public abstract class NetCheckerSubscriber<T> extends Subscriber<T> {

    private Context context;
    private BaseNetContract.IBaseNetView netView;

    public NetCheckerSubscriber(Context context, BaseNetContract.IBaseNetView netView) {
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

}
