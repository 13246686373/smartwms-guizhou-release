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
 * @Date: 2018/7/5
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("收货需求DTO")
public class ReceiptDemandDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收货需求ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "收货单号", required = true)
    private String singleNumber;

    @ApiModelProperty(value = "客户ID", required = true)
    @NotNull(message = "客户ID不能为空")
    private Integer clientId;

    @ApiModelProperty(value = "客户名称", required = true)
    @NotBlank(message = "客户名称不能为空")
    @Length(min = 1, max = 32, message = "客户名称长度异常")
    private String clientName;

    @ApiModelProperty(value = "收货仓库ID", required = true)
    @NotNull(message = "收货仓库ID不能为空")
    private Integer warehouseId;

    @ApiModelProperty(value = "收货类型ID", required = true)
    @NotNull(message = "收货类型ID不能为空")
    private Integer typeId;

    @ApiModelProperty(value = "收货类型名称", required = true)
    @NotBlank(message = "收货类型名称不能为空")
    @Length(min = 1, max = 32, message = "收货类型名称长度异常")
    private String typeName;

    @ApiModelProperty(value = "收货方式ID", required = true)
    @NotNull(message = "收货方式ID不能为空")
    private Integer modeId;

    @ApiModelProperty(value = "收货方式名称", required = true)
    @NotNull(message = "收货方式名称不能为空")
    private String modeName;

    @ApiModelProperty(value = "需求来源ID", required = true)
    private Integer sourceId;

    @ApiModelProperty(value = "需求来源名称", required = true)
    private String sourceName;

    @ApiModelProperty(value = "订单号", required = true)
    private String orderNumber;

    @ApiModelProperty(value = "来源单号(多个用逗号分隔)", required = true)
    private String waybillNumber;

    @ApiModelProperty(value = "预计到仓时间", required = true)
    @NotNull(message = "预计到仓时间不能为空")
    private Date expectedTime;

    @ApiModelProperty(value = "操作模式ID", required = true)
    @NotNull(message = "操作模式ID不能为空")
    private Integer operatingModeId;

    @ApiModelProperty(value = "操作模式名称", required = true)
    @Length(min = 1, max = 32, message = "操作模式名称长度异常")
    @NotBlank(message = "操作模式名称不能为空")
    private String operatingModeName;

    @ApiModelProperty(value = "备注", allowEmptyValue = true)
    @Length(max = 255, message = "备注长度异常")
    private String remark;

    @ApiModelProperty(value = "关联项目ID", allowEmptyValue = true)
    private Integer projectId;

    @ApiModelProperty(value = "关联项目名称", allowEmptyValue = true)
    private String projectName;

    @ApiModelProperty(value = "收货需求明细", allowEmptyValue = true)
    List<ReceiptCargoDetailsDTO> receiptCargoDetailsList;

    @ApiModelProperty(value = "删除收货需求明细ID数组", allowEmptyValue = true)
    List<Integer> receiptCargoDetailsIdDeleteList;
}
