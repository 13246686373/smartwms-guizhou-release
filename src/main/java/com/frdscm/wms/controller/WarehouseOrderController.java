package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.dto.WarehouseOrderDTO;
import com.frdscm.wms.entity.dto.WarehouseOrderPageDTO;
import com.frdscm.wms.entity.vo.WarehouseOrderVO;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.service.IWarehouseOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dizhang
 * @since 2019-04-05
 */
@Api(tags = "仓库订单数据接口")
@RestController
@RequestMapping("/warehouseOrder")
public class WarehouseOrderController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(WarehouseOrderController.class);

    @Autowired
    private IWarehouseOrderService warehouseOrderService;

    @ApiOperation("添加或者修改仓库订单")
    @PostMapping("/save")
    public Response add(@RequestBody @Valid WarehouseOrderDTO warehouseOrderDTO) throws UnsupportedEncodingException {
        return renderSuccess(warehouseOrderService.save(warehouseOrderDTO, getUser()));
    }

    @ApiOperation("删除仓库订单信息")
    @PutMapping("/delete/{id}/{orderNo}")
    public Response delete(@PathVariable("id") Integer id, @PathVariable("orderNo") String orderNo) {
        warehouseOrderService.delete(id, orderNo);
        return renderSuccess("操作成功");
    }

    @ApiOperation("仓库订单信息列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid WarehouseOrderPageDTO warehouseOrderPageDTO) {
        // 调用查询分页接口
        Page<WarehouseOrderVO> warehouseOrderDTOPage = new Page<>(warehouseOrderPageDTO.getPageNo(), warehouseOrderPageDTO.getPageSize());
        return renderSuccess(warehouseOrderService.selectByPage(warehouseOrderDTOPage, warehouseOrderPageDTO));
    }

    @ApiOperation("根据订单ID获取仓库订单信息")
    @GetMapping("/{id}")
    public Response getWarehouseOrder(@PathVariable Integer id) {
        return renderSuccess(warehouseOrderService.getWarehouseOrderById(id));
    }

//    @ApiOperation("获取仓库订单应收对账信息")
//    @GetMapping("/accountsReceivableList")
//    public Response accountsReceivableList(@PathVariable Integer id) {
//        return renderSuccess(warehouseOrderService.getWarehouseOrderById(id));
//    }

}

