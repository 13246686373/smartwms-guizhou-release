package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: chengdong
 * @description: 添加仓库对象
 */
@Setter
@Getter
@ToString
@ApiModel("添加仓库对象")
public class WarehouseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "仓库ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "仓库名称", required = true)
    @NotBlank(message = "仓库名称不能为空")
    @Length(min = 1, max = 256, message = "仓库名称长度异常")
    private String name;

    @ApiModelProperty(value = "仓库类型ID", required = true)
    @NotNull(message = "仓库类型ID不能为空")
    private Integer typeId;

    @ApiModelProperty(value = "仓库类型名称", required = true)
    @NotBlank(message = "仓库类型名称不能为空")
    @Length(min = 1, max = 128, message = "仓库类型名称长度异常")
    private String typeName;

    @ApiModelProperty(value = "负责人", required = true)
    @NotBlank(message = "负责人不能为空")
    @Length(min = 1, max = 32, message = "负责人长度异常")
    private String principal;

    @ApiModelProperty(value = "联系方式", required = true)
    @NotBlank(message = "联系方式不能为空")
    @Length(min = 1, max = 12, message = "联系方式长度异常")
    private String phone;

    @ApiModelProperty(value = "仓库地址", required = true)
    @NotBlank(message = "仓库地址不能为空")
    @Length(min = 1, max = 1000, message = "仓库地址长度异常")
    private String address;

    @ApiModelProperty(value = "所属片区", required = true)
    private String areaName;

    @ApiModelProperty(value = "经度", required = true)
    private BigDecimal longitude;

    @ApiModelProperty(value = "维度", required = true)
    private BigDecimal latitude;

    @ApiModelProperty(value = "备注", allowEmptyValue = true)
    @Length(max = 256, message = "备注长度异常")
    private String remark;

}
