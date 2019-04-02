package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.dto.ReceiptListDTO;
import com.frdscm.wms.entity.dto.ReceiptListPageDTO;
import com.frdscm.wms.entity.dto.UpperShelfDTO;
import com.frdscm.wms.mapper.InventoryManageMapper;
import com.frdscm.wms.mapper.ReceiptListMapper;
import com.frdscm.wms.service.IReceiptListService;
import com.frdscm.wms.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 收货列表 前端控制器
 *
 * @author March_CD
 * @since 2018-07-06
 */
@Api(tags = "收货明细接口")
@RestController
@RequestMapping("/receiptList")
public class ReceiptListController extends BaseController {

    private final IReceiptListService receiptListService;
    private final InventoryManageMapper inventoryManageMapper;
    private final ReceiptListMapper receiptListMapper;

    @Autowired
    public ReceiptListController(IReceiptListService receiptListService, ReceiptListMapper receiptListMapper, InventoryManageMapper inventoryManageMapper) {
        this.receiptListService = receiptListService;
        this.inventoryManageMapper = inventoryManageMapper;
        this.receiptListMapper = receiptListMapper;
    }

    @ApiOperation("收货明细")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid ReceiptListPageDTO receiptListPageDTO) {
        Page<ReceiptList> page = new Page<>(receiptListPageDTO.getPageNo(), receiptListPageDTO.getPageSize());
        return renderSuccess(receiptListService.getReceiptListByReceiptManageIdPage(page, receiptListPageDTO.getReceiptManageId()));
    }

    @ApiOperation("生成板号")
    @GetMapping("/boardNumber")
    public Response boardNumber() {
        int count = inventoryManageMapper.countBoardNumber("P" + DateUtil.dayNow());
        String str = String.format("%04d", count);
        // 生成版号 格式 P+yyMMdd+0001
        String sNum = "P" + DateUtil.dayNow() + str;
        return renderSuccess((Object) sNum);
    }

    @ApiOperation("选择物料")
    @GetMapping("/material/{receiptManageId}")
    public Response material(@PathVariable Integer receiptManageId) {
        Page<ReceiptCargoDetails> page = new Page<>();
        page.setSearchCount(false);
        page.setRecords(receiptListMapper.getReceiptCargoDetailsByReceiptManageId(receiptManageId));
        return renderSuccess(page);
    }

    @ApiOperation("添加板")
    @GetMapping("/addBoardNumber/{receiptManageId}")
    public Response addBoardNumber(@PathVariable("receiptManageId") Integer receiptManageId) {
        ReceiptList receiptList = new ReceiptList();
        int count = inventoryManageMapper.countBoardNumber("P" + DateUtil.dayNow());
        String str = String.format("%04d", count);
        // 生成版号 格式 P+yyMMdd+0001
        String boardNumber = "P" + DateUtil.dayNow() + str;
        receiptList.setBoardNumber(boardNumber);
        receiptList.setReceiptManageId(receiptManageId);
        receiptListMapper.insert(receiptList);
        return renderSuccess(receiptList);
    }

    @ApiOperation("批量添加板")
    @GetMapping("/batchBoardNumber/{receiptManageId}")
    public Response batchBoardNumber(@PathVariable("receiptManageId") Integer receiptManageId) {
        return renderSuccess(receiptListService.batchBoardNumber(receiptManageId));
    }

    @ApiOperation("修改收货明细列表")
    @PostMapping("/edit")
    public Response edit(@RequestBody @Valid ReceiptListDTO receiptListDTO) {
        receiptListService.edit(receiptListDTO);
        return renderSuccess("Successful");
    }

    @ApiOperation("删除收货明细列表")
    @PostMapping("/delete")
    public Response delete(@RequestBody List<Integer> ids) {
        receiptListService.delete(ids);
        return renderSuccess("操作成功");
    }

//    @ApiOperation("删除收货明细Item")
//    @GetMapping("/item/delete/{id}")
//    public Response delete(@PathVariable("id") Integer id) {
//        receiptListService.deleteItem(id);
//        return renderSuccess("操作成功");
//    }

    @ApiOperation("入储")
    @PostMapping("/upperShelf")
    public Response upperShelf(@RequestBody @Valid List<UpperShelfDTO> upperShelfDTOList) {
        receiptListService.upperShelf(upperShelfDTOList);
        return renderSuccess("Successful");
    }

}

