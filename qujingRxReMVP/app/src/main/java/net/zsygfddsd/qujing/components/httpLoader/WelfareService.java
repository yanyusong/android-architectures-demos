package net.zsygfddsd.qujing.components.httpLoader;

import net.zsygfddsd.qujing.bean.ComRespInfo;
import net.zsygfddsd.qujing.bean.Welfare;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by mac on 16/7/19.
 */
public interface WelfareService {

    String BaseUrl = "http://gank.io/api/";

    @GET("data/{type}/{pageSize}/{page}")
    Observable<ComRespInfo<List<Welfare>>> getWelfareList(@Path("type") String type, @Path("pageSize") String pageSize, @Path("page") String page);

}
