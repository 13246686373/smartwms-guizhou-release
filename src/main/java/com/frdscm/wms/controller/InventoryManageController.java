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
 * 库存管理表 前端控制器
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Api(tags = "库存管理接口")
@RestController
@RequestMapping("/inventoryManage")
public class InventoryManageController extends BaseController {

    private final IInventoryManageService inventoryManageService;
    private final InventoryManageMapper inventoryManageMapper;
    private final InventoryScanListMapper inventoryScanListMapper;

    @Autowired
    public InventoryManageController(IInventoryManageService inventoryManageService, InventoryManageMapper inventoryManageMapper, InventoryScanListMapper inventoryScanListMapper) {
        this.inventoryManageService = inventoryManageService;
        this.inventoryScanListMapper = inventoryScanListMapper;
        this.inventoryManageMapper = inventoryManageMapper;
    }

    @ApiOperation("库存管理列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid InventoryManagePageDTO inventoryManagePageDTO) {
        Page<InventoryManage> page = new Page<>(inventoryManagePageDTO.getPageNo(), inventoryManagePageDTO.getPageSize());
        return renderSuccess(inventoryManageService.getInventoryManageByPageList(page, inventoryManagePageDTO));
    }

    @ApiOperation("移位记录列表")
    @PostMapping("/displacement")
    public Response Displacement(@RequestBody @Valid InventoryManagePageDTO inventoryManagePageDTO) {
        Page<InventoryManage> page = new Page<>(inventoryManagePageDTO.getPageNo(), inventoryManagePageDTO.getPageSize());
        return renderSuccess(inventoryManageService.getInventoryManageByPageList(page, inventoryManagePageDTO));
    }

    @ApiOperation("调拨记录列表")
    @PostMapping("/allocation")
    public Response allocation(@RequestBody @Valid InventoryManagePageDTO inventoryManagePageDTO) {
        Page<InventoryManage> page = new Page<>(inventoryManagePageDTO.getPageNo(), inventoryManagePageDTO.getPageSize());
        return renderSuccess(inventoryManageService.getInventoryManageByPageList(page, inventoryManagePageDTO));
    }

    @ApiOperation("收货单号列表")
    @GetMapping("/singleNumberList/{warehouseId}")
    public Response singleNumberList(@PathVariable Integer warehouseId) {
        return renderSuccess(inventoryManageMapper.getSingleNumberList(warehouseId));
    }

    @ApiOperation("修改库存状态 0-不良品 1-良品")
    @PostMapping("/updateStatus")
    public Response updateStatus(@RequestBody @Valid InventoryManageStatusDTO inventoryManageStatusDTO) {
        inventoryManageService.updateStatus(inventoryManageStatusDTO.getStatus(), inventoryManageStatusDTO.getId());
        return renderSuccess("操作成功");
    }

    @ApiOperation("板货明细")
    @GetMapping("/detail/{id}")
    public Response detail(@PathVariable Integer id) {
        EntityWrapper<InventoryScanList> ew = new EntityWrapper<>();
        ew.eq("inventory_manage_id", id);
        return renderSuccess(inventoryScanListMapper.selectList(ew));
    }

    @ApiOperation("板货明细删除")
    @GetMapping("/detail/delete/{id}")
    public Response detailDelete(@PathVariable Integer id) {
        inventoryScanListMapper.deleteById(id);
        return renderSuccess("Successful");
    }

    @ApiOperation("板货明细添加编辑")
    @PostMapping("/detail/add")
    public Response detailAdd(@RequestBody InventoryScanList inventoryScanList) throws UnsupportedEncodingException {
        if (inventoryScanList.getId() != null) {
            inventoryScanListMapper.updateById(inventoryScanList);
        } else {
            inventoryScanList.setOperatorId(getUser().getUserId());
            inventoryScanList.setOperatorName(getUser().getUserName());
            inventoryScanListMapper.insert(inventoryScanList);
        }
        return renderSuccess("Successful");
    }

    @ApiOperation("板货明细导出")
    @GetMapping("/detail/export/{inventoryManageId}")
    public void export(@PathVariable Integer inventoryManageId, HttpServletResponse response) throws IOException {
        List<ShipmentScanListBO> shipmentScanListBOS = inventoryScanListMapper.exportExcel(inventoryManageId);
        if (shipmentScanListBOS.size() < 1) {
            throw new RuntimeException("暂无数据");
        }
        EasyExcel fastExcel = new EasyExcel();
        response.setContentType("application/vnd.ms-excel");
        String finalFileName = URLEncoder.encode("板货明细数据", "UTF8");
        response.setHeader("Content-disposition", "attachment;filename=" + finalFileName + ".xlsx");
        Workbook workbook = fastExcel.reportExcel(shipmentScanListBOS);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        fastExcel.close();
        outputStream.flush();
        outputStream.close();
    }

    @ApiOperation("移库")
    @PostMapping("/moveWereHouseStorage")
    public Response moveWereHouseStorage(@RequestBody @Valid MoveWereHouseStorageDTO moveWereHouseStorageDTO) {
        moveWereHouseStorageDTO.getId();
        inventoryManageService.moveWarehouseStorage(moveWereHouseStorageDTO);
        return renderSuccess("操作成功");
    }

    @ApiOperation("调拨")
    @PostMapping("/transferWereHouseStorage")
    public Response transferWereHouseStorage(@RequestBody @Valid TransferWereHouseStorageDTO transferWereHouseStorageDTO) {
        inventoryManageService.transferWereHouseStorage(transferWereHouseStorageDTO);
        return renderSuccess("操作成功");
    }

    @ApiOperation("重量汇总")
    @PostMapping("/count")
    public Response count(@RequestParam(required = false) Integer warehouseId) {
        return renderSuccess(inventoryManageMapper.getCount(warehouseId));
    }

    @ApiOperation("拆板")
    @PostMapping("/dismantlingPlate")
    public Response dismantlingPlate(@RequestBody @Valid DismantlingPlateDTO dismantlingPlateDTO) {
        return renderSuccess(inventoryManageService.dismantlingPlate(dismantlingPlateDTO));
    }

    @ApiOperation("合板")
    @PostMapping("/mergePlate")
    public Response mergePlate(@RequestBody @Valid MergePlateDTO mergePlateDTO) {
        String boardNumber = inventoryManageService.mergePlate(mergePlateDTO);
        return renderSuccess(boardNumber);
    }

    @ApiOperation("查询批次号")
    @PostMapping("/getBatchNumberList")
    public Response getBatchNumberList(@RequestBody @Valid SelectbatchNumberDTO selectbatchNumberDTO) {
        List<String> getBatchNumberList = inventoryManageService.getBatchNumberList(selectbatchNumberDTO);
        return renderSuccess(getBatchNumberList);
    }

}

