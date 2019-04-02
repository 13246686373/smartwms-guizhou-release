package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.ShipmentScanList;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.bo.ShipmentScanListBO;
import com.frdscm.wms.entity.vo.OutgoingScanVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dizhang
 * @since 2018-08-11
 */
@Repository
public interface ShipmentScanListMapper extends BaseMapper<ShipmentScanList> {


    /**
     * 列表分页
     *
     * @param page
     * @param shipmentManageId
     * @return
     */
    // @Select("select osm.*,osl.* from outgoing_scan_list osl left join outgoing_scan_manage osm on osl.outgoing_scan_manage_id = osm.id where osm.shipment_manage_id = #{shipmentManageId}")
    List<OutgoingScanVO> getOutgoingScanListList(Pagination page, @Param("shipmentManageId") Integer shipmentManageId);

    @Select("select * from shipment_scan_list where shipment_manage_id = #{shipmentManageId}")
    List<ShipmentScanListBO> exportExcel(@Param("shipmentManageId") Integer shipmentManageId);

    // List<>

    /**
     * 查询该出货扫描下有没有该板号
     *
     * @param boardNumber
     * @return
     */
    @Select("select count(1) from outgoing_scan_list where board_number = #{boardNumber} and outgoing_scan_manage_id =#{outgoingScanManageId} ")
    int getBoardNumberCount(@Param("boardNumber") String boardNumber, @Param("outgoingScanManageId") Integer outgoingScanManageId);

    @Select("select count(1) from shipment_scan_list where shipment_manage_id = #{shipmentManageId}")
    int materialIsScan(@Param("shipmentManageId") Integer shipmentManageId);

    @Select("select count(boxNum) from (select count(box_num) boxNum from shipment_scan_list where shipment_manage_id = #{shipmentManageId} and board_number = #{boardNumber} group by box_num) a")
    int getShipmentScanBoxNum(@Param("shipmentManageId") Integer shipmentManageId, @Param("boardNumber") String boardNumber);

    @Select("select count(id) from shipment_scan_list where shipment_manage_id = #{shipmentManageId} and board_number = #{boardNumber}")
    int getShipmentScanBox(@Param("shipmentManageId") Integer shipmentManageId, @Param("boardNumber") String boardNumber);

    @Select("select count(id) from shipment_scan_list where shipment_manage_id = #{shipmentManageId} and board_number = #{boardNumber} and box_num = #{boxNumber}")
    int getShipmentScanBoxSerialNum(@Param("shipmentManageId") Integer shipmentManageId, @Param("boardNumber") String boardNumber, @Param("boxNumber") String boxNumber);

    @Select("select count(id) from shipment_scan_list where shipment_manage_id = #{shipmentManageId} and board_number = #{boardNumber} and box_num = #{boxNumber} and box_serial_num = #{boxSerialNum}")
    int getBoxSerialNumCount(@Param("shipmentManageId") Integer shipmentManageId, @Param("boardNumber") String boardNumber, @Param("boxNumber") String boxNumber, @Param("boxSerialNum") String boxSerialNum);

    @Select("select id from shipment_scan_list where shipment_manage_id = #{shipmentManageId} and material_number = #{materialNumber} and box_num = #{boxNumber} and board_number is null")
    List<Integer> getBoardNumberIsNull(@Param("shipmentManageId") Integer shipmentManageId, @Param("materialNumber") String materialNumber, @Param("boxNumber") String boxNumber);
}
