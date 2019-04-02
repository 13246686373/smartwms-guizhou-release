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
 * @Date: 2018/7/16
 * @Desc: 移库
 **/
@Setter
@Getter
@ToString
@ApiModel("移库请求参数")
public class MoveWereHouseStorageAppDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "板号", required = true)
    @NotBlank(message = "板号不能为空")
    private String boardNumber;

    @ApiModelProperty(value = "原储位号", required = true)
    @NotBlank(message = "原储位号不能为空")
    private String oldWarehouseStorageNumber;

    @ApiModelProperty(value = "新储位号", required = true)
    @NotBlank(message = "新储位号不能为空")
    private String newWarehouseStorageNumber;

    @ApiModelProperty(value = "仓库ID")
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;
}
