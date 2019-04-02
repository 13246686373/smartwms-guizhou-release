package com.frdscm.wms.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dizhang
 * @Date: 2018/8/14
 * @Desc:
 **/
@Data
public class OutgoingScanListVO  implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Integer id;
    /**
     * 出货扫描管理ID
     */
    private Integer outgoingScanManageId;
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




}
