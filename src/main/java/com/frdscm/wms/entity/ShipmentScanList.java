package com.frdscm.wms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author dizhang
 * @since 2018-08-11
 */
@Data
public class ShipmentScanList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 出货扫描管理ID
     */
    private Integer shipmentManageId;
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
//    /**
//     * 子箱号
//     */
//    private String boxSonNum;
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
    private Integer operatorId;
    /**
     * 操作人姓名
     */
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
                ", materialNumber='" + materialNumber + '\'' +
                ", boxNum='" + boxNum + '\'' +
                ", boxSerialNum='" + boxSerialNum + '\'' +
                ", operatorId=" + operatorId +
                ", operatorName='" + operatorName + '\'' +
                '}';
    }
}
