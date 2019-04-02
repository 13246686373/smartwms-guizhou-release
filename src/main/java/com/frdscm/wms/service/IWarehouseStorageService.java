package com.frdscm.wms.service;

import com.baomidou.mybatisplus.service.IService;
import com.frdscm.wms.entity.WarehouseStorage;
import com.frdscm.wms.entity.dto.MoveWereHouseStorageDTO;
import com.frdscm.wms.entity.dto.WarehouseStorageDTO;

import java.util.List;


/**
 * 仓库储位表 服务类
 *
 * @author dizhang
 * @since 2018-07-03
 */
public interface IWarehouseStorageService extends IService<WarehouseStorage> {

    /**
     * 根据ID查询储位信息
     *
     * @param warehouseStorageId
     * @return
     */
    WarehouseStorage getWarehouseStorageById(Integer warehouseStorageId);

    /**
     * 保存储位信息
     *
     * @param warehouseStorageDTO
     */
    void save(WarehouseStorageDTO warehouseStorageDTO);

    /**
     * 批量保存储位信息
     *
     * @param WarehouseStorageDTOList
     */
    void batchSave(List<WarehouseStorageDTO> warehouseStorageDTOList);

    /**
     * 修改储位信息
     *
     * @param warehouseStorageDTO
     */
    void edit(WarehouseStorageDTO warehouseStorageDTO);

    /**
     * 删除储位信息
     *
     * @param id
     */
    void delete(Integer id);

    void batchDelete(List<Integer> ids);

    /**
     * 修改储位
     * @param wsgId 储位等级ID
     * @param wsId 储位id
     */
    void updateWareHouseStorage(Integer wsgId,Integer wsId);

}
