package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.CheckList;
import com.frdscm.wms.entity.CheckManage;
import com.frdscm.wms.entity.dto.CheckManageDTO;
import com.frdscm.wms.entity.dto.CheckManagePageDTO;
import com.frdscm.wms.entity.vo.CheckManageVO;
import com.frdscm.wms.entity.vo.InventoryListVO;
import com.frdscm.wms.mapper.CheckListMapper;
import com.frdscm.wms.mapper.CheckManageMapper;
import com.frdscm.wms.service.ICheckManageService;
import com.frdscm.wms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CheckManageServiceImpl extends ServiceImpl<CheckManageMapper, CheckManage> implements ICheckManageService {

    private final CheckListMapper checkListMapper;
    private final CheckManageMapper checkManageMapper;

    @Autowired
    public CheckManageServiceImpl(CheckListMapper checkListMapper, CheckManageMapper checkManageMapper) {
        this.checkListMapper = checkListMapper;
        this.checkManageMapper = checkManageMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void add(CheckManageDTO checkManageDTO) {
        CheckManage checkManage = BeanUtils.copy(checkManageDTO, CheckManage.class);
        int count = super.baseMapper.countCheckNumber("I" + DateUtil.dayNow());
        String str = String.format("%03d", count);
        //生成运单号 格式 R+yyMMdd+0001
        String sNum = "I" + DateUtil.dayNow() + str;
        checkManage.setCheckNumber(sNum);
        checkManage.setStatus(1);
        super.insert(checkManage);
        if (checkManageDTO.getInventoryManagesList() != null) {
            CheckList checkList;
            for (InventoryListVO inventoryListVO : checkManageDTO.getInventoryManagesList()) {
                checkList = BeanUtils.copy(inventoryListVO, CheckList.class);
                checkList.setCheckManageId(checkManage.getId());
                checkListMapper.insert(checkList);
            }
        }
    }

    @Override
    public Page<CheckManageVO> selectCheckPage(Page<CheckManageVO> page, CheckManagePageDTO checkManagePageDTO) {
        return page.setRecords(checkManageMapper.selectCheckPage(page, checkManagePageDTO));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(Integer checkManageId) {
        checkListMapper.deleteByCheckManageId(checkManageId);
        checkManageMapper.deleteById(checkManageId);
    }

    @Override
    public void confirm(Integer checkManageId) {
        CheckManage checkManage = super.selectById(checkManageId);
        if ((checkManage.getFilePath() != null && !"".equals(checkManage.getFilePath())) || checkListMapper.getNotCheckCount(checkManageId) == 0) {
            checkManage.setStatus(2);
            super.updateById(checkManage);
        } else {
            throw new BusinessException("该盘点任务还有板未盘点完成，操作失败");
        }
    }

    @Override
    public List<CheckManage> checkManageList(Integer warehouseId) {
        return checkManageMapper.checkManageList(warehouseId);
    }
}
