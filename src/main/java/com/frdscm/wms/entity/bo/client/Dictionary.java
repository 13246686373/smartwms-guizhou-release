package com.frdscm.wms.entity.bo.client;

import java.io.Serializable;

/**
 * 数据字典
 */
public class Dictionary implements Serializable {

    private Integer id; // 编号
    private String title; // 名称
    private String type; // 类型

    public Dictionary() {}

    public Dictionary(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
