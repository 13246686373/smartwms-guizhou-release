package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.commons.util.NumberGenerateUtil;
import com.frdscm.wms.entity.Warehouse;
import com.frdscm.wms.entity.bo.UserBO;
import com.frdscm.wms.entity.dto.WarehouseDTO;
import com.frdscm.wms.entity.dto.WarehousePageDTO;
import com.frdscm.wms.entity.vo.WarehouseVO;
import com.frdscm.wms.mapper.WarehouseMapper;
import com.frdscm.wms.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 仓库表 服务实现类
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

    private final WarehouseMapper warehouseMapper;

    @Autowired
    public WarehouseServiceImpl(WarehouseMapper warehouseMapper) {
        this.warehouseMapper = warehouseMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Warehouse save(WarehouseDTO warehouseDTO, UserBO userBO) {
        Warehouse warehouse = BeanUtils.copy(warehouseDTO, Warehouse.class);
        String code = NumberGenerateUtil.generateWarehouseCode(super.baseMapper.selectCount());
        warehouse.setCode(code);
        warehouse.setOperatorId(userBO.getUserId());
        warehouse.setOperatorName(userBO.getUserName());
        super.insert(warehouse);
        return warehouse;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void edit(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = BeanUtils.copy(warehouseDTO, Warehouse.class);
        super.updateById(warehouse);
    }

    @Override
    public void delete(Integer id) {
        Warehouse warehouse = super.selectById(id);
        if (null == warehouse) {
            throw new BusinessException("仓库信息不存在，请确认");
        }
        if (1 != warehouse.getStatus()) {
            throw new BusinessException("仓库信息已删除，请刷新");
        }
        super.deleteById(id);
    }

    @Override
    public List<Warehouse> getWarehouseList() {
        return warehouseMapper.getWarehouseList();
    }

    @Override
    public Page<WarehouseVO> selectByPage(Page<WarehouseVO> page, WarehousePageDTO warehousePageDTO) {
        return page.setRecords(warehouseMapper.selectByPageList(page, warehousePageDTO));
    }
}
