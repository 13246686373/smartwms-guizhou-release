package com.frdscm.wms.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.frdscm.wms.entity.vo.CustomsArea;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 合作项目表
 * </p>
 *
 * @author huangchunyi
 * @since 2018-05-31
 */
@Data
public class CooperationProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 项目编号
     */
    private String projectNumber;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 客户ID
     */
    private Integer clientId;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 客户需求ID
     */
    private Integer demandId;
    /**
     * 客户需求名称
     */
    private String demandName;
    /**
     * 客户需求类型
     */
    private String demandType;
    /**
     * 项目开始日期
     */
    private Date projectStartDate;
    /**
     * 项目结束日期
     */
    private Date projectEndDate;
    /**
     * 客户法人ID
     */
    private Integer clientLegalId;
    /**
     * 客户法人名称
     */
    private String clientLegalName;
    /**
     * 接单法人ID
     */
    private Integer orderLegalId;
    /**
     * 接单法人名称
     */
    private String orderLegalName;
    /**
     * 对账周期
     */
    private Integer checkAccountPeriod;
    /**
     * 状态(-1删除 1-已保存 2-已启动 3-已暂停)
     */
    private Integer status;
    /**
     * 项目部门ID
     */
    private String projectDepartmentId;
    /**
     * 项目部门名称
     */
    private String projectDepartmentName;
    /**
     * 预订单模式(-1-否 1-是)
     */
    private Integer bookingMode;
    /**
     * 是否报关(-1-否 1-是)
     */
    private Integer isCustomsClearance;
    /**
     * 报关行ID
     */
    private Integer customsBrokerId;
    /**
     * 报关行名称
     */
    private String customsBrokerName;
    /**
     * 关区
     */
    private String customsArea;
    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private String createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;
    /**
     * 修改人
     */
    @ApiModelProperty(hidden = true)
    private String updateUser;
    /**
     * 修改时间
     */
    @TableField(update = "now()")
    private Date updateTime;
    /**
     * 运输报价
     */
    @TableField(exist = false)
    private List<CooperationProjectBusiness> transportQuotationList;
    /**
     * 仓储报价
     */
    @TableField(exist = false)
    private List<CooperationProjectBusiness> warehouseQuotationList;
    /**
     * 承运商
     */
    @TableField(exist = false)
    private List<CooperationProjectBusiness> carrierList;
    /**
     * 关区
     */
    @TableField(exist = false)
    private List<CooperationProjectBusiness> customAreas;
    /**
     * 关区（返回给页面的数据）
     */
    @TableField(exist = false)
    private List<CustomsArea> customsAreaList;
    /**
     * 企业ID
     */
    @TableField(exist = false)
    private Integer companyId;
    /**
     * 单位映射（数量）
     */
    //@TableField(exist = false)
    //private List<CooperationProjectUnitMappingMain> quantityList;
    /**
     * 单位映射（箱量）
     */
    //@TableField(exist = false)
    //private List<CooperationProjectUnitMappingMain> boxCountList;
    /**
     * 单位映射（板量）
     */
    //@TableField(exist = false)
    //private List<CooperationProjectUnitMappingMain> boardCountList;
    /**
     * 单位映射（净重）
     */
    //TableField(exist = false)
    //private List<CooperationProjectUnitMappingMain> grossWeightList;
    /**
     * 单位映射（毛重）
     */
    //@TableField(exist = false)
    //private List<CooperationProjectUnitMappingMain> netWeightList;
    /**
     * 单位映射（体积）
     */
    //@TableField(exist = false)
    //private List<CooperationProjectUnitMappingMain> volumeList;


    @Override
    public String toString() {
        return "CooperationProject{" +
        ", id=" + id +
        ", projectNumber=" + projectNumber +
        ", projectName=" + projectName +
        ", clientId=" + clientId +
        ", clientName=" + clientName +
        ", demandId=" + demandId +
        ", demandName=" + demandName +
        ", demandType=" + demandType +
        ", projectStartDate=" + projectStartDate +
        ", projectEndDate=" + projectEndDate +
        ", clientLegalId=" + clientLegalId +
        ", clientLegalName=" + clientLegalName +
        ", orderLegalId=" + orderLegalId +
        ", orderLegalName=" + orderLegalName +
        ", checkAccountPeriod=" + checkAccountPeriod +
        ", status=" + status +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
