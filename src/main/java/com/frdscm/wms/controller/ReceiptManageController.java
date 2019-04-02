package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.*;
import com.frdscm.wms.entity.bo.materials.Materials;
import com.frdscm.wms.entity.dto.ReceiptListPageDTO;
import com.frdscm.wms.entity.dto.ReceiptManagePageDTO;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.mapper.*;
import com.frdscm.wms.service.IReceiptManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收货管理表 前端控制器
 *
 * @author March_CD
 * @since 2018-07-06
 */
@Api(tags = "收货管理接口")
@RestController
@RequestMapping("/receiptManage")
public class ReceiptManageController extends BaseController {

    private final IReceiptManageService receiptManageService;
    private final ReceiptCargoDetailsMapper receiptCargoDetailsMapper;
    private final ReceiptDemandMapper receiptDemandMapper;
    private final ReceiptManageMapper receiptManageMapper;

    @Autowired
    public ReceiptManageController(IReceiptManageService receiptManageService, ReceiptDemandMapper receiptDemandMapper, ReceiptManageMapper receiptManageMapper, ReceiptCargoDetailsMapper receiptCargoDetailsMapper) {
        this.receiptManageService = receiptManageService;
        this.receiptCargoDetailsMapper = receiptCargoDetailsMapper;
        this.receiptDemandMapper = receiptDemandMapper;
        this.receiptManageMapper = receiptManageMapper;
    }

//    @ApiOperation("添加收货管理配置信息")
//    @PostMapping("/add")
//    public Response save(@RequestBody @Valid ReceiptManageDTO receiptManageDTO) {
//        ReceiptManage receiptManage = BeanUtils.copy(receiptManageDTO, ReceiptManage.class);
//        receiptManageService.save(receiptManage);
//        return renderSuccess("操作成功");
//    }
//
//    @ApiOperation("修改收货管理配置信息")
//    @PostMapping("/edit")
//    public Response edit(@RequestBody @Valid ReceiptManageDTO receiptManageDTO) {
//        ReceiptManage receiptManage = BeanUtils.copy(receiptManageDTO, ReceiptManage.class);
//        receiptManageService.edit(receiptManage);
//        return renderSuccess("操作成功");
//    }

//    @ApiOperation("查询收货管理配置信息")
//    @GetMapping("/getReceiptManage/{receiptManageId}")
//    public Response getReceiptManageByReceiptManageId(@PathVariable("receiptManageId") Integer receiptManageId) {
//        return renderSuccess(receiptManageService.selectReceiptManageByReceiptManageId(receiptManageId));
//    }

    @ApiOperation("收货管理列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid ReceiptManagePageDTO receiptManagePageDTO) {
        Page<Map<String, Object>> page = new Page<>(receiptManagePageDTO.getPageNo(), receiptManagePageDTO.getPageSize());
        return renderSuccess(receiptManageService.getReceiptManageByPageList(page, receiptManagePageDTO));
    }

    @ApiOperation("收货汇总")
    @PostMapping("/receiptSummary")
    public Response receiptSummary(@RequestBody @Valid ReceiptListPageDTO receiptListPageDTO) {
        Page<ReceiptCargoDetails> page = new Page<>(receiptListPageDTO.getPageNo(), receiptListPageDTO.getPageSize());
        page.setRecords(receiptCargoDetailsMapper.getReceiptSummary(page, receiptListPageDTO.getReceiptManageId()));
        return renderSuccess(page);
    }

    @ApiOperation("入储完成")
    @GetMapping("/completeUpperShelf/{id}")
    public Response completeUpperShelf(@PathVariable Integer id) {
        receiptManageService.completeReceipt(id, getCompanyId());
        return renderSuccess("Successful");
    }

    @ApiOperation("完成收货")
    @GetMapping("/completeReceipt/{id}")
    public Response completeReceipt(@PathVariable Integer id) {
        ReceiptDemand receiptDemand = receiptDemandMapper.getReceiptDemandByReceiptManageId(id);
        List<ReceiptCargoDetails> receiptCargoDetailsList = receiptCargoDetailsMapper.getReceiptCargoDetailsByReceiptDemandId(receiptDemand.getId());
        boolean isTrue = true;
        for (ReceiptCargoDetails receiptCargoDetail : receiptCargoDetailsList) {
            switch (receiptCargoDetail.getUnitType()) {
                case 1:
                    if (!receiptCargoDetail.getActualQuantityCount().equals(receiptCargoDetail.getQuantityCount())) {
                        isTrue = false;
                    }
                    break;
                case 2:
                    if (!receiptCargoDetail.getActualBoxCount().equals(receiptCargoDetail.getBoxCount())) {
                        isTrue = false;
                    }
                    break;
                case 3:
                    if (!receiptCargoDetail.getActualBoardCount().equals(receiptCargoDetail.getBoardCount())) {
                        isTrue = false;
                    }
                    break;
                case 4:
                    if (!receiptCargoDetail.getActualGrossWeight().equals(receiptCargoDetail.getGrossWeight())) {
                        isTrue = false;
                    }
                    break;
                case 5:
                    if (!receiptCargoDetail.getActualVolume().equals(receiptCargoDetail.getVolume())) {
                        isTrue = false;
                    }
                    break;
                default:
                    throw new BusinessException("");
            }
            if (!isTrue) {
                break;
            }
        }
        if (!isTrue) {
            return renderError("收货数量不足，请检查");
        }
        Map<String, Boolean> map = new HashMap<>(0);
        receiptManageMapper.updateStatus(id, 2);
        map.put("isReceipt", true);
        return renderSuccess(map);
    }

//    @ApiOperation("看板统计")
//    @PostMapping("/receiptBoardCount")
//    public Response receiptBoardCount(@RequestBody @Valid CountDTO countDTO) {
//        return renderSuccess(receiptManageService.getReceiptBoardCount(countDTO));
//    }
//
//    @ApiOperation("看板数据统计")
//    @PostMapping("/receiptDataCount")
//    public Response receiptDataCount(@RequestBody @Valid CountDTO countDTO) {
//        return renderSuccess(receiptManageService.getReceiptDataCount(countDTO));
//    }
//
//
//    @ApiOperation("看板日期统计")
//    @PostMapping("/receiptDateCount")
//    public Response receiptCountDate(@RequestBody @Valid CountDTO countDTO) {
//        return renderSuccess(receiptManageService.getReceiptDateCount(countDTO));
//    }
}

