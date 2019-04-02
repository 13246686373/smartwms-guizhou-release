package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.ShipmentItemList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 拣货列表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-07
 */
@Repository
public interface ShipmentItemListMapper extends BaseMapper<ShipmentItemList> {
    @Delete("delete from shipment_item_list where shipment_list_id = #{shipmentListId}")
    void deleteShipmentItemListByShipmentListId(@Param("shipmentListId") Integer shipmentListId);

    @Select("select count(id) from shipment_item_list where shipment_list_id = #{shipmentListId}")
    int countList(@Param("shipmentListId") Integer shipmentListId);
}
