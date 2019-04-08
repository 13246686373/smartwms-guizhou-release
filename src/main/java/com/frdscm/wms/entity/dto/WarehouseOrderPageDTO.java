package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: chengdong
 * @description: 查询仓库对象
 */
@Data
@ToString
@ApiModel("仓库")
public class WarehouseOrderPageDTO extends BasePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 订单号  客户名称 订单模式  创建时间 合同编号


    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "客户名称")
    private String clientName;

    @ApiModelProperty(value = "订单模式 1 普通入储  2 仓单质押 3 电商下单")
    private Integer orderType = 1;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "合同编号")
    private String contractNo;



}
