package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.ReceiptScanList;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.ReceiptScanListDTO;
import com.frdscm.wms.entity.dto.ShipmentScanListDTO;
import com.frdscm.wms.mapper.ReceiptListMapper;
import com.frdscm.wms.mapper.ReceiptScanListMapper;
import com.frdscm.wms.service.IReceiptScanListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收货列表 服务实现类
 *
 * @author dizhang
 * @since 2018-07-06
 */
@Service
public class ReceiptScanListServiceImpl extends ServiceImpl<ReceiptScanListMapper, ReceiptScanList> implements IReceiptScanListService {

    private final ReceiptListMapper receiptListMapper;

    @Autowired
    public ReceiptScanListServiceImpl(ReceiptListMapper receiptListMapper) {
        this.receiptListMapper = receiptListMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void batchSave(List<ReceiptScanListDTO> receiptScanListDTOList, UserBO userBo) {
        for (ReceiptScanListDTO receiptScanListDTO : receiptScanListDTOList) {
            if (!receiptListMapper.getReceiptListByReceiptManageId(receiptScanListDTO.getReceiptManageId()).stream().anyMatch(item -> item.getBoardNumber().equals(receiptScanListDTO.getBoardNumber()))) {
                throw new BusinessException("板号" + receiptScanListDTO.getBoardNumber() + "不存在");
            }
            ReceiptScanList receiptScanList = BeanUtils.copy(receiptScanListDTO, ReceiptScanList.class);
            receiptScanList.setOperatorId(userBo.getUserId());
            receiptScanList.setOperatorName(userBo.getUserName());
            super.insert(receiptScanList);
        }
    }

}
