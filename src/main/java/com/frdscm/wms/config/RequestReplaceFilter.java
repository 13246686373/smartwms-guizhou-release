package com.frdscm.wms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: chengdong
 * @description: TODO
 * @date: 2018/10/29 12:44 PM
 */
@Configuration
public class RequestReplaceFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestReplaceFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ServletRequest requestWrapper = new CustomServletRequestWrapper(request);
        String json = HttpHelper.getBodyString(requestWrapper);
        filterChain.doFilter(requestWrapper, response);
    }
}