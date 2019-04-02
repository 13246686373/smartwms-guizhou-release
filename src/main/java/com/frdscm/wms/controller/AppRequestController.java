package com.frdscm.wms.controller;

import com.frdscm.wms.WmsApplication;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.dto.*;
import com.frdscm.wms.entity.vo.ReceiptDemandAppVO;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: dizhang
 * @Date: 2018/7/13
 * @Desc:
 **/
@Api(tags = "APP接口")
@RestController
@RequestMapping("/appRequest")
public class AppRequestController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(WmsApplication.class);

    private final IWarehouseService warehouseService;
    private final IReceiptListService receiptListService;
    private final IReceiptDemandService receiptDemandService;
    private final IReceiptManageService receiptManageService;
    private final IInventoryManageService inventoryManageService;
    private final ICheckListService checkListService;
    private final ICheckManageService checkManageService;
    private final IShipmentListService shipmentListService;
    private final IShipmentDemandService shipmentDemandService;
    private final IShipmentCargoDetailsService shipmentCargoDetailsService;
    private final IShipmentScanListService shipmentScanListService;
    private final ProjectService projectService;

    @Autowired
    public AppRequestController(IWarehouseService warehouseService, IReceiptListService receiptListService,
                                IReceiptDemandService receiptDemandService,
                                IReceiptManageService receiptManageService,
                                IInventoryManageService inventoryManageService, ICheckListService checkListService, ICheckManageService checkManageService,
                                ProjectService projectService, IShipmentListService shipmentListService, IShipmentDemandService shipmentDemandService, IShipmentCargoDetailsService shipmentCargoDetailsService, IShipmentScanListService shipmentScanListService) {
        this.warehouseService = warehouseService;
        this.receiptListService = receiptListService;
        this.receiptDemandService = receiptDemandService;
        this.receiptManageService = receiptManageService;
        this.inventoryManageService = inventoryManageService;
        this.checkListService = checkListService;
        this.checkManageService = checkManageService;
        this.shipmentListService = shipmentListService;
        this.shipmentDemandService = shipmentDemandService;
        this.shipmentCargoDetailsService = shipmentCargoDetailsService;
        this.shipmentScanListService = shipmentScanListService;
        this.projectService = projectService;
    }

    @ApiOperation("获取所有仓库列表")
    @PostMapping("/getWarehouseList")
    public Response getWarehouseList() {
        return renderSuccess(warehouseService.getWarehouseList());
    }

    @ApiOperation("查询收货列表")
    @PostMapping("receiptDemandList")
    public Response receiptDemandList(@RequestBody @Valid WarehouseIdDTO warehouseIdDTO) {
        List<ReceiptDemandAppVO> receiptDemandList = receiptDemandService.receiptDemandAppVOList(warehouseIdDTO.getWarehouseId());
        return renderSuccess(receiptDemandList);
    }

    @ApiOperation("收货操作")
    @PostMapping("/receiptListAdd")
    public Response receiptListAdd(@RequestBody @Valid ReceiptListAppDTO receiptListAppDTO) {
        ReceiptList receiptList = receiptListService.updateApp(receiptListAppDTO);
        return renderSuccess(receiptList);
    }

    @ApiOperation("修改收货状态为待入储")
    @GetMapping("/updateStatus/{receiptManageId}")
    public Response onConfirmReceipt(@PathVariable("receiptManageId") Integer receiptManageId) {
        //修改收货状态为确认状态
        receiptManageService.updateStatus(receiptManageId, 4);
        return renderSuccess("操作成功");
    }

    @ApiOperation("待上架列表")
    @PostMapping("/theShelfList")
    public Response theShelfList(@RequestBody @Valid WarehouseIdDTO warehouseIdDTO) {
        List<ReceiptDemandAppVO> receiptDemandList = receiptDemandService.getReceipetManageByShelf(warehouseIdDTO.getWarehouseId());
        return renderSuccess(receiptDemandList);
    }

    @ApiOperation("收货上架")
    @PostMapping("/receiptDemandPickUp")
    public Response receiptDemandPickUp(@RequestBody @Valid ReceiptDemandPickUpDTO receiptDemandPickUpDTO) throws UnsupportedEncodingException {
        ReceiptList receiptList = receiptDemandService.getReceiptListByBoardNumber(receiptDemandPickUpDTO.getBoardNumber());
        if (receiptList == null) {
            return renderError("该货物已有储位请选择移位操作");
        }
        String returnStr = inventoryManageService.addInventoryManage(receiptList, receiptDemandPickUpDTO, getUser());
        return renderSuccess(returnStr);
    }

    @ApiOperation("获取单位")
    @GetMapping("/getBillUnitByUnitId/{unitId}/{materialItemNumber}")
    public Response getBillUnitByUnitId(@PathVariable("unitId") Integer unitId, @PathVariable("materialItemNumber") String materialItemNumber) {
        return renderSuccess(projectService.getByMaterialItemNumber(unitId, materialItemNumber, getCompanyId()));
    }

    @ApiOperation("盘点列表")
    @PostMapping("/checkManageList")
    public Response checkManageList(@RequestBody @Valid WarehouseIdDTO warehouseIdDTO) {
        return renderSuccess(checkManageService.checkManageList(warehouseIdDTO.getWarehouseId()));
    }

    @ApiOperation("盘点操作(查询盘点库位)")
    @GetMapping("/checkList/{checkManageId}")
    public Response checkListList(@PathVariable("checkManageId") Integer checkManageId) {
        return renderSuccess(checkListService.getCheckListByCheckManageIdApp(checkManageId));
    }

    @ApiOperation("盘点操作(移除盘点库位)")
    @PostMapping("/checkListRemove")
    public Response checkListRemove(@RequestBody @Valid CheckListRemoveDTO checkListRemoveDTO) {
        checkListService.checkListRemove(checkListRemoveDTO.getWarehouseStorageNumber(), checkListRemoveDTO.getCheckManageId());
        return renderSuccess("操作成功");
    }

    @ApiOperation("盘点操作(查询盘点信息)")
    @PostMapping("/checkList")
    public Response checkList(@RequestBody @Valid GetCheckListDTO getCheckListDTO) {
        return renderSuccess(checkListService.getCheckList(getCheckListDTO));
    }

    @ApiOperation("盘点操作")
    @PostMapping("/check")
    public Response check(@RequestBody @Valid CheckListAppDTO checkListAppDTO) {
        checkListService.checkUpdate(checkListAppDTO);
        return renderSuccess("操作成功");
    }

    @ApiOperation("完成盘点计划 传checkManageId")
    @GetMapping("/confirmCheck/{id}")
    public Response confirmCheck(@PathVariable("id") Integer id) {
        checkManageService.confirm(id);
        return renderSuccess("操作成功");
    }

    @ApiOperation("拣货任务")
    @PostMapping("/shipmentTaskList")
    public Response shipmentTaskList(@RequestBody @Valid WarehouseIdDTO warehouseIdDTO) {
        return renderSuccess(shipmentDemandService.getShipmentDemandByTaskList(warehouseIdDTO.getWarehouseId()));
    }

//    @ApiOperation("移除拣货任务")
//    @GetMapping("/shipmentTaskListRemove/{shipmentDemandId}")
//    public Response shipmentTaskListRemove(@PathVariable("shipmentDemandId") Integer shipmentId) {
//        shipmentManageService.removeTask(shipmentId);
//        return renderSuccess("操作成功");
//    }

    @ApiOperation("已拣明细")
    @GetMapping("/shipmentList/{shipmentDemandId}")
    public Response shipmentTaskListApp(@PathVariable("shipmentDemandId") Integer shipmentId) {
        return renderSuccess(shipmentListService.getShipmentListByApp(shipmentId));
    }

    @ApiOperation("查询拣货明细(定点拣货，先进先出)")
    @PostMapping("/shipmentTaskDetail/{shipmentDemandId}")
    public Response shipmentTaskDetail(@PathVariable("shipmentDemandId") Integer shipmentId) {
        return renderSuccess(shipmentListService.getShimentListByshipmentTask(shipmentId));
    }

    @ApiOperation("查询拣货明细(随机拣货)")
    @GetMapping("/shipmentTaskDetailRandom/{shipmentDemandId}")
    public Response shipmentTaskDetailRandom(@PathVariable("shipmentDemandId") Integer shipmentId) {
        return renderSuccess(shipmentCargoDetailsService.shipmentTaskDetailRandom(shipmentId));
    }

    @ApiOperation("拣货扫描提交")
    @PostMapping("/shipmentScanning")
    public Response scanning(@RequestBody @Valid ScanningDTO scanningDTO) {
        if (scanningDTO.getType() == 1) {
            Map<String, BigDecimal> resultMap = shipmentListService.getScanningRandom(scanningDTO);
            if (resultMap != null) {
                return renderSuccess(resultMap);
            }
        } else {
            Map<String, BigDecimal> resultMap = shipmentListService.getScanning(scanningDTO);
            if (resultMap != null) {
                return renderSuccess(resultMap);
            }
        }
        return renderSuccess("操作成功");
    }

    @ApiOperation("移库")
    @PostMapping("/moveWereHouseStorage")
    public Response moveWereHouseStorage(@RequestBody @Valid MoveWereHouseStorageAppDTO moveWereHouseStorageDTO) {
        inventoryManageService.moveWarehouseStorageApp(moveWereHouseStorageDTO);
        return renderSuccess("操作成功");
    }

    @ApiOperation("货物查询")
    @PostMapping("/freightInquiry")
    public Response freightInquiry(@RequestBody @Valid FreightInquiryDTO freightInquiryDTO) {
        return renderSuccess(inventoryManageService.freightInquiry(freightInquiryDTO));
    }

    @ApiOperation("拆板查询物料信息")
    @PostMapping("/dismantlingPlateInformation")
    public Response dismantlingPlateInformation(@RequestBody @Valid DismantlingPlateAppDTO dismantlingPlateAppDTO) {
        return renderSuccess(inventoryManageService.getInventoryManageByWarehouseIdAndBoardNumber(dismantlingPlateAppDTO));
    }

    @ApiOperation("拆板")
    @PostMapping("/dismantlingPlate")
    public Response dismantlingPlate(@RequestBody @Valid DismantlingPlateAppDTO dismantlingPlateAppDTO) {
        logger.error("dismantlingPlateAppDTO: {}", dismantlingPlateAppDTO.toString());
        return renderSuccess(inventoryManageService.dismantlingPlateApp(dismantlingPlateAppDTO));
    }

    @ApiOperation("合版")
    @PostMapping("/mergePlate")
    public Response mergePlate(@RequestBody @Valid MergePlateDTO mergePlateDTO) {
        String boardNumber = inventoryManageService.mergePlate(mergePlateDTO);
        return renderSuccess(boardNumber);
    }

    @ApiOperation("修改库存状态")
    @PostMapping("/updateStatus")
    public Response updateStatus(@RequestBody @Valid UpdateStatusDTO updateStatusDTO) {
        inventoryManageService.updateStatus(updateStatusDTO.getStatus(), updateStatusDTO.getId());
        return renderSuccess("操作成功");
    }

    @ApiOperation("出货扫描列表")
    @PostMapping("/getOutgoingScanList")
    public Response getOutgoingScanList(@RequestBody @Valid WarehouseIdDTO warehouseIdDTO) {
//        return renderSuccess(outgoingScanManageService.getOutgoingScanManageListByApp(warehouseIdDTO.getWarehouseId()));
        return renderSuccess("");
    }

    @ApiOperation("出货扫描操作")
    @PostMapping("/addOutgoingScanList")
    public Response addOutgoingScanList(@RequestBody @Valid ShipmentScanListDTO shipmentScanListDTO) throws UnsupportedEncodingException {
        shipmentScanListService.save(shipmentScanListDTO, getUser());
        return renderSuccess("操作成功");
    }

    @ApiOperation("出货扫描操作完成")
    @PostMapping("/outgoingScanConfirm/{outgoingScanManageId}")
    public Response addOutgoingScanListConfirm(@PathVariable("outgoingScanManageId") Integer outgoingScanManageId) {
//        shipmentScanListService.updateStatus(2, outgoingScanManageId);
        return renderSuccess("操作成功");
    }


//    @ApiOperation("获取物料明细")
//    @GetMapping("/getMaterialList/{receiptManageId}")
//    public Response getMaterialList(@PathVariable("receiptManageId") Integer receiptManageId) {
//        List<MaterialListAppVO> materialList = receiptListService.getMaterialListByReceiptManageId(receiptManageId);
//        return renderSuccess(materialList);
//    }


}
