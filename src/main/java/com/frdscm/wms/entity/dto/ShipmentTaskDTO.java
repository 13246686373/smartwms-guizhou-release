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
 * @Date: 2018/8/9
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("拣货任务")
public class ShipmentTaskDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "出货需求ID")
    @NotNull(message = "出货需求ID不能为空")
    private Integer shipmentDemandId;

}
