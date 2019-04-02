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
 * <p>
 *
 * </p>
 *
 * @author March_CD
 * @since 2018-07-27
 */
@Setter
@Getter
@ToString
@ApiModel("储位等级")
public class WarehouseStorageGradeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "储位等级ID")
    private Integer id;

    @ApiModelProperty(value = "父ID")
    @NotNull(message = "父id不能为空")
    private Integer pid;

    @ApiModelProperty(value = "类型 1-仓 2-层 3-区 4-巷道")
    @NotNull(message = "类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "编号")
    @NotBlank(message = "编号不能为空")
    private String number;

    @ApiModelProperty(value = "仓库ID")
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;

    @ApiModelProperty(value = "对应区域 1-良品区 2-不良品区 3-待检区 4-待收区 5-发货区")
    private Integer region;

}
