package com.csjbot.coshandler.aiui.aiui_soft.send_msg.http;



import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 所有http请求实现类的基类
 * Created by jingwc on 2017/9/18.
 */

public abstract class BaseImpl {

    /**
     * 由子类必须实现的方法,获取一个Retrofit实例
     */
    public abstract <T> T getRetrofit();

    /**
     * 提供给子类调用的方法,通过传入参数Service.class创建一个retrofit的实例
     */
    public <T> T getRetrofit(Class<T> c) {
        return RetrofitFactory.create(c);
    }


    public <T> T getNewRetrofit(Class<T> c) {
        return RetrofitFactory.createNew(c);
    }

    /**
     * 封装请求Body方法
     */
    public RequestBody getBody(String params) {
        return RequestBody
                .create(MediaType.parse("Content-Type,application/json"), params);
    }

    /**
     * 线程调度封装
     */
    public <T> Observable<T> scheduler(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 线程调度封装
     */
    public <T> Observable<T> schedulerInNewThread(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    /**
     * 线程调度,指定运行的线程
     */
    public <T> Observable<T> scheduler(Observable<T> observable, Scheduler[] schedulers) {
        if (schedulers == null || schedulers.length != 2) {
            /* 指定线程不可用,则使用默认线程调度 */
            return scheduler(observable);
        }
        return observable.subscribeOn(schedulers[0])
                .observeOn(schedulers[1]);
    }
}
