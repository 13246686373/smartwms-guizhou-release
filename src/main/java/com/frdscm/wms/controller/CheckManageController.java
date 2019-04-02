package com.frdscm.wms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.CheckManage;
import com.frdscm.wms.entity.dto.CheckManageDTO;
import com.frdscm.wms.entity.dto.CheckManagePageDTO;
import com.frdscm.wms.entity.dto.CheckManageSelectDTO;
import com.frdscm.wms.entity.dto.UploadAttachment;
import com.frdscm.wms.entity.vo.CheckManageVO;
import com.frdscm.wms.service.ICheckListService;
import com.frdscm.wms.service.ICheckManageService;
import com.frdscm.wms.service.IInventoryManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dizhang
 * @since 2018-08-02
 */
@Api(tags = "盘点管理")
@RestController
@RequestMapping("/checkManage")
public class CheckManageController extends BaseController {

    private final IInventoryManageService inventoryManageService;
    private final ICheckManageService checkManageService;

    @Autowired
    public CheckManageController(IInventoryManageService inventoryManageService, ICheckManageService checkManageService) {
        this.inventoryManageService = inventoryManageService;
        this.checkManageService = checkManageService;
    }

    @ApiOperation("查询")
    @PostMapping("/select")
    public Response getInventoryManageList(@RequestBody @Valid CheckManageSelectDTO checkManageSelectDTO) {
        return renderSuccess(inventoryManageService.getInventoryManageList(checkManageSelectDTO));
    }

    @ApiOperation("添加盘点计划")
    @PostMapping("/add")
    public Response add(@RequestBody @Valid CheckManageDTO checkManageDTO) {
        checkManageService.add(checkManageDTO);
        return renderSuccess("操作成功");
    }

    @ApiOperation("盘点计划列表")
    @PostMapping("/list")
    public Response list(@RequestBody @Valid CheckManagePageDTO checkManageDTO) {
        Page<CheckManageVO> page = new Page<>(checkManageDTO.getPageNo(), checkManageDTO.getPageSize());
        return renderSuccess(checkManageService.selectCheckPage(page, checkManageDTO));
    }

    @ApiOperation("删除盘点计划")
    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Integer id) {
        checkManageService.delete(id);
        return renderSuccess("操作成功");
    }

    @ApiOperation("上传附件")
    @PostMapping("/uploadAttachment")
    public Response uploadAttachment(@RequestBody @Valid UploadAttachment uploadAttachment) {
        CheckManage checkManage = checkManageService.selectById(uploadAttachment.getId());
        checkManage.setFileName(uploadAttachment.getFileName());
        checkManage.setFilePath(uploadAttachment.getFilePath());
        checkManageService.updateById(checkManage);
        return renderSuccess("操作成功");
    }

    @ApiOperation("完成盘点计划")
    @GetMapping("/confirm/{id}")
    public Response confirm(@PathVariable("id") Integer id) {
        checkManageService.confirm(id);
        return renderSuccess("操作成功");
    }

}

