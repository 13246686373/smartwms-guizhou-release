package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/8/2
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("盘点列表")
public class CheckManagePageDTO extends BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "盘点类型",required = true)
    private List<Integer> checkTypeFilters;

    @ApiModelProperty(value = "仓库ID", required = true)
    private List<Integer> warehouseFilters;

    @ApiModelProperty(value = "状态ID", required = true)
    private List<Integer> checkManageStatusFilters;

}
