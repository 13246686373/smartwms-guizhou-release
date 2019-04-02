package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/7/10
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("拣货列表")
public class ShipmentListPageDTO extends BasePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "出货管理ID", required = true)
    private Integer shipmentManageId;

}
