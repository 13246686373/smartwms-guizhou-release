package com.frdscm.wms.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 合作项目-项目报价/承运商中间表
 * </p>
 *
 * @author huangchunyi
 * @since 2018-05-31
 */
@Data
public class CooperationProjectBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 合作项目ID
     */
    private Integer cooperationProjectId;
    /**
     * 客户ID
     */
    private Integer clientId;
    /**
     * 业务ID
     */
    private Integer businessId;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 报价类型(1-运输报价 2-仓储报价 3-承运商 4-关区)
     */
    private Integer businessType;


    @Override
    public String toString() {
        return "CooperationProjectBusiness{" +
        ", id=" + id +
        ", cooperationProjectId=" + cooperationProjectId +
        ", businessId=" + businessId +
        ", businessName=" + businessName +
        ", businessType=" + businessType +
        "}";
    }
}
