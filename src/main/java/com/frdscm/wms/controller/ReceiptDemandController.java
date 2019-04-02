package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.ReceiptDemand;
import com.frdscm.wms.entity.ReceiptManage;
import com.frdscm.wms.entity.dto.ReceiptDemandDTO;
import com.frdscm.wms.entity.dto.ReceiptDemandPageDTO;
import com.frdscm.wms.entity.vo.ReceiptDemandVO;
import com.frdscm.wms.service.IReceiptDemandService;
import com.frdscm.wms.service.IReceiptManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * 收货需求表 前端控制器
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Api(tags = "收货需求接口")
@RestController
@RequestMapping("/receiptDemand")
public class ReceiptDemandController extends BaseController {
    private final IReceiptDemandService receiptDemandService;
    private final IReceiptManageService receiptManageService;

    @Autowired
    public ReceiptDemandController(IReceiptDemandService receiptDemandService, IReceiptManageService receiptManageService) {
        this.receiptDemandService = receiptDemandService;
        this.receiptManageService = receiptManageService;
    }

    @ApiOperation("添加收货需求")
    @PostMapping("/add")
    public Response add(@RequestBody @Valid ReceiptDemandDTO receiptDemandDTO) throws UnsupportedEncodingException {
        return renderSuccess(receiptDemandService.save(receiptDemandDTO, getUser()));
    }

    @ApiOperation("修改收货需求")
    @PostMapping("/edit")
    public Response edit(@RequestBody @Valid ReceiptDemandDTO receiptDemandDTO) {
        if (receiptDemandDTO.getId() == null) {
            return renderError("收货需求ID不能为空");
        }
        return renderSuccess(receiptDemandService.edit(receiptDemandDTO));
    }

    @ApiOperation("删除收货需求")
    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Integer id) {
        receiptDemandService.delete(id);
        return renderSuccess("操作成功");
    }

    @ApiOperation("收货需求列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid ReceiptDemandPageDTO receiptDemandPageDTO) {
        Page<ReceiptDemandVO> page = new Page<>(receiptDemandPageDTO.getPageNo(), receiptDemandPageDTO.getPageSize());
        return renderSuccess(receiptDemandService.getReceiptCargoDetailsList(page, receiptDemandPageDTO));
    }

    @ApiOperation("确认收货需求")
    @GetMapping("/confirm/{id}")
    public Response confirmReceiptDemand(@PathVariable("id") Integer id) {
        ReceiptDemand receiptDemand = receiptDemandService.selectById(id);
        if (receiptDemand.getStatus() != 1) {
            return renderError("操作失败，请检查收货需求状态");
        }
        receiptDemandService.confirmDemand(receiptDemand, getCompanyId());
        return renderSuccess("操作成功");
    }

    @ApiOperation("取消收货需求")
    @GetMapping("/cancel/{id}")
    public Response cancelReceiptDemand(@PathVariable("id") Integer id) {
        ReceiptManage receiptManage = receiptManageService.selectReceiptManageByReceiptDemandId(id);
        if (receiptManage != null && receiptManage.getStatus() > 1) {
            return renderError("操作失败，无法取消已开始收货的收货需求");
        }
        ReceiptDemand receiptDemand = receiptDemandService.selectById(id);
        if (receiptDemand.getStatus() != 2) {
            return renderError("操作失败，请检查收货需求状态");
        }
        receiptDemandService.cancelDemand(receiptDemand);
        return renderSuccess("操作成功");
    }


}

