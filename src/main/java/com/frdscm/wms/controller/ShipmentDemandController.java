package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.ShipmentDemand;
import com.frdscm.wms.entity.ShipmentManage;
import com.frdscm.wms.entity.dto.MaterialDTO;
import com.frdscm.wms.entity.dto.SelectbatchNumberDTO;
import com.frdscm.wms.entity.dto.ShipmentDemandDTO;
import com.frdscm.wms.entity.dto.ShipmentDemandPageDTO;
import com.frdscm.wms.entity.vo.GetMaterialVO;
import com.frdscm.wms.entity.vo.ShipmentDemandVO;
import com.frdscm.wms.service.IInventoryManageService;
import com.frdscm.wms.service.IShipmentDemandService;
import com.frdscm.wms.service.IShipmentManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 出货需求表 前端控制器
 *
 * @author dizhang
 * @since 2018-07-07
 */
@Api(tags = " 出货需求接口")
@RestController
@RequestMapping("/shipmentDemand")
public class ShipmentDemandController extends BaseController {


    private final IShipmentDemandService shipmentDemandService;
    private final IShipmentManageService shipmentManageService;
    private final IInventoryManageService inventoryManageService;

    @Autowired
    public ShipmentDemandController(IShipmentDemandService shipmentDemandService, IShipmentManageService shipmentManageService, IInventoryManageService inventoryManageService) {
        this.shipmentDemandService = shipmentDemandService;
        this.shipmentManageService = shipmentManageService;
        this.inventoryManageService = inventoryManageService;
    }

    @ApiOperation("添加出货需求信息列表")
    @PostMapping("/add")
    public Response add(@RequestBody @Valid ShipmentDemandDTO shipmentDemandDTO) throws UnsupportedEncodingException {
        return renderSuccess(shipmentDemandService.save(shipmentDemandDTO, getUser()));
    }

    @ApiOperation("修改出货需求信息列表")
    @PostMapping("/edit")
    public Response edit(@RequestBody @Valid ShipmentDemandDTO shipmentDemandDTO) {
        if (null == shipmentDemandDTO.getId()) {
            return renderError("出货需求ID不能为空");
        }
        return renderSuccess(shipmentDemandService.edit(shipmentDemandDTO));
    }

    @ApiOperation("出货需求信息列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid ShipmentDemandPageDTO shipmentDemandPageDTO) {
        Page<ShipmentDemandVO> page = new Page<>(shipmentDemandPageDTO.getPageNo(), shipmentDemandPageDTO.getPageSize());
        return renderSuccess(shipmentDemandService.getShipmentDemandByPageList(page, shipmentDemandPageDTO));
    }

    @ApiOperation("确认出货需求")
    @GetMapping("/confirm/{id}")
    public Response onConfirm(@PathVariable("id") Integer id) throws UnsupportedEncodingException {
        ShipmentDemand shipmentDemand = shipmentDemandService.selectById(id);
        if (shipmentDemand.getStatus() != 1) {
            return renderError("操作失败，请检查出货需求状态");
        }
        shipmentDemandService.confirmDemand(shipmentDemand, getUser());
        return renderSuccess("操作成功");
    }

    @ApiOperation("取消出货需求")
    @GetMapping("/cancel/{id}")
    public Response onCancel(@PathVariable("id") Integer id) {
        ShipmentManage shipmentManage = shipmentManageService.selectShipmentManageByShipmentDemandId(id);
        if (shipmentManage != null && shipmentManage.getStatus() > 1) {
            return renderError("操作失败，无法取消已开始出货的收货需求");
        }
        ShipmentDemand shipmentDemand = shipmentDemandService.selectById(id);
        if (shipmentDemand.getStatus() != 2) {
            return renderError("操作失败，请检查出货需求状态");
        }
        if ("送货上门".equals(shipmentDemand.getShipmentMethodName())) {
            return renderError("送货上门需求不能取消");
        }
        shipmentDemandService.cancelDemand(shipmentDemand, shipmentManage);
        return renderSuccess("操作成功");
    }

//    @ApiOperation("按条件查询出货需求列表")
//    @PostMapping("/getInventoryManageListByShipment")
//    public Response getInventoryManageListByShipment(@RequestBody @Valid InventoryManageByShipmentPageDTO inventoryManageByShipmentPageDTO) {
//        Page<InventoryManage> page = new Page<>(inventoryManageByShipmentPageDTO.getPageNo(), inventoryManageByShipmentPageDTO.getPageSize());
//        return renderSuccess(inventoryManageService.getInventoryManageListByShipment(page, inventoryManageByShipmentPageDTO));
//    }

    @ApiOperation("查询批次号")
    @PostMapping("/getBatchNumberList")
    public Response getBatchNumberList(@RequestBody @Valid SelectbatchNumberDTO selectbatchNumberDTO) {
        List<String> getBatchNumberList = inventoryManageService.getBatchNumberList(selectbatchNumberDTO);
        return renderSuccess(getBatchNumberList);
    }

    @ApiOperation("查询物料信息")
    @PostMapping("/getMaterialList")
    public Response getMaterialList(@RequestBody @Valid MaterialDTO materialDTO) {
        List<GetMaterialVO> getMaterialList = inventoryManageService.getMaterialList(materialDTO);
        return renderSuccess(getMaterialList);
    }

    @ApiOperation("删除出货需求")
    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Integer id) {
        shipmentDemandService.deleteByShipmentDemandId(id);
        return renderSuccess("操作成功");
    }


}

