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
 * @Date: 2018/8/9
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("货物查询")
public class FreightInquiryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "仓库ID")
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;

    @ApiModelProperty(value = "库位", required = true)
    @Length(max = 32, message = "库位号长度异常")
    private String warehouseStorageNumber;

    @ApiModelProperty(value = "物料料号", required = true)
    @Length(max = 32, message = "物料料号长度异常")
    private String materialNumber;

    @ApiModelProperty(value = "板号", required = true)
    @Length(max = 255, message = "板号名称长度异常")
    private String boardNumber;




}
