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

/**
 * @Author: dizhang
 * @Date: 2018/7/13
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("货物上架")
public class ReceiptDemandPickUpDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "储位编号")
    @NotBlank(message = "储位编号不能为空")
    @Length(min = 1, max = 32, message = "储位编号长度异常")
    private  String  warehouseStorageNumber;

    @ApiModelProperty(value = "板号", required = true)
    @NotBlank(message = "板号不能为空")
    @Length(min = 1, max = 32, message = "板号名称长度异常")
    private String boardNumber;

    @ApiModelProperty(value = "仓库ID", required = true)
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;
}
