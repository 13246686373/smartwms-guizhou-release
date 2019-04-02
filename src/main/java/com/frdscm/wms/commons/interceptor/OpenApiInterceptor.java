package com.frdscm.wms.commons.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frdscm.wms.commons.exceptions.AuthException;
import com.frdscm.wms.commons.util.MD5Util;
import com.frdscm.wms.config.CustomServletRequestWrapper;
import com.frdscm.wms.config.HttpHelper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenApiInterceptor implements HandlerInterceptor {
    /**
     * 私钥（sha256 wms）
     */
    private static final String PRIVATE_KEY = "9366D1E7528D97AE2EB30D1AA76447D88C35725117BE00F4245BBF77E1155E30";
    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "676c621506ad4443ac626422a736345f";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println(">>>MyInterceptor1>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (request instanceof CustomServletRequestWrapper) {
            String body = HttpHelper.getBodyString(request);
            JSONObject bodyObject = JSON.parseObject(body);
            System.out.println(body);
            String sign = MD5Util.MD5Encode(JSON.toJSONString(bodyObject.getJSONObject("data")) + "key=" + PRIVATE_KEY, "UTF-8").toUpperCase();
            System.out.println("service sign = " + sign);
            if (bodyObject.getString("sign") == null || bodyObject.getString("sign").length() != 32) {
                throw new AuthException("认证异常，请检查参数sign长度");
            }
            if (!PUBLIC_KEY.equals(bodyObject.getJSONObject("data").getString("appid"))) {
                throw new AuthException("appid不存在");
            }
            System.out.println("data = " + JSON.toJSONString(bodyObject.getJSONObject("data")));
            System.out.println("client sign = " + bodyObject.getString("sign"));
            if (!sign.equals(bodyObject.getString("sign"))) {
                throw new AuthException("认证异常，请检查参数sign");
            }
        } else {
            throw new AuthException("认证异常");
        }
        return true;
    }

}