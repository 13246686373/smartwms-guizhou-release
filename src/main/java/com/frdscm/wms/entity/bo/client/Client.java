package com.frdscm.wms.entity.bo.client;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Client implements Serializable {
    private static final long serialVersionUID = -3957857851621783155L;

    @ApiModelProperty(value = "客户编号")
    private Integer id;

    @ApiModelProperty(value="客户名称")
    private String username;

    @ApiModelProperty(value="客户简称")
    private String shortname;

    @ApiModelProperty(value="客户代码")
    private String code;

    @ApiModelProperty(value="所属行业")
    private Dictionary userclass;

    @ApiModelProperty(value="业务范围")
    private List<Dictionary> scopes;

    @ApiModelProperty(value="地址")
    private Address address;

    @ApiModelProperty(value="所属片区")
    private List<Dictionary> area;

    @ApiModelProperty(value="业务代表")
    private Account salesman;

    @ApiModelProperty(value="母公司")
    private Client parent;

    @ApiModelProperty(value="合作状态")
    private Dictionary status;

    @ApiModelProperty(value="接单法人")
    private Account legal;

    @ApiModelProperty(value="标签")
    private List<Dictionary> labels;

    @ApiModelProperty(value="客户联系人")
    private List<Contact> contacts;

    @ApiModelProperty(value="对账周期")
    private Integer period;

    @ApiModelProperty(value="客户法人")
    private List<Legal> legals;

    @ApiModelProperty(hidden = true)
    private Integer companyId;
}
