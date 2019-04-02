package com.frdscm.wms.entity.bo.client.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: chengdong
 * @description: TODO
 * @date: 2018/10/30 11:22 AM
 */
@Data
public class ApiClient {
    @NotBlank(message = "客户名称不能为空")
    private String username;

    @NotBlank(message = "客户简称不能为空")
    private String shortname;

    private String code;

    @NotBlank(message = "客户行业不能为空")
    private String userclass;

    @NotBlank(message = "公司地址省不能为空")
    private String pro;

    @NotBlank(message = "公司地址市不能为空")
    private String dist;

    @NotBlank(message = "公司地址区不能为空")
    private String city;
    /**
     * 街道
     */
    private String street;

    @NotBlank(message = "详细地址不能为空")
    private String extra;

    @NotBlank(message = "业务代表不能为空")
    private String salesman;

    @ApiModelProperty(value = "母公司")
    private String parent;

    @NotBlank(message = "合作状态不能为空")
    private String status;

    @NotBlank(message = "接单法人不能为空")
    private String legal;

    @NotBlank(message = "对账周期不能为空")
    private Integer period;

}
