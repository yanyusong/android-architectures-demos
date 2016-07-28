package net.zsygfddsd.qujing.components.httpLoader.Subscriber;

import android.content.Context;
import android.widget.Toast;

import net.zsygfddsd.qujing.common.utils.DeviceUtils;

import rx.Subscriber;

/**
 * Created by mac on 16/7/27.
 */
public abstract class NetCheckerSubscriber<T> extends Subscriber<T> {

    private Context context;

    public NetCheckerSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!new DeviceUtils(context).isHasNetWork()) {
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            Toast.makeText(context, "请检查网络连接后重试!", Toast.LENGTH_SHORT).show();
        }
    }

}
