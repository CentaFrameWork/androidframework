package com.centaandroidframework.network;

import com.centaandroidframework.models.respmodels.RespCityModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author yanwenqiang
 * @Date 17/1/3
 * @description 请求交互者
 */
public class RequestPresenter {

    public static void getName(String name, Subscriber<String> subscriber) {
        RequestManager.getFirstApi().getName(name)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getCity(String name, Subscriber<RespCityModel> subscriber) {
        RequestManager.getFirstApi().getCity(name)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
