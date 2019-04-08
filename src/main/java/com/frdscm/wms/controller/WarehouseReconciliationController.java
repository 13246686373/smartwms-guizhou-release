package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.dto.WarehouseOrderDTO;
import com.frdscm.wms.entity.dto.WarehouseOrderPageDTO;
import com.frdscm.wms.entity.dto.WarehouseReconciliationDTO;
import com.frdscm.wms.entity.dto.WarehouseReconciliationPageDTO;
import com.frdscm.wms.entity.vo.WarehouseOrderVO;
import com.frdscm.wms.entity.vo.WarehouseReconciliationVO;
import com.frdscm.wms.service.IWarehouseReconciliationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dizhang
 * @since 2019-04-06
 */
@Api(tags = "仓库订单应收账单")
@RestController
@RequestMapping("/reconciliation")
public class WarehouseReconciliationController extends BaseController {

    @Autowired
    private IWarehouseReconciliationService warehouseReconciliationService;

    @ApiOperation("新增仓库订单应收对账单")
    @PostMapping("/save")
    public Response save(@RequestBody @Valid WarehouseReconciliationDTO reconciliationDTO) throws UnsupportedEncodingException {
        return renderSuccess(warehouseReconciliationService.save(reconciliationDTO));
    }

    @ApiOperation("应收对账单信息列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid WarehouseReconciliationPageDTO reconciliationPageDTO) {
        // 调用查询分页接口
        Page<WarehouseReconciliationVO> reconciliationVO = new Page<>(reconciliationPageDTO.getPageNo(), reconciliationPageDTO.getPageSize());
        return renderSuccess(warehouseReconciliationService.selectByPage(reconciliationVO, reconciliationPageDTO));
    }

    @ApiOperation("修改对账单状态 1-对账单确认 2-财务确认")
    @PutMapping("/updateStatus/{status}")
    public Response updateStatus(@RequestBody List<Integer> idList, @PathVariable("status") Integer status) {
        warehouseReconciliationService.updateStatus(idList, status);
        return renderSuccess("更新成功");
    }

}

