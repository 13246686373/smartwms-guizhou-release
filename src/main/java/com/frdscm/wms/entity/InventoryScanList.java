package com.frdscm.wms.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 库存管理板货明细表
 * </p>
 *
 * @author dizhang
 * @since 2018-08-11
 */
@Data
public class InventoryScanList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 库存管理ID
     */
    private Integer inventoryManageId;
    /**
     * 板号
     */
    private String boardNumber;
    /**
     * 物料料号
     */
    private String materialNumber;
    /**
     * 箱号
     */
    private String boxNum;
    /**
     * 子箱号
     */
    private String boxSonNum;
    /**
     * 序列号
     */
    private String boxSerialNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 操作人ID
     */
    @ApiModelProperty(hidden = true)
    private Integer operatorId;
    /**
     * 操作人姓名
     */
    @ApiModelProperty(hidden = true)
    private String operatorName;
    /**
     * 创建时间
     */
    private Date createTime;

    @Override
    public String toString() {
        return "OutgoingScanList{" +
                "id=" + id +
                ", boardNumber='" + boardNumber + '\'' +
                ", boxNum='" + boxNum + '\'' +
                ", boxSonNum='" + boxSonNum + '\'' +
                ", boxSerialNum='" + boxSerialNum + '\'' +
                ", operatorId=" + operatorId +
                ", operatorName='" + operatorName + '\'' +
                '}';
    }
}
