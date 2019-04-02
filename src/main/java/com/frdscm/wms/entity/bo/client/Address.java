package com.frdscm.wms.entity.bo.client;

import java.io.Serializable;

/**
 * Created by maxuan on 14/3/2018.
 */
public class Address implements Serializable {

    private District pro;
    private District city;
    private District dist;
    private District street;
    private String extra;

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public District getPro() {
        return pro;
    }

    public void setPro(District pro) {
        this.pro = pro;
    }

    public District getCity() {
        return city;
    }

    public void setCity(District city) {
        this.city = city;
    }

    public District getDist() {
        return dist;
    }

    public void setDist(District dist) {
        this.dist = dist;
    }

    public District getStreet() {
        return street;
    }

    public void setStreet(District street) {
        this.street = street;
    }
}
