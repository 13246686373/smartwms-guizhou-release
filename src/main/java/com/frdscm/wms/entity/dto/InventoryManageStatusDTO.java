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
 * @Date: 2018/7/10
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("库存状态")
public class InventoryManageStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "库存ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "状态 -1 删除 0 不良品 1 良品", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;

}
