package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: chengdong
 * @description: 添加储位对象
 */
@Setter
@Getter
@ToString
@ApiModel("添加储位对象")
public class WarehouseStorageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "储位ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "储位编号")
    @Length(min = 1, max = 20, message = "储位编号长度异常")
    private  String  number;

    @ApiModelProperty(value = "储位类型ID")
    @NotNull(message = "储位类型ID不能为空")
    private Integer typeId;

    @ApiModelProperty(value = "储位类型名称")
    @Length(min = 1, max = 32, message = "储位类型名称长度异常")
    private String typeName;

    @ApiModelProperty(value = "储位容量")
    @NotNull(message = "储位容量不能为空")
    private BigDecimal capacity;

    @ApiModelProperty(value = "储位长")
    private BigDecimal length;

    @ApiModelProperty(value = "储位宽")
    private BigDecimal width;

    @ApiModelProperty(value = "储位高")
    private BigDecimal height;

    @ApiModelProperty(value = "备注")
    @Length(max = 255, message = "备注长度异常")
    private String remark;

    @ApiModelProperty(value = "仓库ID")
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;

}
