package com.frdscm.wms.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 仓库订单表
 *
 * @author zhangling
 * @since 2019-04-05
 */
@Data
@ApiModel("添加仓库订单对象")
public class WarehouseOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "订单编号", required = true)
    private String orderNo;

    @ApiModelProperty(value = "客户ID", required = true)
    private Integer clientId;

    @ApiModelProperty(value = "客户名称", required = true)
    @NotBlank(message = "客户名称不能为空")
    private String clientName;

    @ApiModelProperty(value = "合同编号", required = true)
    private String contractNo;

    @ApiModelProperty(value = "合同开始时间", required = true)
    private Date contactStartTime;

    @ApiModelProperty(value = "合同结束时间", required = true)
    private Date contactEndTime;

    @ApiModelProperty(value = "收货仓库ID", required = true)
    private Integer warehouseId;

    @ApiModelProperty(value = "收货仓库", required = true)
    private String receivingWarehouse;

    @ApiModelProperty(value = "预计到仓时间", required = true)
    private Date rojectedToWarehouseTime;

    @ApiModelProperty(value = "合同附件服务地址")
    private String addressOfContractAnnex;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "订单模式: 1-普通入库  2-仓单质押  3-电商下单")
    private Integer orderType;

    @ApiModelProperty(value = "操作人ID")
    private Integer operatorId;

    @ApiModelProperty(value = "操作人名称")
    private String operatorName;

    @ApiModelProperty(value = "订单货物明细列表")
    private List<CargoDetailsDTO> cargoDetailList;

    @ApiModelProperty(value = "订单费用项列表")
    private List<ExpenseItemDTO> expenseItemList;
}
