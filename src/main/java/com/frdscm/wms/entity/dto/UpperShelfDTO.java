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
 * @Date: 2018/8/2
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("入储DTO")
public class UpperShelfDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(message = "ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "ID")
    @NotNull(message = "收货管理ID不能为空")
    private Integer receiptManageId;

    @ApiModelProperty(value = "仓库ID")
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;

    @ApiModelProperty(value = "储位号")
    @NotNull(message = "储位号不能为空")
    private String warehouseStorageNumber;
}
