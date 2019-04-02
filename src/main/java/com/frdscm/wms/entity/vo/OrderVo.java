package com.frdscm.wms.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderVo {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(hidden = true)
    private String orderNumber;
    /**
     * 客户ID
     */
    private Integer clientId;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 客户简称
     */
    private String clientShortName;
    /**
     * 项目ID
     */
    private Integer projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 客户单号
     */
    private String customerNumber;
    /**
     * 客户单号2
     */
    private String customerNumberBackup;
    /**
     * 特殊说明
     */
    private String specialInstruction;
    /**
     * 业务模式ID
     */
    private Integer businessModelId;
    /**
     * 业务模式
     */
    private String businessModelName;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 提送模式ID
     */
    private Integer liftingModeId;
    /**
     * 提送模式名称
     */
    private String liftingModeName;
    /**
     * 时效
     */
    private BigDecimal aging;
    /**
     * 是否高速(0-否 1-是)
     */
    private Integer isHighway;
    /**
     * 起运地
     */
    private String departure;
    /**
     * 目的地
     */
    private String destination;
    /**
     * 中转地1 ID
     */
    private Integer transitPlaceOneId;
    /**
     * 中转地1（节点）
     */
    private String transitPlaceOneName;
    /**
     * 中转地2 ID
     */
    private Integer transitPlaceTwoId;
    /**
     * 中转地2（节点）
     */
    private String transitPlaceTwoName;
    /**
     * 发车时间
     */
    private Date departureTime;
    /**
     * 到达时间
     */
    private Date arrivalTime;
    /**
     * 是否报关(-1-否 1-是)
     */
    private Integer isCustomsClearance;
    /**
     * 关区ID
     */
    private Integer customsAreaId;
    /**
     * 关区名字
     */
    private String customsAreaName;
    /**
     * 是否保险(-1-否 1-是)
     */
    private Integer isInsurance;
    /**
     * 投保货值
     */
    private BigDecimal insuredValue;
    /**
     * 收货方
     */
    private List<OrderReceiverOrSenderVo> receiverList;
    /**
     * 发货方
     */
    private List<OrderReceiverOrSenderVo> senderList;
    /**
     * 车型
     */
    private List<OrderCarTypeVo> carTypeList;
    /**
     * 费用项
     */
    private List<OrderExpenseItemVo> expenseItemList;
    /**
     * 总数
     */
    private Integer totalQuantity;
    /**
     * 总箱数
     */
    private Integer totalBoxCount;
    /**
     * 总板数
     */
    private Integer totalBoardCount;
    /**
     * 总毛重(kg)
     */
    private BigDecimal totalGrossWeight;
    /**
     * 总净重(kg）
     */
    private BigDecimal totalNetWeight;
    /**
     * 总体积(m³)
     */
    private BigDecimal totalVolume;
}
