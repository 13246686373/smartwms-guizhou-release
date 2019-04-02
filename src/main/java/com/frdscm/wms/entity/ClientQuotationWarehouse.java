package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangchunyi
 * @since 2018-05-17
 */
@Data
public class ClientQuotationWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 费用项ID
     */
    private Integer expenseId;
    /**
     * 费用项名称
     */
    private String expenseName;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 费用单位ID
     */
    private Integer costUnitId;
    /**
     * 费用单位名称
     */
    private String costUnitName;
    /**
     * 料号ID
     */
    private Integer partId;
    /**
     * 料号名称
     */
    private String partName;
    /**
     * 倍数
     */
    private BigDecimal multiple;
    /**
     * 最低收费
     */
    private BigDecimal lowestFee;
    /**
     * 是否按月收费(0-否 1-是)
     */
    private Integer isMonthlyCharges;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 客户报价ID
     */
    private Integer clientQuotationId;

    @Override
    public String toString() {
        return "ClientQuotationWarehouse{" +
                "id=" + id +
                ", expenseId=" + expenseId +
                ", expenseName='" + expenseName + '\'' +
                ", unitPrice=" + unitPrice +
                ", costUnitId=" + costUnitId +
                ", costUnitName='" + costUnitName + '\'' +
                ", partId=" + partId +
                ", partName='" + partName + '\'' +
                ", multiple=" + multiple +
                ", lowestFee=" + lowestFee +
                ", isMonthlyCharges=" + isMonthlyCharges +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", clientQuotationId=" + clientQuotationId +
                '}';
    }
}
