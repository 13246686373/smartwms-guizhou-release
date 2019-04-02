package com.frdscm.wms.entity.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.ReceiptManage;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/7/13
 * @Desc:
 **/
@Data
public class ReceiptDemandAppVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 收货单号
     */
    private String singleNumber;
    /**
     * 客户ID
     */
    private Integer clientId;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 运单号(多个用逗号分隔)
     */
    private String waybillNumber;

    /**
     * 收货管理
     */
    private Integer receiptManageId;

    /**
     * 状态 1-待收货 2-已收货
     */
    private Integer status;
    /**
     * 是否计量收货
     */
    private Integer isMeasureReceipt;
    /**
     * 是否箱数
     */
    private Integer isBoxCount;
    /**
     * 是否数量
     */
    private Integer isQuantityCount;
    /**
     * 是否计重收货
     */
    private Integer isWeightReceipt;
    /**
     * 是否毛重（KG）
     */
    private Integer isGrossWeight;
    /**
     * 是否净重（KG）
     */
    private Integer isNetWeight;
    /**
     * 是否计体积收货
     */
    private Integer isVolumeReceipt;
    /**
     * 是否按批次收货
     */
    private Integer isBatchReceipt;

    private List<ReceiptCargoDetails> receiptListList;

    private List<ReceiptListVO> receiptLists;

    private List<ReceiptManage> receiptManageList;

    @Override
    public String toString() {
        return "ReceiptDemandAppVO{" +
                "singleNumber='" + singleNumber + '\'' +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", waybillNumber='" + waybillNumber + '\'' +
                ", receiptManageId=" + receiptManageId +
                ", status=" + status +
                ", isMeasureReceipt=" + isMeasureReceipt +
                ", isBoxCount=" + isBoxCount +
                ", isQuantityCount=" + isQuantityCount +
                ", isWeightReceipt=" + isWeightReceipt +
                ", isGrossWeight=" + isGrossWeight +
                ", isNetWeight=" + isNetWeight +
                ", isVolumeReceipt=" + isVolumeReceipt +
                ", isBatchReceipt=" + isBatchReceipt +
                ", receiptListList=" + receiptListList +
                ", receiptManageList=" + receiptManageList +
                '}';
    }
}
