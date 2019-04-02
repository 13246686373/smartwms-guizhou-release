package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.*;
import com.frdscm.wms.entity.bo.materials.Materials;
import com.frdscm.wms.entity.dto.CountDTO;
import com.frdscm.wms.entity.dto.ReceiptManagePageDTO;
import com.frdscm.wms.entity.vo.ReceiptCountVO;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.mapper.*;
import com.frdscm.wms.service.IReceiptManageService;
import com.frdscm.wms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 收货管理表 服务实现类
 *
 * @author March_CD
 * @since 2018-07-06
 */
@Service
public class ReceiptManageServiceImpl extends ServiceImpl<ReceiptManageMapper, ReceiptManage> implements IReceiptManageService {

    private final ReceiptManageMapper receiptManageMapper;
    private final ReceiptDemandMapper receiptDemandMapper;
    private final ProjectService projectService;
    private final ReceiptScanListMapper receiptScanListMapper;
    private final ReceiptListMapper receiptListMapper;
    private final InventoryManageMapper inventoryManageMapper;
    private final InventoryScanListMapper inventoryScanListMapper;
    private final ReceiptCargoDetailsMapper receiptCargoDetailsMapper;

    @Autowired
    public ReceiptManageServiceImpl(ReceiptManageMapper receiptManageMapper, ReceiptDemandMapper receiptDemandMapper, ProjectService projectService, ReceiptScanListMapper receiptScanListMapper, ReceiptListMapper receiptListMapper, InventoryManageMapper inventoryManageMapper, InventoryScanListMapper inventoryScanListMapper, ReceiptCargoDetailsMapper receiptCargoDetailsMapper) {
        this.receiptManageMapper = receiptManageMapper;
        this.receiptDemandMapper = receiptDemandMapper;
        this.projectService = projectService;
        this.receiptScanListMapper = receiptScanListMapper;
        this.receiptListMapper = receiptListMapper;
        this.inventoryManageMapper = inventoryManageMapper;
        this.inventoryScanListMapper = inventoryScanListMapper;
        this.receiptCargoDetailsMapper = receiptCargoDetailsMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(ReceiptManage receiptManage) {
        if (receiptManageMapper.selectReceiptManageByReceiptDemandId(receiptManage.getReceiptDemandId()) != null) {
            throw new BusinessException("配置信息已存在，无法添加");
        }
        super.insert(receiptManage);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void edit(ReceiptManage receiptManage) {
        if (super.selectById(receiptManage.getId()) == null) {
            throw new BusinessException("配置信息不存在，无法修改");
        }
        super.updateById(receiptManage);
    }

    @Override
    public ReceiptManage selectReceiptManageByReceiptDemandId(Integer receiptDemandId) {
        return receiptManageMapper.selectReceiptManageByReceiptDemandId(receiptDemandId);
    }

    @Override
    public ReceiptManage selectReceiptManageByReceiptManageId(Integer receiptManageId) {
        return super.selectById(receiptManageId);
    }

    @Override
    public Page<Map<String, Object>> getReceiptManageByPageList(Page<Map<String, Object>> page, ReceiptManagePageDTO receiptManagePageDTO) {
        return page.setRecords(receiptManageMapper.getReceiptManageByPageList(page, receiptManagePageDTO));
    }


    @Override
    public void deleteReceiptManageByReceiptDemandId(Integer receiptDemandId) {
        receiptManageMapper.deleteReceiptManageByReceiptDemandId(receiptDemandId);
    }

    @Override
    public void updateStatus(Integer receiptManageId, Integer status) {
        receiptManageMapper.updateStatus(receiptManageId, status);
    }

    @Override
    public Map getReceiptBoardCount(CountDTO receiptCountDTO) {
        return receiptManageMapper.getReceiptBoardCount(receiptCountDTO);
    }

    @Override
    public Page<ReceiptCountVO> getReceiptDataCount(CountDTO countDTO) {
        Page<ReceiptCountVO> page = new Page<>(countDTO.getPageNo(), countDTO.getPageSize());
        page.setRecords(receiptManageMapper.getReceiptDataCount(page, countDTO));
        return page;
    }

    @Override
    public List<Map<String, Object>> getReceiptDateCount(CountDTO countDTO) {
        Date start = new Date(countDTO.getReceiptTimeStart());
        Date end = new Date(countDTO.getReceiptTimeEnd());
        List<String> listDay = DateUtil.findDates(start, end);
        List<Map<String, Object>> resultMap = receiptManageMapper.getReceiptDateCount(countDTO);
        List<Map<String, Object>> returnMap = new ArrayList<>();
        ShipmentManageServiceImpl.generateCountData(listDay, resultMap, returnMap);
        return returnMap;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void completeReceipt(Integer id, Integer companyId) {
        ReceiptManage receiptManage = super.selectById(id);
        if (receiptManage.getStatus() != 2) {
            throw new BusinessException("完成收货失败，请检查收货状态");
        }
        List<ReceiptList> receiptLists = receiptListMapper.getReceiptListByReceiptManageId(id);
        ReceiptDemand receiptDemand = receiptDemandMapper.getReceiptDemandByReceiptManageId(id);
        InventoryManage inventoryManage;
        InventoryScanList inventoryScanList;
        List<ReceiptScanList> receiptScanLists;
        List<ReceiptCargoDetails> receiptCargoDetailsList = receiptCargoDetailsMapper.getReceiptCargoDetailsByReceiptDemandId(receiptDemand.getId());
        boolean isTrue = false;
        for (ReceiptCargoDetails receiptCargoDetails : receiptCargoDetailsList) {
            switch (receiptCargoDetails.getUnitType()) {
                case 1:
                    if (!receiptCargoDetails.getActualQuantityCount().equals(receiptCargoDetails.getQuantityCount())) {
                        isTrue = true;
                    }
                    break;
                case 2:
                    if (!receiptCargoDetails.getActualBoxCount().equals(receiptCargoDetails.getBoxCount())) {
                        isTrue = true;
                    }
                    break;
                case 3:
                    if (!receiptCargoDetails.getActualBoardCount().equals(receiptCargoDetails.getBoardCount())) {
                        isTrue = true;
                    }
                    break;
                case 4:
                    if (!receiptCargoDetails.getActualGrossWeight().equals(receiptCargoDetails.getGrossWeight())) {
                        isTrue = true;
                    }
                    break;
                case 5:
                    if (!receiptCargoDetails.getActualVolume().equals(receiptCargoDetails.getVolume())) {
                        isTrue = true;
                    }
                    break;
                default:
                    throw new BusinessException("");
            }
            if (isTrue) {
                throw new BusinessException("确认收货失败，物料" + receiptCargoDetails.getMaterialName() + "收货量不足，请确认");
            }
        }
        for (ReceiptList receiptList : receiptLists) {
            if (receiptList.getWarehouseStorageNumber() == null || "".equals(receiptList.getWarehouseStorageNumber())) {
                throw new BusinessException("确认收货失败，还有板未入储，请确认");
            }
            if (receiptList.getIsReceiptScan() == 1 && receiptList.getScanned() == 0) {
                throw new BusinessException("确认收货失败，物料 '" + receiptList.getMaterialName() + "' 已开启收货扫描，请先完成收货扫描，请确认");
            }
            inventoryManage = BeanUtils.copy(receiptList, InventoryManage.class);
            inventoryManage.setStatus(null);
            inventoryManage.setWarehouseId(receiptDemand.getWarehouseId());
            inventoryManage.setClientId(receiptDemand.getClientId());
            inventoryManage.setClientName(receiptDemand.getClientName());
            inventoryManage.setSingleNumber(receiptDemand.getSingleNumber());
            inventoryManageMapper.insert(inventoryManage);
            receiptScanLists = receiptScanListMapper.getReceiptScanListByBoardNumber(receiptList.getReceiptManageId(), receiptList.getBoardNumber());
            for (ReceiptScanList receiptScanList : receiptScanLists) {
                inventoryScanList = BeanUtils.copy(receiptScanList, InventoryScanList.class);
                inventoryScanList.setId(null);
                inventoryScanList.setInventoryManageId(inventoryManage.getId());
                inventoryScanListMapper.insert(inventoryScanList);
            }
        }
//        List<ReceiptCargoDetails> receiptCargoDetailsList = receiptCargoDetailsMapper.getReceiptCargoDetailsByReceiptDemandId(receiptManage.getReceiptDemandId());
//        List<String> materialItemNumbers = new ArrayList<>();
//        for (ReceiptCargoDetails receiptCargoDetails : receiptCargoDetailsList) {
//            materialItemNumbers.add(receiptCargoDetails.getMaterialNumber());
//        }
//        if (materialItemNumbers.size() > 0) {
//            List<Materials> materialsList = projectService.getByMaterialItemNumberAndClientId(materialItemNumbers, receiptDemand.getClientId(), companyId);
//            for (Materials materials : materialsList) {
//                if (materials.getIsScanningSerialNumber() != null && materials.getIsScanningSerialNumber() == 1) {
//                    if (receiptScanListMapper.materialIsScan(id) < 1) {
//                        throw new BusinessException("确认收货失败，物料 '" + materials.getItemName() + "' 已开启收货扫描，请先完成收货扫描");
//                    }
//                }
//            }
//        }
        receiptManage.setStatus(3);
        super.updateById(receiptManage);
    }
}