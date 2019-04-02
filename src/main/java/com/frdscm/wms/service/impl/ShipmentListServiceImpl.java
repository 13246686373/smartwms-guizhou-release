package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.*;
import com.frdscm.wms.entity.dto.ScanningDTO;
import com.frdscm.wms.entity.dto.ShipmentListDTO;
import com.frdscm.wms.entity.dto.UnitMappingDTO;
import com.frdscm.wms.entity.vo.ShipmentAppList;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.mapper.*;
import com.frdscm.wms.service.IShipmentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 拣货列表 服务实现类
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Service
public class ShipmentListServiceImpl extends ServiceImpl<ShipmentListMapper, ShipmentList> implements IShipmentListService {

    private final ShipmentListMapper shipmentListMapper;
    private final ShipmentItemListMapper shipmentItemListMapper;
    private final WarehouseStorageMapper warehouseStorageMapper;
    private final InventoryManageMapper inventoryManageMapper;
    private final ShipmentCargoDetailsMapper shipmentCargoDetailsMapper;
    private final ShipmentManageMapper shipmentManageMapper;
    private final ProjectService projectService;
    private final ShipmentDemandMapper shipmentDemandMapper;
    private final InventoryListMapper inventoryListMapper;

    @Autowired
    public ShipmentListServiceImpl(ShipmentListMapper shipmentListMapper, InventoryListMapper inventoryListMapper, ShipmentItemListMapper shipmentItemListMapper, ShipmentDemandMapper shipmentDemandMapper, WarehouseStorageMapper warehouseStorageMapper, InventoryManageMapper inventoryManageMapper, ShipmentCargoDetailsMapper shipmentCargoDetailsMapper, ShipmentManageMapper shipmentManageMapper, ProjectService projectService) {
        this.shipmentListMapper = shipmentListMapper;
        this.warehouseStorageMapper = warehouseStorageMapper;
        this.inventoryManageMapper = inventoryManageMapper;
        this.shipmentCargoDetailsMapper = shipmentCargoDetailsMapper;
        this.shipmentManageMapper = shipmentManageMapper;
        this.projectService = projectService;
        this.shipmentDemandMapper = shipmentDemandMapper;
        this.shipmentItemListMapper = shipmentItemListMapper;
        this.inventoryListMapper = inventoryListMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(ShipmentListDTO shipmentListDTO) {
        super.insert(BeanUtils.copy(shipmentListDTO, ShipmentList.class));
    }

    @Override
    public List<ShipmentList> getShipmentListBytShipmentManageId(Integer shipmentManageId) {
        return shipmentListMapper.getShipmentListBytShipmentManageId(shipmentManageId);
    }

    @Override
    public Page<ShipmentList> getShipmentListByPageShipmentManageId(Page<ShipmentList> page, Integer shipmentManageId) {
        page.setRecords(shipmentListMapper.getShipmentListByShipmentManageIdPage(page, shipmentManageId));
        return page;
    }

    @Override
    public void deleteByShipmentManageId(Integer shipmentManageId) {
        shipmentListMapper.deleteByShipmentManageId(shipmentManageId);
    }

    @Override
    public List<ShipmentAppList> getShipmentListByApp(Integer shipmentId) {
        List<ShipmentAppList> shipmentAppListList = shipmentListMapper.getShipmentListByApp(shipmentId);
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Integer companyId = Integer.valueOf(request.getHeader("companyId"));
        for (ShipmentAppList shipmentAppList : shipmentAppListList) {
            UnitMappingDTO unitMappingDTO = projectService.getBillUnitByUnitId(shipmentAppList.getUnitId(), companyId);
            if (unitMappingDTO.getIsGrossWeight() == 1) {
                shipmentAppList.setCount(shipmentAppList.getGrossWeight());
            }
            if (unitMappingDTO.getIsVolumeReceipt() == 1) {
                shipmentAppList.setCount(shipmentAppList.getVolume());
            }
            if (unitMappingDTO.getIsBoxCount() == 1) {
                shipmentAppList.setCount(new BigDecimal(shipmentAppList.getBoxCount()));
            }
            if (unitMappingDTO.getIsQuantityCount() == 1) {
                shipmentAppList.setCount(new BigDecimal(shipmentAppList.getQuantityCount()));
            }
            if (unitMappingDTO.getIsNetWeight() == 1) {
                shipmentAppList.setCount(shipmentAppList.getNetWeight());
            }
        }

        return shipmentAppListList;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ShipmentAppList getShimentListByshipmentTask(Integer shipmentId) {
//        List<ShipmentAppList> shipmentListList = shipmentListMapper.getShipmentListByNotPick(shipmentId);
//        if (shipmentListList == null || shipmentListList.size() < 1) {
//            ShipmentManage shipmentManage = shipmentManageMapper.selectShipmentManageByShipmentDemandId(shipmentId);
//            shipmentManageMapper.updateStatus(2, shipmentManage.getId());
//            return null;
//        }
//        ShipmentAppList shipmentAppList = shipmentListList.get(0);
//        shipmentAppList.setWarehouseStorageNumber(warehouseStorageMapper.getNumberById(shipmentAppList.getWarehouseStorageId()));
//        return shipmentAppList;
        return null;
    }

    @Override
    public ShipmentList getShipmentTaskDetail(Integer shipmentDemandId) {
        List<ShipmentList> shipmentLists = shipmentListMapper.getShipmentListByNotPick(shipmentDemandId);
        List<ShipmentList> lists = shipmentListMapper.getShipmentListByShipmentDemandId(shipmentDemandId);
        if (lists == null || lists.size() < 1) {
            throw new BusinessException("请先操作系统拣货");
        }
        if (shipmentLists == null || shipmentLists.size() < 1) {
            return null;
        }
        return shipmentLists.get(0);
    }

    @Override
    public ShipmentList getShipmentTaskDetailRandom(Integer shipmentDemandId) {
        List<ShipmentCargoDetails> shipmentCargoDetailsList = shipmentCargoDetailsMapper.shipmentScanningRandom(shipmentDemandId);
        shipmentCargoDetailsList = shipmentCargoDetailsList.stream().filter(receiptCargoDetail -> {
            switch (receiptCargoDetail.getUnitType()) {
                case 1:
                    if (!receiptCargoDetail.getActualQuantityCount().equals(receiptCargoDetail.getQuantityCount())) {
                        return true;
                    }
                    break;
                case 2:
                    if (!receiptCargoDetail.getActualBoxCount().equals(receiptCargoDetail.getBoxCount())) {
                        return true;
                    }
                    break;
                case 3:
                    if (!receiptCargoDetail.getActualBoardCount().equals(receiptCargoDetail.getBoardCount())) {
                        return true;
                    }
                    break;
                case 4:
                    if (!receiptCargoDetail.getActualGrossWeight().equals(receiptCargoDetail.getGrossWeight())) {
                        return true;
                    }
                    break;
                case 5:
                    if (!receiptCargoDetail.getActualVolume().equals(receiptCargoDetail.getVolume())) {
                        return true;
                    }
                    break;
                default:
                    throw new BusinessException("单位类型错误");
            }
            return false;
        }).collect(Collectors.toList());
        if (shipmentCargoDetailsList == null || shipmentCargoDetailsList.size() < 1) {
            return null;
        }
        return BeanUtils.copy(shipmentCargoDetailsList.get(0), ShipmentList.class);
    }

    @Override
    public Map<String, BigDecimal> getScanning(ScanningDTO scanningDTO) {
        InventoryManage inventoryManage = inventoryManageMapper.getInventoryManageByWarehouseIdAndBoardNumberAndWarehouseStorageNumber(scanningDTO.getWarehouseId(), scanningDTO.getWarehouseStorageNumber(), scanningDTO.getBoardNumber());
        if (inventoryManage == null) {
            throw new BusinessException("该板号不在该库位，请确认");
        }
        ShipmentList shipmentListOriginal = shipmentListMapper.selectById(scanningDTO.getShipmentListId());
        if (!shipmentListOriginal.getBoardNumber().equals(scanningDTO.getBoardNumber())) {
            throw new BusinessException("该板号不符合先进先出规则，请选择其他板号");
        }
        ShipmentManage shipmentManage = shipmentManageMapper.selectShipmentManageByShipmentDemandId(scanningDTO.getShipmentDemandId());
        ShipmentList shipmentListCount = shipmentListMapper.getCountByShipmentManageId(shipmentManage.getId(), shipmentListOriginal.getMaterialNumber());
        ShipmentCargoDetails shipmentCargoDetails = new ShipmentCargoDetails();
        shipmentCargoDetails.setShipmentDemandId(shipmentManage.getShipmentDemandId());
        shipmentCargoDetails.setMaterialNumber(shipmentListOriginal.getMaterialNumber());
        shipmentCargoDetails = shipmentCargoDetailsMapper.selectOne(shipmentCargoDetails);
        Map<String, BigDecimal> resultMap = new HashMap<>(0);
        if (shipmentListOriginal.getUnitType() == 1) {
            shipmentListOriginal.setQuantityCount(inventoryManage.getQuantityCount());
            if (inventoryManage.getQuantityCount() - (shipmentCargoDetails.getQuantityCount() - shipmentListCount.getQuantityCount()) > 0) {
                resultMap.put("dismantlingPlateCount", new BigDecimal(inventoryManage.getQuantityCount() - (shipmentCargoDetails.getQuantityCount() - shipmentListCount.getQuantityCount())));
                return resultMap;
            }
        }
        if (shipmentListOriginal.getUnitType() == 2) {
            shipmentListOriginal.setBoxCount(inventoryManage.getBoxCount());
            if (inventoryManage.getBoxCount() - (shipmentCargoDetails.getBoxCount() - shipmentListCount.getBoxCount()) > 0) {
                resultMap.put("dismantlingPlateCount", new BigDecimal(inventoryManage.getBoxCount() - (shipmentCargoDetails.getBoxCount() - shipmentListCount.getBoxCount())));
                return resultMap;
            }
        }
        if (shipmentListOriginal.getUnitType() == 5) {
            shipmentListOriginal.setVolume(inventoryManage.getVolume());
            if (inventoryManage.getVolume().subtract(shipmentCargoDetails.getVolume().subtract(shipmentListCount.getVolume())).compareTo(BigDecimal.ZERO) > 0) {
                resultMap.put("dismantlingPlateCount", inventoryManage.getVolume().subtract(shipmentCargoDetails.getVolume().subtract(shipmentListCount.getVolume())));
                return resultMap;
            }
        }
        if (shipmentListOriginal.getUnitType() == 4) {
            shipmentListOriginal.setGrossWeight(inventoryManage.getGrossWeight());
            if (inventoryManage.getGrossWeight().subtract(shipmentCargoDetails.getGrossWeight().subtract(shipmentListCount.getGrossWeight())).compareTo(BigDecimal.ZERO) > 0) {
                resultMap.put("dismantlingPlateCount", inventoryManage.getGrossWeight().subtract(shipmentCargoDetails.getGrossWeight().subtract(shipmentListCount.getGrossWeight())));
                return resultMap;
            }
        }
        shipmentListOriginal.setStatus(2);
        shipmentListOriginal.setIsBaffle(0);
        shipmentListMapper.updateById(shipmentListOriginal);
        EntityWrapper<ShipmentList> ew = new EntityWrapper<>();
        ew.eq("shipment_manage_id", shipmentManage.getId());
        if (shipmentListMapper.getBaffle(shipmentManage.getId()) == 0 && shipmentListMapper.selectList(ew).size() > 0) {
            shipmentManage.setStatus(2);
            shipmentManageMapper.updateById(shipmentManage);
        }
        return null;
    }

    @Override
    public void updateStatus(Integer status, Integer id) {
        shipmentListMapper.updateStatus(status, id);
    }

    @Override
    public Map<String, BigDecimal> getScanningRandom(ScanningDTO scanningDTO) {
        InventoryManage inventoryManage = inventoryManageMapper.getInventoryManageByWarehouseIdAndBoardNumberAndWarehouseStorageNumber(scanningDTO.getWarehouseId(), scanningDTO.getWarehouseStorageNumber(), scanningDTO.getBoardNumber());
        if (inventoryManage == null) {
            throw new BusinessException("该板号不在该库位，请确认");
        }
        ShipmentCargoDetails shipmentCargoDetails = shipmentCargoDetailsMapper.selectById(scanningDTO.getShipmentListId());
        if (!shipmentCargoDetails.getMaterialNumber().equals(inventoryManage.getMaterialNumber())) {
            throw new BusinessException("该板上无该物料，请确认");
        }
        EntityWrapper<ShipmentList> ew = new EntityWrapper<>();
        ew.eq("board_number", scanningDTO.getBoardNumber());
        ShipmentManage shipmentManage = shipmentManageMapper.selectShipmentManageByShipmentDemandId(scanningDTO.getShipmentDemandId());
        ew.eq("shipment_manage_id", shipmentManage.getId());
        if (super.selectOne(ew) != null) {
            throw new BusinessException("该板号已拣货");
        }
        ShipmentList shipmentListCount = shipmentListMapper.getCountByShipmentManageId(shipmentCargoDetails.getShipmentDemandId(), shipmentCargoDetails.getMaterialNumber());
        Map<String, BigDecimal> resultMap = new HashMap<>(0);
        if (shipmentCargoDetails.getUnitType() == 1) {
            if (inventoryManage.getQuantityCount() - (shipmentCargoDetails.getQuantityCount() - shipmentListCount.getQuantityCount()) > 0) {
                resultMap.put("dismantlingPlateCount", new BigDecimal(inventoryManage.getQuantityCount() - (shipmentCargoDetails.getQuantityCount() - shipmentListCount.getQuantityCount())));
                return resultMap;
            }
            if (inventoryManage.getQuantityCount() + shipmentListCount.getQuantityCount() == shipmentCargoDetails.getQuantityCount()) {
                shipmentCargoDetailsMapper.updateStatus(2, shipmentCargoDetails.getId());
            }
        }
        if (shipmentCargoDetails.getUnitType() == 2) {
            if (inventoryManage.getBoxCount() - (shipmentCargoDetails.getBoxCount() - shipmentListCount.getBoxCount()) > 0) {
                resultMap.put("dismantlingPlateCount", new BigDecimal(inventoryManage.getBoxCount() - (shipmentCargoDetails.getBoxCount() - shipmentListCount.getBoxCount())));
                return resultMap;
            }
            if (inventoryManage.getBoxCount() + shipmentListCount.getBoxCount() == shipmentCargoDetails.getBoxCount()) {
                shipmentCargoDetailsMapper.updateStatus(2, shipmentCargoDetails.getId());
            }
        }
        if (shipmentCargoDetails.getUnitType() == 5) {
            if (inventoryManage.getVolume().subtract(shipmentCargoDetails.getVolume().subtract(shipmentListCount.getVolume())).compareTo(BigDecimal.ZERO) > 0) {
                resultMap.put("dismantlingPlateCount", inventoryManage.getVolume().subtract(shipmentCargoDetails.getVolume().subtract(shipmentListCount.getVolume())));
                return resultMap;
            }
            if (inventoryManage.getVolume().add(shipmentListCount.getVolume()).subtract(shipmentCargoDetails.getVolume()).compareTo(BigDecimal.ZERO) == 0) {
                shipmentCargoDetailsMapper.updateStatus(2, shipmentCargoDetails.getId());
            }
        }
        if (shipmentCargoDetails.getUnitType() == 5) {
            if (inventoryManage.getGrossWeight().subtract(shipmentCargoDetails.getGrossWeight().subtract(shipmentListCount.getGrossWeight())).compareTo(BigDecimal.ZERO) > 0) {
                resultMap.put("dismantlingPlateCount", inventoryManage.getGrossWeight().subtract(shipmentCargoDetails.getGrossWeight().subtract(shipmentListCount.getGrossWeight())));
                return resultMap;
            }
            if (inventoryManage.getGrossWeight().add(shipmentListCount.getGrossWeight()).subtract(shipmentCargoDetails.getGrossWeight()).compareTo(BigDecimal.ZERO) == 0) {
                shipmentCargoDetailsMapper.updateStatus(2, shipmentCargoDetails.getId());
            }
        }
        ShipmentList shipmentList = BeanUtils.copy(inventoryManage, ShipmentList.class);
        shipmentList.setShipmentManageId(shipmentManage.getId());
        shipmentList.setStatus(2);
        super.insert(shipmentList);
        ew = new EntityWrapper<>();
        ew.eq("shipment_manage_id", shipmentManage.getId());
        if (shipmentListMapper.getBaffle(shipmentManage.getId()) == 0 && shipmentListMapper.selectList(ew).size() > 0) {
            shipmentManage.setStatus(2);
            shipmentManageMapper.updateById(shipmentManage);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void quicklyPick(Integer shipmentManageId) {
        ShipmentManage shipmentManage = shipmentManageMapper.selectById(shipmentManageId);
        if (shipmentListMapper.getBaffle(shipmentManageId) != 0 || shipmentManage.getStatus() == 1) {
            EntityWrapper<ShipmentList> ew = new EntityWrapper<>();
            ew.eq("shipment_manage_id", shipmentManageId);
            for (ShipmentList shipmentList : shipmentListMapper.selectList(ew)) {
                inventoryManageMapper.updateLockStatus(shipmentList.getInventoryManageId(), 0);
                shipmentListMapper.deleteById(shipmentList.getId());
            }

            ShipmentDemand shipmentDemand = shipmentDemandMapper.selectById(shipmentManage.getShipmentDemandId());
            List<ShipmentCargoDetails> shipmentCargoDetailsList = shipmentCargoDetailsMapper.getShipmentCargoDetailsListByShipmentDemandId(shipmentDemand.getId());
            for (ShipmentCargoDetails shipmentCargoDetails : shipmentCargoDetailsList) {
                List<InventoryManage> inventoryManages = inventoryManageMapper.getFirstIDirstOut(shipmentDemand.getClientId(), shipmentDemand.getWarehouseId(), shipmentCargoDetails);
                if (inventoryManages == null || inventoryManages.size() < 1) {
                    throw new BusinessException("库存不足，请确认");
                }
                inventoryManageListFor(inventoryManages, shipmentManage.getId(), shipmentCargoDetails);
            }
            if (shipmentListMapper.getBaffle(shipmentManageId) == 0 && shipmentListMapper.selectList(ew).size() > 0) {
                shipmentManage.setStatus(2);
                shipmentManageMapper.updateById(shipmentManage);
            }
        } else {
            throw new BusinessException("系统拣货失败，该拣货操作已完成");
        }
    }

    @Override
    public ShipmentList addPick(ShipmentListDTO shipmentListDTO) {
        Integer shipmentDemandId = shipmentManageMapper.selectById(shipmentListDTO.getShipmentManageId()).getShipmentDemandId();
        InventoryManage inventoryManage = inventoryManageMapper.selectById(shipmentListDTO.getInventoryManageId());
        if (inventoryManage.getLockStatus() != 0) {
            throw new BusinessException("该板已被锁定");
        }
        ShipmentCargoDetails shipmentCargoDetails = shipmentCargoDetailsMapper.getCargoDetailsByMaterialNumber(shipmentDemandId, inventoryManage);
        if (shipmentCargoDetails == null) {
            throw new BusinessException("库存不足，请确认");
        }
        BigDecimal sCount;
        boolean isBaffle = false;
        BigDecimal count;
        BigDecimal common;
        ShipmentList shipmentList = BeanUtils.copy(inventoryManage, ShipmentList.class);
        switch (shipmentCargoDetails.getUnitType()) {
            case 1:
                common = new BigDecimal(shipmentCargoDetails.getQuantityCount());
                sCount = new BigDecimal(shipmentCargoDetails.getActualQuantityCount()).add(new BigDecimal(inventoryManage.getQuantityCount()));
                shipmentCargoDetails.setActualQuantityCount(sCount.intValue());
                if (sCount.compareTo(common) >= 0) {
                    if (sCount.compareTo(common) > 0) {
                        count = common.subtract(sCount.subtract(new BigDecimal(inventoryManage.getQuantityCount())));
                        isBaffle = true;
                        shipmentList.setQuantityCount(count.intValue());
                        shipmentList.setBoxCount((int) Math.ceil((double) shipmentList.getQuantityCount() / inventoryManage.getQuantityScale()));
                        if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                            if (inventoryManage.getPerUnitVolume() == 1) {
                                shipmentList.setVolume(inventoryManage.getVolumeScale());
                            } else if (inventoryManage.getPerUnitVolume() == 2) {
                                shipmentList.setVolume(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getVolumeScale()));
                            } else if (inventoryManage.getPerUnitVolume() == 3) {
                                shipmentList.setVolume(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getVolumeScale()));
                            } else {
                                throw new BusinessException("单位重量单位错误");
                            }
                        }
                        if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                            if (inventoryManage.getPerUnitWeight() == 1) {
                                shipmentList.setGrossWeight(inventoryManage.getWeightScale());
                            } else if (inventoryManage.getPerUnitWeight() == 2) {
                                shipmentList.setGrossWeight(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getWeightScale()));
                            } else if (inventoryManage.getPerUnitWeight() == 3) {
                                shipmentList.setGrossWeight(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getWeightScale()));
                            } else {
                                throw new BusinessException("单位重量单位错误");
                            }
                        }
                    }
                    shipmentCargoDetails.setActualQuantityCount(shipmentCargoDetails.getQuantityCount());
                }
                break;
            case 2:
                common = new BigDecimal(shipmentCargoDetails.getBoxCount());
                sCount = new BigDecimal(shipmentCargoDetails.getActualBoxCount()).add(new BigDecimal(inventoryManage.getBoxCount()));
                shipmentCargoDetails.setActualBoxCount(sCount.intValue());
                if (sCount.compareTo(common) >= 0) {
                    if (sCount.compareTo(common) > 0) {
                        count = common.subtract((sCount.subtract(new BigDecimal(inventoryManage.getQuantityCount()))));
                        isBaffle = true;
                        shipmentList.setBoardCount(count.intValue());

                        shipmentList.setQuantityCount(shipmentList.getBoxCount() * inventoryManage.getQuantityCount());
                        if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                            if (inventoryManage.getPerUnitVolume() == 1) {
                                shipmentList.setVolume(inventoryManage.getVolumeScale());
                            } else if (inventoryManage.getPerUnitVolume() == 2) {
                                shipmentList.setVolume(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getVolumeScale()));
                            } else if (inventoryManage.getPerUnitVolume() == 3) {
                                shipmentList.setVolume(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getVolumeScale()));
                            } else {
                                throw new BusinessException("单位重量单位错误");
                            }
                        }
                        if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                            if (inventoryManage.getPerUnitWeight() == 1) {
                                shipmentList.setGrossWeight(inventoryManage.getWeightScale());
                            } else if (inventoryManage.getPerUnitWeight() == 2) {
                                shipmentList.setGrossWeight(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getWeightScale()));
                            } else if (inventoryManage.getPerUnitWeight() == 3) {
                                shipmentList.setGrossWeight(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getWeightScale()));
                            } else {
                                throw new BusinessException("单位重量单位错误");
                            }
                        }
                    }
                    shipmentCargoDetails.setActualBoxCount(shipmentCargoDetails.getBoxCount());
                }
                break;
            case 3:
                common = new BigDecimal(shipmentCargoDetails.getBoardCount());
                sCount = new BigDecimal(shipmentCargoDetails.getActualBoardCount()).add(BigDecimal.ONE);
                shipmentCargoDetails.setActualBoardCount(sCount.intValue());
                if (sCount.compareTo(common) >= 0) {
                    if (sCount.compareTo(common) > 0) {
                        count = common.subtract(sCount.subtract(BigDecimal.ONE));
                        isBaffle = true;
                        shipmentList.setBoardCount(count.intValue());
                    }
                    shipmentCargoDetails.setActualBoardCount(shipmentCargoDetails.getBoardCount());
                }
                break;
            case 4:
                common = shipmentCargoDetails.getGrossWeight();
                sCount = shipmentCargoDetails.getActualGrossWeight().add(inventoryManage.getGrossWeight());
                shipmentCargoDetails.setActualGrossWeight(sCount);
                if (sCount.compareTo(common) >= 0) {
                    if (sCount.compareTo(common) > 0) {
                        count = common.subtract(sCount.subtract(inventoryManage.getGrossWeight()));
                        isBaffle = true;
                        shipmentList.setGrossWeight(count);

                        if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                            if (inventoryManage.getPerUnitWeight() == 2) {
                                shipmentList.setBoxCount(shipmentList.getGrossWeight().divide(inventoryManage.getWeightScale(), RoundingMode.UP).intValue());
                                shipmentList.setQuantityCount(shipmentList.getBoxCount() * inventoryManage.getQuantityCount());
                            } else if (inventoryManage.getPerUnitWeight() == 3) {
                                shipmentList.setQuantityCount(shipmentList.getGrossWeight().divide(inventoryManage.getWeightScale(), RoundingMode.UP).intValue());
                                shipmentList.setBoxCount((int) Math.ceil((double) shipmentList.getQuantityCount() / inventoryManage.getQuantityScale()));
                            } else {
                                throw new BusinessException("单位重量单位错误");
                            }
                        }

                        if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                            if (inventoryManage.getPerUnitVolume() == 1) {
                                shipmentList.setVolume(inventoryManage.getVolumeScale());
                            } else if (inventoryManage.getPerUnitVolume() == 2) {
                                shipmentList.setVolume(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getVolumeScale()));
                            } else if (inventoryManage.getPerUnitVolume() == 3) {
                                shipmentList.setVolume(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getVolumeScale()));
                            } else {
                                throw new BusinessException("单位重量单位错误");
                            }
                        }
                    }
                    shipmentCargoDetails.setActualGrossWeight(shipmentCargoDetails.getGrossWeight());
                }
                break;
            case 5:
                common = shipmentCargoDetails.getVolume();
                sCount = shipmentCargoDetails.getActualVolume().add(inventoryManage.getVolume());
                shipmentCargoDetails.setActualVolume(sCount);
                if (sCount.compareTo(common) >= 0) {
                    if (sCount.compareTo(common) > 0) {
                        count = common.subtract(sCount.subtract(inventoryManage.getVolume()));
                        isBaffle = true;
                        shipmentList.setVolume(count);

                        if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                            if (inventoryManage.getPerUnitVolume() == 2) {
                                shipmentList.setBoxCount(shipmentList.getGrossWeight().divide(inventoryManage.getVolumeScale(), RoundingMode.UP).intValue());
                                shipmentList.setQuantityCount(shipmentList.getBoxCount() * inventoryManage.getQuantityCount());
                            } else if (inventoryManage.getPerUnitVolume() == 3) {
                                shipmentList.setQuantityCount(shipmentList.getGrossWeight().divide(inventoryManage.getVolumeScale(), RoundingMode.UP).intValue());
                                shipmentList.setBoxCount((int) Math.ceil((double) shipmentList.getQuantityCount() / inventoryManage.getQuantityScale()));
                            } else {
                                throw new BusinessException("单位重量单位错误");
                            }
                        }

                        if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                            if (inventoryManage.getPerUnitWeight() == 1) {
                                shipmentList.setGrossWeight(inventoryManage.getWeightScale());
                            } else if (inventoryManage.getPerUnitWeight() == 2) {
                                shipmentList.setGrossWeight(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getWeightScale()));
                            } else if (inventoryManage.getPerUnitWeight() == 3) {
                                shipmentList.setGrossWeight(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getWeightScale()));
                            } else {
                                throw new BusinessException("单位重量单位错误");
                            }
                        }
                    }
                    shipmentCargoDetails.setActualVolume(shipmentCargoDetails.getVolume());
                }
                break;
            default:
                throw new BusinessException("");
        }
        inventoryManage.setLockStatus(1);
        inventoryManageMapper.updateById(inventoryManage);
        shipmentList.setStatus(1);
        shipmentList.setInventoryManageId(inventoryManage.getId());
        shipmentList.setShipmentManageId(shipmentListDTO.getShipmentManageId());
        if (isBaffle) {
            shipmentList.setIsBaffle(1);
        }
        if (shipmentListDTO.getId() != null) {
            shipmentList.setId(shipmentListDTO.getId());
            shipmentListMapper.updateById(shipmentList);
        } else {
            shipmentListMapper.insert(shipmentList);
        }
        shipmentCargoDetailsMapper.updateById(shipmentCargoDetails);
        EntityWrapper<ShipmentList> ew = new EntityWrapper<>();
        ew.eq("shipment_manage_id", shipmentListDTO.getShipmentManageId());
        if (shipmentListMapper.getBaffle(shipmentListDTO.getShipmentManageId()) == 0 && shipmentListMapper.selectList(ew).size() > 0) {
            shipmentManageMapper.updateStatus(shipmentListDTO.getShipmentManageId(), 2);
        }
        return shipmentList;
    }

    @Override
    public void delete(Integer id) {
        ShipmentList shipmentList = super.selectById(id);
        if (shipmentList.getStatus() == 2) {
            throw new BusinessException("已扫描的货物不允许删除");
        }
        ShipmentManage shipmentManage = shipmentManageMapper.selectById(shipmentList.getShipmentManageId());
        ShipmentCargoDetails shipmentCargoDetails = shipmentCargoDetailsMapper.getCargoDetailsByMaterialNumbers(shipmentManage.getShipmentDemandId(), shipmentList);
        shipmentCargoDetails.setActualBoxCount(shipmentCargoDetails.getActualBoxCount() - shipmentList.getBoxCount());
        shipmentCargoDetails.setActualQuantityCount(shipmentCargoDetails.getActualQuantityCount() - shipmentList.getQuantityCount());
        shipmentCargoDetails.setActualGrossWeight(shipmentCargoDetails.getActualGrossWeight().subtract(shipmentList.getGrossWeight()));
        shipmentCargoDetails.setActualNetWeight(shipmentCargoDetails.getActualNetWeight().subtract(shipmentList.getNetWeight()));
        shipmentCargoDetails.setActualVolume(shipmentCargoDetails.getActualVolume().subtract(shipmentList.getVolume()));
        shipmentCargoDetails.setActualBoardCount(shipmentCargoDetails.getActualBoardCount() - shipmentList.getBoardCount());
        shipmentCargoDetailsMapper.updateById(shipmentCargoDetails);
        super.deleteById(id);
    }

    private void inventoryManageListFor(List<InventoryManage> inventoryManages, int shipmentManageId, ShipmentCargoDetails shipmentCargoDetails) {
        //剩余的数量
        BigDecimal sCount = BigDecimal.ZERO;
        boolean isTrue = false;
        boolean isBaffle;
        ShipmentList shipmentList;
        int sum = 1;
        BigDecimal count;
        BigDecimal common;
        for (InventoryManage inventoryManage : inventoryManages) {
            isBaffle = false;
            shipmentList = BeanUtils.copy(inventoryManage, ShipmentList.class);
            switch (shipmentCargoDetails.getUnitType()) {
                case 1:
                    common = new BigDecimal(shipmentCargoDetails.getQuantityCount());
                    sCount = sCount.add(new BigDecimal(inventoryManage.getQuantityCount()));
                    if (sCount.compareTo(common) >= 0) {
                        if (sCount.compareTo(common) > 0) {
                            count = common.subtract(sCount.subtract(new BigDecimal(inventoryManage.getQuantityCount())));
                            isBaffle = true;
                            shipmentList.setQuantityCount(count.intValue());
                            shipmentList.setBoxCount((int) Math.ceil((double) shipmentList.getQuantityCount() / inventoryManage.getQuantityScale()));
                            if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                                if (inventoryManage.getPerUnitVolume() == 1) {
                                    shipmentList.setVolume(inventoryManage.getVolumeScale());
                                } else if (inventoryManage.getPerUnitVolume() == 2) {
                                    shipmentList.setVolume(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getVolumeScale()));
                                } else if (inventoryManage.getPerUnitVolume() == 3) {
                                    shipmentList.setVolume(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getVolumeScale()));
                                } else {
                                    throw new BusinessException("单位重量单位错误");
                                }
                            }
                            if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                                if (inventoryManage.getPerUnitWeight() == 1) {
                                    shipmentList.setGrossWeight(inventoryManage.getWeightScale());
                                } else if (inventoryManage.getPerUnitWeight() == 2) {
                                    shipmentList.setGrossWeight(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getWeightScale()));
                                } else if (inventoryManage.getPerUnitWeight() == 3) {
                                    shipmentList.setGrossWeight(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getWeightScale()));
                                } else {
                                    throw new BusinessException("单位重量单位错误");
                                }
                            }
                        }
                        shipmentCargoDetails.setActualQuantityCount(shipmentCargoDetails.getQuantityCount());
                        isTrue = true;
                    }
                    System.out.println(sum);
                    System.out.println(inventoryManages.size());
                    if (sum == inventoryManages.size() && sCount.intValue() < shipmentCargoDetails.getQuantityCount()) {
                        throw new BusinessException("库存不足，请确认");
                    }
                    break;
                case 2:
                    common = new BigDecimal(shipmentCargoDetails.getBoxCount());
                    sCount = sCount.add(new BigDecimal(inventoryManage.getBoxCount()));
                    if (sCount.compareTo(common) >= 0) {
                        if (sCount.compareTo(common) > 0) {
                            count = common.subtract((sCount.subtract(new BigDecimal(inventoryManage.getQuantityCount()))));
                            isBaffle = true;
                            shipmentList.setBoxCount(count.intValue());


                            shipmentList.setQuantityCount(shipmentList.getBoxCount() * inventoryManage.getQuantityCount());
                            if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                                if (inventoryManage.getPerUnitVolume() == 1) {
                                    shipmentList.setVolume(inventoryManage.getVolumeScale());
                                } else if (inventoryManage.getPerUnitVolume() == 2) {
                                    shipmentList.setVolume(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getVolumeScale()));
                                } else if (inventoryManage.getPerUnitVolume() == 3) {
                                    shipmentList.setVolume(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getVolumeScale()));
                                } else {
                                    throw new BusinessException("单位重量单位错误");
                                }
                            }
                            if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                                if (inventoryManage.getPerUnitWeight() == 1) {
                                    shipmentList.setGrossWeight(inventoryManage.getWeightScale());
                                } else if (inventoryManage.getPerUnitWeight() == 2) {
                                    shipmentList.setGrossWeight(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getWeightScale()));
                                } else if (inventoryManage.getPerUnitWeight() == 3) {
                                    shipmentList.setGrossWeight(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getWeightScale()));
                                } else {
                                    throw new BusinessException("单位重量单位错误");
                                }
                            }


                        }
                        shipmentCargoDetails.setActualBoxCount(shipmentCargoDetails.getBoxCount());
                        isTrue = true;
                    }
                    if (sum == inventoryManages.size() && sCount.intValue() < shipmentCargoDetails.getBoxCount()) {
                        throw new BusinessException("库存不足，请确认");
                    }
                    break;
                case 3:
                    common = new BigDecimal(shipmentCargoDetails.getBoardCount());
                    sCount = sCount.add(BigDecimal.ONE);
                    if (sCount.compareTo(common) >= 0) {
                        if (sCount.compareTo(common) > 0) {
                            count = common.subtract(sCount.subtract(BigDecimal.ONE));
                            isBaffle = true;
                            shipmentList.setBoardCount(count.intValue());
                        }
                        shipmentCargoDetails.setActualBoardCount(shipmentCargoDetails.getBoardCount());
                        isTrue = true;
                    }
                    if (sum == inventoryManages.size() && sCount.intValue() < shipmentCargoDetails.getBoardCount()) {
                        throw new BusinessException("库存不足，请确认");
                    }
                    break;
                case 4:
                    common = shipmentCargoDetails.getGrossWeight();
                    sCount = sCount.add(inventoryManage.getGrossWeight());
                    if (sCount.compareTo(common) >= 0) {
                        if (sCount.compareTo(common) > 0) {
                            count = common.subtract(sCount.subtract(inventoryManage.getGrossWeight()));
                            isBaffle = true;
                            shipmentList.setGrossWeight(count);


                            if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                                if (inventoryManage.getPerUnitWeight() == 2) {
                                    shipmentList.setBoxCount(shipmentList.getGrossWeight().divide(inventoryManage.getWeightScale(), RoundingMode.UP).intValue());
                                    shipmentList.setQuantityCount(shipmentList.getBoxCount() * inventoryManage.getQuantityCount());
                                } else if (inventoryManage.getPerUnitWeight() == 3) {
                                    shipmentList.setQuantityCount(shipmentList.getGrossWeight().divide(inventoryManage.getWeightScale(), RoundingMode.UP).intValue());
                                    shipmentList.setBoxCount((int) Math.ceil((double) shipmentList.getQuantityCount() / inventoryManage.getQuantityScale()));
                                } else {
                                    throw new BusinessException("单位重量单位错误");
                                }
                            }
                            if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                                if (inventoryManage.getPerUnitVolume() == 1) {
                                    shipmentList.setVolume(inventoryManage.getVolumeScale());
                                } else if (inventoryManage.getPerUnitVolume() == 2) {
                                    shipmentList.setVolume(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getVolumeScale()));
                                } else if (inventoryManage.getPerUnitVolume() == 3) {
                                    shipmentList.setVolume(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getVolumeScale()));
                                } else {
                                    throw new BusinessException("单位重量单位错误");
                                }
                            }


                        }
                        shipmentCargoDetails.setActualGrossWeight(shipmentCargoDetails.getGrossWeight());
                        isTrue = true;
                    }
                    if (sum == inventoryManages.size() && sCount.compareTo(shipmentCargoDetails.getGrossWeight()) < 0) {
                        throw new BusinessException("库存不足，请确认");
                    }
                    break;
                case 5:
                    common = shipmentCargoDetails.getVolume();
                    sCount = sCount.add(inventoryManage.getVolume());
                    if (sCount.compareTo(common) >= 0) {
                        if (sCount.compareTo(common) > 0) {
                            count = common.subtract(sCount.subtract(inventoryManage.getVolume()));
                            isBaffle = true;
                            shipmentList.setVolume(count);

                            if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                                if (inventoryManage.getPerUnitVolume() == 2) {
                                    shipmentList.setBoxCount(shipmentList.getGrossWeight().divide(inventoryManage.getVolumeScale(), RoundingMode.UP).intValue());
                                    shipmentList.setQuantityCount(shipmentList.getBoxCount() * inventoryManage.getQuantityCount());
                                } else if (inventoryManage.getPerUnitVolume() == 3) {
                                    shipmentList.setQuantityCount(shipmentList.getGrossWeight().divide(inventoryManage.getVolumeScale(), RoundingMode.UP).intValue());
                                    shipmentList.setBoxCount((int) Math.ceil((double) shipmentList.getQuantityCount() / inventoryManage.getQuantityScale()));
                                } else {
                                    throw new BusinessException("单位重量单位错误");
                                }
                            }

                            if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                                if (inventoryManage.getPerUnitWeight() == 1) {
                                    shipmentList.setGrossWeight(inventoryManage.getWeightScale());
                                } else if (inventoryManage.getPerUnitWeight() == 2) {
                                    shipmentList.setGrossWeight(new BigDecimal(shipmentList.getBoxCount()).multiply(inventoryManage.getWeightScale()));
                                } else if (inventoryManage.getPerUnitWeight() == 3) {
                                    shipmentList.setGrossWeight(new BigDecimal(shipmentList.getQuantityCount()).multiply(inventoryManage.getWeightScale()));
                                } else {
                                    throw new BusinessException("单位重量单位错误");
                                }
                            }

                        }
                        shipmentCargoDetails.setActualVolume(shipmentCargoDetails.getVolume());
                        isTrue = true;
                    }
                    if (sum == inventoryManages.size() && sCount.compareTo(shipmentCargoDetails.getVolume()) < 0) {
                        throw new BusinessException("库存不足，请确认");
                    }
                    break;
                default:
                    throw new BusinessException("");
            }

            inventoryManage.setLockStatus(1);
            inventoryManageMapper.updateById(inventoryManage);
            shipmentList.setStatus(1);
            shipmentList.setInventoryManageId(inventoryManage.getId());
            shipmentList.setShipmentManageId(shipmentManageId);
            if (isBaffle) {
                shipmentList.setIsBaffle(1);
            }
            shipmentListMapper.insert(shipmentList);
            if (isTrue) {
                shipmentCargoDetailsMapper.updateById(shipmentCargoDetails);
                break;
            }
            sum++;
        }
    }


//    private void inventoryManageListFor(List<InventoryList> inventoryLists, int shipmentManageId, ShipmentCargoDetails shipmentCargoDetails, UnitMappingDTO unitMappingDTO) {
//        //剩余的数量
//        int sCount = 0;
//        //剩余的数量
//        BigDecimal sCount1 = BigDecimal.ZERO;
//        boolean isTrue = false;
//        boolean isBaffle = false;
//        ShipmentList shipmentList;
//        ShipmentItemList shipmentItemList;
//        int sum = 1;
//        for (InventoryList inventoryList : inventoryLists) {
//            if (unitMappingDTO.getIsQuantityCount() == 1) {
//                sCount += inventoryList.getQuantityCount();
//                if (sCount >= shipmentCargoDetails.getQuantityCount()) {
//                    if (sCount > shipmentCargoDetails.getQuantityCount()) {
//                        inventoryList.setQuantityCount(sCount - shipmentCargoDetails.getQuantityCount());
//                        isBaffle = true;
//                    }
//                    shipmentCargoDetails.setActualQuantityCount(sCount);
//                    isTrue = true;
//                }
//                if (sum == inventoryLists.size() && (shipmentCargoDetails.getActualQuantityCount() == null || shipmentCargoDetails.getActualQuantityCount() < shipmentCargoDetails.getQuantityCount())) {
//                    throw new BusinessException("库存不足，请确认");
//                }
//            } else if (unitMappingDTO.getIsBoxCount() == 1) {
//                sCount += inventoryList.getBoxCount();
//                if (sCount >= shipmentCargoDetails.getBoxCount()) {
//                    if (sCount > shipmentCargoDetails.getBoxCount()) {
//                        inventoryList.setBoxCount(sCount - shipmentCargoDetails.getBoxCount());
//                        isBaffle = true;
//                    }
//                    shipmentCargoDetails.setActualBoxCount(sCount);
//                    isTrue = true;
//                }
//                if (sum == inventoryLists.size() && (shipmentCargoDetails.getActualBoxCount() == null || shipmentCargoDetails.getActualBoxCount() < shipmentCargoDetails.getBoxCount())) {
//                    throw new BusinessException("库存不足，请确认");
//                }
//            } else if (unitMappingDTO.getIsVolumeReceipt() == 1) {
//                sCount1 = inventoryList.getVolume().add(sCount1);
//                if (sCount1.compareTo(shipmentCargoDetails.getGrossWeight()) >= 0) {
//                    if (sCount1.compareTo(shipmentCargoDetails.getGrossWeight()) > 0) {
//                        inventoryList.setGrossWeight(sCount1.subtract(shipmentCargoDetails.getGrossWeight()));
//                        isBaffle = true;
//                    }
//                    shipmentCargoDetails.setActualGrossWeight(sCount1);
//                    isTrue = true;
//                }
//                if (sum == inventoryLists.size() && (shipmentCargoDetails.getActualGrossWeight() == null || shipmentCargoDetails.getActualGrossWeight().compareTo(shipmentCargoDetails.getGrossWeight()) < 0)) {
//                    throw new BusinessException("库存不足，请确认");
//                }
//            } else if (unitMappingDTO.getIsGrossWeight() == 1) {
//                sCount1 = inventoryList.getGrossWeight().add(sCount1);
//                if (sCount1.compareTo(shipmentCargoDetails.getVolume()) >= 0) {
//                    if (sCount1.compareTo(shipmentCargoDetails.getVolume()) > 0) {
//                        inventoryList.setVolume(sCount1.subtract(shipmentCargoDetails.getVolume()));
//                        isBaffle = true;
//                    }
//                    shipmentCargoDetails.setActualVolume(sCount1);
//                    isTrue = true;
//                }
//                if (sum == inventoryLists.size() && (shipmentCargoDetails.getActualVolume() == null || shipmentCargoDetails.getActualVolume().compareTo(shipmentCargoDetails.getVolume()) < 0)) {
//                    throw new BusinessException("库存不足，请确认");
//                }
//            } else {
//                throw new BusinessException("未找到单位对应配置");
//            }
//            shipmentItemList = BeanUtils.copy(inventoryList, ShipmentItemList.class);
//            InventoryManage inventoryManage = inventoryManageMapper.selectById(inventoryList.getInventoryManageId());
//            inventoryManage.setLockStatus(1);
//            inventoryManageMapper.updateById(inventoryManage);
//
//            shipmentList = BeanUtils.copy(inventoryManage, ShipmentList.class);
//            shipmentList.setInventoryManageId(inventoryManage.getId());
//            shipmentList.setShipmentManageId(shipmentManageId);
//            if (isBaffle || inventoryListMapper.getInventoryListByInventoryManageId(inventoryManage.getId()) > 0) {
//                shipmentList.setIsBaffle(1);
//            }
//            shipmentListMapper.insert(shipmentList);
//
//            shipmentItemList.setShipmentListId(shipmentList.getId());
//            shipmentItemListMapper.insert(shipmentItemList);
//
//            if (isTrue) {
//                shipmentCargoDetailsMapper.updateById(shipmentCargoDetails);
//                break;
//            }
//            sum++;
//        }
//    }

}
