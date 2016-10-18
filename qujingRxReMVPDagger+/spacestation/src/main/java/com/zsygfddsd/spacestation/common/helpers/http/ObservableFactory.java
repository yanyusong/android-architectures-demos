package com.zsygfddsd.spacestation.common.helpers.http;

import android.content.Context;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.zsygfddsd.spacestation.common.helpers.http.transformer.SchedulerTransformer;
import com.zsygfddsd.spacestation.data.bean.ComRespInfo;

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
