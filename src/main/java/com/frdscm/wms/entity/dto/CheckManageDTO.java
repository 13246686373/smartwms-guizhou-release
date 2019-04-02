package com.frdscm.wms.entity.dto;


import com.frdscm.wms.entity.InventoryList;
import com.frdscm.wms.entity.InventoryManage;
import com.frdscm.wms.entity.vo.InventoryListVO;
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
 * @Date: 2018/8/2
 * @Desc:
 **/
@Setter
@Getter
@ToString
@ApiModel("盘点")
public class CheckManageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "盘点ID（编辑时必传）")
    private Integer id;

    @ApiModelProperty(value = "仓库ID", required = true)
    @NotNull(message = "仓库ID不能为空")
    private Integer warehouseId;

    @ApiModelProperty(value = "收货开始日期", required = true)
    private String receiptTimeStart;

    @ApiModelProperty(value = "收货结束日期", required = true)
    private String receiptTimeEnd;

    @ApiModelProperty(value = "客户ID", required = true)
    private Integer clientId;

    @ApiModelProperty(value = "客户名称", required = true)
    private String clientName;

    @ApiModelProperty(value = "物料名称")
    private String materialName;

    @ApiModelProperty(value = "物料料号", required = true)
    private String materialNumber;

    @ApiModelProperty(value = "盘点类型ID")
    private Integer checkTypeId;

    @ApiModelProperty(value = "盘点类型名称")
    private String checkTypeName;

    @ApiModelProperty(value = "仓库明细", required = true)
    List<InventoryListVO> inventoryManagesList;

}
