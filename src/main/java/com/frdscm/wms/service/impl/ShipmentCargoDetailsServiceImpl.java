package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.ShipmentCargoDetails;
import com.frdscm.wms.entity.ShipmentManage;
import com.frdscm.wms.entity.dto.ShipmentCargoDetailsDTO;
import com.frdscm.wms.entity.vo.ShipmentAppList;
import com.frdscm.wms.mapper.ShipmentCargoDetailsMapper;
import com.frdscm.wms.mapper.ShipmentManageMapper;
import com.frdscm.wms.service.IShipmentCargoDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 出货需求货物明细表 服务实现类
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Service
public class ShipmentCargoDetailsServiceImpl extends ServiceImpl<ShipmentCargoDetailsMapper, ShipmentCargoDetails> implements IShipmentCargoDetailsService {


    private final ShipmentCargoDetailsMapper shipmentCargoDetailsMapper;
    private final ShipmentManageMapper shipmentManageMapper;

    @Autowired
    public ShipmentCargoDetailsServiceImpl(ShipmentCargoDetailsMapper shipmentCargoDetailsMapper, ShipmentManageMapper shipmentManageMapper) {
        this.shipmentCargoDetailsMapper = shipmentCargoDetailsMapper;
        this.shipmentManageMapper = shipmentManageMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(ShipmentCargoDetailsDTO shipmentCargoDetailsDTO) {
        if (shipmentCargoDetailsMapper.selectById(shipmentCargoDetailsDTO.getShipmentDemandId()) == null) {
            throw new BusinessException("出货需求不存在，请检查");
        }
        super.insert(BeanUtils.copy(shipmentCargoDetailsDTO, ShipmentCargoDetails.class));
    }

    @Override
    public List<ShipmentCargoDetails> getShipmentCargoDetailsList(Integer shipmentDemandId) {
        return shipmentCargoDetailsMapper.shipmentScanningRandom(shipmentDemandId);
    }


    @Override
    public ShipmentAppList shipmentTaskDetailRandom(Integer shipmentDemandId) {
        List<ShipmentCargoDetails> shipmentCargoDetailsList = shipmentCargoDetailsMapper.shipmentScanningRandom(shipmentDemandId);

        if (shipmentCargoDetailsList == null || shipmentCargoDetailsList.size() < 1) {
            ShipmentManage shipmentManage = shipmentManageMapper.selectShipmentManageByShipmentDemandId(shipmentDemandId);
            shipmentManageMapper.updateStatus(2, shipmentManage.getId());
            return null;
        }
        ShipmentCargoDetails shipmentCargoDetails = shipmentCargoDetailsList.get(0);
        ShipmentAppList shipmentAppList = new ShipmentAppList();
        shipmentAppList.setId(shipmentCargoDetails.getId());
        shipmentAppList.setMaterialNumber(shipmentCargoDetails.getMaterialNumber());
        shipmentAppList.setMaterialName(shipmentCargoDetails.getMaterialName());
        return shipmentAppList;
    }

    @Override
    public ShipmentCargoDetails getShipmentCargoDetails(Integer id) {
        return super.selectById(id);
    }


}
