package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.ShipmentDemand;
import com.frdscm.wms.entity.ShipmentList;
import com.frdscm.wms.entity.ShipmentManage;
import com.frdscm.wms.entity.dto.ShipmentListDTO;
import com.frdscm.wms.entity.dto.ShipmentListPageDTO;
import com.frdscm.wms.mapper.InventoryManageMapper;
import com.frdscm.wms.mapper.ShipmentCargoDetailsMapper;
import com.frdscm.wms.service.IShipmentDemandService;
import com.frdscm.wms.service.IShipmentListService;
import com.frdscm.wms.service.IShipmentManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 拣货列表 前端控制器
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Api(tags = " 拣货列表")
@RestController
@RequestMapping("/shipmentList")
public class ShipmentListController extends BaseController {

    private final IShipmentListService shipmentListService;
    private final IShipmentManageService shipmentManageService;
    private final ShipmentCargoDetailsMapper shipmentCargoDetailsMapper;
    private final InventoryManageMapper inventoryManageMapper;
    private final IShipmentDemandService shipmentDemandService;

    @Autowired
    public ShipmentListController(IShipmentListService shipmentListService, IShipmentDemandService shipmentDemandService, InventoryManageMapper inventoryManageMapper, ShipmentCargoDetailsMapper shipmentCargoDetailsMapper, IShipmentManageService shipmentManageService) {
        this.shipmentListService = shipmentListService;
        this.shipmentManageService = shipmentManageService;
        this.shipmentCargoDetailsMapper = shipmentCargoDetailsMapper;
        this.inventoryManageMapper = inventoryManageMapper;
        this.shipmentDemandService = shipmentDemandService;
    }

    @ApiOperation("拣货明细")
    @PostMapping("list")
    public Response list(@RequestBody @Valid ShipmentListPageDTO shipmentListPageDTO) {
        Page<ShipmentList> page = new Page<>(shipmentListPageDTO.getPageNo(), shipmentListPageDTO.getPageSize());
        return renderSuccess(shipmentListService.getShipmentListByPageShipmentManageId(page, shipmentListPageDTO.getShipmentManageId()));
    }

    @ApiOperation("添加拣货")
    @PostMapping("addPick")
    public Response addPick(@RequestBody ShipmentListDTO shipmentListDTO) {
        return renderSuccess(shipmentListService.addPick(shipmentListDTO));
    }

    @ApiOperation("板货明细列表")
    @GetMapping("boardDetail/{shipmentManageId}")
    public Response boardDetail(@PathVariable Integer shipmentManageId) {
        ShipmentManage shipmentManage = shipmentManageService.selectById(shipmentManageId);
        ShipmentDemand shipmentDemand = shipmentDemandService.selectById(shipmentManage.getShipmentDemandId());
        List<String> materialNumbers = shipmentCargoDetailsMapper.getMaterialNumbers(shipmentManage.getShipmentDemandId());
        return renderSuccess(inventoryManageMapper.getInventoryManageByMaterial(shipmentDemand.getClientId(), shipmentDemand.getWarehouseId(), materialNumbers));
    }

    @ApiOperation("删除拣货")
    @GetMapping("delete/{id}")
    public Response deletePick(@PathVariable Integer id) {
        shipmentListService.delete(id);
        return renderSuccess("Successful");
    }

    @ApiOperation("一键拣货")
    @GetMapping("quicklyPick/{shipmentManageId}")
    public Response quicklyPick(@PathVariable Integer shipmentManageId) {
        shipmentListService.quicklyPick(shipmentManageId);
        Page<ShipmentList> page = new Page<>(1, 10);
        return renderSuccess(shipmentListService.getShipmentListByPageShipmentManageId(page, shipmentManageId));
    }
}

