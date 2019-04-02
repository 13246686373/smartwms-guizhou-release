package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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
@ApiModel("返回盘点信息")
public class GetCheckListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "板号")
    @NotBlank(message = "板号不能为空")
    private String boardNumber;

    @ApiModelProperty(value = "库位")
    @NotBlank(message = "库位不能为空")
    private String warehouseStorageNumber;

    @ApiModelProperty(value = "盘点任务ID")
    @NotNull(message = "盘点任务ID不能为空")
    private Integer checkManageId;
}
