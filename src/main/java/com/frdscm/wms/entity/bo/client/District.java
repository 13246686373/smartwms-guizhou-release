package com.frdscm.wms.entity.bo.client;

import java.io.Serializable;

/**
 * Created by maxuan on 15/3/2018.
 */
public class District implements Serializable {

    private Integer code;
    private String name;
    private Double lat;
    private Double lng;

    public District() {
    }

    public District(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
