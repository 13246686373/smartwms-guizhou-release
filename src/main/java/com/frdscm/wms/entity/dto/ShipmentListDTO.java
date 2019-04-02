package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/7/11
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("拣货列表DTO")
public class ShipmentListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "拣货列表ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "库存管理ID", required = true)
    @NotNull(message = "库存管理ID不能为空")
    private Integer inventoryManageId;

    @ApiModelProperty(value = "出货管理ID", required = true)
    @NotNull(message = "出货管理ID不能为空")
    private Integer shipmentManageId;
}
