package net.zsygfddsd.qujing.data.http;

import net.zsygfddsd.qujing.data.bean.RepInfo;
import net.zsygfddsd.qujing.data.bean.Welfare;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by mac on 16/7/19.
 */
public interface WelfareService {

    @GET("data/{type}/{pageSize}/{page}")
    Observable<RepInfo<List<Welfare>>> getWelfareList(@Path("type") String type, @Path("pageSize") String pageSize, @Path("page") String page);

}
