package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.entity.*;
import com.frdscm.wms.entity.bo.materials.Materials;
import com.frdscm.wms.entity.dto.CountDTO;
import com.frdscm.wms.entity.dto.ShipmentManagePageDTO;
import com.frdscm.wms.entity.vo.ShipmentManageListVO;
import com.frdscm.wms.entity.vo.ShipmentsCountVO;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.mapper.*;
import com.frdscm.wms.service.IShipmentManageService;
import com.frdscm.wms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 出货管理表 服务实现类
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Service
public class ShipmentManageServiceImpl extends ServiceImpl<ShipmentManageMapper, ShipmentManage> implements IShipmentManageService {

    private final ShipmentManageMapper shipmentManageMapper;
    private final ShipmentListMapper shipmentListMapper;
    private final InventoryManageMapper inventoryManageMapper;
    private final ShipmentCargoDetailsMapper shipmentCargoDetailsMapper;
    private final ShipmentDemandMapper shipmentDemandMapper;
    private final ShipmentScanListMapper shipmentScanListMapper;
    private final ProjectService projectService;

    @Autowired
    public ShipmentManageServiceImpl(ShipmentManageMapper shipmentManageMapper, ProjectService projectService, ShipmentScanListMapper shipmentScanListMapper, ShipmentCargoDetailsMapper shipmentCargoDetailsMapper, ShipmentListMapper shipmentListMapper, InventoryManageMapper inventoryManageMapper, ShipmentDemandMapper shipmentDemandMapper) {
        this.shipmentManageMapper = shipmentManageMapper;
        this.shipmentListMapper = shipmentListMapper;
        this.inventoryManageMapper = inventoryManageMapper;
        this.shipmentDemandMapper = shipmentDemandMapper;
        this.shipmentCargoDetailsMapper = shipmentCargoDetailsMapper;
        this.projectService = projectService;
        this.shipmentScanListMapper = shipmentScanListMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(ShipmentManage shipmentManage) {
        if (shipmentManageMapper.selectShipmentManageByShipmentDemandId(shipmentManage.getShipmentDemandId()) == null) {
            super.insert(shipmentManage);
        }
    }

    @Override
    public ShipmentManage selectShipmentManageByShipmentDemandId(Integer shipmentDemandId) {
        return shipmentManageMapper.selectShipmentManageByShipmentDemandId(shipmentDemandId);
    }

    @Override
    public void deleteShipmentManageByShipmentDemandId(Integer shipmentDemandId) {
        shipmentManageMapper.deleteShipmentManageByShipmentDemandId(shipmentDemandId);
    }

    @Override
    public Page<Map<String, Object>> getShipmentManageByPageList(Page<Map<String, Object>> page, ShipmentManagePageDTO shipmentManagePageDTO) {
        return page.setRecords(shipmentManageMapper.getShipmentManageByPageList(page, shipmentManagePageDTO));
    }

    @Override
    public Map getShipmentBoardCount(CountDTO countDTO) {
        return shipmentManageMapper.getShipmentBoardCount(countDTO);
    }

    @Override
    public Page<ShipmentsCountVO> getShipmentDataCount(CountDTO countDTO) {
        Page<ShipmentsCountVO> page = new Page<>(countDTO.getPageNo(), countDTO.getPageSize());
        page.setRecords(shipmentManageMapper.getShipmentDataCount(page, countDTO));
        return page;
    }

    @Override
    public void onConfirm(Integer shipmentManageId) {
        List<ShipmentList> shipmentListList = shipmentListMapper.getShipmentListBytShipmentManageId(shipmentManageId);
        for (ShipmentList shipmentList : shipmentListList) {
            if (shipmentList.getStatus() == 1) {
                throw new BusinessException("还未出货完成");
            }
        }
        for (ShipmentList shipmentList : shipmentListList) {
            ShipmentManage shipmentManage = shipmentManageMapper.selectById(shipmentList.getShipmentManageId());
            ShipmentDemand shipmentDemand = shipmentDemandMapper.selectById(shipmentManage.getShipmentDemandId());
//            InventoryManage inventoryManage = inventoryManageMapper.getInventoryManageByBoardNumberAndMaterialNumber(shipmentList.getBoardNumber(), shipmentList.getMaterialNumber(), shipmentDemand.getClientId());
//            inventoryManage.setVolume(inventoryManage.getVolume().subtract(shipmentList.getVolume()));
//            inventoryManage.setNetWeight(inventoryManage.getNetWeight().subtract(shipmentList.getNetWeight()));
//            inventoryManage.setGrossWeight(inventoryManage.getGrossWeight().subtract(shipmentList.getGrossWeight()));
//            inventoryManage.setBoxCount(inventoryManage.getBoxCount() - shipmentList.getBoxCount());
//            inventoryManage.setQuantityCount(inventoryManage.getQuantityCount() - shipmentList.getQuantityCount());
//            inventoryManageMapper.updateById(inventoryManage);
        }
        shipmentManageMapper.updateStatus(3, shipmentManageId);
    }

    @Override
    public List<Map<String, Object>> getShipmentDateCount(CountDTO countDTO) {
        Date start = new Date(countDTO.getReceiptTimeStart());
        Date end = new Date(countDTO.getReceiptTimeEnd());
        List<String> listDay = DateUtil.findDates(start, end);
        List<Map<String, Object>> resultMap = shipmentManageMapper.getShipmentDateCount(countDTO);
        List<Map<String, Object>> returnMap = new ArrayList<>();
        generateCountData(listDay, resultMap, returnMap);
        return returnMap;
    }

    static void generateCountData(List<String> listDay, List<Map<String, Object>> resultMap, List<Map<String, Object>> returnMap) {
        Map<String, Object> objectMap;
        for (String day : listDay) {
            objectMap = new HashMap<>(0);
            objectMap.put("date", day);
            objectMap.put("orderCount", 0);
            for (Map<String, Object> map : resultMap) {
                if (day.equals(map.get("date").toString())) {
                    objectMap.put("orderCount", map.get("orderCount"));
                }
            }
            returnMap.add(objectMap);
        }
    }

    @Override
    public void removeTask(Integer shipmentDemandId) {
        ShipmentManage shipmentManage = super.baseMapper.selectShipmentManageByShipmentDemandId(shipmentDemandId);
        shipmentManage.setRemove(2);
        super.updateById(shipmentManage);
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void completeShipment(Integer id, Integer companyId) {
        ShipmentManage shipmentManage = super.selectById(id);
        if (shipmentManage.getStatus() != 2) {
            throw new BusinessException("确认出货失败，请先完成拣货");
        }
        ShipmentDemand shipmentDemand = shipmentDemandMapper.getShipmentDemandByShipmentManageId(id);
        List<ShipmentCargoDetails> shipmentCargoDetailsList = shipmentCargoDetailsMapper.getShipmentCargoDetailsListByShipmentDemandId(shipmentDemand.getId());
        boolean isTrue = false;
        for (ShipmentCargoDetails shipmentCargoDetails : shipmentCargoDetailsList) {
            switch (shipmentCargoDetails.getUnitType()) {
                case 1:
                    if (!shipmentCargoDetails.getActualQuantityCount().equals(shipmentCargoDetails.getQuantityCount())) {
                        isTrue = true;
                    }
                    break;
                case 2:
                    if (!shipmentCargoDetails.getActualBoxCount().equals(shipmentCargoDetails.getBoxCount())) {
                        isTrue = true;
                    }
                    break;
                case 3:
                    if (!shipmentCargoDetails.getActualBoardCount().equals(shipmentCargoDetails.getBoardCount())) {
                        isTrue = true;
                    }
                    break;
                case 4:
                    if (!shipmentCargoDetails.getActualGrossWeight().equals(shipmentCargoDetails.getGrossWeight())) {
                        isTrue = true;
                    }
                    break;
                case 5:
                    if (!shipmentCargoDetails.getActualVolume().equals(shipmentCargoDetails.getVolume())) {
                        isTrue = true;
                    }
                    break;
                default:
                    throw new BusinessException("");
            }
            if (isTrue) {
                throw new BusinessException("确认出货失败，物料" + shipmentCargoDetails.getMaterialName() + "出货量不足，请确认");
            }
        }
        List<ShipmentList> shipmentLists = shipmentListMapper.getShipmentListBytShipmentManageId(shipmentManage.getId());
        shipmentLists.forEach(e -> {
            if (e.getIsShipmentScan() == 1 && e.getScanned() == 0) {
                throw new BusinessException("确认出货失败，物料 '" + e.getMaterialName() + "' 已开启出货扫描，请先完成出货扫描");
            }
        });
//        ShipmentDemand shipmentDemand = shipmentDemandMapper.selectById(shipmentManage.getShipmentDemandId());
//        List<ShipmentCargoDetails> shipmentCargoDetailsList = shipmentCargoDetailsMapper.getShipmentCargoDetailsListByShipmentDemandId(shipmentManage.getShipmentDemandId());
//        List<String> materialItemNumbers = new ArrayList<>();
//        for (ShipmentCargoDetails shipmentCargoDetails : shipmentCargoDetailsList) {
//            materialItemNumbers.add(shipmentCargoDetails.getMaterialNumber());
//        }
//        if (materialItemNumbers.size() > 0) {
//            List<Materials> materialsList = projectService.getByMaterialItemNumberAndClientId(materialItemNumbers, shipmentDemand.getClientId(), companyId);
//            for (Materials materials : materialsList) {
//                if (materials.getShipmentIsScanningSerialNumber() != null && materials.getShipmentIsScanningSerialNumber() == 1) {
//                    if (shipmentScanListMapper.materialIsScan(shipmentManage.getId()) < 1) {
//                        throw new BusinessException("确认出货失败，物料 '" + materials.getItemName() + "' 已开启出货扫描，请先完成出货扫描");
//                    }
//                }
//            }
//        }
        inventoryManageMapper.removeInventoryByShipmentManageId(shipmentManage.getId());
        shipmentManage.setStatus(3);
        super.updateById(shipmentManage);
    }
}
