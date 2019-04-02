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
import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/8/10
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("拆板DTO")
public class DismantlingPlateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "需拆板ID不能为空")
    private Integer id;

    @NotNull(message = "拆板量不能为空")
    private Integer quantityCount;

    @NotNull(message = "拆板箱数不能为空")
    private Integer boxCount;

    private BigDecimal grossWeight;

    private BigDecimal netWeight;

    private BigDecimal volume;
}
