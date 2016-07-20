package net.zsygfddsd.qujing.components.httpLoader;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac on 16/7/19.
 */
public class HttpLoader implements HttpContract {

    private static final HttpLoader instance = new HttpLoader();

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;

    private WelfareHttp welfareHttp;


    private HttpLoader() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                //                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(WelfareHttp.BaseUrl)
                .build();
    }

    public static HttpLoader getInstance() {

        return instance;
    }

    @Override
    public WelfareHttp welfareHttp() {
        if (welfareHttp==null) {
            welfareHttp = retrofit.create(WelfareHttp.class);
        }
        return welfareHttp;
    }
}
