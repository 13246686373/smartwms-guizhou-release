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
 * @Date: 2018/8/1
 * @Desc: 查询批次号
 **/
@Setter
@Getter
@ToString
@ApiModel("查询批次号")
public class SelectbatchNumberDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "出货需求ID", required = true)
    @NotNull(message = "出货需求ID")
    private Integer clientId;


    @ApiModelProperty(value = "仓库ID", required = true)
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;
}
