package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author: chengdong
 * @description: 公共分页对象
 */
@Setter
@Getter
@ToString
@ApiModel("公共分页对象")
public class BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1309005582930223787L;

    @ApiModelProperty(value = "所有文本框输入查询", allowEmptyValue = true)
    private String keywords;

    @ApiModelProperty(value = "当前页码", required = true)
    @NotNull(message = "当前页码不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "每页显示多少条数据")
    @NotNull(message = "每页显示多少条数据不能为空")
    private Integer pageSize;
}
