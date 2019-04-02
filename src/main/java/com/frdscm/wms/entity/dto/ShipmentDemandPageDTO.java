package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/7/11
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("出货需求信息")
public class ShipmentDemandPageDTO  extends BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户ID", required = true)
    private Integer clientId;

    @ApiModelProperty(value = "出货仓库ID", required = true)
    private Integer warehouseId;

    @ApiModelProperty(value = "出货类型ID", required = true)
    private Integer typeId;

    @ApiModelProperty(value = "需求来源ID", required = true)
    private Integer sourceId;

    @ApiModelProperty(value = "出货方式ID", required = true)
    private Integer shipmentMethodId;

}
