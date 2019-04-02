package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangchunyi
 * @since 2018-05-17
 */
@Data
public class ClientQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 客户ID
     */
    private Integer clientId;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 客户法人ID
     */
    private Integer clientLegalId;
    /**
     * 客户法人名称
     */
    private String clientLegalName;
    /**
     * 客戶代码
     */
    private String clientCode;
    /**
     * 报价单号
     */
    private String quotationNumber;
    /**
     * 需求ID
     */
    private Integer demandId;
    /**
     * 需求名称
     */
    private String demandName;
    /**
     * 运输类型ID
     */
    private Integer transportTypeId;
    /**
     * 运输类型名称
     */
    private String transportTypeName;
    /**
     * 是否含税 0-不含税 1-含税
     */
    private Integer isTextsIncluded;
    /**
     * 税率
     */
    private BigDecimal taxes;
    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectiveDate;
    /**
     * 截止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;
    /**
     * 延期条款 0-有限延期 1-无限延期
     */
    private Integer extensionTerms;
    /**
     * 延期月份数
     */
    private Integer extensionMonths;
    /**
     * 付款条件 0-月结 1-见票结 2-现金结 3-回单结 4-单票结 5-每周结
     */
    private Integer paymentTerms;
    /**
     * 结算天数
     */
    private Integer days;
    /**
     * 币种ID
     */
    private Integer currencyId;
    /**
     * 币种名称
     */
    private String currencyName;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 仓库ID
     */
    private Integer warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 免仓期
     */
    private Integer freeWarehousePeriod;
    /**
     * 客户报价类型 0-客户运输报价 1-客户仓储报价
     */
    private Integer clientQuotationType;
    /**
     * 状态 -1-删除 1-正常
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
    @TableField(update = "now()")
    private Date updateTime;
    /**
     * 审核状态 1-已保存 2-已提交 3-驳回 4-审核通过 5-已生效 6-已失效
     */
    private Integer reviewStatus;
    /**
     * 驳回理由
     */
    private String rejectReason;
    /**
     * 制单人ID
     */
    private Integer operatePersonId;
    /**
     * 制单人
     */
    private String operatePersonName;

//    @TableField(exist = false)
//    List<ClientQuotationTransport> clientQuotationTransports;

    @TableField(exist = false)
    List<QuotationLine> clientQuotationTransports;

    @TableField(exist = false)
    List<ClientQuotationWarehouse> clientQuotationWarehouses;
    /**
     * 企业ID
     */
    @TableField(exist = false)
    private Integer companyId;

    @Override
    public String toString() {
        return "ClientQuotation{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientLegalId=" + clientLegalId +
                ", clientLegalName='" + clientLegalName + '\'' +
                ", quotationNumber='" + quotationNumber + '\'' +
                ", demandId=" + demandId +
                ", demandName='" + demandName + '\'' +
                ", transportTypeId=" + transportTypeId +
                ", transportTypeName='" + transportTypeName + '\'' +
                ", isTextsIncluded=" + isTextsIncluded +
                ", taxes=" + taxes +
                ", effectiveDate=" + effectiveDate +
                ", expirationDate=" + expirationDate +
                ", extensionTerms=" + extensionTerms +
                ", extensionMonths=" + extensionMonths +
                ", paymentTerms=" + paymentTerms +
                ", days=" + days +
                ", currencyId=" + currencyId +
                ", currencyName='" + currencyName + '\'' +
                ", remark='" + remark + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", freeWarehousePeriod=" + freeWarehousePeriod +
                ", clientQuotationType=" + clientQuotationType +
                ", status=" + status +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", reviewStatus=" + reviewStatus +
                ", rejectReason='" + rejectReason + '\'' +
                '}';
    }
}
