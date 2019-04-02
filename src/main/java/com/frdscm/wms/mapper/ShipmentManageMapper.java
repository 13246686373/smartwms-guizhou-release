package com.frdscm.wms.mapper;


import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.ShipmentList;
import com.frdscm.wms.entity.ShipmentManage;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.dto.CountDTO;
import com.frdscm.wms.entity.dto.ShipmentManagePageDTO;
import com.frdscm.wms.entity.vo.ShipmentManageListVO;
import com.frdscm.wms.entity.vo.ShipmentsCountVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 出货管理表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Repository
public interface ShipmentManageMapper extends BaseMapper<ShipmentManage> {


    /**
     * 根据出货需求ID查询出货管理
     *
     * @param shipmentDemandId
     * @return
     */
    @Select("select * from shipment_manage where shipment_demand_id = #{shipmentDemandId}")
    ShipmentManage selectShipmentManageByShipmentDemandId(@Param("shipmentDemandId") Integer shipmentDemandId);

    /**
     * 更具出货需求ID删除出货管理信息
     *
     * @param shipmentDemandId
     */
    @Delete("delete from shipment_manage where shipment_demand_id = #{shipmentDemandId}")
    void deleteShipmentManageByShipmentDemandId(@Param("shipmentDemandId") Integer shipmentDemandId);


    /**
     * 出货列表分页
     *
     * @param page
     * @param shipmentManagePageDTO
     * @return
     */
    List<Map<String, Object>> getShipmentManageByPageList(Pagination page, ShipmentManagePageDTO shipmentManagePageDTO);


    @Update("update shipment_manage set status = #{status} where id = #{id}")
    void updateStatus(@Param("status") Integer status, @Param("id") Integer id);


    /**
     * 出货看板统计
     *
     * @param countDTO
     * @return
     */
    Map getShipmentBoardCount(CountDTO countDTO);

    /**
     * 出货看板数据统计
     *
     * @param countDTO
     * @return
     */
    List<ShipmentsCountVO> getShipmentDataCount(Pagination page, CountDTO countDTO);

    /**
     * 出货看板统计日期
     *
     * @param countDTO
     * @return
     */
    List<Map<String, Object>> getShipmentDateCount(CountDTO countDTO);


    /**
     * 移除拣货任务
     *
     * @param remove
     * @param id
     */
    @Update("update shipment_manage set remove = #{remove} where id=#{id}")
    void updateRemove(@Param("remove") Integer remove, @Param("id") Integer id);

}
