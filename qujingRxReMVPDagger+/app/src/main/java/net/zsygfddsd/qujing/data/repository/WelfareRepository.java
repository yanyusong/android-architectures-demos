package net.zsygfddsd.qujing.data.repository;

import com.zsygfddsd.spacestation.data.bean.ComRespInfo;

import net.zsygfddsd.qujing.common.helpers.ResponseTransformer;
import net.zsygfddsd.qujing.data.DataSource;
import net.zsygfddsd.qujing.data.bean.Welfare;
import net.zsygfddsd.qujing.data.http.HttpLoader;

import java.util.List;

import rx.Observable;

/**
 * Created by mac on 2016/10/11.
 */
public final class WelfareRepository implements DataSource.WelfareDataSource {

    //    @Inject
    public WelfareRepository() {
    }

    @Override
    public Observable<ComRespInfo<List<Welfare>>> getWelfareList(String type, String pageSize, String page) {
        return HttpLoader.getInstance().welfareHttp().getWelfareList(type, pageSize, page)
                .compose(new ResponseTransformer<List<Welfare>>());
    }

}
