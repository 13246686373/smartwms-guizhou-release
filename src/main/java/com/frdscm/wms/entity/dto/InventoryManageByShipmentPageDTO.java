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
 * @Date: 2018/8/4
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("出货需求明细")
public class InventoryManageByShipmentPageDTO extends BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "仓库ID",required = true)
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;

    @ApiModelProperty(value = "批次号",required = true)
    private String batchNumber;

    @ApiModelProperty(value = "拣货ID",required = true)
    private Integer pickTypeId;

}
