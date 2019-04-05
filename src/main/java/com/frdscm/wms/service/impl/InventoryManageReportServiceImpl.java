package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.entity.InventoryManage;
import com.frdscm.wms.entity.dto.InventoryManagePageDTO;
import com.frdscm.wms.mapper.InventoryManageMapper;
import com.frdscm.wms.mapper.InventoryManageRecordMapper;
import com.frdscm.wms.service.IInventoryManageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
