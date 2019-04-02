package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author March_CD
 * @since 2018-04-04
 */
@Data
public class QuotationLineExpenseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 费用项ID
     */
    private Integer expenseItemId;
    /**
     * 费用项
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
     * 路线报价ID
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
    private String secondStartValue;
    /**
     * 第二个限制区间结束
     */
    private String secondEndValue;
    /**
     * 第二个限制区间费用单位ID
     */
    private String secondIntervalCostUnitId;
    /**
     * 第二个限制区间费用单位
     */
    private String secondIntervalCostUnitName;

    @Override
    public String toString() {
        return "CarrierQuotationLineExpenseItem{" +
                ", id=" + id +
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
                "}";
    }
}
