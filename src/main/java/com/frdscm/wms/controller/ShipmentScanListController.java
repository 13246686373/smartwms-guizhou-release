package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.excel.EasyExcel;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.ShipmentList;
import com.frdscm.wms.entity.ShipmentScanList;
import com.frdscm.wms.entity.bo.ShipmentScanListBO;
import com.frdscm.wms.entity.dto.ShipmentScanListDTO;
import com.frdscm.wms.entity.dto.ShipmentScanListPageDTO;
import com.frdscm.wms.mapper.ShipmentScanListMapper;
import com.frdscm.wms.service.IShipmentScanListService;
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
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dizhang
 * @since 2018-08-11
 */
@Api(tags = "扫描列表")
@RestController
@RequestMapping("/shipmentScanList")
public class ShipmentScanListController extends BaseController {

    private final IShipmentScanListService shipmentScanListService;
    private final ShipmentScanListMapper shipmentScanListMapper;

    @Autowired
    public ShipmentScanListController(IShipmentScanListService shipmentScanListService, ShipmentScanListMapper shipmentScanListMapper) {
        this.shipmentScanListService = shipmentScanListService;
        this.shipmentScanListMapper = shipmentScanListMapper;
    }

    @ApiOperation("出货扫描列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid ShipmentScanListPageDTO shipmentScanListPageDTO) {
        Page<ShipmentScanList> page = new Page<>(shipmentScanListPageDTO.getPageNo(), shipmentScanListPageDTO.getPageSize());
        EntityWrapper<ShipmentScanList> ew = new EntityWrapper<>();
        ew.eq("shipment_manage_id", shipmentScanListPageDTO.getShipmentManageId());
        page.setRecords(shipmentScanListMapper.selectPage(page, ew));
        return renderSuccess(page);
    }

    @ApiOperation("出货扫描编辑")
    @PostMapping("/edit")
    public Response receiptScan(@RequestBody @Valid ShipmentScanListDTO shipmentScanListDTO) {
        ShipmentScanList shipmentScanList = BeanUtils.copy(shipmentScanListDTO, ShipmentScanList.class);
        shipmentScanListMapper.updateById(shipmentScanList);
        return renderSuccess("Successful");
    }

    @ApiOperation("出货扫描删除")
    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable Integer id) {
        shipmentScanListMapper.deleteById(id);
        return renderSuccess("Successful");
    }

    @ApiOperation("批量添加出货扫描")
    @PostMapping("/batchAdd")
    public Response batchAdd(@RequestBody @Valid List<ShipmentScanListDTO> shipmentScanListDTOList) throws UnsupportedEncodingException {
        shipmentScanListService.batchSave(shipmentScanListDTOList, getUser());
        return renderSuccess("Successful");
    }

    @ApiOperation("出货扫描导出")
    @GetMapping("/export/{shipmentManageId}")
    public void export(@PathVariable Integer shipmentManageId, HttpServletResponse response) throws IOException {
        List<ShipmentScanListBO> shipmentScanListBOS = shipmentScanListMapper.exportExcel(shipmentManageId);
        if (shipmentScanListBOS.size() < 1) {
            throw new RuntimeException("暂无数据");
        }
        EasyExcel fastExcel = new EasyExcel();
        response.setContentType("application/vnd.ms-excel");
        String finalFileName = URLEncoder.encode("出货扫描数据", "UTF8");
        response.setHeader("Content-disposition", "attachment;filename=" + finalFileName + ".xlsx");
        Workbook workbook = fastExcel.reportExcel(shipmentScanListBOS);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        fastExcel.close();
        outputStream.flush();
        outputStream.close();
    }
}

