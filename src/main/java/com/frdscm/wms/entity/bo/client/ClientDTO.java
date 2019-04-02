package com.frdscm.wms.entity.bo.client;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class ClientDTO implements Serializable {

    @NotBlank(message = "客户全称不能为空")
    private String username;

    @NotBlank(message = "客户简称不能为空")
    private String shortname;

    @NotBlank(message = "客户行业不能为空")
    private String industry;

    private Address address;

    private List<Dictionary> area;

    @NotBlank(message = "业务代表不能为空")
    private String salesman;

    private String parent;

    @NotBlank(message = "合作状态不能为空")
    private String status;

    @NotNull(message = "对账周期不能为空")
    private Integer period;

    @NotNull(message = "接单法人不能为空")
    private String legal;

    private List<Contact> contacts;

    private List<Legal> legals;
}
