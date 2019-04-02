package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author March_CD
 * @since 2018-04-03
 */
@Data
public class QuotationLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
     * 里程
     */
    private BigDecimal mileage;
    /**
     * 时效(两位小数)
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private BigDecimal aging;
    /**
     * 是否高速
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer isHighway;
    /**
     * 是否分拣
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer isPick;
    /**
     * 最低收费
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private BigDecimal lowestFee;
    /**
     * 承运商报价ID
     */
    private Integer quotationId;
    /**
     * 报价类型 0-零担 1-整车 2-快递
     */
    private Integer quotationType;
    /**
     * 报价分类 0-合作报价 1-承运商参考报价
     */
    private Integer quotationClassify;
    /**
     * 备注信息
     */
    private String remark;

    @TableField(exist = false)
    List<QuotationLineExpenseItem> quotationLineExpenseItems;

    @TableField(exist = false)
    private String carrierName;

    @TableField(exist = false)
    private String carrierId;

    @Override
    public String toString() {
        return "CarrierQuotationLine{" +
        ", id=" + id +
        ", departure=" + departure +
        ", destination=" + destination +
        ", mileage=" + mileage +
        ", aging=" + aging +
        ", isHighway=" + isHighway +
        ", lowestFee=" + lowestFee +
        ", quotationClassify=" + quotationClassify +
        ", quotationType=" + quotationType +
        ", remark=" + remark +
        "}";
    }
}
