package com.frdscm.wms.service;

import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.ReceiptScanList;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.ReceiptScanListDTO;

import java.util.List;

/**
 * 收货列表 服务类
 *
 * @author March_CD
 * @since 2018-07-06
 */
public interface IReceiptScanListService extends IService<ReceiptScanList> {
    void batchSave(List<ReceiptScanListDTO> receiptScanListDTOList, UserBO userBO);
}
