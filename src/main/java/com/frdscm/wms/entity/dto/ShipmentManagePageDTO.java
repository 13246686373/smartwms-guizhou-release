package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/7/27
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("出货管理分页")
public class ShipmentManagePageDTO  extends BasePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户ID", required = true)
    private Integer clientId;

    @ApiModelProperty(value = "仓库ID", required = true)
    private List<Integer> warehouseFilters;

    @ApiModelProperty(value = "状态ID", required = true)
    private List<Integer> shipmentManageStatusFilters;

    @ApiModelProperty(value = "单号", required = true)
    private String singleNumber;
}
