package com.zsygfddsd.spacestation.common.helpers.http.transformer;


import com.zsygfddsd.spacestation.data.bean.ComRespInfo;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 16/7/26.
 * Schedulers线程调度器的统一处理
 */
public class SchedulerTransformer<T> implements Observable.Transformer<ComRespInfo<T>, ComRespInfo<T>> {

    public SchedulerTransformer() {
    }

    @Override
    public Observable<ComRespInfo<T>> call(Observable<ComRespInfo<T>> comRespInfoObservable) {
        return comRespInfoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }


}
