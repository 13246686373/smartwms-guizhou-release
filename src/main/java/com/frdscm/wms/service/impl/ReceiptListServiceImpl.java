package com.frdscm.wms.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.enums.ResponseEnum;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.*;
import com.frdscm.wms.entity.dto.ReceiptItemListDTO;
import com.frdscm.wms.entity.dto.ReceiptListAppDTO;
import com.frdscm.wms.entity.dto.ReceiptListDTO;
import com.frdscm.wms.entity.dto.UpperShelfDTO;
import com.frdscm.wms.entity.vo.MaterialListAppVO;
import com.frdscm.wms.mapper.*;
import com.frdscm.wms.service.IReceiptListService;
import com.frdscm.wms.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收货列表 服务实现类
 *
 * @author dizhang
 * @since 2018-07-06
 */
@Service
public class ReceiptListServiceImpl extends ServiceImpl<ReceiptListMapper, ReceiptList> implements IReceiptListService {

    private Logger LOGGER = LoggerFactory.getLogger(ReceiptListServiceImpl.class);

    private final ReceiptListMapper receiptListMapper;

    private final InventoryManageMapper inventoryManageMapper;

    private final ReceiptManageMapper receiptManageMapper;

    private final ReceiptCargoDetailsMapper receiptCargoDetailsMapper;

    private final ReceiptItemListMapper receiptItemListMapper;

    private final ReceiptDemandMapper receiptDemandMapper;

    private final InventoryListMapper inventoryListMapper;

    private final InventoryScanListMapper inventoryScanListMapper;

    private final ReceiptScanListMapper receiptScanListMapper;

    private final ShipmentDemandMapper shipmentDemandMapper;

    private final WarehouseStorageMapper warehouseStorageMapper;

    @Autowired
    public ReceiptListServiceImpl(ReceiptListMapper receiptListMapper, WarehouseStorageMapper warehouseStorageMapper, ShipmentDemandMapper shipmentDemandMapper, InventoryScanListMapper inventoryScanListMapper, ReceiptScanListMapper receiptScanListMapper, InventoryListMapper inventoryListMapper, ReceiptDemandMapper receiptDemandMapper, ReceiptItemListMapper receiptItemListMapper, InventoryManageMapper inventoryManageMapper, ReceiptManageMapper receiptManageMapper, ReceiptCargoDetailsMapper receiptCargoDetailsMapper) {
        this.receiptListMapper = receiptListMapper;
        this.inventoryManageMapper = inventoryManageMapper;
        this.receiptManageMapper = receiptManageMapper;
        this.receiptCargoDetailsMapper = receiptCargoDetailsMapper;
        this.receiptItemListMapper = receiptItemListMapper;
        this.receiptDemandMapper = receiptDemandMapper;
        this.inventoryListMapper = inventoryListMapper;
        this.receiptScanListMapper = receiptScanListMapper;
        this.inventoryScanListMapper = inventoryScanListMapper;
        this.shipmentDemandMapper = shipmentDemandMapper;
        this.warehouseStorageMapper = warehouseStorageMapper;
    }

    @Override
    public ReceiptList updateApp(ReceiptListAppDTO receiptListDTO) {
        ReceiptCargoDetails receiptCargoDetails = receiptCargoDetailsMapper.selectById(receiptListDTO.getId());
        //箱数
        receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getActualBoxCount() == null ? receiptListDTO.getActualBoxCount() : receiptCargoDetails.getActualBoxCount() + receiptListDTO.getActualBoxCount());
        //数量
        receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getActualQuantityCount() == null ? receiptListDTO.getActualQuantityCount() : receiptCargoDetails.getActualQuantityCount() + receiptListDTO.getActualQuantityCount());
        //毛重
        receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getActualGrossWeight() == null ? receiptListDTO.getActualGrossWeight() : receiptCargoDetails.getActualGrossWeight().add(receiptListDTO.getActualGrossWeight()));
        //净重
        receiptCargoDetails.setActualNetWeight(receiptCargoDetails.getActualNetWeight() == null ? receiptListDTO.getActualNetWeight() : receiptCargoDetails.getActualNetWeight().add(receiptListDTO.getActualNetWeight()));
        //体积
        receiptCargoDetails.setActualVolume(receiptCargoDetails.getActualVolume() == null ? receiptListDTO.getActualVolume() : receiptCargoDetails.getActualVolume().add(receiptListDTO.getActualVolume()));
        //板数
        receiptCargoDetails.setActualBoardCount(receiptCargoDetails.getActualBoardCount() == null ? receiptListDTO.getActualBoardCount() : receiptCargoDetails.getActualBoardCount() + receiptListDTO.getActualBoardCount());

        ReceiptManage receiptManage = new ReceiptManage();
        receiptManage.setReceiptDemandId(receiptCargoDetails.getReceiptDemandId());
        receiptManage = receiptManageMapper.selectOne(receiptManage);


        ReceiptList receiptList = BeanUtils.copy(receiptCargoDetails, ReceiptList.class);
        int count = inventoryManageMapper.countBoardNumber("P" + DateUtil.dayNow());
        count++;
        String str = String.format("%04d", count);
        //生成版号 格式 P+yyMMdd+0001
        String sNum = "P" + DateUtil.dayNow() + str;
        receiptList.setBoardNumber(sNum);
//        receiptList.setReceiptManageId(receiptManage.getId());
//        receiptList.setMaterialSpecifications(receiptCargoDetails.getMaterialSpecifications());
        receiptList.setStatus(1);
//        receiptList.setQuantityCount(receiptListDTO.getActualQuantityCount());
//        receiptList.setBoxCount(receiptListDTO.getActualBoxCount());
//        receiptList.setGrossWeight(receiptListDTO.getActualGrossWeight());
//        receiptList.setNetWeight(receiptListDTO.getActualNetWeight());
//        receiptList.setVolume(receiptListDTO.getActualVolume());
//        receiptList.setBoardCount(receiptListDTO.getActualBoardCount());
        receiptListMapper.insert(receiptList);
        receiptCargoDetailsMapper.updateById(receiptCargoDetails);
        if (receiptManage.getStatus() == 1) {
            receiptManage.setStatus(3);
            receiptManageMapper.updateById(receiptManage);
        }
        return receiptList;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ReceiptListDTO save(ReceiptListDTO receiptListDTO) {
        if (inventoryManageMapper.getBoardNumberExist(receiptListDTO.getBoardNumber()) > 0) {
            throw new BusinessException("板号" + receiptListDTO.getBoardNumber() + "已被使用，请重新生成");
        }
        ReceiptList receiptList = BeanUtils.copy(receiptListDTO, ReceiptList.class);
        if (receiptList.getId() == null) {
            receiptListMapper.insert(receiptList);
        }
        Integer receiptDemandId = receiptManageMapper.selectById(receiptListDTO.getReceiptManageId()).getReceiptDemandId();
        ReceiptItemList receiptItemList;
//        for (ReceiptItemListDTO receiptItemListDTO : receiptListDTO.getReceiptItemListDTOList()) {
//            updateActualDetail(receiptDemandId, receiptItemListDTO);
//            receiptItemList = BeanUtils.copy(receiptItemListDTO, ReceiptItemList.class);
//            receiptItemList.setReceiptListId(receiptList.getId());
//            receiptItemListMapper.insert(receiptItemList);
//            receiptItemListDTO.setId(receiptItemList.getId());
//        }
        receiptListDTO.setId(receiptList.getId());
        return receiptListDTO;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Page<ReceiptList> batchBoardNumber(Integer receiptManageId) {
        ReceiptManage receiptManage = receiptManageMapper.selectById(receiptManageId);
        List<ReceiptCargoDetails> receiptCargoDetailsList = receiptCargoDetailsMapper.getReceiptCargoDetailsByReceiptDemandId(receiptManage.getReceiptDemandId());
        ReceiptList receiptList;
        for (ReceiptCargoDetails receiptCargoDetails : receiptCargoDetailsList) {
            int count;
            String str;
            String boardNumber;
            for (int i = 0; i < receiptCargoDetails.getBoardCount(); i++) {
                receiptList = BeanUtils.copy(receiptCargoDetails, ReceiptList.class);
                count = inventoryManageMapper.countBoardNumber("P" + DateUtil.dayNow());
                str = String.format("%04d", count);
                boardNumber = "P" + DateUtil.dayNow() + str;
                receiptList.setBoardNumber(boardNumber);
                receiptList.setQuantityCount(receiptList.getBoxScale() * receiptList.getQuantityScale());
                receiptList.setBoxCount(receiptList.getBoxScale());
                if (receiptList.getWeightScale() != null && receiptList.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && receiptList.getPerUnitWeight() != null && receiptList.getPerUnitWeight() != 0) {
                    if (receiptList.getPerUnitWeight() == 1) {
                        receiptList.setGrossWeight(receiptList.getWeightScale());
                    } else if (receiptList.getPerUnitWeight() == 2) {
                        receiptList.setGrossWeight(new BigDecimal(receiptList.getBoxCount()).multiply(receiptList.getWeightScale()));
                    } else if (receiptList.getPerUnitWeight() == 3) {
                        receiptList.setGrossWeight(new BigDecimal(receiptList.getQuantityCount()).multiply(receiptList.getWeightScale()));
                    } else {
                        throw new BusinessException("单位重量单位错误");
                    }
                }
                if (receiptList.getVolumeScale() != null && receiptList.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && receiptList.getPerUnitVolume() != null && receiptList.getPerUnitVolume() != 0) {
                    if (receiptList.getPerUnitVolume() == 1) {
                        receiptList.setVolume(receiptList.getVolumeScale());
                    } else if (receiptList.getPerUnitVolume() == 2) {
                        receiptList.setVolume(new BigDecimal(receiptList.getBoxCount()).multiply(receiptList.getVolumeScale()));
                    } else if (receiptList.getPerUnitVolume() == 3) {
                        receiptList.setVolume(new BigDecimal(receiptList.getQuantityCount()).multiply(receiptList.getVolumeScale()));
                    } else {
                        throw new BusinessException("单位重量单位错误");
                    }
                }
                receiptList.setReceiptManageId(receiptManageId);
                receiptList.setBoardCount(1);
                receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getActualQuantityCount() + receiptList.getQuantityCount());
                receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getActualBoxCount() + receiptList.getBoxCount());
                receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getActualGrossWeight().add(receiptList.getGrossWeight()));
                receiptCargoDetails.setActualVolume(receiptCargoDetails.getActualVolume().add(receiptList.getVolume()));
                receiptCargoDetails.setActualBoardCount(receiptCargoDetails.getActualBoardCount() + 1);
                if (i == receiptCargoDetails.getBoardCount() - 1) {
                    switch (receiptCargoDetails.getUnitType()) {
                        case 1:
                            if (receiptCargoDetails.getActualQuantityCount() > receiptCargoDetails.getQuantityCount()) {
                                receiptList.setQuantityCount(receiptCargoDetails.getQuantityCount() - (receiptCargoDetails.getActualQuantityCount() - receiptList.getQuantityCount()));
                                receiptList.setBoxCount((int) Math.ceil((double) receiptList.getQuantityCount() / receiptCargoDetails.getQuantityScale()));
                                if (receiptList.getVolumeScale() != null && receiptList.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && receiptList.getPerUnitVolume() != null && receiptList.getPerUnitVolume() != 0) {
                                    if (receiptList.getPerUnitVolume() == 1) {
                                        receiptList.setVolume(receiptList.getVolumeScale());
                                    } else if (receiptList.getPerUnitVolume() == 2) {
                                        receiptList.setVolume(new BigDecimal(receiptList.getBoxCount()).multiply(receiptList.getVolumeScale()));
                                    } else if (receiptList.getPerUnitVolume() == 3) {
                                        receiptList.setVolume(new BigDecimal(receiptList.getQuantityCount()).multiply(receiptList.getVolumeScale()));
                                    } else {
                                        throw new BusinessException("单位重量单位错误");
                                    }
                                }
                                if (receiptList.getWeightScale() != null && receiptList.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && receiptList.getPerUnitWeight() != null && receiptList.getPerUnitWeight() != 0) {
                                    if (receiptList.getPerUnitWeight() == 1) {
                                        receiptList.setGrossWeight(receiptList.getWeightScale());
                                    } else if (receiptList.getPerUnitWeight() == 2) {
                                        receiptList.setGrossWeight(new BigDecimal(receiptList.getBoxCount()).multiply(receiptList.getWeightScale()));
                                    } else if (receiptList.getPerUnitWeight() == 3) {
                                        receiptList.setGrossWeight(new BigDecimal(receiptList.getQuantityCount()).multiply(receiptList.getWeightScale()));
                                    } else {
                                        throw new BusinessException("单位重量单位错误");
                                    }
                                }
                                receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getQuantityCount());
                                receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getBoxCount());
                                receiptCargoDetails.setActualVolume(receiptCargoDetails.getVolume());
                                receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getGrossWeight());
                            }
                            receiptList.setShouldPickCount(new BigDecimal(receiptCargoDetails.getQuantityCount()));
                            break;
                        case 2:
                            if (receiptCargoDetails.getActualBoxCount() > receiptCargoDetails.getBoxCount()) {
                                receiptList.setBoxCount(receiptCargoDetails.getBoxCount() - receiptCargoDetails.getActualBoxCount() + receiptList.getBoxCount());
                                receiptList.setQuantityCount(receiptList.getBoxCount() * receiptCargoDetails.getQuantityScale());
                                if (receiptList.getVolumeScale() != null && receiptList.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && receiptList.getPerUnitVolume() != null && receiptList.getPerUnitVolume() != 0) {
                                    if (receiptList.getPerUnitVolume() == 1) {
                                        receiptList.setVolume(receiptList.getVolumeScale());
                                    } else if (receiptList.getPerUnitVolume() == 2) {
                                        receiptList.setVolume(new BigDecimal(receiptList.getBoxCount()).multiply(receiptList.getVolumeScale()));
                                    } else if (receiptList.getPerUnitVolume() == 3) {
                                        receiptList.setVolume(new BigDecimal(receiptList.getQuantityCount()).multiply(receiptList.getVolumeScale()));
                                    } else {
                                        throw new BusinessException("单位重量单位错误");
                                    }
                                }
                                if (receiptList.getWeightScale() != null && receiptList.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && receiptList.getPerUnitWeight() != null && receiptList.getPerUnitWeight() != 0) {
                                    if (receiptList.getPerUnitWeight() == 1) {
                                        receiptList.setGrossWeight(receiptList.getWeightScale());
                                    } else if (receiptList.getPerUnitWeight() == 2) {
                                        receiptList.setGrossWeight(new BigDecimal(receiptList.getBoxCount()).multiply(receiptList.getWeightScale()));
                                    } else if (receiptList.getPerUnitWeight() == 3) {
                                        receiptList.setGrossWeight(new BigDecimal(receiptList.getQuantityCount()).multiply(receiptList.getWeightScale()));
                                    } else {
                                        throw new BusinessException("单位重量单位错误");
                                    }
                                }
                                receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getQuantityCount());
                                receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getBoxCount());
                                receiptCargoDetails.setActualVolume(receiptCargoDetails.getVolume());
                                receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getGrossWeight());
                            }
                            receiptList.setShouldPickCount(new BigDecimal(receiptCargoDetails.getBoxCount()));
                            break;
                        case 3:
                            if (receiptCargoDetails.getActualBoardCount() > receiptCargoDetails.getBoardCount()) {
                                receiptCargoDetails.setActualBoardCount(receiptCargoDetails.getBoardCount());
                                receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getQuantityCount());
                                receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getBoxCount());
                                receiptCargoDetails.setActualVolume(receiptCargoDetails.getVolume());
                                receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getGrossWeight());
                            }
                            receiptList.setShouldPickCount(new BigDecimal(receiptCargoDetails.getBoardCount()));
                            break;
                        case 4:
                            if (receiptCargoDetails.getActualGrossWeight().compareTo(receiptCargoDetails.getGrossWeight()) > 0) {
                                receiptList.setGrossWeight(receiptCargoDetails.getGrossWeight().subtract(receiptCargoDetails.getActualGrossWeight()).add(receiptList.getGrossWeight()));
                                if (receiptList.getWeightScale() != null && receiptList.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && receiptList.getPerUnitWeight() != null && receiptList.getPerUnitWeight() != 0) {
                                    if (receiptList.getPerUnitWeight() == 2) {
                                        receiptList.setBoxCount(receiptList.getGrossWeight().divide(receiptCargoDetails.getWeightScale(), RoundingMode.UP).intValue());
                                        receiptList.setQuantityCount(receiptList.getBoxCount() * receiptCargoDetails.getQuantityScale());
                                    } else if (receiptList.getPerUnitWeight() == 3) {
                                        receiptList.setQuantityCount(receiptList.getGrossWeight().divide(receiptCargoDetails.getWeightScale(), RoundingMode.UP).intValue());
                                        receiptList.setBoxCount((int) Math.ceil((double) receiptList.getQuantityCount() / receiptCargoDetails.getQuantityScale()));
                                    } else {
                                        throw new BusinessException("单位重量单位错误");
                                    }
                                }
                                if (receiptList.getVolumeScale() != null && receiptList.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && receiptList.getPerUnitVolume() != null && receiptList.getPerUnitVolume() != 0) {
                                    if (receiptList.getPerUnitVolume() == 1) {
                                        receiptList.setVolume(receiptList.getVolumeScale());
                                    } else if (receiptList.getPerUnitVolume() == 2) {
                                        receiptList.setVolume(new BigDecimal(receiptList.getBoxCount()).multiply(receiptList.getVolumeScale()));
                                    } else if (receiptList.getPerUnitVolume() == 3) {
                                        receiptList.setVolume(new BigDecimal(receiptList.getQuantityCount()).multiply(receiptList.getVolumeScale()));
                                    } else {
                                        throw new BusinessException("单位重量单位错误");
                                    }
                                }
                                receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getQuantityCount());
                                receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getBoxCount());
                                receiptCargoDetails.setActualVolume(receiptCargoDetails.getVolume());
                                receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getGrossWeight());
                            }
                            receiptList.setShouldPickCount(receiptCargoDetails.getGrossWeight());
                            break;
                        case 5:
                            if (receiptCargoDetails.getActualVolume().compareTo(receiptCargoDetails.getVolume()) > 0) {
                                receiptList.setVolume(receiptCargoDetails.getVolume().subtract(receiptCargoDetails.getActualVolume()).add(receiptList.getVolume()));

                                if (receiptList.getVolumeScale() != null && receiptList.getVolumeScale().compareTo(BigDecimal.ZERO) != 0 && receiptList.getPerUnitVolume() != null && receiptList.getPerUnitVolume() != 0) {
                                    if (receiptList.getPerUnitVolume() == 2) {
                                        receiptList.setBoxCount(receiptList.getVolume().divide(receiptCargoDetails.getVolumeScale(), RoundingMode.UP).intValue());
                                        receiptList.setQuantityCount(receiptList.getBoxCount() * receiptCargoDetails.getQuantityScale());
                                    } else if (receiptList.getPerUnitVolume() == 3) {
                                        receiptList.setQuantityCount(receiptList.getVolume().divide(receiptCargoDetails.getVolumeScale(), RoundingMode.UP).intValue());
                                        receiptList.setBoxCount((int) Math.ceil((double) receiptList.getQuantityCount() / receiptCargoDetails.getQuantityScale()));
                                    } else {
                                        throw new BusinessException("单位重量单位错误");
                                    }
                                }

                                if (receiptList.getWeightScale() != null && receiptList.getWeightScale().compareTo(BigDecimal.ZERO) != 0 && receiptList.getPerUnitWeight() != null && receiptList.getPerUnitWeight() != 0) {
                                    if (receiptList.getPerUnitWeight() == 1) {
                                        receiptList.setGrossWeight(receiptList.getWeightScale());
                                    } else if (receiptList.getPerUnitWeight() == 2) {
                                        receiptList.setGrossWeight(new BigDecimal(receiptList.getBoxCount()).multiply(receiptList.getWeightScale()));
                                    } else if (receiptList.getPerUnitWeight() == 3) {
                                        receiptList.setGrossWeight(new BigDecimal(receiptList.getQuantityCount()).multiply(receiptList.getWeightScale()));
                                    } else {
                                        throw new BusinessException("单位重量单位错误");
                                    }
                                }
                                receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getQuantityCount());
                                receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getBoxCount());
                                receiptCargoDetails.setVolume(receiptCargoDetails.getVolume());
                                receiptCargoDetails.setGrossWeight(receiptCargoDetails.getGrossWeight());
                            }
                            receiptList.setShouldPickCount(receiptCargoDetails.getVolume());
                            break;
                        default:
                            throw new BusinessException("");
                    }
                }
                receiptListMapper.insert(receiptList);
            }
            receiptCargoDetailsMapper.updateById(receiptCargoDetails);
        }
//        receiptManageMapper.updateStatus(receiptManageId, 2);
        Page<ReceiptList> page = new Page<>(1, 99999);
        page.setRecords(receiptListMapper.getReceiptListByReceiptManageIdPage(page, receiptManageId));
        return page;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void edit(ReceiptListDTO receiptListDTO) {
        Integer receiptDemandId = receiptManageMapper.selectById(receiptListDTO.getReceiptManageId()).getReceiptDemandId();
        ReceiptCargoDetails receiptCargoDetails;
        ReceiptList oldReceiptList;
        oldReceiptList = super.selectById(receiptListDTO.getId());
        if (oldReceiptList.getWarehouseStorageNumber() != null && !"".equals(oldReceiptList.getWarehouseStorageNumber())) {
            throw new BusinessException("已上架的货物不能被修改");
        }
        ReceiptList receiptList = BeanUtils.copy(receiptListDTO, ReceiptList.class);
        receiptCargoDetails = receiptCargoDetailsMapper.getCargoDetailsByMaterialNumber(receiptDemandId, receiptList);
        if (receiptList.getBoxCount() != null) {
            receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getActualBoxCount() + receiptList.getBoxCount() - oldReceiptList.getBoxCount());
        }
        if (receiptList.getQuantityCount() != null) {
            receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getActualQuantityCount() + receiptList.getQuantityCount() - oldReceiptList.getQuantityCount());
        }
        if (receiptList.getGrossWeight() != null) {
            receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getActualGrossWeight().add(receiptList.getGrossWeight()).subtract(oldReceiptList.getGrossWeight()));
        }
        if (receiptList.getNetWeight() != null) {
            receiptCargoDetails.setActualNetWeight(receiptCargoDetails.getActualNetWeight().add(receiptList.getNetWeight()).subtract(oldReceiptList.getNetWeight()));
        }
        if (receiptList.getVolume() != null) {
            receiptCargoDetails.setActualVolume(receiptCargoDetails.getActualVolume().add(receiptList.getVolume()).subtract(oldReceiptList.getVolume()));
        }
        if (receiptCargoDetails.getUnitType() == 3) {
            receiptCargoDetails.setActualBoardCount(receiptCargoDetails.getActualBoardCount() + 1);
            if (receiptCargoDetails.getActualBoardCount() > receiptCargoDetails.getBoardCount()) {
                throw new BusinessException("该物料已经超过收板量了");
            }
        } else {
            if (receiptList.getBoardCount() != null) {
                receiptCargoDetails.setActualBoardCount(receiptCargoDetails.getActualBoardCount() + receiptList.getBoardCount() - oldReceiptList.getBoardCount());
            }
        }
        receiptCargoDetailsMapper.updateById(receiptCargoDetails);
        receiptListMapper.updateById(receiptList);


//        for (ReceiptItemListDTO receiptItemListDTO : receiptListDTO.getReceiptItemListDTOList()) {
//            if (super.selectById(receiptItemListDTO.getReceiptListId()).getWarehouseStorageNumber() != null) {
//                throw new BusinessException("已上架的货物不能被修改");
//            }
//            receiptItemList = BeanUtils.copy(receiptItemListDTO, ReceiptItemList.class);
//            if (receiptItemList.getId() != null) {
//                oldReceiptItemList = receiptItemListMapper.selectById(receiptItemList.getId());
//                receiptCargoDetails = receiptCargoDetailsMapper.getCargoDetailsByDemandIdAndMaterialNumber(receiptDemandId, receiptItemListDTO.getMaterialNumber());
//                if (receiptItemListDTO.getBoxCount() != null) {
//                    receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getActualBoxCount() + receiptItemListDTO.getBoxCount() - oldReceiptItemList.getBoxCount());
//                }
//                if (receiptItemListDTO.getQuantityCount() != null) {
//                    receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getActualQuantityCount() + receiptItemListDTO.getQuantityCount() - oldReceiptItemList.getQuantityCount());
//                }
//                if (receiptItemListDTO.getGrossWeight() != null) {
//                    receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getActualGrossWeight().add(receiptItemListDTO.getGrossWeight()).subtract(oldReceiptItemList.getGrossWeight()));
//                }
//                if (receiptItemListDTO.getNetWeight() != null) {
//                    receiptCargoDetails.setActualNetWeight(receiptCargoDetails.getActualNetWeight().add(receiptItemListDTO.getNetWeight()).subtract(oldReceiptItemList.getNetWeight()));
//                }
//                if (receiptItemListDTO.getVolume() != null) {
//                    receiptCargoDetails.setActualVolume(receiptCargoDetails.getActualVolume().add(receiptItemListDTO.getVolume()).subtract(oldReceiptItemList.getVolume()));
//                }
//                if (receiptItemListDTO.getBoardCount() != null) {
//                    receiptCargoDetails.setActualBoardCount(receiptCargoDetails.getActualBoardCount() + receiptItemListDTO.getBoardCount() - oldReceiptItemList.getBoardCount());
//                }
//                receiptCargoDetailsMapper.updateById(receiptCargoDetails);
//                receiptItemListMapper.updateById(receiptItemList);
//            } else {
//                updateActualDetail(receiptDemandId, receiptItemListDTO);
//                receiptItemListMapper.insert(receiptItemList);
//                receiptItemListDTO.setId(receiptItemList.getId());
//            }
//        }
    }

    private void updateActualDetail(Integer receiptDemandId, ReceiptItemListDTO receiptItemListDTO) {
        ReceiptCargoDetails receiptCargoDetails;
        receiptCargoDetails = receiptCargoDetailsMapper.getCargoDetailsByMaterialNumber(receiptDemandId, null);
        if (receiptItemListDTO.getBoxCount() != null) {
            receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getActualBoxCount() + receiptItemListDTO.getBoxCount());
        }
        if (receiptItemListDTO.getQuantityCount() != null) {
            receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getActualQuantityCount() + receiptItemListDTO.getQuantityCount());
        }
        if (receiptItemListDTO.getGrossWeight() != null) {
            receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getActualGrossWeight().add(receiptItemListDTO.getGrossWeight()));
        }
        if (receiptItemListDTO.getNetWeight() != null) {
            receiptCargoDetails.setActualNetWeight(receiptCargoDetails.getActualNetWeight().add(receiptItemListDTO.getNetWeight()));
        }
        if (receiptItemListDTO.getVolume() != null) {
            receiptCargoDetails.setActualVolume(receiptCargoDetails.getActualVolume().add(receiptItemListDTO.getVolume()));
        }
        if (receiptItemListDTO.getBoardCount() != null) {
            receiptCargoDetails.setActualBoardCount(receiptCargoDetails.getActualBoardCount() + receiptItemListDTO.getBoardCount());
        }
        receiptCargoDetailsMapper.updateById(receiptCargoDetails);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void upperShelf(List<UpperShelfDTO> upperShelfDTOList) {
        ReceiptList receiptList;
//        InventoryManage inventoryManage;
        InventoryScanList inventoryScanList;
//        List<ReceiptList> receiptLists;
        List<ReceiptScanList> receiptScanLists;
//        ReceiptDemand receiptDemand = receiptDemandMapper.getReceiptDemandByReceiptManageId(upperShelfDTOList.get(0).getReceiptManageId());
        for (UpperShelfDTO upperShelfDTO : upperShelfDTOList) {
            receiptList = super.selectById(upperShelfDTO.getId());
//            if (receiptList.getStatus() != 1) {
//                throw new BusinessException("该板已入储");
//            }
            receiptList.setStatus(2);
            receiptList.setWarehouseStorageNumber(upperShelfDTO.getWarehouseStorageNumber());
            super.updateById(receiptList);
//            receiptLists = receiptListMapper.getReceiptListByReceiptManageIdStatus(receiptList.getReceiptManageId());
//            receiptItemLists = receiptItemListMapper.getReceiptListById(upperShelfDTO.getId());

//            inventoryManage = BeanUtils.copy(receiptList, InventoryManage.class);
//            inventoryManage.setStatus(null);
//            inventoryManage.setWarehouseId(upperShelfDTO.getWarehouseId());
//            inventoryManage.setSingleNumber(receiptDemand.getSingleNumber());
//            inventoryManageMapper.insert(inventoryManage);
//            for (ReceiptList receiptItemList : receiptItemLists) {
//            inventoryList = BeanUtils.copy(receiptItemList, InventoryList.class);
//            inventoryList.setInventoryManageId(inventoryManage.getId());
//            inventoryList.setClientId(receiptDemand.getClientId());
//            inventoryList.setClientName(receiptDemand.getClientName());
//            inventoryList.setWarehouseId(upperShelfDTO.getWarehouseId());
//            inventoryList.setSingleNumber(receiptDemand.getSingleNumber());
//            inventoryListMapper.insert(inventoryList);
//            }
//            receiptScanLists = receiptScanListMapper.getReceiptScanListByBoardNumber(receiptList.getReceiptManageId(), receiptList.getBoardNumber());
//            for (ReceiptScanList receiptScanList : receiptScanLists) {
//                inventoryScanList = BeanUtils.copy(receiptScanList, InventoryScanList.class);
//                inventoryScanList.setId(null);
//                inventoryScanList.setInventoryManageId(inventoryManage.getId());
//                inventoryScanListMapper.insert(inventoryScanList);
//            }
//            if (receiptLists == null || receiptLists.size() < 1) {
//                receiptManageMapper.updateStatus(receiptList.getReceiptManageId(), 3);
//                map.put("isReceipt", true);
//            }
        }
//        return map;
    }

    @Override
    public void appUpperShelf(Integer id, String boardNumber, String warehouseStorageNumber) {
        ReceiptDemand receiptDemand = receiptDemandMapper.getReceiptDemandByReceiptManageId(id);
        if (warehouseStorageMapper.selectCount(warehouseStorageNumber, receiptDemand.getWarehouseId()) < 1) {
            throw new BusinessException("储位号不存在");
        }
        List<ReceiptList> receiptLists = receiptListMapper.getReceiptListByReceiptManageId(id);
        ReceiptList receiptList = receiptLists.stream().filter(e -> e.getBoardNumber().equals(boardNumber)).findAny().orElse(null);
        if (receiptList == null) {
            throw new BusinessException("板号不存在或板号不在该收货单号内");
        }
        if (receiptList.getStatus() != 1) {
            throw new BusinessException("该板已入储，请确认");
        }
        receiptList.setStatus(2);
        receiptList.setWarehouseStorageNumber(warehouseStorageNumber);
        super.updateById(receiptList);
    }

    @Override
    public void delete(Integer id) {
        if (super.selectById(receiptItemListMapper.selectById(id).getReceiptListId()).getWarehouseStorageNumber() != null) {
            throw new BusinessException("已上架的货物不能删除");
        }
        receiptListMapper.deleteById(id);
    }

    @Override
    public List<ReceiptList> getReceiptListByReceiptManageId(Integer receiptManageId) {
        return receiptListMapper.getReceiptListByReceiptManageId(receiptManageId);
    }

    @Override
    public Page<ReceiptList> getReceiptListByReceiptManageIdPage(Page<ReceiptList> page, Integer receiptManageId) {
        page.setRecords(receiptListMapper.getReceiptListByReceiptManageIdPage(page, receiptManageId));
        return page;
    }

    @Override
    public List<MaterialListAppVO> getMaterialListByReceiptManageId(Integer receiptManageId) {
        return receiptListMapper.getMaterialListByReceiptManageId(receiptManageId);
    }

    @Override
    public void deleteByReceiptManageId(Integer receiptManageId) {
        receiptListMapper.deleteByReceiptManageId(receiptManageId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void delete(List<Integer> ids) {
        ReceiptList receiptList;
        receiptList = super.selectById(ids.get(0));
        ReceiptManage receiptManage = receiptManageMapper.selectById(receiptList.getReceiptManageId());
        ReceiptCargoDetails receiptCargoDetails;
        for (Integer id : ids) {
            receiptList = super.selectById(id);
            if (receiptList.getWarehouseStorageNumber() != null && !"".equals(receiptList.getWarehouseStorageNumber())) {
                throw new BusinessException("已上架的货物不能删除");
            }
            if (receiptList.getMaterialNumber() != null && !"".equals(receiptList.getMaterialNumber())) {
                receiptCargoDetails = receiptCargoDetailsMapper.getCargoDetailsByMaterialNumber(receiptManage.getReceiptDemandId(), receiptList);
                receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getActualBoxCount() - receiptList.getBoxCount());
                receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getActualQuantityCount() - receiptList.getQuantityCount());
                receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getActualGrossWeight().subtract(receiptList.getGrossWeight()));
                receiptCargoDetails.setActualNetWeight(receiptCargoDetails.getActualNetWeight().subtract(receiptList.getNetWeight()));
                receiptCargoDetails.setActualVolume(receiptCargoDetails.getActualVolume().subtract(receiptList.getVolume()));
                receiptCargoDetails.setActualBoardCount(receiptCargoDetails.getActualBoardCount() - receiptList.getBoardCount());
                receiptCargoDetailsMapper.updateById(receiptCargoDetails);
            }
            receiptList.setStatus(-1);
            super.deleteById(id);
        }
    }

    @Override
    public void deleteItem(Integer id) {
        ReceiptItemList receiptItemList = receiptItemListMapper.selectById(id);
        ReceiptList receiptList = receiptListMapper.selectById(receiptItemList.getReceiptListId());
        ReceiptManage receiptManage = receiptManageMapper.selectById(receiptList.getReceiptManageId());
//        ReceiptCargoDetails receiptCargoDetails = receiptCargoDetailsMapper.getCargoDetailsByDemandIdAndMaterialNumber(receiptManage.getReceiptDemandId(), receiptItemList.getMaterialNumber());
//        receiptCargoDetails.setActualBoxCount(receiptCargoDetails.getActualBoxCount() - receiptItemList.getBoxCount());
//        receiptCargoDetails.setActualQuantityCount(receiptCargoDetails.getActualQuantityCount() - receiptItemList.getQuantityCount());
//        receiptCargoDetails.setActualGrossWeight(receiptCargoDetails.getActualGrossWeight().subtract(receiptItemList.getGrossWeight()));
//        receiptCargoDetails.setActualNetWeight(receiptCargoDetails.getActualNetWeight().subtract(receiptItemList.getNetWeight()));
//        receiptCargoDetails.setActualVolume(receiptCargoDetails.getActualVolume().subtract(receiptItemList.getVolume()));
//        receiptCargoDetails.setActualBoardCount(receiptCargoDetails.getActualBoardCount() - receiptItemList.getBoardCount());
//        receiptCargoDetailsMapper.updateById(receiptCargoDetails);
//        receiptItemListMapper.deleteById(id);
    }

}
