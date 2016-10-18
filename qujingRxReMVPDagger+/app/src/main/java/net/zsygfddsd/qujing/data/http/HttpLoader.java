package net.zsygfddsd.qujing.data.http;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac on 16/7/19.
 */
public class HttpLoader implements HttpContract {

    private static final String BaseUrl = "http://gank.io/api/";

    private static final int DEFAULT_TIMEOUT = 10;

    private Retrofit retrofit;

    private HttpLoader() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("okhttp", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okhttpBuiler = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        retrofit = retrofitBuilder
                .baseUrl(BaseUrl)
                .client(okhttpBuiler.build())
                .build();
    }

    public static HttpLoader getInstance() {
        return HttpLoaderHolder.welfareInstance;
    }

    //设计模式推荐的内部静态类实现的单例模式
    private static class HttpLoaderHolder {
        private static final HttpLoader welfareInstance = new HttpLoader();
    }

    private <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }

    @Override
    public WelfareService welfareHttp() {
        return createService(WelfareService.class);
    }
}
