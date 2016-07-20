package net.zsygfddsd.qujing.components.httpLoader;

import net.zsygfddsd.qujing.bean.ComRespInfo;
import net.zsygfddsd.qujing.bean.Welfare;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mac on 16/7/19.
 */
public interface WelfareHttp {

    String BaseUrl = "http://gank.io/api/";

    @GET("data/{type}/{pageSize}/{page}")
    Call<ComRespInfo<List<Welfare>>> getWelfareList(@Path("type") String type, @Path("pageSize") String pageSize, @Path("page") String page);

}
