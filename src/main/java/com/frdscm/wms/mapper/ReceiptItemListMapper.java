package com.frdscm.wms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.frdscm.wms.entity.ReceiptCargoDetails;
import com.frdscm.wms.entity.ReceiptItemList;
import com.frdscm.wms.entity.ReceiptList;
import com.frdscm.wms.entity.vo.MaterialListAppVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收货列表 Mapper 接口
 *
 * @author March_CD
 * @since 2018-07-06
 */
@Repository
public interface ReceiptItemListMapper extends BaseMapper<ReceiptItemList> {
    @Select("select * from receipt_item_list where receipt_list_id = #{receiptListId}")
    List<ReceiptItemList> getReceiptListById(@Param("receiptListId") Integer receiptListId);
}
