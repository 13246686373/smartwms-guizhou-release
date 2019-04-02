package com.frdscm.wms.feign.hystrix;

import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.bo.materials.Materials;
import com.frdscm.wms.entity.dto.UnitMappingDTO;
import com.frdscm.wms.feign.ProjectService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/8/15
 * @Desc:
 **/
@Component
public class ProjectServiceImpl implements ProjectService {

    @Override
    public UnitMappingDTO getBillUnitByUnitId(Integer unitId, Integer companyId) {
        return null;
    }

    @Override
    public List<Materials> getByMaterialItemNumberAndClientId(List<String> materialItemNumbers, Integer clientId, Integer companyId) {
        return null;
    }

    @Override
    public UnitMappingDTO getByMaterialItemNumber(Integer unitId, String materialItemNumber, Integer companyId) {
        return null;
    }

    @Override
    public Response materialsSave(Materials materials, Integer companyId) {
        return null;
    }
}
