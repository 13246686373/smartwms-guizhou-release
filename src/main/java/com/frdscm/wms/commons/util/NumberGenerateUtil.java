package com.frdscm.wms.commons.util;

/**
 * @author: chengdong
 * @description: 编号生成工具类
 */
public final class NumberGenerateUtil {
    /**
     * @author: chengdong
     * @param: count 当前生成数量
     * @description: 生成仓库代码
     */
    public static String generateWarehouseCode(Integer count) {
        count += 1;
        String str = String.format("%03d", count);
        return "WH" + str;
    }
}
