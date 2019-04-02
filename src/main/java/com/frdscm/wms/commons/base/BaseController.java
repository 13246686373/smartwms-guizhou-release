package com.frdscm.wms.commons.base;


import com.frdscm.wms.commons.enums.ResponseEnum;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.entity.bo.UserBO;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author March_CD
 * @description： BaseController
 */
public abstract class BaseController {

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @Autowired
    protected HttpServletRequest request;

    protected Integer getCompanyId() {
        String companyId = request.getHeader("companyId");
        if (companyId != null) {
            return Integer.valueOf(companyId);
        }
        return null;
    }

    protected UserBO getUser() throws UnsupportedEncodingException {
        Integer uid = Integer.valueOf(request.getHeader("id"));
        String username = request.getHeader("username");
        if (null != username) {
            username = URLDecoder.decode(username, "UTF-8");
        } else {
            username = "";
        }
        return new UserBO(uid, username, Integer.valueOf(request.getHeader("companyId")));
    }

    protected Response renderError(ResponseEnum responseEnum) {
        Response response = new Response(responseEnum);
        response.setSuccess(false);
        return response;
    }

    protected Response renderError(String msg) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMsg(msg);
        return response;
    }

    protected Response renderSuccess(ResponseEnum responseEnum) {
        return new Response(responseEnum);
    }

    protected Response renderSuccess(String msg) {
        Response response = new Response();
        response.setMsg(msg);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        return response;
    }

    protected Response renderSuccess(Object obj) {
        return new Response(ResponseEnum.SUCCESS, obj);
    }

    /**
     * redirect跳转
     *
     * @param url 目标url
     */
    protected String redirect(String url) {
        return "redirect:" + url;
    }

    /**
     * 下载文件
     *
     * @param file 文件
     */
    protected ResponseEntity<Resource> download(File file) {
        String fileName = file.getName();
        Resource resource = new FileSystemResource(file);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String header = request.getHeader("User-Agent");
        // 避免空指针
        header = header == null ? "" : header.toUpperCase();
        HttpStatus status;
        if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
            fileName = UriUtils.encode(fileName, Charsets.UTF_8.name());
            status = HttpStatus.OK;
        } else {
            fileName = new String(fileName.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
            status = HttpStatus.CREATED;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(resource, headers, status);
    }
}
