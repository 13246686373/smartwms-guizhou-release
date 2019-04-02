package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.frdscm.wms.entity.ReceiptScanList;
import com.frdscm.wms.entity.bo.ShipmentScanListBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dizhang
 * @since 2018-08-11
 */
@Repository
public interface ReceiptScanListMapper extends BaseMapper<ReceiptScanList> {

    @Select("select * from receipt_scan_list where receipt_manage_id = #{receiptManageId}")
    List<ShipmentScanListBO> exportExcel(@Param("receiptManageId") Integer receiptManageId);

    @Select("select * from receipt_scan_list where receipt_manage_id = #{receiptManageId} and board_number = #{boardNumber}")
    List<ReceiptScanList> getReceiptScanListByBoardNumber(@Param("receiptManageId") Integer receiptManageId, @Param("boardNumber") String boardNumber);

    @Select("select count(1) from receipt_scan_list where receipt_manage_id = #{receiptManageId}")
    int materialIsScan(@Param("receiptManageId") Integer receiptManageId);

    @Select("select count(boxNum) from (select count(box_num) boxNum from receipt_scan_list where receipt_manage_id = #{receiptManageId} and board_number = #{boardNumber} group by box_num) a")
    int getReceiptScanBoxNum(@Param("receiptManageId") Integer receiptManageId, @Param("boardNumber") String boardNumber);

    @Select("select count(id) from receipt_scan_list where receipt_manage_id = #{receiptManageId} and board_number = #{boardNumber}")
    int getReceiptScanBox(@Param("receiptManageId") Integer receiptManageId, @Param("boardNumber") String boardNumber);

    @Select("select count(id) from receipt_scan_list where receipt_manage_id = #{receiptManageId} and board_number = #{boardNumber} and box_num = #{boxNumber}")
    int getReceiptScanBoxSerialNum(@Param("receiptManageId") Integer receiptManageId, @Param("boardNumber") String boardNumber, @Param("boxNumber") String boxNumber);

    @Select("select count(id) from receipt_scan_list where receipt_manage_id = #{receiptManageId} and board_number = #{boardNumber} and box_num = #{boxNumber} and box_serial_num = #{boxSerialNum}")
    int getBoxSerialNumCount(@Param("receiptManageId") Integer receiptManageId, @Param("boardNumber") String boardNumber, @Param("boxNumber") String boxNumber, @Param("boxSerialNum") String boxSerialNum);

    @Select("select id from receipt_scan_list where receipt_manage_id = #{receiptManageId} and material_number = #{materialNumber} and box_num = #{boxNumber} and board_number is null")
    List<Integer> getBoardNumberIsNull(@Param("receiptManageId") Integer receiptManageId, @Param("materialNumber") String materialNumber, @Param("boxNumber") String boxNumber);
}