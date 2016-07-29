package net.zsygfddsd.qujing.components.httpLoader;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac on 16/7/19.
 */
public class HttpLoader implements HttpContract {

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;

    private WelfareService welfareHttp;


    private HttpLoader(String baseUrl) {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public static HttpLoader getWelfareInstance() {
        return HttpLoaderHolder.welfareInstance;
    }

    public static HttpLoader getOtherInstance() {
        return HttpLoaderHolder.otherInstance;
    }

    //设计模式推荐的内部静态类实现的单例模式
    private static class HttpLoaderHolder {

        private static final HttpLoader welfareInstance = new HttpLoader(WelfareService.BaseUrl);

        private static final HttpLoader otherInstance = new HttpLoader(WelfareService.BaseUrl);
    }


    @Override
    public WelfareService welfareHttp() {
        if (welfareHttp == null) {
            welfareHttp = retrofit.create(WelfareService.class);
        }
        return welfareHttp;
    }
}
