package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.entity.InventoryManage;
import com.frdscm.wms.entity.dto.InventoryManagePageDTO;
import com.frdscm.wms.entity.dto.ReceiptListPageDTO;
import com.frdscm.wms.entity.dto.ReceiptManagePageDTO;
import com.frdscm.wms.entity.dto.ReceiptReportFormDTO;
import com.frdscm.wms.mapper.InventoryManageMapper;
import com.frdscm.wms.mapper.InventoryManageRecordMapper;
import com.frdscm.wms.service.IInventoryManageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 移位、调拨记录表 实现类
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Service
public class InventoryManageReportServiceImpl extends ServiceImpl<InventoryManageMapper, InventoryManage> implements IInventoryManageReportService {

    private final InventoryManageRecordMapper inventoryManageRecordMapper;


    @Autowired
    public InventoryManageReportServiceImpl(InventoryManageRecordMapper inventoryManageRecordMapper) {
        this.inventoryManageRecordMapper = inventoryManageRecordMapper;
    }


    @Override
    public Page<InventoryManage> getInventoryManageRecordByPageList(Page<InventoryManage> page, InventoryManagePageDTO inventoryManagePageDTO, String tableName) {
        page.setSearchCount(false);
        page.setRecords(inventoryManageRecordMapper.getInventoryManageRecordByPageList(page.getOffset(), page.getLimit(), inventoryManagePageDTO,tableName));
        page.setTotal(inventoryManageRecordMapper.getInventoryManageRecordByPageCount(inventoryManagePageDTO,tableName));
        return page;
    }

    @Override
    public Page<InventoryManage> getInventoryListByPageList(Page<InventoryManage> page, InventoryManagePageDTO inventoryManagePageDTO) {
        page.setSearchCount(false);
        page.setRecords(inventoryManageRecordMapper.getInventoryListByPageList(page.getOffset(), page.getLimit(), inventoryManagePageDTO));
        page.setTotal(inventoryManageRecordMapper.getInventoryListByPageCount(inventoryManagePageDTO));
        return page;
    }

    @Override
    public Page<Map<String, Object>> getReceiptListByPageList(Page<Map<String, Object>> page, ReceiptReportFormDTO receiptReportFormDTO) {
        page.setSearchCount(false);
        page.setRecords(inventoryManageRecordMapper.getReceiptListByPageList(page.getOffset(), page.getLimit(), receiptReportFormDTO));
        page.setTotal(inventoryManageRecordMapper.getReceiptListByPageCount(receiptReportFormDTO));
        return page;
    }

    @Override
    public Page<Map<String, Object>> getShipmentListByPageList(Page<Map<String, Object>> page, ReceiptReportFormDTO receiptReportFormDTO) {
        page.setSearchCount(false);
        page.setRecords(inventoryManageRecordMapper.getShipmentListByPageList(page.getOffset(), page.getLimit(), receiptReportFormDTO));
        page.setTotal(inventoryManageRecordMapper.getShipmentListByPageCount(receiptReportFormDTO));
        return page;
    }
}
