package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
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
@ApiModel("移动端拆板")
public class DismantlingPlateAppDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "板号")
    @NotBlank(message = "板号不能为空")
    private  String boardNumber;

    @ApiModelProperty(value = "拆板量")
    private Integer dismantlingPlateCount;

    @ApiModelProperty(value = "出货管理ID")
    private Integer shipmentManageId;

    @ApiModelProperty("仓库ID")
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;
}
