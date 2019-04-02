package com.frdscm.wms.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: chengdong
 * @description: TODO
 * @date: 2018-12-19 13:50
 */
@Data
public class UploadAttachment {
    @NotNull(message = "ID不能为空")
    private Integer id;
    @NotNull(message = "文件路径不能为空")
    private String filePath;
    @NotNull(message = "文件名不能为空")
    private String fileName;
}
