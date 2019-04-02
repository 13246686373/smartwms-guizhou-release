package com.frdscm.wms.feign;

import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.bo.materials.Materials;
import com.frdscm.wms.entity.dto.UnitMappingDTO;
import com.frdscm.wms.feign.hystrix.ProjectServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/8/15
 * @Desc:
 **/
@FeignClient(value = "project-service", fallback = ProjectServiceImpl.class)
public interface ProjectService {
    /**
     * @param unitId
     * @return
     */
    @RequestMapping(value = "project/cooperationProject/getBillUnitByUnitId/{unitId}", method = RequestMethod.GET)
    UnitMappingDTO getBillUnitByUnitId(@PathVariable("unitId") Integer unitId, @RequestHeader("companyId") Integer companyId);

    @RequestMapping(value = "project/materials/getByMaterialItemNumberAndClientId", method = RequestMethod.GET)
    List<Materials> getByMaterialItemNumberAndClientId(@RequestParam("materialItemNumbers") List<String> materialItemNumbers, @RequestParam("clientId") Integer clientId, @RequestHeader("companyId") Integer companyId);

    @RequestMapping(value = "project/materials/getByMaterialItemNumber/{unitId}/{materialItemNumber}", method = RequestMethod.POST)
    UnitMappingDTO getByMaterialItemNumber(@PathVariable("unitId") Integer unitId, @PathVariable("materialItemNumber") String materialItemNumber, @RequestHeader("companyId") Integer companyId);

    @RequestMapping(value = "project/materials/save", method = RequestMethod.POST)
    Response materialsSave(@RequestBody Materials materials, @RequestHeader("companyId") Integer companyId);
}
