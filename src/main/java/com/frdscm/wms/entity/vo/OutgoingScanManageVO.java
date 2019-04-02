package com.frdscm.wms.entity.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
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
public class OutgoingScanManageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 出货管理ID
     */
    private Integer shipmentManageId;
    /**
     * 出货单号
     */
    private String singleNumber;
    /**
     *  是否扫描箱号  1-扫描 2-不扫描
     */
    private Integer isBoxNum;
    /**
     * 是否扫描子箱号  1-扫描 2-不扫描
     */
    private Integer isBoxSonNum;
    /**
     * 是否扫描序列号  1-扫描 2-不扫描
     */
    private Integer isSerialNum;
    /**
     * 是否扫描板号  1-扫描  2-不扫描
     */
    private Integer isBoardNum;


    private String clientName;



    @Override
    public String toString() {
        return "OutgoingScanManageVO{" +
                "id=" + id +
                ", shipmentManageId=" + shipmentManageId +
                ", singleNumber='" + singleNumber + '\'' +
                ", isBoxNum=" + isBoxNum +
                ", isBoxSonNum=" + isBoxSonNum +
                ", isSerialNum=" + isSerialNum +
                ", isBoardNum=" + isBoardNum +
                ", clientName=" + clientName +
                '}';
    }
}
