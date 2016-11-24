package com.sola.github.tool_project.tools;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by slove
 * 2016/11/22.
 */
public class ApiConnection {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private static OkHttpClient httpClient;
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
//                    .addCallAdapterFactory(new)
                    //添加对于RxJava的适用，致使可以在Api接口当中直接使用Rxjava
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .addConverterFactory()
                    //直接使用Gson转换进行对于参数的适配
                    .addConverterFactory(GsonConverterFactory.create(gson));

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * @param baseUrl      注意这里的基础Url为域名或者Ip地址
     * @param serviceClass 实现服务接口的类
     * @param <S>          返回的服务接口
     * @return 服务接口对象
     */
    public static <S> S createService(String baseUrl, Class<S> serviceClass) {
        if (httpClient == null)
            httpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(false)
                    .connectTimeout(1, TimeUnit.MINUTES)
//                    .cache()
                    .addInterceptor(new LoggingInterceptor()).build();
//        buildSSlClient();
        //这里注意把builder改成了adapterBuilder，用来适配现有服务端的接口形式
        Retrofit retrofit = builder.baseUrl(baseUrl).client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    /**
     * 监听请求日志用的类
     */
    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            long t1 = System.nanoTime();
            Request.Builder ongoing = chain.request().newBuilder();
            Request request = ongoing.build();
            Log.d("OkHttp", String.format("Sending request %s \n %s",
                    request.url(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.d("OkHttp", String.format(Locale.getDefault(),
                    "Received response for %s in %.1fms ",
                    response.request().url(),
                    (t2 - t1) / 1e6d));
            return response;
        }
    }
}
