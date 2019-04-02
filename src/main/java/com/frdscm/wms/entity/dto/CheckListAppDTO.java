package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: dizhang
 * @Date: 2018/8/9
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("盘点操作")
public class CheckListAppDTO implements Serializable {

    @ApiModelProperty(value = "盘点明细列表ID")
    @NotNull(message = "盘点任务ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "板号")
    @NotBlank(message = "板号不能为空")
    private String boardNumber;

    @ApiModelProperty(value = "盘点结果 1-正常 2-数量异常 3-破损异常")
    @NotNull(message = "盘点结果不能为空")
    private Integer checkResult;

    @ApiModelProperty(value = "盘点数量")
    @NotNull(message = "盘点数量不能为空")
    private BigDecimal checkSum;
}
