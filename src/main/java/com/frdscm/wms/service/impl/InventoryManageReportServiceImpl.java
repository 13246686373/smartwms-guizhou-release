package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.entity.InventoryManage;
import com.frdscm.wms.mapper.InventoryManageMapper;
import com.frdscm.wms.service.IInventoryManageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 库存管理(报表)表 实现类
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Service
public class InventoryManageReportServiceImpl extends ServiceImpl<InventoryManageMapper, InventoryManage> implements IInventoryManageReportService {

    private final InventoryManageMapper inventoryManageMapper;


    @Autowired
    public InventoryManageReportServiceImpl(InventoryManageMapper inventoryManageMapper) {
        this.inventoryManageMapper = inventoryManageMapper;
    }


    @Override
    public void addInventoryReportManage(InventoryManage inventoryReportManage) {
        inventoryManageMapper.addInventoryReportManage(inventoryReportManage);
    }
}
