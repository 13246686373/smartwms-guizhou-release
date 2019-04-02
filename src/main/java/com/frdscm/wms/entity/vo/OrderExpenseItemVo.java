package com.frdscm.wms.entity.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单-费用项表
 * </p>
 *
 * @author huangchunyi
 * @since 2018-06-05
 */
@Data
public class OrderExpenseItemVo{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 报价路线费用项ID
     */
    private Integer quotationLineExpenseItemId;
    /**
     * 费用项ID
     */
    private Integer expenseItemId;
    /**
     * 费用项名称
     */
    private String expenseItemName;
    /**
     * 费用单位ID
     */
    private Integer costUnitId;
    /**
     * 费用单位
     */
    private String costUnitName;
    /**
     * 最低收费
     */
    private BigDecimal lowestFee;
    /**
     * 收费金额
     */
    private BigDecimal chargeFee;
    /**
     * 开始值
     */
    private BigDecimal startValue;
    /**
     * 结束值
     */
    private BigDecimal endValue;
    /**
     * 报价路线ID
     */
    private Integer quotationLineId;
    /**
     * 车辆类型ID
     */
    private Integer carType;
    /**
     * 车辆类型
     */
    private String carTypeName;
    /**
     * 首重（KG）
     */
    private BigDecimal firstWeight;
    /**
     * 续重（KG）
     */
    private BigDecimal continuedWeight;
    /**
     * 限制区间费用单位ID
     */
    private Integer intervalCostUnitId;
    /**
     * 限制区间费用单位名称
     */
    private String intervalCostUnitName;
    /**
     * 第二个限制区间开始
     */
    private BigDecimal secondStartValue;
    /**
     * 第二个限制区间结束
     */
    private BigDecimal secondEndValue;
    /**
     * 第二个限制区间费用单位ID
     */
    private Integer secondIntervalCostUnitId;
    /**
     * 第二个限制区间费用单位
     */
    private String secondIntervalCostUnitName;
    /**
     * 状态(-1-未启用 1-已启用)
     */
    private Integer status;

    private Integer orderType;
    /**
     * 费用项系数
     */
    private List<OrderExpenseItemUnitCoefficientVo> orderExpenseItemUnitCoefficientList;


    @Override
    public String toString() {
        return "OrderExpenseItem{" +
        ", id=" + id +
        ", orderId=" + orderId +
        ", quotationLineExpenseItemId=" + quotationLineExpenseItemId +
        ", expenseItemId=" + expenseItemId +
        ", expenseItemName=" + expenseItemName +
        ", costUnitId=" + costUnitId +
        ", costUnitName=" + costUnitName +
        ", lowestFee=" + lowestFee +
        ", chargeFee=" + chargeFee +
        ", startValue=" + startValue +
        ", endValue=" + endValue +
        ", quotationLineId=" + quotationLineId +
        ", carType=" + carType +
        ", carTypeName=" + carTypeName +
        ", firstWeight=" + firstWeight +
        ", continuedWeight=" + continuedWeight +
        ", intervalCostUnitId=" + intervalCostUnitId +
        ", intervalCostUnitName=" + intervalCostUnitName +
        ", secondStartValue=" + secondStartValue +
        ", secondEndValue=" + secondEndValue +
        ", secondIntervalCostUnitId=" + secondIntervalCostUnitId +
        ", secondIntervalCostUnitName=" + secondIntervalCostUnitName +
        ", status=" + status +
        "}";
    }
}
