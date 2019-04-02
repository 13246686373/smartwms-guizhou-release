package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.Warehouse;
import com.frdscm.wms.entity.dto.WarehouseDTO;
import com.frdscm.wms.entity.dto.WarehousePageDTO;
import com.frdscm.wms.entity.vo.WarehouseVO;
import com.frdscm.wms.mapper.WarehouseStorageMapper;
import com.frdscm.wms.service.IWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 仓库表 前端控制器
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Api(tags = "仓库数据接口")
@RestController
@RequestMapping("/warehouse")
public class WarehouseController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    private final IWarehouseService warehouseService;
    private final WarehouseStorageMapper warehouseStorageMapper;

    @Autowired
    public WarehouseController(IWarehouseService warehouseService, WarehouseStorageMapper warehouseStorageMapper) {
        this.warehouseService = warehouseService;
        this.warehouseStorageMapper = warehouseStorageMapper;
    }

    @ApiOperation("添加或者修改仓库信息")
    @PostMapping("/add")
    public Response add(@RequestBody @Valid WarehouseDTO warehouseDTO) throws UnsupportedEncodingException {
        return renderSuccess(warehouseService.save(warehouseDTO, getUser()));
    }

    @ApiOperation("修改仓库信息")
    @PostMapping("/edit")
    public Response edit(@RequestBody @Valid WarehouseDTO warehouseDTO) {
        if (null == warehouseDTO.getId()) {
            return renderError("仓库ID不能为空");
        }
        warehouseService.edit(warehouseDTO);
        return renderSuccess("操作成功");
    }

    @ApiOperation("仓库规模汇总")
    @GetMapping("/scale/summary/{id}")
    public Response scaleSummary(@PathVariable("id") Integer id) {
        return renderSuccess(warehouseStorageMapper.getScaleSummary(id));
    }

    @ApiOperation("删除仓库信息")
    @GetMapping("/delete/{id}")
    public Response edit(@PathVariable("id") Integer id) {
        warehouseService.delete(id);
        return renderSuccess("操作成功");
    }

    @ApiOperation("仓库信息列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid WarehousePageDTO warehousePageDTO) {
        Page<WarehouseVO> warehouseDTOPage = new Page<>(warehousePageDTO.getPageNo(), warehousePageDTO.getPageSize());
        return renderSuccess(warehouseService.selectByPage(warehouseDTOPage, warehousePageDTO));
    }

    @ApiOperation("获取仓库信息")
    @GetMapping("/{id}")
    public Response list(@PathVariable Integer id) {
        return renderSuccess(warehouseService.selectById(id));
    }

    @ApiOperation(value = "Order Feign", hidden = true)
    @PostMapping("/list/ids")
    public List<Warehouse> list(@RequestBody List<Integer> idList) {
        if (idList == null || idList.size() < 1) {
            return new ArrayList<>();
        }
        return warehouseService.selectBatchIds(idList);
    }

    @ApiOperation(value = "仓库信息列表（其他模块调用）", hidden = true)
    @PostMapping("/getList")
    public Page<WarehouseVO> getList(@RequestBody @Valid WarehousePageDTO warehousePageDTO) {
        Page<WarehouseVO> warehouseDTOPage = new Page<>(warehousePageDTO.getPageNo(), warehousePageDTO.getPageSize());
        logger.info("其他模块调用仓库信息列表返回的数据：--> {}", warehouseDTOPage.getRecords().toString());
        return warehouseService.selectByPage(warehouseDTOPage, warehousePageDTO);
    }
}

