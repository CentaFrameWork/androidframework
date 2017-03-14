package com.centaandroidframework.network.api;

import com.centaandroidframework.models.respmodels.RespCityModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author yanwenqiang
 * @Date 17/1/3
 * @description FirstApiController下的api定义
 */
public interface FirstApi {

    @GET("FirstApi/getName")
    Observable<String> getName(@Query("name") String name);

    @GET("FirstApi/getCity")
    Observable<RespCityModel> getCity(@Query("cityName") String name);
}

