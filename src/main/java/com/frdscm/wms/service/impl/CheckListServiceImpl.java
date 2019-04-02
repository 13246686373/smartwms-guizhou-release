package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.CheckList;
import com.frdscm.wms.entity.CheckManage;
import com.frdscm.wms.entity.dto.CheckListAppDTO;
import com.frdscm.wms.entity.dto.CheckListDTO;
import com.frdscm.wms.entity.dto.GetCheckListDTO;
import com.frdscm.wms.entity.dto.UnitMappingDTO;
import com.frdscm.wms.entity.vo.CheckListVO;
import com.frdscm.wms.feign.ProjectService;
import com.frdscm.wms.mapper.CheckListMapper;
import com.frdscm.wms.mapper.CheckManageMapper;
import com.frdscm.wms.service.ICheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dizhang
 * @since 2018-08-02
 */
@Service
public class CheckListServiceImpl extends ServiceImpl<CheckListMapper, CheckList> implements ICheckListService {


    private final CheckListMapper checkListMapper;
    private final ProjectService projectService;
    private final CheckManageMapper checkManageMapper;

    @Autowired
    public CheckListServiceImpl(CheckListMapper checkListMapper, ProjectService projectService, CheckManageMapper checkManageMapper) {
        this.checkListMapper = checkListMapper;
        this.projectService = projectService;
        this.checkManageMapper = checkManageMapper;
    }

    @Override
    public List<CheckList> getCheckListByCheckManageId(Integer checkManageId) {
        return checkListMapper.getCheckListByCheckManageId(checkManageId);
    }

    @Override
    public String getCheckListByCheckManageIdApp(Integer checkManageId) {
        List<String> list = checkListMapper.getCheckListByCheckManageIdApp(checkManageId);
        if (list == null || list.size() < 1) {
            throw new BusinessException("当前已无需要盘点的任务");
        }
        return list.get(0);
    }

    @Override
    public void checkListRemove(String warehouseStorageNum, Integer checkManageId) {
        checkListMapper.updateStatusByWarehouseStorageNumber(warehouseStorageNum, checkManageId);
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(CheckListDTO checkListDTO) {
        CheckList checkList = BeanUtils.copy(checkListDTO, CheckList.class);
        super.updateById(checkList);
    }

    @Override
    public void checkUpdate(CheckListAppDTO checkListAppDTO) {
        CheckList checkList = super.selectById(checkListAppDTO.getId());
        CheckManage checkManage = checkManageMapper.selectById(checkList.getCheckManageId());
        checkList.setCheckResult(checkListAppDTO.getCheckResult());
        if ("盲盘".equals(checkManage.getCheckTypeName())) {
            if (checkListAppDTO.getCheckSum().equals(checkList.getQuantityCount())) {
                checkList.setCheckResult(1);
            } else {
                checkList.setCheckResult(2);
            }
        }
        checkList.setCheckSum(checkListAppDTO.getCheckSum());
        checkList.setStatus(2);
        super.updateById(checkList);
    }

    @Override
    public CheckListVO getCheckList(GetCheckListDTO getCheckListDTO) {
        CheckList checkList = checkListMapper.getCheckList(getCheckListDTO.getBoardNumber(), getCheckListDTO.getWarehouseStorageNumber(), getCheckListDTO.getCheckManageId());
        if (checkList == null) {
            throw new BusinessException("该板号不在盘点任务内");
        }
        CheckListVO checkListVO = BeanUtils.copy(checkList, CheckListVO.class);
        checkListVO.setId(checkList.getId());
        checkListVO.setBoardNumber(checkList.getBoardNumber());
        checkListVO.setWarehouseStorageNumber(checkList.getWarehouseStorageNumber());
        checkListVO.setMaterialNumber(checkList.getMaterialNumber());
        checkListVO.setMaterialName(checkList.getMaterialName());
        checkListVO.setUnit(checkList.getUnit());
        checkListVO.setCheckManageId(checkList.getCheckManageId());
        checkListVO.setUnitId(checkList.getUnitId());
        if (checkList.getUnitType() == 1) {
            checkListVO.setCheckCount(checkList.getQuantityCount());
        } else if (checkList.getUnitType() == 2) {
            checkListVO.setCheckCount(checkList.getBoxCount());
        } else if (checkList.getUnitType() == 3) {
            checkListVO.setCheckCount(1);
        } else if (checkList.getUnitType() == 5) {
            checkListVO.setCheckCount(checkList.getVolume());
        } else if (checkList.getUnitType() == 4) {
            checkListVO.setCheckCount(checkList.getGrossWeight());
        }
        return checkListVO;
    }
}
