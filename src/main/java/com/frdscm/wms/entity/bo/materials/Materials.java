package com.frdscm.wms.entity.bo.materials;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: chengdong
 * @description: TODO
 * @date: 2018/11/21 3:02 PM
 */
@Data
public class Materials implements Serializable {
    private Integer id;
    /**
     * 物料料号
     */
    @NotBlank(message = "物料料号不能为空")
    private String materialItemNumber;
    /**
     * 客户ID
     */
    @NotBlank(message = "客户ID不能为空")
    private Integer clientId;
    /**
     * 客户名称
     */
    @NotBlank(message = "客户名称不能为空")
    private String clientName;
    /**
     * 物品名称
     */
    @NotBlank(message = "物料名称不能为空")
    private String itemName;
    /**
     * 单位ID
     */
    private Integer unitId;
    /**
     * 单位名称
     */
    @NotNull(message = "单位名称")
    private String unitName;
    /**
     * 物品规格
     */
    private String itemSpecifications;
    /**
     * 单个重量
     */
    private BigDecimal singleWeight;
    /**
     * 单个体积
     */
    private BigDecimal singleVolume;

    /**
     * 重泡货类型ID(1-无 2-重货 3-泡货)
     */
    private Integer heavyBubbleId;
    /**
     * 重泡货类型名称
     */
    private String heavyBubbleName;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 是否含仓储管理(1-包含 2-不包含)
     */
    private Integer isIncludeWarehouseManagement;
    /**
     * 品牌
     */
    private String brands;
    /**
     * 商品类型
     */
    private String productType;
    /**
     * 制造商
     */
    private String manufacturer;
    /**
     * 制造商料号
     */
    private String manufacturerPartNumber;
    /**
     * 包装类型
     */
    private String packageType;
    /**
     * 出货箱数/板
     */
    private Integer shippingBoxCount;
    /**
     * 箱数/板
     */
    private Integer boxCount;
    /**
     * 数量/箱
     */
    private Integer quantity;
    /**
     * 毛重/箱
     */
    private BigDecimal grossWeight;
    /**
     * 最大库存
     */
    private Integer maxStock;
    /**
     * 最小库存
     */
    private Integer minStock;
    /**
     * 是否扫描序列号(1-扫描 2-不扫描)
     */
    private Integer isScanningSerialNumber = 2;
    /**
     * 重泡比
     */
    private BigDecimal heavyBubbleRatio;
    /**
     * 每单位重量ID(1-每板 2-每箱 3-每个)
     */
    private Integer perUnitWeightId;
    /**
     * 每单位重量
     */
    private String perUnitWeightName;
    /**
     * 每单位体积ID(1-每板 2-每箱 3-每个)
     */
    private Integer perUnitVolumeId;
    /**
     * 每单位体积
     */
    private String perUnitVolumeName;
    /**
     * 收货模式ID(1-板收 2-箱收 3-PCS收)
     */
    private Integer receiptModeId;
    /**
     * 收货模式
     */
    private String receiptModeName;
    /**
     * 出货模式ID(1-板出 2-箱出 3-PCS出)
     */
    private Integer shippingModeId;
    /**
     * 出货模式
     */
    private String shippingModeName;
    /**
     * 出货是否扫描序列号(1-扫描 2-不扫描)
     */
    private Integer shipmentIsScanningSerialNumber = 2;
    /**
     * 物料类型：materialTyp（1-运输 2-仓储）
     */
    private Integer materialType = 2;

}

