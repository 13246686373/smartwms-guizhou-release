package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.excel.EasyExcel;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.InventoryManage;
import com.frdscm.wms.entity.InventoryScanList;
import com.frdscm.wms.entity.bo.ShipmentScanListBO;
import com.frdscm.wms.entity.dto.*;
import com.frdscm.wms.mapper.InventoryManageMapper;
import com.frdscm.wms.mapper.InventoryScanListMapper;
import com.frdscm.wms.service.IInventoryManageReportService;
import com.frdscm.wms.service.IInventoryManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 移位、调拨表 前端控制器
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Api(tags = "移位、调拨管理接口")
@RestController
@RequestMapping("/recordManage")
public class InventoryManageRecordController extends BaseController {

    private final IInventoryManageReportService inventoryManageReportService;

    @Autowired
    public InventoryManageRecordController(IInventoryManageReportService inventoryManageReportService) {
        this.inventoryManageReportService = inventoryManageReportService;
    }

    @ApiOperation("位移记录列表")
    @PostMapping("/displacementList")
    public Response displacementList(@RequestBody @Valid InventoryManagePageDTO inventoryManagePageDTO) {
        Page<InventoryManage> page = new Page<>(inventoryManagePageDTO.getPageNo(), inventoryManagePageDTO.getPageSize());
        return renderSuccess(inventoryManageReportService.getInventoryManageRecordByPageList(page, inventoryManagePageDTO,"inventory_manage_displacement"));
    }

    @ApiOperation("调拨记录列表")
    @PostMapping("/allocationList")
    public Response allocationList(@RequestBody @Valid InventoryManagePageDTO inventoryManagePageDTO) {
        Page<InventoryManage> page = new Page<>(inventoryManagePageDTO.getPageNo(), inventoryManagePageDTO.getPageSize());
        return renderSuccess(inventoryManageReportService.getInventoryManageRecordByPageList(page, inventoryManagePageDTO,"inventory_manage_allocation"));
    }

}

