package com.frdscm.wms.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dizhang
 * @since 2018-08-11
 */
@Setter
@Getter
@ToString
@ApiModel("出货扫描列表")
public class ShipmentScanListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "板号", required = true)
    private String boardNumber;

    @ApiModelProperty(value = "箱号", required = true)
    private String boxNum;

    /**
     * 物料料号
     */
    private String materialNumber;

    @ApiModelProperty(value = "子箱号", required = true)
    private String boxSonNum;

    @ApiModelProperty(value = "序列号", required = true)
    private String boxSerialNum;

    @ApiModelProperty(value = "备注", required = true)
    private String remark;

    @ApiModelProperty(value = "出货需求ID", required = true)
    @NotNull(message = "出货需求ID不能为空")
    private Integer shipmentManageId;

}
