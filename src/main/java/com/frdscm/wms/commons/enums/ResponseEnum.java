package com.frdscm.wms.commons.enums;

/**
 * @author: chengdong
 * @description: 错误返回通用枚举
 */
public enum ResponseEnum {
    /**
     * 成功
     */
    SUCCESS(0, "successful"),
    /**
     * 失败
     */
    FAILURE(-1, "服务器错误"),


    RECEIPT_SCAN_SUCCESS(100,"该收货单号已完成收货扫描"),
    RECEIPT_BOARD_SCAN_SUCCESS(101,"该板已完成收货扫描"),
    RECEIPT_BOX_SCAN_SUCCESS(102,"该箱已完成收货扫描"),

    SHIPMENT_SCAN_SUCCESS(110,"该出货单号已完成收货扫描"),
    SHIPMENT_BOARD_SCAN_SUCCESS(111,"该板已完成出货扫描"),
    SHIPMENT_BOX_SCAN_SUCCESS(112,"该箱已完成出货扫描"),

    RECEIPT_UPPER_SHELF_SUCCESS(120,"该箱已完成出货扫描");

    private int code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
