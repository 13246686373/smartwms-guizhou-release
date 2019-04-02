package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.ShipmentCargoDetails;
import com.frdscm.wms.entity.dto.ShipmentListPageDTO;
import com.frdscm.wms.entity.dto.ShipmentManagePageDTO;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.mapper.ShipmentCargoDetailsMapper;
import com.frdscm.wms.mapper.ShipmentDemandMapper;
import com.frdscm.wms.mapper.ShipmentScanListMapper;
import com.frdscm.wms.service.IShipmentManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 出货管理表 前端控制器
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Api(tags = "出货管理接口")
@RestController
@RequestMapping("/shipmentManage")
public class ShipmentManageController extends BaseController {

    private final IShipmentManageService shipmentManageService;

    private final ShipmentCargoDetailsMapper shipmentCargoDetailsMapper;

    private final ShipmentScanListMapper shipmentScanListMapper;

    private final ProjectService projectService;

    private final ShipmentDemandMapper shipmentDemandMapper;

    @Autowired
    public ShipmentManageController(IShipmentManageService shipmentManageService, ShipmentScanListMapper shipmentScanListMapper, ShipmentDemandMapper shipmentDemandMapper, ProjectService projectService, ShipmentCargoDetailsMapper shipmentCargoDetailsMapper) {
        this.shipmentManageService = shipmentManageService;
        this.shipmentCargoDetailsMapper = shipmentCargoDetailsMapper;
        this.projectService = projectService;
        this.shipmentScanListMapper = shipmentScanListMapper;
        this.shipmentDemandMapper = shipmentDemandMapper;
    }

    @ApiOperation("出货管理列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid ShipmentManagePageDTO shipmentManagePageDTO) {
        Page<Map<String, Object>> page = new Page<>(shipmentManagePageDTO.getPageNo(), shipmentManagePageDTO.getPageSize());
        return renderSuccess(shipmentManageService.getShipmentManageByPageList(page, shipmentManagePageDTO));
    }

    @ApiOperation("出货汇总")
    @PostMapping("/shipmentSummary")
    public Response shipmentSummary(@RequestBody @Valid ShipmentListPageDTO shipmentListPageDTO) {
        Page<ShipmentCargoDetails> page = new Page<>(shipmentListPageDTO.getPageNo(), shipmentListPageDTO.getPageSize());
        page.setRecords(shipmentCargoDetailsMapper.getShipmentSummary(page, shipmentListPageDTO.getShipmentManageId()));
        return renderSuccess(page);
    }

    @ApiOperation("确认出货")
    @GetMapping("/completeShipment/{id}")
    public Response completeShipment(@PathVariable Integer id) {
        shipmentManageService.completeShipment(id, getCompanyId());
        return renderSuccess("Successful");
    }

//    @ApiOperation("确认出货")
//    @GetMapping("/confirm/{shipmentManageId}")
//    public Response onConfirm(@PathVariable("shipmentManageId") Integer shipmentManageId) {
//        shipmentManageService.onConfirm(shipmentManageId);
//        return renderSuccess("操作成功");
//    }
//
//    @ApiOperation("看板统计")
//    @PostMapping("/shipmentBoardCount")
//    public Response shipmentBoardCount(@RequestBody @Valid CountDTO countDTO) {
//        return renderSuccess(shipmentManageService.getShipmentBoardCount(countDTO));
//    }
//
//    @ApiOperation("看板数据统计")
//    @PostMapping("/shipmentDataCount")
//    public Response shipmentDataCount(@RequestBody @Valid CountDTO countDTO) {
//        return renderSuccess(shipmentManageService.getShipmentDataCount(countDTO));
//    }
//
//    @ApiOperation("看板日期统计")
//    @PostMapping("/shipmentDateCount")
//    public Response shipmentDateCount(@RequestBody @Valid CountDTO countDTO) {
//        return renderSuccess(shipmentManageService.getShipmentDateCount(countDTO));
//    }

}

