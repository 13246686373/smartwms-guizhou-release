package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.WarehouseStorage;
import com.frdscm.wms.entity.dto.UpdateWarehouseStorageDTO;
import com.frdscm.wms.entity.dto.WarehouseStorageDTO;
import com.frdscm.wms.entity.dto.WarehouseStoragePageDTO;
import com.frdscm.wms.service.IWarehouseStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 仓库储位表 前端控制器
 *
 * @author dizhang
 * @since 2018-07-03
 */
@Api(tags = "仓库储位接口")
@RestController
@RequestMapping("/warehouseStorage")
public class WarehouseStorageController extends BaseController {

    private final IWarehouseStorageService warehouseStorageService;

    @Autowired
    public WarehouseStorageController(IWarehouseStorageService warehouseStorageService) {
        this.warehouseStorageService = warehouseStorageService;
    }

    @ApiOperation("添加储位")
    @PostMapping("/add")
    public Response add(@RequestBody @Valid WarehouseStorageDTO warehouseStorageDTO) {
        warehouseStorageService.save(warehouseStorageDTO);
        return renderSuccess("操作成功");
    }

    @ApiOperation("批量添加储位")
    @PostMapping("/batch/add")
    public Response batchAdd(@RequestBody @Valid List<WarehouseStorageDTO> warehouseStorageDTOList) {
        warehouseStorageService.batchSave(warehouseStorageDTOList);
        return renderSuccess("操作成功");
    }

    @ApiOperation("修改储位信息")
    @PostMapping("/edit")
    public Response edit(@RequestBody @Valid WarehouseStorageDTO warehouseStorageDTO) {
        if (null == warehouseStorageDTO.getId()) {
            return renderError("储位ID不能为空");
        }
        warehouseStorageService.edit(warehouseStorageDTO);
        return renderSuccess("操作成功");
    }

    @ApiOperation("删除储位信息")
    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Integer id) {
        warehouseStorageService.delete(id);
        return renderSuccess("操作成功");
    }

    @ApiOperation("批量删除储位信息")
    @PostMapping("/batch/delete")
    public Response batchDelete(@RequestBody List<Integer> ids) {
        warehouseStorageService.batchDelete(ids);
        return renderSuccess("操作成功");
    }

    @ApiOperation("储位信息列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid WarehouseStoragePageDTO warehouseStoragePageDTO) {
        Page<WarehouseStorage> warehouseStorageDTOPage = new Page<>(warehouseStoragePageDTO.getPageNo(), warehouseStoragePageDTO.getPageSize(), "update_time", true);
        EntityWrapper<WarehouseStorage> ew = new EntityWrapper<>();
        ew.eq("warehouse_id", warehouseStoragePageDTO.getWarehouseId());
        return renderSuccess(warehouseStorageService.selectPage(warehouseStorageDTOPage, ew));
    }

    @ApiOperation("修改储位信息")
    @PostMapping("/updateWareHouseStorage")
    public Response updateWareHouseStorage(@RequestBody @Valid UpdateWarehouseStorageDTO updateWarehouseStorageDTO) {
        warehouseStorageService.updateWareHouseStorage(updateWarehouseStorageDTO.getWareHouseStorageGaradeId(), updateWarehouseStorageDTO.getWareHouseStorageId());
        return renderSuccess("操作成功");
    }

}

