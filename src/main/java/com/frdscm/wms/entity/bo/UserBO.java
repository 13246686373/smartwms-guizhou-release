package com.frdscm.wms.entity.bo;

import lombok.Data;

/**
 * @author: chengdong
 * @description: 用户信息
 */
@Data
public class UserBO {
    private Integer userId;
    private String userName;
    private Integer companyId;

    public UserBO(Integer userId, String userName, Integer companyId) {
        this.userId = userId;
        this.userName = userName;
        this.companyId = companyId;
    }
}
