package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: dizhang
 * @Date: 2018/8/2
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("盘点明细列表")
public class CheckListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "盘点明细列表ID（编辑时必传）")
    @NotNull(message = "ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "盘点结果 1-正常 2-数量异常 3-破城异常")
    @NotNull(message = "盘点结果不能为空")
    private Integer checkResult;

    @ApiModelProperty(value = "盘点数量")
    @NotNull(message = "盘点数量不能为空")
    private BigDecimal checkSum;
}
