package com.frdscm.wms.entity.bo;

import com.frdscm.wms.commons.excel.ExcelCellType;
import com.frdscm.wms.commons.excel.ExcelField;
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
public class ShipmentScanListBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelField("板号")
    private String boardNumber;

    @ExcelField("物料料号")
    private String materialNumber;

    @ExcelField("箱号")
    private String boxNum;

    @ExcelField("序列号")
    private String boxSerialNum;

    @ExcelField("备注")
    private String remark;

    @ExcelField("操作人")
    private String operatorName;

    @ExcelField(value = "操作时间", type = ExcelCellType.DATE)
    private Date createTime;
}
