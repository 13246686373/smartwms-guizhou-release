package com.frdscm.wms.entity.bo.client;

/**
 * Created by maxuan on 14/3/2018.
 */
public class Contact {
    private Integer id;
    private String name;
    private String position;
    private String phone;
    private String email;
    private String qq;
    private String remark;

    public Contact() {
    }

    public Contact(Integer id, String name, String position, String phone, String email, String qq, String remark) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.phone = phone;
        this.email = email;
        this.qq = qq;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
