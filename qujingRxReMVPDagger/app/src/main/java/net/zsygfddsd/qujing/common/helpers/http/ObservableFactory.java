package net.zsygfddsd.qujing.common.helpers.http;

import android.content.Context;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import net.zsygfddsd.qujing.base.common.ComRespInfo;
import net.zsygfddsd.qujing.common.helpers.http.transformer.SchedulerTransformer;

import rx.Observable;


/**
 * Created by mac on 16/7/26.
 */
public class ObservableFactory {

    public static <T> Observable<ComRespInfo<T>> createNetObservable(Context context, Observable<ComRespInfo<T>> observable, RxFragment rxFragment) {

        return observable
                .compose(new SchedulerTransformer<T>())
                .compose(rxFragment.<ComRespInfo<T>>bindUntilEvent(FragmentEvent.DESTROY));
    }


}
