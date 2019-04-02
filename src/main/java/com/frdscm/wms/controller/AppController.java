package com.frdscm.wms.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.frdscm.wms.WmsApplication;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.enums.ResponseEnum;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.*;
import com.frdscm.wms.entity.dto.*;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.mapper.*;
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
@RequestMapping("/app")
public class AppController extends BaseController {

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
    private final ReceiptCargoDetailsMapper receiptCargoDetailsMapper;
    private final ReceiptManageMapper receiptManageMapper;
    private final ShipmentManageMapper shipmentManageMapper;
    private final ReceiptListMapper receiptListMapper;
    private final ShipmentListMapper shipmentListMapper;
    private final ReceiptScanListMapper receiptScanListMapper;
    private final ShipmentScanListMapper shipmentScanListMapper;
    private final ProjectService projectService;

    @Autowired
    public AppController(IWarehouseService warehouseService, ShipmentManageMapper shipmentManageMapper, ShipmentListMapper shipmentListMapper, ReceiptListMapper receiptListMapper, ReceiptManageMapper receiptManageMapper, ReceiptScanListMapper receiptScanListMapper, IReceiptListService receiptListService,
                         IReceiptDemandService receiptDemandService,
                         IReceiptManageService receiptManageService,
                         IInventoryManageService inventoryManageService, ICheckListService checkListService, ICheckManageService checkManageService,
                         ProjectService projectService, ShipmentScanListMapper shipmentScanListMapper, ReceiptCargoDetailsMapper receiptCargoDetailsMapper, IShipmentListService shipmentListService, IShipmentDemandService shipmentDemandService, IShipmentCargoDetailsService shipmentCargoDetailsService, IShipmentScanListService shipmentScanListService) {
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
        this.receiptCargoDetailsMapper = receiptCargoDetailsMapper;
        this.receiptManageMapper = receiptManageMapper;
        this.receiptScanListMapper = receiptScanListMapper;
        this.receiptListMapper = receiptListMapper;
        this.shipmentManageMapper = shipmentManageMapper;
        this.shipmentListMapper = shipmentListMapper;
        this.shipmentScanListMapper = shipmentScanListMapper;
    }

    @ApiOperation("获取所有仓库列表")
    @PostMapping("/getWarehouseList")
    public Response getWarehouseList() {
        return renderSuccess(warehouseService.getWarehouseList());
    }


    @ApiOperation("收货单号扫描")
    @PostMapping("/receiptSingleNumberScan")
    public Response receiptSingleNumberScan(@RequestParam String singleNumber) {
        EntityWrapper<ReceiptDemand> ew = new EntityWrapper<>();
        ew.eq("single_number", singleNumber);
        ReceiptDemand receiptDemand = receiptDemandService.selectOne(ew);
        if (receiptDemand == null) {
            return renderError("收货单号不存在，请确认");
        }
        ReceiptManage receiptManage = receiptManageMapper.selectReceiptManageByReceiptDemandId(receiptDemand.getId());
        if (isCompleteReceipt(receiptManage.getId())) {
            return renderSuccess(ResponseEnum.RECEIPT_SCAN_SUCCESS);
        }
        return renderSuccess(receiptManage);
    }

    @ApiOperation("收货板号扫描")
    @PostMapping("/receiptBoardScan")
    public Response receiptBoardScan(@RequestParam Integer id, @RequestParam String boardNumber) {
        ReceiptManage receiptManage = receiptManageMapper.selectById(id);
        ReceiptList receiptList = isReceiptBoardNumberExist(receiptManage.getId(), boardNumber);
        if (receiptList == null) {
            return renderError("该板号不在该收货内或不存在，请确认");
        }
        if (receiptList.getIsReceiptScan() != 1) {
            return renderSuccess("该板上物料未开启收货扫描");
        }
        if (isCompleteReceipt(receiptManage.getId())) {
            return renderSuccess(ResponseEnum.RECEIPT_SCAN_SUCCESS);
        }
        if (isCompleteReceiptBoxScan(receiptList, boardNumber)) {
            return renderSuccess(ResponseEnum.RECEIPT_BOARD_SCAN_SUCCESS);
        }
        return renderSuccess("Successful");
    }

    @ApiOperation("收货箱号扫描")
    @PostMapping("/receiptBoxScan")
    public Response receiptBoxScan(@RequestParam Integer id, @RequestParam String boardNumber, @RequestParam String boxNumber) {
        ReceiptManage receiptManage = receiptManageMapper.selectById(id);
        ReceiptList receiptList = isReceiptBoardNumberExist(receiptManage.getId(), boardNumber);
        if (receiptList == null) {
            return renderError("该板号不在该收货内或不存在，请确认");
        }
        List<Integer> ids = receiptScanListMapper.getBoardNumberIsNull(id, receiptList.getMaterialNumber(), boxNumber);
        ReceiptScanList receiptScanList;
        if (ids != null && ids.size() > 0) {
            for (Integer rid : ids) {
                receiptScanList = new ReceiptScanList();
                receiptScanList.setId(rid);
                receiptScanList.setBoardNumber(boardNumber);
                receiptScanListMapper.updateById(receiptScanList);
            }
        }
        if (isCompleteReceipt(receiptManage.getId())) {
            return renderSuccess(ResponseEnum.RECEIPT_SCAN_SUCCESS);
        }
        if (isCompleteReceiptBoxScan(receiptList, boardNumber)) {
            return renderSuccess(ResponseEnum.RECEIPT_BOARD_SCAN_SUCCESS);
        }
        int boxCount = receiptScanListMapper.getReceiptScanBoxSerialNum(id, boardNumber, boxNumber);
        if (boxCount >= receiptList.getQuantityScale()) {
            return renderSuccess(ResponseEnum.RECEIPT_BOX_SCAN_SUCCESS);
        }
        return renderSuccess("Successful");
    }

    @ApiOperation("收货序列号扫描")
    @PostMapping("/receiptSequenceScan")
    public Response receiptSequenceScan(@RequestParam Integer id, @RequestParam String boardNumber, @RequestParam String boxNumber, @RequestParam String sequenceNumber) throws UnsupportedEncodingException {
        ReceiptManage receiptManage = receiptManageMapper.selectById(id);
        ReceiptList receiptList = isReceiptBoardNumberExist(receiptManage.getId(), boardNumber);
        if (receiptList == null) {
            return renderError("该板号不在该收货内或不存在，请确认");
        }
        int count = receiptScanListMapper.getBoxSerialNumCount(id, boardNumber, boxNumber, sequenceNumber);
        if (count > 0) {
            return renderError("序列号重复，请确认");
        }
        if (isCompleteReceipt(receiptManage.getId())) {
            return renderSuccess(ResponseEnum.RECEIPT_SCAN_SUCCESS);
        }
        if (isCompleteReceiptBoxScan(receiptList, boardNumber)) {
            return renderSuccess(ResponseEnum.RECEIPT_BOARD_SCAN_SUCCESS);
        }
        int boxCount = receiptScanListMapper.getReceiptScanBoxSerialNum(id, boardNumber, boxNumber);
        if (boxCount >= receiptList.getQuantityScale()) {
            return renderSuccess(ResponseEnum.RECEIPT_BOX_SCAN_SUCCESS);
        }
        ReceiptScanList receiptScanList = new ReceiptScanList();
        receiptScanList.setReceiptManageId(id);
        receiptScanList.setBoardNumber(boardNumber);
        receiptScanList.setMaterialNumber(receiptList.getMaterialNumber());
        receiptScanList.setBoxNum(boxNumber);
        receiptScanList.setBoxSerialNum(sequenceNumber);
        receiptScanList.setOperatorId(getUser().getUserId());
        receiptScanList.setOperatorName(getUser().getUserName());
        receiptScanListMapper.insert(receiptScanList);
        boolean isTrue = isCompleteReceiptBoxScan(receiptList, boardNumber);
        if (isTrue) {
            receiptList.setScanned(1);
            receiptListMapper.updateById(receiptList);
        }
        if (isCompleteReceipt(receiptManage.getId())) {
            return renderSuccess(ResponseEnum.RECEIPT_SCAN_SUCCESS);
        }
        if (isTrue) {
            return renderSuccess(ResponseEnum.RECEIPT_BOARD_SCAN_SUCCESS);
        }
        boxCount = receiptScanListMapper.getReceiptScanBoxSerialNum(id, boardNumber, boxNumber);
        if (boxCount >= receiptList.getQuantityScale()) {
            return renderSuccess(ResponseEnum.RECEIPT_BOX_SCAN_SUCCESS);
        }
        return renderSuccess("Successful");
    }

    @ApiOperation("出货单号扫描")
    @PostMapping("/shipmentSingleNumberScan")
    public Response shipmentSingleNumberScan(@RequestParam String singleNumber) {
        EntityWrapper<ShipmentDemand> ew = new EntityWrapper<>();
        ew.eq("single_number", singleNumber);
        ShipmentDemand shipmentDemand = shipmentDemandService.selectOne(ew);
        if (shipmentDemand == null) {
            return renderError("出货单号不存在，请确认");
        }
        ShipmentManage shipmentManage = shipmentManageMapper.selectShipmentManageByShipmentDemandId(shipmentDemand.getId());
        if (isCompleteShipment(shipmentManage.getId())) {
            return renderSuccess(ResponseEnum.SHIPMENT_SCAN_SUCCESS);
        }
        return renderSuccess(shipmentManage);
    }

    @ApiOperation("出货板号扫描")
    @PostMapping("/shipmentBoardScan")
    public Response shipmentBoardScan(@RequestParam Integer id, @RequestParam String boardNumber) {
        ShipmentManage shipmentManage = shipmentManageMapper.selectById(id);
        ShipmentList shipmentList = isShipmentBoardNumberExist(shipmentManage.getId(), boardNumber);
        if (shipmentList == null) {
            return renderError("该板号不在该出货内或不存在，请确认");
        }
        if (shipmentList.getIsShipmentScan() != 1) {
            return renderError("该板号上物料未开启出货扫描");
        }
        if (isCompleteShipment(shipmentManage.getId())) {
            return renderSuccess(ResponseEnum.SHIPMENT_SCAN_SUCCESS);
        }
        if (isCompleteShipmentBoxScan(shipmentList, boardNumber)) {
            return renderSuccess(ResponseEnum.SHIPMENT_BOARD_SCAN_SUCCESS);
        }
        return renderSuccess("Successful");
    }

    @ApiOperation("出货箱号扫描")
    @PostMapping("/shipmentBoxScan")
    public Response shipmentBoxScan(@RequestParam Integer id, @RequestParam String boardNumber, @RequestParam String boxNumber) {
        ShipmentManage shipmentManage = shipmentManageMapper.selectById(id);
        ShipmentList shipmentList = isShipmentBoardNumberExist(shipmentManage.getId(), boardNumber);
        if (shipmentList == null) {
            return renderError("该板号不在该出货内或不存在，请确认");
        }
        List<Integer> ids = shipmentScanListMapper.getBoardNumberIsNull(id, shipmentList.getMaterialNumber(), boxNumber);
        ShipmentScanList shipmentScanList;
        if (ids != null && ids.size() > 0) {
            for (Integer rid : ids) {
                shipmentScanList = new ShipmentScanList();
                shipmentScanList.setId(rid);
                shipmentScanList.setBoardNumber(boardNumber);
                shipmentScanListMapper.updateById(shipmentScanList);
            }
        }
        if (isCompleteShipment(shipmentManage.getId())) {
            return renderSuccess(ResponseEnum.SHIPMENT_SCAN_SUCCESS);
        }
        if (isCompleteShipmentBoxScan(shipmentList, boardNumber)) {
            return renderSuccess(ResponseEnum.SHIPMENT_BOARD_SCAN_SUCCESS);
        }
        int boxCount = shipmentScanListMapper.getShipmentScanBoxSerialNum(id, boardNumber, boxNumber);
        if (boxCount >= shipmentList.getQuantityCount()) {
            return renderSuccess(ResponseEnum.SHIPMENT_BOX_SCAN_SUCCESS);
        }
        return renderSuccess("Successful");
    }

    @ApiOperation("出货序列化扫描")
    @PostMapping("/shipmentSequenceScan")
    public Response shipmentSerializableScan(@RequestParam Integer id, @RequestParam String boardNumber, @RequestParam String boxNumber, @RequestParam String sequenceNumber) throws UnsupportedEncodingException {
        ShipmentManage shipmentManage = shipmentManageMapper.selectById(id);
        ShipmentList shipmentList = isShipmentBoardNumberExist(shipmentManage.getId(), boardNumber);
        if (shipmentList == null) {
            return renderError("该板号不在该出货内或不存在，请确认");
        }
        Integer count = shipmentScanListMapper.getBoxSerialNumCount(id, boardNumber, boxNumber, sequenceNumber);
        if (count != null && count > 0) {
            return renderError("序列号重复，请确认");
        }
        if (isCompleteShipment(shipmentManage.getId())) {
            return renderSuccess(ResponseEnum.SHIPMENT_SCAN_SUCCESS);
        }
        if (isCompleteShipmentBoxScan(shipmentList, boardNumber)) {
            return renderSuccess(ResponseEnum.SHIPMENT_BOARD_SCAN_SUCCESS);
        }
        int boxCount = shipmentScanListMapper.getShipmentScanBoxSerialNum(id, boardNumber, boxNumber);
        if (boxCount >= shipmentList.getQuantityCount()) {
            return renderSuccess(ResponseEnum.SHIPMENT_BOX_SCAN_SUCCESS);
        }
        ShipmentScanList shipmentScanList = new ShipmentScanList();
        shipmentScanList.setShipmentManageId(id);
        shipmentScanList.setBoardNumber(boardNumber);
        shipmentScanList.setMaterialNumber(shipmentList.getMaterialNumber());
        shipmentScanList.setBoxNum(boxNumber);
        shipmentScanList.setBoxSerialNum(sequenceNumber);
        shipmentScanList.setOperatorId(getUser().getUserId());
        shipmentScanList.setOperatorName(getUser().getUserName());
        shipmentScanListMapper.insert(shipmentScanList);
        boolean isTrue = isCompleteShipmentBoxScan(shipmentList, boardNumber);
        if (isTrue) {
            shipmentList.setScanned(1);
            shipmentListMapper.updateById(shipmentList);
        }
        if (isCompleteShipment(shipmentManage.getId())) {
            return renderSuccess(ResponseEnum.SHIPMENT_SCAN_SUCCESS);
        }
        if (isTrue) {
            return renderSuccess(ResponseEnum.SHIPMENT_BOARD_SCAN_SUCCESS);
        }
        boxCount = shipmentScanListMapper.getShipmentScanBoxSerialNum(id, boardNumber, boxNumber);
        if (boxCount >= shipmentList.getQuantityCount()) {
            return renderSuccess(ResponseEnum.SHIPMENT_BOX_SCAN_SUCCESS);
        }
        return renderSuccess("Successful");
    }

    @ApiOperation("入储查询")
    @PostMapping("/upperShelfQuery")
    public Response upperShelf(@RequestParam String singleNumber) {
        ReceiptManage receiptManage = receiptManageMapper.getReceiptManageBySingleNumber(singleNumber);
        if (receiptManage == null) {
            return renderError("出货单号不存在，请确认");
        }
        if (receiptListMapper.getReceiptListNotShelf(receiptManage.getId()) < 1) {
            return renderError("该出货单号已入储完成，请确认");
        }
        return renderSuccess(receiptManage);
    }

    @ApiOperation("入储")
    @PostMapping("/upperShelf")
    public Response upperShelf(@RequestParam Integer id, @RequestParam String boardNumber, @RequestParam String warehouseStorageNumber) {
        receiptListService.appUpperShelf(id, boardNumber, warehouseStorageNumber);
        if (receiptListMapper.getReceiptListNotShelf(id) < 1) {
            return renderSuccess(ResponseEnum.RECEIPT_UPPER_SHELF_SUCCESS);
        }
        return renderSuccess("Successful");
    }

    @ApiOperation("拣货任务")
    @PostMapping("/shipmentTaskList")
    public Response shipmentTaskList(@RequestBody @Valid WarehouseIdDTO warehouseIdDTO) {
        return renderSuccess(shipmentDemandService.getShipmentDemandByTaskList(warehouseIdDTO.getWarehouseId()));
    }

    @ApiOperation("已拣明细")
    @GetMapping("/shipmentList/{shipmentDemandId}")
    public Response shipmentTaskListApp(@PathVariable("shipmentDemandId") Integer shipmentDemandId) {
        return renderSuccess(shipmentListService.getShipmentListByApp(shipmentDemandId));
    }

    @ApiOperation("查询拣货明细(先进先出)")
    @GetMapping("/shipmentTaskDetail/{shipmentDemandId}")
    public Response shipmentTaskDetail(@PathVariable("shipmentDemandId") Integer shipmentDemandId) {
        return renderSuccess(shipmentListService.getShipmentTaskDetail(shipmentDemandId));
    }

    @ApiOperation("查询拣货明细(随机拣货)")
    @GetMapping("/shipmentTaskDetailRandom/{shipmentDemandId}")
    public Response shipmentTaskDetailRandom(@PathVariable("shipmentDemandId") Integer shipmentId) {
        return renderSuccess(shipmentListService.getShipmentTaskDetailRandom(shipmentId));
    }

    @ApiOperation("拣货先进先出扫描提交")
    @PostMapping("/shipmentTaskDetailSubmit")
    public Response shipmentTaskDetailSubmit(@RequestBody @Valid ScanningDTO scanningDTO) {
        Map<String, BigDecimal> resultMap = shipmentListService.getScanning(scanningDTO);
        if (resultMap != null) {
            return renderSuccess(resultMap);
        }
        return renderSuccess("操作成功");
    }

    @ApiOperation("拣货随机拣货扫描提交")
    @PostMapping("/shipmentTaskDetailRandomSubmit")
    public Response shipmentTaskDetailRandomSubmit(@RequestBody @Valid ScanningDTO scanningDTO) {
        Map<String, BigDecimal> resultMap = shipmentListService.getScanningRandom(scanningDTO);
        if (resultMap != null) {
            return renderSuccess(resultMap);
        }
        return renderSuccess("操作成功");
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

    @ApiOperation("移库")
    @PostMapping("/moveWereHouseStorage")
    public Response moveWereHouseStorage(@RequestBody @Valid MoveWereHouseStorageAppDTO moveWereHouseStorageAppDTO) {
        inventoryManageService.moveWarehouseStorageApp(moveWereHouseStorageAppDTO);
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
    public Response mergePlate(@RequestBody @Valid MergePlateAppDTO mergePlateDTO) {
        return renderSuccess(inventoryManageService.mergePlateApp(mergePlateDTO));
    }

    @ApiOperation("修改库存状态")
    @PostMapping("/updateStatus")
    public Response updateStatus(@RequestBody @Valid UpdateStatusDTO updateStatusDTO) {
        inventoryManageService.updateStatus(updateStatusDTO.getStatus(), updateStatusDTO.getId());
        return renderSuccess("操作成功");
    }

    //-----------------------------------------------------------------------------------------------------

    private boolean isCompleteReceipt(Integer receiptManageId) {
        List<ReceiptList> receiptLists = receiptListMapper.getReceiptListByReceiptManageId(receiptManageId);
        boolean isTrue = false;
        for (ReceiptList receiptList : receiptLists) {
            if (receiptList.getIsReceiptScan() == 1 && receiptList.getScanned() == 0) {
                isTrue = true;
            }
        }
        return !isTrue;
    }

    private ReceiptList isReceiptBoardNumberExist(Integer receiptManageId, String boardNumber) {
        List<ReceiptList> receiptLists = receiptListMapper.getReceiptListByReceiptManageId(receiptManageId);
        ReceiptList receiptList = receiptLists.stream().filter(e -> e.getBoardNumber().equals(boardNumber)).findAny().orElse(null);
        if (receiptList == null) {
            return null;
        }
        return receiptList;
    }

    private boolean isCompleteReceiptBoxScan(ReceiptList receiptList, String boardNumber) {
        return receiptScanListMapper.getReceiptScanBox(receiptList.getReceiptManageId(), boardNumber) >= receiptList.getQuantityCount();
    }

    //------------

    private boolean isCompleteShipment(Integer shipmentManageId) {
        List<ShipmentList> shipmentLists = shipmentListMapper.getShipmentListBytShipmentManageId(shipmentManageId);
        boolean isTrue = false;
        for (ShipmentList shipmentList : shipmentLists) {
            if (shipmentList.getIsShipmentScan() == 1 && shipmentList.getScanned() == 0) {
                isTrue = true;
            }
        }
        return !isTrue;
    }

    private ShipmentList isShipmentBoardNumberExist(Integer shipmentManageId, String boardNumber) {
        List<ShipmentList> shipmentLists = shipmentListMapper.getShipmentListBytShipmentManageId(shipmentManageId);
        ShipmentList shipmentList = shipmentLists.stream().filter(e -> e.getBoardNumber().equals(boardNumber)).findAny().orElse(null);
        if (shipmentList == null) {
            return null;
        }
        return shipmentList;
    }

    private boolean isCompleteShipmentBoxScan(ShipmentList shipmentList, String boardNumber) {
        return shipmentList.getQuantityCount().equals(shipmentScanListMapper.getShipmentScanBox(shipmentList.getShipmentManageId(), boardNumber));
    }
}
