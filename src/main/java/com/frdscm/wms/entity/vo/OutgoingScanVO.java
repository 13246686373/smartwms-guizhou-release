package com.frdscm.wms.entity.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: dizhang
 * @Date: 2018/8/14
 * @Desc:
 **/
@Data
public class OutgoingScanVO implements Serializable {

    private Integer id;
    private String singleNumber;


    private String boardNumber;


    private String boxNum;

    private String boxSonNum;

    private String boxSerialNum;

    private String operatorName;

    private Date createTime;


}
