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
 * @Date: 2018/8/10
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("修改状态")
public class UpdateStatusDTO implements Serializable {

    @ApiModelProperty(value = "id")
    @NotNull(message = "获取ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "状态 0-不良品 1-良品 ")
    @NotNull(message = "状态不能为空")
    private Integer status;
}
