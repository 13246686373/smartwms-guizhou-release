package com.frdscm.wms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.commons.util.StringUtils;
import com.frdscm.wms.entity.Warehouse;
import com.frdscm.wms.entity.WarehouseStorage;
import com.frdscm.wms.entity.dto.WarehouseStorageDTO;
import com.frdscm.wms.mapper.ReceiptListMapper;
import com.frdscm.wms.mapper.WarehouseMapper;
import com.frdscm.wms.mapper.WarehouseStorageMapper;
import com.frdscm.wms.service.IWarehouseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 仓库储位表 服务实现类
 *
 * @author March_CD
 * @since 2018-07-03
 */
@Service
public class WarehouseStorageServiceImpl extends ServiceImpl<WarehouseStorageMapper, WarehouseStorage> implements IWarehouseStorageService {

    private final WarehouseStorageMapper warehouseStorageMapper;

    private final WarehouseMapper warehouseMapper;

    private final ReceiptListMapper receiptListMapper;

    @Autowired
    public WarehouseStorageServiceImpl(WarehouseStorageMapper warehouseStorageMapper, WarehouseMapper warehouseMapper, ReceiptListMapper receiptListMapper) {
        this.warehouseStorageMapper = warehouseStorageMapper;
        this.warehouseMapper = warehouseMapper;
        this.receiptListMapper = receiptListMapper;
    }

    @Override
    public WarehouseStorage getWarehouseStorageById(Integer warehouseStorageId) {
        return warehouseStorageMapper.selectById(warehouseStorageId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(WarehouseStorageDTO warehouseStorageDTO) {
        int count = warehouseStorageMapper.selectCount(warehouseStorageDTO.getNumber(), warehouseStorageDTO.getWarehouseId());
        if (count > 0) {
            throw new BusinessException("储位编码已存在，请重新填写");
        }
        if (StringUtils.isBlank(warehouseStorageDTO.getNumber())) {
            Warehouse warehouse = warehouseMapper.selectById(warehouseStorageDTO.getWarehouseId());
            String str = String.format("%03d", count + 1);
            warehouseStorageDTO.setNumber(warehouse.getCode() + str);
        }
        WarehouseStorage warehouseStorage = BeanUtils.copy(warehouseStorageDTO, WarehouseStorage.class);
        super.insert(warehouseStorage);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void batchSave(List<WarehouseStorageDTO> warehouseStorageDTOList) {
        WarehouseStorage warehouseStorage;
        for (WarehouseStorageDTO warehouseStorageDTO : warehouseStorageDTOList) {
            warehouseStorage = BeanUtils.copy(warehouseStorageDTO, WarehouseStorage.class);
            if (StringUtils.isBlank(warehouseStorageDTO.getNumber())) {
                Warehouse warehouse = warehouseMapper.selectById(warehouseStorageDTO.getWarehouseId());
                int count = warehouseStorageMapper.selectCount(warehouseStorageDTO.getNumber(), warehouseStorageDTO.getWarehouseId());
                String str = String.format("%03d", count + 1);
                warehouseStorage.setNumber(warehouse.getCode() + str);
                super.insert(warehouseStorage);
            } else {
                WarehouseStorage warehouseStorage1 = warehouseStorageMapper.selectOne(warehouseStorageDTO.getNumber(), warehouseStorageDTO.getWarehouseId());
                if (warehouseStorage1 != null) {
                    warehouseStorage.setId(warehouseStorage1.getId());
                    super.updateById(warehouseStorage);
                } else {
                    super.insert(warehouseStorage);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void edit(WarehouseStorageDTO warehouseStorageDTO) {
        WarehouseStorage warehouseStorage = BeanUtils.copy(warehouseStorageDTO, WarehouseStorage.class);
        warehouseStorage.setNumber(null);
        super.updateById(warehouseStorage);
    }

    @Override
    public void delete(Integer id) {
        WarehouseStorage warehouseStorage = super.selectById(id);
        if (null == warehouseStorage) {
            throw new BusinessException("仓库储位不存在，请确认");
        }
        if (1 != warehouseStorage.getStatus()) {
            throw new BusinessException("仓库储位已删除，请刷新");
        }
        if (super.baseMapper.getStorageNumberCount(warehouseStorage.getWarehouseId(), warehouseStorage.getNumber()) > 0) {
            throw new BusinessException("储位正在被使用，删除失败");
        }
        super.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        WarehouseStorage warehouseStorage;
        for (Integer id : ids) {
            warehouseStorage = super.selectById(id);
            if (super.baseMapper.getStorageNumberCount(warehouseStorage.getWarehouseId(), warehouseStorage.getNumber()) > 0) {
                throw new BusinessException("储位正在被使用，删除失败");
            }
            super.deleteById(id);
        }
    }

    @Override
    public void updateWareHouseStorage(Integer wsgId, Integer wsId) {
        warehouseStorageMapper.updateWareHouseStorage(wsgId, wsId);
    }


}
