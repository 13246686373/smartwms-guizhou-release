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
 * @Author: dizhang
 * @Date: 2018/8/9
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("收货操作")
public class ReceiptListAppDTO implements Serializable {

    @ApiModelProperty(value = "收货管理列表ID 添加操作是不传，修改操作必传")
    private Integer id;

    @ApiModelProperty(value = "实际收货箱数 App上传", required = true)
    private Integer actualBoxCount;

    @ApiModelProperty(value = "实际收货数量 App上传", required = true)
    private Integer actualQuantityCount;

    @ApiModelProperty(value = "实际收货毛重（KG） App上传", required = true)
    private BigDecimal actualGrossWeight;

    @ApiModelProperty(value = "实际收货净重（KG） App上传", required = true)
    private BigDecimal actualNetWeight;

    @ApiModelProperty(value = "实际收货体积（m³） App上传", required = true)
    private BigDecimal actualVolume;

    @ApiModelProperty(value = "实际收货板数 App上传", required = true)
    private Integer actualBoardCount;

}
