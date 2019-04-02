package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/8/10
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("合版DTO")
public class MergePlateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "合板ID不能为空")
    private List<Integer> ids;

    private Integer id;

    @ApiModelProperty(value = "板号")
    @NotBlank(message = "板号不能为空")
    private String boardNumber;

    @ApiModelProperty(value = "储位号")
    @NotBlank(message = "储位号不能为空")
    private String warehouseStorageNumber;
}
