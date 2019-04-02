package com.frdscm.wms.entity.bo.client;

import java.io.Serializable;

/**
 * Created by maxuan on 14/3/2018.
 */
public class Account implements Serializable {

    private Integer id;
    private String uid;
    private String username;
    private String fullName;
    private String name;

    public Account() {
    }

    public Account(String name) {
        this.name = name;
    }

    public Account(Integer id, String account, String username) {
        this.id = id;
        this.uid = account;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
