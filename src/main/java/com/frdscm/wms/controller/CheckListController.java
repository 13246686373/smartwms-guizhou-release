package com.frdscm.wms.controller;


import com.frdscm.wms.commons.base.BaseController;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.CheckList;
import com.frdscm.wms.entity.dto.CheckListDTO;
import com.frdscm.wms.service.ICheckListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dizhang
 * @since 2018-08-02
 */
@RestController
@Api(tags = "盘点管理明细接口")
@RequestMapping("/checkList")
public class CheckListController extends BaseController {

    private final ICheckListService checkListService;

    @Autowired
    public CheckListController(ICheckListService checkListService) {
        this.checkListService = checkListService;
    }

    @ApiOperation("查询")
    @GetMapping("/{checkManageId}")
    public Response getInventoryManageList(@PathVariable("checkManageId") Integer checkManageId) {
        List<CheckList> list = checkListService.getCheckListByCheckManageId(checkManageId);
        return renderSuccess(list);
    }

    @ApiOperation("盘点操作")
    @PostMapping("/check")
    public Response check(@RequestBody @Valid CheckListDTO checkListDTO) {
        CheckList checkList = checkListService.selectById(checkListDTO.getId());
        checkList.setCheckSum(checkListDTO.getCheckSum());
        checkList.setCheckResult(checkListDTO.getCheckResult());
        checkList.setStatus(2);
        checkListService.updateById(checkList);
        return renderSuccess("操作成功");
    }

//    @ApiOperation("修改盘点数量")
//    @PostMapping("/edit")
//    public Response update(@RequestBody @Valid CheckListDTO checkListDTO) {
//        checkListService.update(checkListDTO);
//        return renderSuccess("操作成功");
//    }

}

