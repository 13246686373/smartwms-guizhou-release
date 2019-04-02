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
@ApiModel("APP合版DTO")
public class MergePlateAppDTO implements Serializable {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "合板ID不能为空")
//    private List<Integer> ids;
//
//    private Integer id;
//
//    @ApiModelProperty(value = "板号")
//    @NotBlank(message = "板号不能为空")
//    private String boardNumber;
//
//    @ApiModelProperty(value = "储位号")
//    @NotBlank(message = "储位号不能为空")
//    private String warehouseStorageNumber;


    @ApiModelProperty(value = "板号1")
    @NotBlank(message = "板号1不能为空")
    private String boardNumberOne;

    @ApiModelProperty(value = "板号2")
    @NotBlank(message = "板号2不能为空")
    private String boardNumberTwo;

    @ApiModelProperty(value = "使用的板号(使用新板传空或null)")
    private String boardNumberMerge;

    @ApiModelProperty(value = "仓库ID")
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;
}
