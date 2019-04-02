package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.ShipmentScanList;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.ShipmentScanListDTO;
import com.frdscm.wms.entity.vo.OutgoingScanVO;
import com.frdscm.wms.mapper.ShipmentListMapper;
import com.frdscm.wms.mapper.ShipmentScanListMapper;
import com.frdscm.wms.service.IShipmentScanListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dizhang
 * @since 2018-08-11
 */
@Service
public class ShipmentScanListServiceImpl extends ServiceImpl<ShipmentScanListMapper, ShipmentScanList> implements IShipmentScanListService {

    private final ShipmentScanListMapper shipmentScanListMapper;
    private final ShipmentListMapper shipmentListMapper;

    @Autowired
    public ShipmentScanListServiceImpl(ShipmentScanListMapper shipmentScanListMapper, ShipmentListMapper shipmentListMapper) {
        this.shipmentScanListMapper = shipmentScanListMapper;
        this.shipmentListMapper = shipmentListMapper;
    }

    @Override
    public Page<OutgoingScanVO> getOutgoingScanListPageList(Page<OutgoingScanVO> page, Integer shipmentDemandId) {
        return page.setRecords(shipmentScanListMapper.getOutgoingScanListList(page, shipmentDemandId));
    }

    @Override
    public void save(ShipmentScanListDTO shipmentScanListDTO, UserBO userBO) {
        ShipmentScanList shipmentScanList = BeanUtils.copy(shipmentScanListDTO, ShipmentScanList.class);
        shipmentScanList.setOperatorId(userBO.getUserId());
        shipmentScanList.setOperatorName(userBO.getUserName());
//        int count = shipmentListMapper.getReceiptListCount(outgoingScanManage.getShipmentManageId(), outgoingScanListDTO.getBoardNumber());
//        int countList = shipmentScanListMapper.getBoardNumberCount(outgoingScanListDTO.getBoardNumber(), outgoingScanManage.getId());
//        if (count < 1) {
//            throw new BusinessException("该板号不属于该收货需求下，请检查");
//        }
//        if (countList >= 1) {
//            throw new BusinessException("该板号已经扫描过，请勿重新扫描");
//        }
        super.insert(shipmentScanList);
    }

    @Override
    public void batchSave(List<ShipmentScanListDTO> shipmentScanListDTOList, UserBO userBO) {
        for (ShipmentScanListDTO shipmentScanListDTO : shipmentScanListDTOList) {
            if (!shipmentListMapper.getShipmentListBytShipmentManageId(shipmentScanListDTO.getShipmentManageId()).stream().anyMatch(item -> item.getBoardNumber().equals(shipmentScanListDTO.getBoardNumber()))) {
                throw new BusinessException("板号" + shipmentScanListDTO.getBoardNumber() + "不存在");
            }
            ShipmentScanList shipmentScanList = BeanUtils.copy(shipmentScanListDTO, ShipmentScanList.class);
            shipmentScanList.setOperatorId(userBO.getUserId());
            shipmentScanList.setOperatorName(userBO.getUserName());
            shipmentScanListMapper.insert(shipmentScanList);
        }
    }
}
