package com.csjbot.coshandler.aiui.aiui_soft.send_msg.http;

import com.csjbot.coshandler.util.ConfInfoUtil;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    /* 使用自定义的OKHttpClient */
    private static OkHttpClient httpClient;

    private static Retrofit retrofit;

    public static String sn = ConfInfoUtil.getSN();

    public static String ISOLanguage = "zh";
//    public static String ISOLanguage = "en";

    /**
     * 初始化OKHttpClient
     */
    public static void initClient() {
        try {
            if (httpClient == null) {
                // 系统日志拦截器
//                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//                // 设置日志级别
//                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                // 自定义OkhttpClient
                OkHttpClient.Builder client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS);
                // 添加拦截器
                client.addInterceptor(chain -> {
                    // 1、取得本地时间：
                    Calendar cal = Calendar.getInstance();
                    // 2、取得时间偏移量：
                    int timeOffSet = cal.get(Calendar.ZONE_OFFSET);
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .addHeader("sn", sn)
                            .addHeader("language", ISOLanguage)
                            .addHeader("timeOffset", String.valueOf(timeOffSet))
                            .build();
//            client.addInterceptor(loggingInterceptor);
                    return chain.proceed(request);
                });
//            client.addInterceptor(loggingInterceptor);
                httpClient = client.build();
            }
        } catch (Exception e) {

        }

    }

    /**
     * 构建一个retrofit对象
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T create(final Class<T> service) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .baseUrl(BaseAgentApiUrl.getDefaultAdress())
                    .build();
        }
        return retrofit.create(service);
    }

    /**
     * 构建一个retrofit对象
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T createNew(final Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .baseUrl("http://dev.csjbot.com:8888/")
                .build();
        return retrofit.create(service);
    }
}
