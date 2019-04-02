package com.frdscm.wms.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 订单费用项-单位系数中间表
 * </p>
 *
 * @author huangchunyi
 * @since 2018-06-12
 */
@Data
public class OrderExpenseItemUnitCoefficientVo{

    private Integer id;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 子订单ID
     */
    private Integer subOrderId;
    /**
     * 订单类型(1-未拆单 2-已拆单)
     */
    private Integer orderType;
    /**
     * 订单-费用项表ID
     */
    private Integer orderExpenseItemId;
    /**
     * 报价路线费用项ID
     */
    private Integer quotationLineExpenseItemId;
    /**
     * 费用项ID
     */
    private Integer expenseItemId;
    /**
     * 费用单位ID
     */
    private Integer costUnitId;
    /**
     * 费用单位名称
     */
    private String costUnitName;
    /**
     * 费用单位系数
     */
    private BigDecimal costUnitValue;


    @Override
    public String toString() {
        return "OrderExpenseItemUnitCoefficient{" +
        ", id=" + id +
        ", orderId=" + orderId +
        ", subOrderId=" + subOrderId +
        ", orderType=" + orderType +
        ", quotationLineExpenseItemId=" + quotationLineExpenseItemId +
        ", expenseItemId=" + expenseItemId +
        ", costUnitId=" + costUnitId +
        ", costUnitName=" + costUnitName +
        ", costUnitValue=" + costUnitValue +
        "}";
    }
}
