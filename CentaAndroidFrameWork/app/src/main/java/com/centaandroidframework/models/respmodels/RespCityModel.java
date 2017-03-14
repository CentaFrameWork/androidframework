package com.centaandroidframework.models.respmodels;

/**
 * @author yanwenqiang
 * @Date 17/1/3
 * @description 返回城市信息实体
 */
public class RespCityModel {
    private String Id;
    private String CityName;
    private String WebSite;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getWebSite() {
        return WebSite;
    }

    public void setWebSite(String webSite) {
        WebSite = webSite;
    }
}
