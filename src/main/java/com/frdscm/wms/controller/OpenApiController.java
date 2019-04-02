package com.frdscm.wms.controller;

import com.alibaba.fastjson.JSON;
import com.frdscm.wms.commons.enums.ResponseEnum;
import com.frdscm.wms.commons.response.Response;
import com.frdscm.wms.commons.util.BeanUtils;
import com.frdscm.wms.entity.bo.client.*;
import com.frdscm.wms.entity.bo.materials.Materials;
import com.frdscm.wms.feign.ClientService;
import com.frdscm.wms.feign.CommonService;
import com.frdscm.wms.feign.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: chengdong
 * @description: TODO
 * @date: 2018/10/29 2:28 PM
 */
@Api(tags = "开放接口")
@RestController
@RequestMapping("/openapi")
public class OpenApiController {
    private final Logger logger = LoggerFactory.getLogger(OpenApiController.class);

    private final ClientService clientService;
    private final CommonService commonService;
    private final ProjectService projectService;

    @Autowired
    public OpenApiController(ClientService clientService, ProjectService projectService, CommonService commonService) {
        this.clientService = clientService;
        this.commonService = commonService;
        this.projectService = projectService;
    }

    @ApiOperation("创建客户")
    @PostMapping("/client/create")
    public Response clientCreate(@RequestBody @Valid OpenApiBase<ClientDTO> requestBody) {
        System.out.println("client : " + JSON.toJSONString(requestBody));
        ClientDTO clientDTO = requestBody.getData();
        Client client = BeanUtils.copy(clientDTO, Client.class);
        Dictionary dictionary = new Dictionary(clientDTO.getIndustry());
        client.setUserclass(dictionary);
        Account account = new Account(clientDTO.getSalesman());
        client.setSalesman(account);
        account = new Account(clientDTO.getLegal());
        client.setLegal(account);
        Integer clientId = clientService.createAdd(client, 1);
        if (clientId == null) {
            return renderError("创建失败，请重试");
        }
        Map<String, Integer> responseMap = new HashMap<>(0);
        responseMap.put("clientId", clientId);
        return renderSuccess(responseMap);
    }

    @ApiOperation("创建物料")
    @PostMapping("/materials/create")
    public Response materialsCreate(@RequestBody @Valid OpenApiBase<Materials> requestBody) {
        logger.info("materials : " + JSON.toJSONString(requestBody));
        List<Dictionary> dictionaries = commonService.getDictionary("29", 1);
        if (dictionaries == null) {
            return renderError("获取物料单位失败，请重试");
        }
        Materials materials = requestBody.getData();
        Dictionary dictionary = dictionaries.stream().filter(e -> materials.getUnitName().equals(e.getTitle())).findAny().orElse(null);
        if (dictionary == null) {
            return renderError("物料单位不存在失败，请确认");
        }
        materials.setUnitId(dictionary.getId());
        Response response = projectService.materialsSave(materials, 1);
        if (!response.isSuccess()) {
            return renderError(response.getMsg());
        }
        logger.info("response : {} ", JSON.toJSONString(response));
        Map<String, Integer> responseMap = new HashMap<>(0);
        responseMap.put("materialsId", Integer.valueOf(((LinkedHashMap) response.getData()).get("id").toString()));
        return renderSuccess(responseMap);
    }

    private Response renderError(String msg) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMsg(msg);
        return response;
    }

    private Response renderSuccess(Object obj) {
        Response response = new Response();
        response.setData(obj);
        response.setMsg(ResponseEnum.SUCCESS.getMsg());
        response.setCode(ResponseEnum.SUCCESS.getCode());
        return response;
    }
}
