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
@ApiModel("移位请求参数")
public class MoveWereHouseStorageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "储位号", required = true)
    @NotBlank(message = "储位号不能为空")
    private String warehouseStorageNumber;

}
