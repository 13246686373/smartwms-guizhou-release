package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.excel.EasyExcel;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.ReceiptScanList;
import com.frdscm.wms.entity.bo.ShipmentScanListBO;
import com.frdscm.wms.entity.dto.ReceiptScanListDTO;
import com.frdscm.wms.entity.dto.ReceiptScanListPageDTO;
import com.frdscm.wms.mapper.ReceiptScanListMapper;
import com.frdscm.wms.service.IReceiptScanListService;
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
@Api(tags = "收货扫描列表")
@RestController
@RequestMapping("/receiptScanList")
public class ReceiptScanListController extends BaseController {

    private final ReceiptScanListMapper receiptScanListMapper;
    private final IReceiptScanListService receiptScanListService;

    @Autowired
    public ReceiptScanListController(ReceiptScanListMapper receiptScanListMapper, IReceiptScanListService receiptScanListService) {
        this.receiptScanListMapper = receiptScanListMapper;
        this.receiptScanListService = receiptScanListService;
    }

    @ApiOperation("收货扫描列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid ReceiptScanListPageDTO receiptScanListPageDTO) {
        Page<ReceiptScanList> page = new Page<>(receiptScanListPageDTO.getPageNo(), receiptScanListPageDTO.getPageSize());
        EntityWrapper<ReceiptScanList> ew = new EntityWrapper<>();
        ew.eq("receipt_manage_id", receiptScanListPageDTO.getReceiptManageId());
        page.setRecords(receiptScanListMapper.selectPage(page, ew));
        return renderSuccess(page);
    }

    @ApiOperation("收货扫描编辑")
    @PostMapping("/edit")
    public Response receiptScan(@RequestBody @Valid ReceiptScanListDTO receiptScanListDTO) {
        ReceiptScanList receiptScanList = BeanUtils.copy(receiptScanListDTO, ReceiptScanList.class);
        receiptScanListMapper.updateById(receiptScanList);
        return renderSuccess("Successful");
    }

    @ApiOperation("收货扫描删除")
    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable Integer id) {
        receiptScanListMapper.deleteById(id);
        return renderSuccess("Successful");
    }

    @ApiOperation("批量添加收货扫描")
    @PostMapping("/batchAdd")
    public Response batchAdd(@RequestBody @Valid List<ReceiptScanListDTO> receiptScanListDTOList) throws UnsupportedEncodingException {
        receiptScanListService.batchSave(receiptScanListDTOList, getUser());
        return renderSuccess("Successful");
    }

    @ApiOperation("收货扫描导出")
    @GetMapping("/export/{receiptManageId}")
    public void export(@PathVariable Integer receiptManageId, HttpServletResponse response) throws IOException {
        List<ShipmentScanListBO> shipmentScanListBOS = receiptScanListMapper.exportExcel(receiptManageId);
        if (shipmentScanListBOS.size() < 1) {
            throw new RuntimeException("暂无数据");
        }
        EasyExcel fastExcel = new EasyExcel();
        response.setContentType("application/vnd.ms-excel");
        String finalFileName = URLEncoder.encode("收货扫描数据", "UTF8");
        response.setHeader("Content-disposition", "attachment;filename=" + finalFileName + ".xlsx");
        Workbook workbook = fastExcel.reportExcel(shipmentScanListBOS);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        fastExcel.close();
        outputStream.flush();
        outputStream.close();
    }
}

