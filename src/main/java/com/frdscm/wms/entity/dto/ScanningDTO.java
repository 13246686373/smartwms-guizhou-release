package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/8/10
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("拣货扫描")
public class ScanningDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "拣货列表明细ID")
    @NotNull(message = "拣货列表明细ID不能为空")
    private Integer shipmentListId;

    @ApiModelProperty(value = "板号")
    @NotBlank(message = "板号不能为空")
    private String boardNumber;

    @ApiModelProperty(value = "储位号")
    @NotBlank(message = "储位号不能为空")
    private String warehouseStorageNumber;

    @ApiModelProperty(value = "拣货列表ID")
    @NotNull(message = "拣货列表ID不能为空")
    private Integer shipmentDemandId;

    @ApiModelProperty(value = "仓库ID")
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;

    @ApiModelProperty(value = "拣货类型 1-随机 2-定点或者先进先出")
    @NotNull(message = "拣货类型不能为空")
    private Integer type;
}
