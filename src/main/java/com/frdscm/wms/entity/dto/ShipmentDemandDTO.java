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
import java.util.Date;
import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/7/10
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("添加出货对象")
public class ShipmentDemandDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "出货需求ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "客户ID", required = true)
    @NotNull(message = "客户ID不能为空")
    private Integer clientId;

    @ApiModelProperty(value = "客户名称", required = true)
    @NotBlank(message = "客户名称不能为空")
    @Length(min = 1, max = 32, message = "出货单号长度异常")
    private String clientName;

    @ApiModelProperty(value = "出货单号", required = true)
    private String singleNumber;

    @ApiModelProperty(value = "出货仓库ID", required = true)
    @NotNull(message = "出货仓库ID不能为空")
    private Integer warehouseId;

    @ApiModelProperty(value = "出货仓库名称", required = true)
    @NotBlank(message = "出货仓库名称不能为空")
    @Length(min = 1, max = 255, message = "出货仓库名称长度异常")
    private String warehouseName;

    @ApiModelProperty(value = "出货类型ID", required = true)
    @NotNull(message = "出货类型ID不能为空")
    private Integer typeId;

    @ApiModelProperty(value = "出货类型名称", required = true)
    @NotBlank(message = "出货类型名称不能为空")
    @Length(min = 1, max = 32, message = "出货类型名称长度异常")
    private String typeName;

    @ApiModelProperty(value = "需求来源ID", required = true)
    @NotNull(message = "需求来源ID不能为空")
    private Integer sourceId;

    @ApiModelProperty(value = "需求来源名称", required = true)
    @NotBlank(message = "需求来源名称不能为空")
    @Length(min = 1, max = 32, message = "出货类型名称长度异常")
    private String sourceName;

    @ApiModelProperty(value = "来源单号", required = true)
    @Length(max = 32, message = "出货类型名称长度异常")
    private String sourceNumber;

    @ApiModelProperty(value = "交货日期", required = true)
    @NotNull(message = "交货日期")
    private Date deliveryTime;

    @ApiModelProperty(value = "拣货要求ID", required = true)
    @NotNull(message = "拣货要求ID")
    private Integer pickTypeId;

    @ApiModelProperty(value = "拣货要求名称", required = true)
    @NotBlank(message = "拣货要求名称")
    @Length(min = 1, max = 32, message = "拣货要求名称长度异常")
    private String pickTypeName;

    @ApiModelProperty(value = "出货方式ID", required = true)
    @NotNull(message = "出货方式ID")
    private Integer shipmentMethodId;

    @ApiModelProperty(value = "出货方式名称", required = true)
    @NotBlank(message = "出货方式名称不能为空")
    @Length(min = 1, max = 32, message = "出货方式名称长度异常")
    private String shipmentMethodName;

    @ApiModelProperty(value = "联系方式", required = true)
    private String phone;

    @ApiModelProperty(value = "收货方ID", required = true)
    private Integer cargoPartyId;

    @ApiModelProperty(value = "收货方(代码)", required = true)
    private String cargoPartyName;

    private String consigneeName;

    private String consigneeId;

    @ApiModelProperty(value = "收货地址")
    private String receiptAddress;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "所属片区")
    private String areaName;

    @ApiModelProperty(value = "备注", required = true)
    @Length(max = 32, message = "备注出货方式名称长度异常")
    private String remark;

    @ApiModelProperty(value = "要删除的出货明细ID", required = true)
    List<Integer> shipmentCargoDetailsIdDeleteList;

    @ApiModelProperty(value = "关联项目ID", required = true)
    private Integer projectId;

    @ApiModelProperty(value = "关联项目名称", required = true)
    private String projectName;

    @ApiModelProperty(value = "出货明细", required = true)
    List<ShipmentCargoDetailsDTO> shipmentCargoDetailsList;
}
