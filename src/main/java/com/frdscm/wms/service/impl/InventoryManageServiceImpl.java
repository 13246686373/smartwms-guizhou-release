package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.*;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.*;
import com.frdscm.wms.entity.vo.GetMaterialVO;
import com.frdscm.wms.entity.vo.InventoryListVO;
import com.frdscm.wms.entity.vo.InventoryManageVO;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.mapper.*;
import com.frdscm.wms.service.IInventoryManageService;
import com.frdscm.wms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存管理表 服务实现类
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Service
public class InventoryManageServiceImpl extends ServiceImpl<InventoryManageMapper, InventoryManage> implements IInventoryManageService {

    private final InventoryManageMapper inventoryManageMapper;

    private final ReceiptDemandMapper receiptDemandMapper;

    private final WarehouseStorageMapper warehouseStorageMapper;

    private final ReceiptManageMapper receiptManageMapper;

    private final ReceiptListMapper receiptListMapper;

    private final ProjectService projectService;

    private final ShipmentListMapper shipmentListMapper;

    private final InventoryScanListMapper inventoryScanListMapper;

    private final InventoryListMapper inventoryListMapper;

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public InventoryManageServiceImpl(InventoryManageMapper inventoryManageMapper, InventoryListMapper inventoryListMapper, InventoryScanListMapper inventoryScanListMapper, ReceiptDemandMapper receiptDemandMapper, WarehouseStorageMapper warehouseStorageMapper, ReceiptManageMapper receiptManageMapper, ReceiptListMapper receiptListMapper, ProjectService projectService, ShipmentListMapper shipmentListMapper) {
        this.inventoryManageMapper = inventoryManageMapper;
        this.receiptDemandMapper = receiptDemandMapper;
        this.warehouseStorageMapper = warehouseStorageMapper;
        this.receiptManageMapper = receiptManageMapper;
        this.receiptListMapper = receiptListMapper;
        this.projectService = projectService;
        this.shipmentListMapper = shipmentListMapper;
        this.inventoryScanListMapper = inventoryScanListMapper;
        this.inventoryListMapper = inventoryListMapper;
    }

    @Override
    public Page<InventoryManage> getInventoryManageByPageList(Page<InventoryManage> page, InventoryManagePageDTO inventoryManagePageDTO) {
        page.setSearchCount(false);
        page.setRecords(inventoryManageMapper.getInventoryManageByPageList(page.getOffset(), page.getLimit(), inventoryManagePageDTO));
        page.setTotal(inventoryManageMapper.getInventoryManageByPageCount(inventoryManagePageDTO));
        return page;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateStatus(Integer status, Integer id) {
        if (inventoryManageMapper.selectById(id) == null) {
            throw new BusinessException("库存信息需求不存在，请确认");
        }
        if (status != 1 && status != 0) {
            throw new BusinessException("状态码只能为0或者1，请确认");
        }
        inventoryManageMapper.updateStatus(status, id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public InventoryManage dismantlingPlate(DismantlingPlateDTO dismantlingPlateDTO) {
        int count = inventoryManageMapper.countBoardNumber("P" + DateUtil.dayNow());
        String str = String.format("%04d", count);
        //生成版号 格式 P+yyMMdd+0001
        String sNum = "P" + DateUtil.dayNow() + str;
        InventoryManage oldInventoryManage = inventoryManageMapper.selectById(dismantlingPlateDTO.getId());
        InventoryManage newInventoryManage = BeanUtils.copy(oldInventoryManage, InventoryManage.class);
        newInventoryManage.setId(null);
        newInventoryManage.setLockStatus(0);
        newInventoryManage.setBoardNumber(sNum);
        newInventoryManage.setQuantityCount(dismantlingPlateDTO.getQuantityCount());
        newInventoryManage.setGrossWeight(dismantlingPlateDTO.getGrossWeight());
        newInventoryManage.setNetWeight(dismantlingPlateDTO.getNetWeight());
        newInventoryManage.setVolume(dismantlingPlateDTO.getVolume());
        newInventoryManage.setBoxCount(dismantlingPlateDTO.getBoxCount());
        newInventoryManage.setCreateTime(null);
        newInventoryManage.setUpdateTime(null);
        newInventoryManage.setAbnormal(1);
        inventoryManageMapper.insert(newInventoryManage);
        if (dismantlingPlateDTO.getQuantityCount().equals(oldInventoryManage.getQuantityCount())) {
            inventoryManageMapper.deleteById(oldInventoryManage.getId());
        } else {
            oldInventoryManage.setQuantityCount(oldInventoryManage.getQuantityCount() - dismantlingPlateDTO.getQuantityCount());
            oldInventoryManage.setGrossWeight(oldInventoryManage.getGrossWeight().subtract(dismantlingPlateDTO.getGrossWeight()));
            oldInventoryManage.setNetWeight(oldInventoryManage.getNetWeight().subtract(dismantlingPlateDTO.getNetWeight()));
            oldInventoryManage.setVolume(oldInventoryManage.getVolume().subtract(dismantlingPlateDTO.getVolume()));
            oldInventoryManage.setBoxCount(oldInventoryManage.getBoxCount() - dismantlingPlateDTO.getBoxCount());
            inventoryManageMapper.updateById(oldInventoryManage);
        }

//        InventoryList oldInventoryList;
//        InventoryList newInventoryList;
//        for (DismantlingPlateItemDTO dismantlingPlateItemDTO : dismantlingPlateDTO.getDismantlingPlateItemDTOS()) {
//            oldInventoryList = inventoryListMapper.selectById(dismantlingPlateItemDTO.getId());
//            if (dismantlingPlateItemDTO.getQuantityCount().equals(oldInventoryList.getQuantityCount())) {
//                inventoryListMapper.deleteById(oldInventoryList.getId());
//            } else {
//                oldInventoryList.setQuantityCount(oldInventoryList.getQuantityCount() - dismantlingPlateItemDTO.getQuantityCount());
//            }
//            newInventoryList = BeanUtils.copy(oldInventoryList, InventoryList.class);
//            newInventoryList.setId(null);
//            newInventoryList.setCreateTime(null);
//            newInventoryList.setUpdateTime(null);
//            newInventoryList.setInventoryManageId(newInventoryManage.getId());
//            newInventoryList.setQuantityCount(dismantlingPlateItemDTO.getQuantityCount());
//            inventoryListMapper.insert(newInventoryList);
//            inventoryListMapper.updateById(oldInventoryList);
//        }
        return newInventoryManage;
//        for (InventoryManageDTO inventoryManageDTO : inventoryManageDTOList) {
//            InventoryManage inventoryManage = BeanUtils.copy(inventoryManageDTO, InventoryManage.class);
//            if (inventoryManage.getId() != null) {
//                inventoryManageMapper.updateById(inventoryManage);
//                //异动
//                inventoryManageMapper.updateAbnormalById(2, inventoryManage.getId());
//                imList.add(inventoryManage);
//            }
//            if (inventoryManage.getBoardNumber() == null) {
//                inventoryManage.setBoardNumber(sNum);
//                inventoryManageMapper.insert(inventoryManage);
//                imList.add(inventoryManage);
//            }
//
//        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String addInventoryManage(ReceiptList receiptList, ReceiptDemandPickUpDTO receiptDemandPickUpDTO, UserBO userBO) {
//        ReceiptDemand receiptDemand = receiptDemandMapper.getReceiptDemandByReceiptManageId(receiptList.getReceiptManageId());
//
//        if (receiptDemand == null) {
//            throw new BusinessException("收货需求为空，请确认");
//        }
//        if (receiptList.getStatus() == 2) {
//            throw new BusinessException("该货物已上架，请勿重复操作");
//        }
//        WarehouseStorage warehouseStorage = warehouseStorageMapper.selectOne(receiptDemandPickUpDTO.getWarehouseStorageNumber(), receiptDemandPickUpDTO.getWarehouseId());
//        if (warehouseStorage == null) {
//            throw new BusinessException("储位不存在");
//        }
//        String str = "上架成功";
//        InventoryManage inventoryManage = new InventoryManage();
//        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request =  ra.getRequest();
//        Integer companyId = Integer.valueOf(request.getHeader("companyId"));
//        UnitMappingDTO unitMappingDTO = projectService.getBillUnitByUnitId(receiptList.getUnitId(), companyId);
//        if (unitMappingDTO.getIsQuantityCount() == 1) {
//            inventoryManage.setAvailableStock(new BigDecimal(receiptList.getQuantityCount()));
//        }
//        if (unitMappingDTO.getIsBoxCount() == 1) {
//            inventoryManage.setAvailableStock(new BigDecimal(receiptList.getBoxCount()));
//        }
//        if (unitMappingDTO.getIsVolumeReceipt() == 1) {
//            inventoryManage.setAvailableStock(receiptList.getVolume());
//        }
//        if (unitMappingDTO.getIsGrossWeight() == 1) {
//            inventoryManage.setAvailableStock(receiptList.getGrossWeight());
//        }
//        inventoryManage.setBoardNumber(receiptList.getBoardNumber());
//        inventoryManage.setWarehouseStorageNumber(receiptDemandPickUpDTO.getWarehouseStorageNumber());
//        inventoryManage.setBatchNumber(receiptList.getBatchNumber());
//        inventoryManage.setClientId(receiptDemand.getClientId());
//        inventoryManage.setClientName(receiptDemand.getClientName());
//        inventoryManage.setWarehouseId(receiptDemand.getWarehouseId());
//        inventoryManage.setSingleNumber(receiptDemand.getSingleNumber());
//        inventoryManage.setMaterialNumber(receiptList.getMaterialNumber());
//        inventoryManage.setMaterialSpecifications(receiptList.getMaterialSpecifications());
//        inventoryManage.setMaterialName(receiptList.getMaterialName());
//        inventoryManage.setUnit(receiptList.getUnit());
//        inventoryManage.setUnitId(receiptList.getUnitId());
//        inventoryManage.setQuantityCount(receiptList.getQuantityCount());
//        inventoryManage.setBoxCount(receiptList.getBoxCount());
//        inventoryManage.setGrossWeight(receiptList.getGrossWeight());
//        inventoryManage.setNetWeight(receiptList.getNetWeight());
//        inventoryManage.setVolume(receiptList.getVolume());
//        inventoryManage.setLockStock(BigDecimal.ZERO);
//        inventoryManageMapper.insert(inventoryManage);
//        receiptList.setWarehouseStorageNumber(receiptDemandPickUpDTO.getWarehouseStorageNumber());
//        receiptList.setStatus(2);
//        receiptListMapper.updateById(receiptList);
//        List<ReceiptList> receiptListList = receiptListMapper.getReceiptListByReceiptManageIdStatus(receiptList.getReceiptManageId());
//        if (receiptListList.size() <= 0) {
//            str = "上架完成";
//            receiptManageMapper.updateStatus(receiptList.getReceiptManageId(), 2);
//            return str;
//        }
//        return str;
        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void moveWarehouseStorage(MoveWereHouseStorageDTO moveWereHouseStorageDTO) {
        InventoryManage inventoryManage = inventoryManageMapper.selectById(moveWereHouseStorageDTO.getId());
        inventoryManage.setWarehouseStorageNumber(moveWereHouseStorageDTO.getWarehouseStorageNumber());
        inventoryManage.setAbnormal(1);
        inventoryManageMapper.updateById(inventoryManage);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void moveWarehouseStorageApp(MoveWereHouseStorageAppDTO moveWereHouseStorageAppDTO) {
        InventoryManage inventoryManage = inventoryManageMapper.getInventoryManageByBNumAndWsNum(moveWereHouseStorageAppDTO.getBoardNumber(), moveWereHouseStorageAppDTO.getOldWarehouseStorageNumber());
        if (inventoryManage == null) {
            throw new BusinessException("信息不存在，请确认");
        }
        WarehouseStorage warehouseStorage = warehouseStorageMapper.getWarehouseStorageByNumberAndWarehouseId(moveWereHouseStorageAppDTO.getOldWarehouseStorageNumber(), moveWereHouseStorageAppDTO.getWarehouseId());
        if (warehouseStorage == null) {
            throw new BusinessException("储位不存在，请确认");
        }
        if (warehouseStorageMapper.getWarehouseStorageByNumberAndWarehouseId(moveWereHouseStorageAppDTO.getNewWarehouseStorageNumber(), moveWereHouseStorageAppDTO.getWarehouseId()) == null) {
            throw new BusinessException("新储位不存在或不在该仓库，请确认");
        }
        inventoryManageMapper.moveWarehouseStorage(moveWereHouseStorageAppDTO.getNewWarehouseStorageNumber(), moveWereHouseStorageAppDTO.getBoardNumber());
        //异动
        inventoryManageMapper.updateAbnormalById(1, inventoryManage.getId());
    }

    @Override
    public void transferWereHouseStorage(TransferWereHouseStorageDTO transferWereHouseStorageDTO) {
        InventoryManage inventoryManage = inventoryManageMapper.selectById(transferWereHouseStorageDTO.getId());
        inventoryManage.setWarehouseStorageNumber(transferWereHouseStorageDTO.getWarehouseStorageNumber());
        inventoryManage.setWarehouseId(transferWereHouseStorageDTO.getWarehouseId());
        inventoryManage.setAbnormal(1);
        inventoryManageMapper.updateById(inventoryManage);
    }

    @Override
    public int countBoardNumber(String boardNumberLike) {
        return inventoryManageMapper.countBoardNumber(boardNumberLike);
    }

    @Override
    public List<String> getBatchNumberList(SelectbatchNumberDTO selectbatchNumberDTO) {
        return inventoryManageMapper.getBatchNumberList(selectbatchNumberDTO);
    }

    @Override
    public List<InventoryManage> getInventoryManageList(CheckManageSelectDTO checkManageSelectDTO) {
//        List<String> wareHouseStorageNumberList = warehouseStorageMapper.getWareHouseStorageNumberByWsgId(checkManageSelectDTO.getWarehouseStorageGradeId());
//        checkManageSelectDTO.setWareHouseStorageNumberList(wareHouseStorageNumberList);
        return inventoryManageMapper.getInventoryManageList(checkManageSelectDTO);
    }

    @Override
    public Page<InventoryManage> getInventoryManageListByShipment(Page<InventoryManage> page, InventoryManageByShipmentPageDTO inventoryManageByShipmentPageDTO) {
        return page.setRecords(inventoryManageMapper.getInventoryManageListByShipment(page, inventoryManageByShipmentPageDTO));
    }

    @Override
    public List<GetMaterialVO> getMaterialList(MaterialDTO materialDTO) {
        return inventoryManageMapper.getMaterialList(materialDTO);
    }

    @Override
    public List<InventoryManageVO> freightInquiry(FreightInquiryDTO freightInquiryDTO) {
        List<InventoryManage> inventoryManageList = inventoryManageMapper.freightInquiry(freightInquiryDTO);
        if (inventoryManageList == null) {
            throw new BusinessException("暂无数据，请确认");
        }
        List<InventoryManageVO> inventoryManageListNew = new ArrayList<>();
        InventoryManageVO inventoryManageVO;
        for (InventoryManage inventoryManage : inventoryManageList) {
            inventoryManageVO = BeanUtils.copy(inventoryManage, InventoryManageVO.class);
            if (inventoryManage.getUnitType() == 1) {
                inventoryManageVO.setQuantityCount(inventoryManage.getQuantityCount());
            }
            if (inventoryManage.getUnitType() == 2) {
                inventoryManageVO.setQuantityCount(inventoryManage.getBoxCount());
            }
            if (inventoryManage.getUnitType() == 5) {
                inventoryManageVO.setQuantityCount(inventoryManage.getVolume());
            }
            if (inventoryManage.getUnitType() == 4) {
                inventoryManageVO.setQuantityCount(inventoryManage.getGrossWeight());
            }
            inventoryManageListNew.add(inventoryManageVO);
        }
        return inventoryManageListNew;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Map<String, String> dismantlingPlateApp(DismantlingPlateAppDTO dismantlingPlateAppDTO) {
        InventoryManage inventoryManage = inventoryManageMapper.getInventoryManageByWarehouseIdAndBoardNumber(dismantlingPlateAppDTO.getWarehouseId(), dismantlingPlateAppDTO.getBoardNumber());
        if (inventoryManage == null) {
            throw new BusinessException("信息不存在，请确认");
        }
        ShipmentList shipmentList = shipmentListMapper.getShipmentListByBoardNumbers(dismantlingPlateAppDTO.getBoardNumber());
        int count = inventoryManageMapper.countBoardNumber("P" + DateUtil.dayNow());
        String str = String.format("%04d", count);
        String sNum = "P" + DateUtil.dayNow() + str;
        InventoryManage inventoryManageNew = BeanUtils.copy(inventoryManage, InventoryManage.class);
        inventoryManageNew.setBoardNumber(sNum);
        if (inventoryManage.getUnitType() == 1) {
            if (inventoryManage.getQuantityCount() - dismantlingPlateAppDTO.getDismantlingPlateCount() < 0) {
                throw new BusinessException("库存数量不能小于零");
            }
            if (shipmentList != null) {
                shipmentList.setQuantityCount(inventoryManage.getQuantityCount() - dismantlingPlateAppDTO.getDismantlingPlateCount());
            }
            inventoryManage.setQuantityCount(inventoryManage.getQuantityCount() - dismantlingPlateAppDTO.getDismantlingPlateCount());
            inventoryManageNew.setQuantityCount(dismantlingPlateAppDTO.getDismantlingPlateCount());
            inventoryManageNew.setBoxCount((int) Math.ceil((double) inventoryManageNew.getQuantityCount() / inventoryManage.getBoxScale()));
            if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                if (inventoryManage.getPerUnitVolume() == 1) {
                    inventoryManageNew.setVolume(inventoryManage.getVolumeScale());
                } else if (inventoryManage.getPerUnitVolume() == 2) {
                    inventoryManageNew.setVolume(new BigDecimal(inventoryManageNew.getBoxCount()).multiply(inventoryManage.getVolumeScale()));
                } else if (inventoryManage.getPerUnitVolume() == 3) {
                    inventoryManageNew.setVolume(new BigDecimal(inventoryManageNew.getQuantityCount()).multiply(inventoryManage.getVolumeScale()));
                } else {
                    throw new BusinessException("单位重量单位错误");
                }
            }
            if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                if (inventoryManage.getPerUnitWeight() == 1) {
                    inventoryManageNew.setGrossWeight(inventoryManage.getWeightScale());
                } else if (inventoryManage.getPerUnitWeight() == 2) {
                    inventoryManageNew.setGrossWeight(new BigDecimal(inventoryManageNew.getBoxCount()).multiply(inventoryManage.getWeightScale()));
                } else if (inventoryManage.getPerUnitWeight() == 3) {
                    inventoryManageNew.setGrossWeight(new BigDecimal(inventoryManageNew.getQuantityCount()).multiply(inventoryManage.getWeightScale()));
                } else {
                    throw new BusinessException("单位重量单位错误");
                }
            }
            inventoryManage.setBoxCount(inventoryManage.getBoxCount() - inventoryManageNew.getBoxCount());
            inventoryManage.setGrossWeight(inventoryManage.getGrossWeight().subtract(inventoryManageNew.getGrossWeight()));
            inventoryManage.setVolume(inventoryManage.getVolume().subtract(inventoryManageNew.getVolume()));
        } else if (inventoryManage.getUnitType() == 2) {
            if (inventoryManage.getBoxCount() - dismantlingPlateAppDTO.getDismantlingPlateCount() < 0) {
                throw new BusinessException("库存数量不能小于零");
            }
            if (shipmentList != null) {
                shipmentList.setBoxCount(inventoryManage.getBoxCount() - dismantlingPlateAppDTO.getDismantlingPlateCount());
            }
            inventoryManage.setBoxCount(inventoryManage.getBoxCount() - dismantlingPlateAppDTO.getDismantlingPlateCount());
            inventoryManageNew.setBoxCount(dismantlingPlateAppDTO.getDismantlingPlateCount());
            inventoryManageNew.setQuantityCount(inventoryManageNew.getBoxCount() * inventoryManage.getQuantityScale());

            if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                if (inventoryManage.getPerUnitVolume() == 1) {
                    inventoryManageNew.setVolume(inventoryManage.getVolumeScale());
                } else if (inventoryManage.getPerUnitVolume() == 2) {
                    inventoryManageNew.setVolume(new BigDecimal(inventoryManageNew.getBoxCount()).multiply(inventoryManage.getVolumeScale()));
                } else if (inventoryManage.getPerUnitVolume() == 3) {
                    inventoryManageNew.setVolume(new BigDecimal(inventoryManageNew.getQuantityCount()).multiply(inventoryManage.getVolumeScale()));
                } else {
                    throw new BusinessException("单位重量单位错误");
                }
            }
            if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                if (inventoryManage.getPerUnitWeight() == 1) {
                    inventoryManageNew.setGrossWeight(inventoryManage.getWeightScale());
                } else if (inventoryManage.getPerUnitWeight() == 2) {
                    inventoryManageNew.setGrossWeight(new BigDecimal(inventoryManageNew.getBoxCount()).multiply(inventoryManage.getWeightScale()));
                } else if (inventoryManage.getPerUnitWeight() == 3) {
                    inventoryManageNew.setGrossWeight(new BigDecimal(inventoryManageNew.getQuantityCount()).multiply(inventoryManage.getWeightScale()));
                } else {
                    throw new BusinessException("单位重量单位错误");
                }
            }

            inventoryManage.setQuantityCount(inventoryManage.getQuantityCount() - inventoryManageNew.getQuantityCount());
            inventoryManage.setGrossWeight(inventoryManage.getGrossWeight().subtract(inventoryManageNew.getGrossWeight()));
            inventoryManage.setVolume(inventoryManage.getVolume().subtract(inventoryManageNew.getVolume()));


        } else if (inventoryManage.getUnitType() == 5) {
            if (inventoryManage.getVolume().subtract(new BigDecimal(dismantlingPlateAppDTO.getDismantlingPlateCount())).compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("库存数量不能小于零");
            }
            if (shipmentList != null) {
                shipmentList.setVolume(inventoryManage.getVolume().subtract(new BigDecimal(dismantlingPlateAppDTO.getDismantlingPlateCount())));
            }
            inventoryManage.setVolume(inventoryManage.getVolume().subtract(new BigDecimal(dismantlingPlateAppDTO.getDismantlingPlateCount())));
            inventoryManageNew.setVolume(new BigDecimal(dismantlingPlateAppDTO.getDismantlingPlateCount()));


            if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                if (inventoryManage.getPerUnitVolume() == 2) {
                    inventoryManageNew.setBoxCount(inventoryManageNew.getVolume().divide(inventoryManage.getVolumeScale(), RoundingMode.UP).intValue());
                    inventoryManageNew.setQuantityCount(inventoryManageNew.getBoxCount() * inventoryManage.getQuantityScale());
                } else if (inventoryManage.getPerUnitVolume() == 3) {
                    inventoryManageNew.setQuantityCount(inventoryManageNew.getVolume().divide(inventoryManage.getVolumeScale(), RoundingMode.UP).intValue());
                    inventoryManageNew.setBoxCount((int) Math.ceil((double)inventoryManageNew.getQuantityCount() / inventoryManage.getBoxScale()));
                } else {
                    throw new BusinessException("单位重量单位错误");
                }
            }

            if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                if (inventoryManage.getPerUnitWeight() == 1) {
                    inventoryManageNew.setGrossWeight(inventoryManage.getWeightScale());
                } else if (inventoryManage.getPerUnitWeight() == 2) {
                    inventoryManageNew.setGrossWeight(new BigDecimal(inventoryManageNew.getBoxCount()).multiply(inventoryManage.getWeightScale()));
                } else if (inventoryManage.getPerUnitWeight() == 3) {
                    inventoryManageNew.setGrossWeight(new BigDecimal(inventoryManageNew.getQuantityCount()).multiply(inventoryManage.getWeightScale()));
                } else {
                    throw new BusinessException("单位重量单位错误");
                }
            }

            inventoryManage.setQuantityCount(inventoryManage.getQuantityCount() - inventoryManageNew.getQuantityCount());
            inventoryManage.setGrossWeight(inventoryManage.getGrossWeight().subtract(inventoryManageNew.getGrossWeight()));
            inventoryManage.setBoxCount(inventoryManage.getBoxCount() - inventoryManageNew.getBoxCount());


        } else if (inventoryManage.getUnitType() == 4) {
            if (inventoryManage.getGrossWeight().subtract(new BigDecimal(dismantlingPlateAppDTO.getDismantlingPlateCount())).compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("库存数量不能小于零");
            }
            if (shipmentList != null) {
                shipmentList.setGrossWeight(inventoryManage.getGrossWeight().subtract(new BigDecimal(dismantlingPlateAppDTO.getDismantlingPlateCount())));
            }
            inventoryManage.setGrossWeight(inventoryManage.getGrossWeight().subtract(new BigDecimal(dismantlingPlateAppDTO.getDismantlingPlateCount())));
            inventoryManageNew.setGrossWeight(new BigDecimal(dismantlingPlateAppDTO.getDismantlingPlateCount()));

            if (inventoryManage.getWeightScale() != null && inventoryManage.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitWeight() != null && inventoryManage.getPerUnitWeight() != 0) {
                if (inventoryManage.getPerUnitWeight() == 2) {
                    inventoryManageNew.setBoxCount(inventoryManageNew.getGrossWeight().divide(inventoryManage.getWeightScale(), RoundingMode.UP).intValue());
                    inventoryManageNew.setQuantityCount(inventoryManageNew.getBoxCount() * inventoryManage.getQuantityScale());
                } else if (inventoryManage.getPerUnitWeight() == 3) {
                    inventoryManageNew.setQuantityCount(inventoryManageNew.getGrossWeight().divide(inventoryManage.getWeightScale(), RoundingMode.UP).intValue());
                    inventoryManageNew.setBoxCount((int) Math.ceil((double)inventoryManageNew.getQuantityCount() / inventoryManage.getBoxScale()));
                } else {
                    throw new BusinessException("单位重量单位错误");
                }
            }
            if (inventoryManage.getVolumeScale() != null && inventoryManage.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && inventoryManage.getPerUnitVolume() != null && inventoryManage.getPerUnitVolume() != 0) {
                if (inventoryManage.getPerUnitVolume() == 1) {
                    inventoryManageNew.setVolume(inventoryManage.getVolumeScale());
                } else if (inventoryManage.getPerUnitVolume() == 2) {
                    inventoryManageNew.setVolume(new BigDecimal(inventoryManageNew.getBoxCount()).multiply(inventoryManage.getVolumeScale()));
                } else if (inventoryManage.getPerUnitVolume() == 3) {
                    inventoryManageNew.setVolume(new BigDecimal(inventoryManageNew.getQuantityCount()).multiply(inventoryManage.getVolumeScale()));
                } else {
                    throw new BusinessException("单位重量单位错误");
                }
            }

            inventoryManage.setQuantityCount(inventoryManage.getQuantityCount() - inventoryManageNew.getQuantityCount());
            inventoryManage.setBoxCount(inventoryManage.getBoxCount() - inventoryManageNew.getBoxCount());
            inventoryManage.setVolume(inventoryManage.getVolume().subtract(inventoryManageNew.getVolume()));

        }
        inventoryManage.setId(inventoryManage.getId());
        inventoryManageMapper.insert(inventoryManageNew);
        inventoryManageMapper.updateById(inventoryManage);
        if (shipmentList != null) {
            shipmentListMapper.updateById(shipmentList);
        }
        Map<String, String> map = new HashMap<>(0);
        map.put("boardNumber", inventoryManageNew.getBoardNumber());
        map.put("singleNumber", inventoryManageNew.getSingleNumber());
        map.put("clientName", inventoryManageNew.getClientName());
        return map;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String mergePlate(MergePlateDTO mergePlateDTO) {
        if (mergePlateDTO.getId() == null && inventoryManageMapper.getBoardNumberExist(mergePlateDTO.getBoardNumber()) > 0) {
            throw new BusinessException("板号" + mergePlateDTO.getBoardNumber() + "已被使用，请重新生成");
        }
        if (mergePlateDTO.getId() == null) {
            Integer firstId = mergePlateDTO.getIds().get(0);
            InventoryManage inventoryManage = super.selectById(firstId);
            inventoryManage.setWarehouseStorageNumber(mergePlateDTO.getWarehouseStorageNumber());
            inventoryManage.setBoardNumber(mergePlateDTO.getBoardNumber());
            inventoryManage.setAbnormal(1);
            InventoryManage oldInventoryManage;
            for (Integer id : mergePlateDTO.getIds()) {
                if (id.equals(firstId)) {
                    continue;
                }
                oldInventoryManage = super.selectById(id);
                if (!dateFormat.format(oldInventoryManage.getReceiptTime()).equals(dateFormat.format(inventoryManage.getReceiptTime())) || !oldInventoryManage.getMaterialNumber().equals(inventoryManage.getMaterialNumber()) || !oldInventoryManage.getBatchNumber().equals(inventoryManage.getBatchNumber()) || !oldInventoryManage.getClientId().equals(inventoryManage.getClientId()) || !oldInventoryManage.getSingleNumber().equals(inventoryManage.getSingleNumber())) {
                    throw new BusinessException(oldInventoryManage.getBoardNumber() + "不符合合板规则，请确认");
                }
                inventoryManage.setVolume(inventoryManage.getVolume().add(oldInventoryManage.getVolume()));
                inventoryManage.setGrossWeight(inventoryManage.getGrossWeight().add(oldInventoryManage.getGrossWeight()));
                inventoryManage.setNetWeight(inventoryManage.getNetWeight().add(oldInventoryManage.getNetWeight()));
                inventoryManage.setBoxCount(inventoryManage.getBoxCount() + oldInventoryManage.getBoxCount());
                inventoryManage.setQuantityCount(inventoryManage.getQuantityCount() + oldInventoryManage.getQuantityCount());
                super.deleteById(id);
            }
            super.updateById(inventoryManage);
        } else {
            InventoryManage inventoryManage = super.selectById(mergePlateDTO.getId());
            inventoryManage.setAbnormal(1);
            InventoryManage oldInventoryManage;
            for (Integer id : mergePlateDTO.getIds()) {
                if (id.equals(mergePlateDTO.getId())) {
                    continue;
                }
                oldInventoryManage = super.selectById(id);
                if (!dateFormat.format(oldInventoryManage.getReceiptTime()).equals(dateFormat.format(inventoryManage.getReceiptTime())) || !oldInventoryManage.getMaterialNumber().equals(inventoryManage.getMaterialNumber()) || !oldInventoryManage.getBatchNumber().equals(inventoryManage.getBatchNumber()) || !oldInventoryManage.getClientId().equals(inventoryManage.getClientId()) || !oldInventoryManage.getSingleNumber().equals(inventoryManage.getSingleNumber())) {
                    throw new BusinessException(oldInventoryManage.getBoardNumber() + "不符合合板规则，请确认");
                }
                inventoryManage.setVolume(inventoryManage.getVolume().add(oldInventoryManage.getVolume()));
                inventoryManage.setGrossWeight(inventoryManage.getGrossWeight().add(oldInventoryManage.getGrossWeight()));
                inventoryManage.setBoxCount(inventoryManage.getBoxCount() + oldInventoryManage.getBoxCount());
                inventoryManage.setNetWeight(inventoryManage.getNetWeight().add(oldInventoryManage.getNetWeight()));
                inventoryManage.setQuantityCount(inventoryManage.getQuantityCount() + oldInventoryManage.getQuantityCount());
                super.deleteById(id);
            }
            super.updateById(inventoryManage);
        }
        return null;
    }

    @Override
    public Map<String, String> mergePlateApp(MergePlateAppDTO mergePlateAppDTO) {
        if (mergePlateAppDTO.getBoardNumberOne().equals(mergePlateAppDTO.getBoardNumberTwo())) {
            throw new BusinessException("板号不能相同，操作失败");
        }
        InventoryManage inventoryManageOne = inventoryManageMapper.getInventoryManageByWarehouseIdAndBoardNumber(mergePlateAppDTO.getWarehouseId(), mergePlateAppDTO.getBoardNumberOne());
        if (inventoryManageOne == null) {
            throw new BusinessException("板号1不存在，操作失败");
        }
        InventoryManage inventoryManageTwo = inventoryManageMapper.getInventoryManageByWarehouseIdAndBoardNumber(mergePlateAppDTO.getWarehouseId(), mergePlateAppDTO.getBoardNumberTwo());
        if (inventoryManageTwo == null) {
            throw new BusinessException("板号2不存在，操作失败");
        }
        String sNum;
        if (mergePlateAppDTO.getBoardNumberMerge() != null && !"".equals(mergePlateAppDTO.getBoardNumberMerge())) {
//            InventoryManage inventoryManageMerge = inventoryManageMapper.getInventoryManageByWarehouseIdAndBoardNumber(mergePlateAppDTO.getWarehouseId(), mergePlateAppDTO.getBoardNumberMerge());
//            if (inventoryManageMerge == null) {
//                throw new BusinessException("合并板号不存在，操作失败");
//            }
            InventoryManage inventoryManageMerge;
            InventoryManage oldInventoryManage;
            if (mergePlateAppDTO.getBoardNumberMerge().equals(inventoryManageOne.getBoardNumber())) {
                oldInventoryManage = inventoryManageOne;
                inventoryManageMerge = inventoryManageTwo;
            } else {
                oldInventoryManage = inventoryManageTwo;
                inventoryManageMerge = inventoryManageOne;
            }
            sNum = oldInventoryManage.getBoardNumber();
            oldInventoryManage.setAbnormal(1);
            if (!dateFormat.format(oldInventoryManage.getReceiptTime()).equals(dateFormat.format(inventoryManageMerge.getReceiptTime())) || !oldInventoryManage.getMaterialNumber().equals(inventoryManageMerge.getMaterialNumber()) || !oldInventoryManage.getBatchNumber().equals(inventoryManageMerge.getBatchNumber()) || !oldInventoryManage.getClientId().equals(inventoryManageMerge.getClientId()) || !oldInventoryManage.getSingleNumber().equals(inventoryManageMerge.getSingleNumber())) {
                throw new BusinessException(inventoryManageMerge.getBoardNumber() + "不符合合板规则，请确认");
            }
            oldInventoryManage.setVolume(oldInventoryManage.getVolume().add(inventoryManageMerge.getVolume()));
            oldInventoryManage.setGrossWeight(oldInventoryManage.getGrossWeight().add(inventoryManageMerge.getGrossWeight()));
            oldInventoryManage.setBoxCount(oldInventoryManage.getBoxCount() + inventoryManageMerge.getBoxCount());
            oldInventoryManage.setNetWeight(oldInventoryManage.getNetWeight().add(inventoryManageMerge.getNetWeight()));
            oldInventoryManage.setQuantityCount(oldInventoryManage.getQuantityCount() + inventoryManageMerge.getQuantityCount());
            super.deleteById(inventoryManageMerge.getId());
            super.updateById(oldInventoryManage);
        } else {
            int count = inventoryManageMapper.countBoardNumber("P" + DateUtil.dayNow());
            String str = String.format("%04d", count);
            sNum = "P" + DateUtil.dayNow() + str;
            inventoryManageOne.setBoardNumber(sNum);
            inventoryManageOne.setAbnormal(1);
            if (!dateFormat.format(inventoryManageTwo.getReceiptTime()).equals(dateFormat.format(inventoryManageOne.getReceiptTime())) || !inventoryManageTwo.getMaterialNumber().equals(inventoryManageOne.getMaterialNumber()) || !inventoryManageTwo.getBatchNumber().equals(inventoryManageOne.getBatchNumber()) || !inventoryManageTwo.getClientId().equals(inventoryManageOne.getClientId()) || !inventoryManageTwo.getSingleNumber().equals(inventoryManageOne.getSingleNumber())) {
                throw new BusinessException(inventoryManageTwo.getBoardNumber() + "不符合合板规则，请确认");
            }
            inventoryManageOne.setVolume(inventoryManageOne.getVolume().add(inventoryManageTwo.getVolume()));
            inventoryManageOne.setGrossWeight(inventoryManageOne.getGrossWeight().add(inventoryManageTwo.getGrossWeight()));
            inventoryManageOne.setNetWeight(inventoryManageOne.getNetWeight().add(inventoryManageTwo.getNetWeight()));
            inventoryManageOne.setBoxCount(inventoryManageOne.getBoxCount() + inventoryManageTwo.getBoxCount());
            inventoryManageOne.setQuantityCount(inventoryManageOne.getQuantityCount() + inventoryManageTwo.getQuantityCount());
            super.deleteById(inventoryManageTwo.getId());
            super.updateById(inventoryManageOne);
        }
        Map<String, String> map = new HashMap<>(0);
        map.put("boardNumber", sNum);
        map.put("singleNumber", inventoryManageOne.getSingleNumber());
        map.put("clientName", inventoryManageOne.getClientName());
        return map;
    }

    @Override
    public InventoryManage getInventoryManageByWarehouseIdAndBoardNumber(DismantlingPlateAppDTO dismantlingPlateAppDTO) {
        InventoryManage inventoryManage = inventoryManageMapper.getInventoryManageByWarehouseIdAndBoardNumber(dismantlingPlateAppDTO.getWarehouseId(), dismantlingPlateAppDTO.getBoardNumber());
        if (inventoryManage == null) {
            throw new BusinessException("该板号不存在当前仓库");
        }
        return inventoryManage;
    }
}
