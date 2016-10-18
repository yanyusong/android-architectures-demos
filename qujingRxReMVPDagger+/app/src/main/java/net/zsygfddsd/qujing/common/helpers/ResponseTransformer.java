package net.zsygfddsd.qujing.common.helpers;

import com.zsygfddsd.spacestation.data.bean.ComRespInfo;

import net.zsygfddsd.qujing.data.bean.RepInfo;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by mac on 2016/10/17.
 */

public class ResponseTransformer<T> implements Observable.Transformer<RepInfo<T>, ComRespInfo<T>> {
    @Override
    public Observable<ComRespInfo<T>> call(Observable<RepInfo<T>> repInfoObservable) {

        return repInfoObservable.map(new Func1<RepInfo<T>, ComRespInfo<T>>() {
            @Override
            public ComRespInfo<T> call(RepInfo<T> tRepInfo) {
                ComRespInfo comRespInfo = new ComRespInfo<>();
                comRespInfo.setResult(!tRepInfo.isError());
                comRespInfo.setResultcode(-1);
                comRespInfo.setMessage("");
                comRespInfo.setData(tRepInfo.getResults());
                return comRespInfo;
            }
        });
    }
}
