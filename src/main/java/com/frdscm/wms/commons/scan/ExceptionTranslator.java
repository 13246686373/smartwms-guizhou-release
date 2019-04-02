package com.frdscm.wms.commons.scan;

import com.frdscm.wms.commons.enums.ResponseEnum;
import com.frdscm.wms.commons.exceptions.BusinessException;
import com.frdscm.wms.commons.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author: March_CD
 * @description: 将服务器端异常转换为客户端友好的JSON结构
 */
@ControllerAdvice
public class ExceptionTranslator {

    private final Logger logger = LoggerFactory.getLogger(ExceptionTranslator.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response processValidationError(MethodArgumentNotValidException ex) {
        logger.error(ex.getMessage(), ex);
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        return getFieldErrorResult(error.getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response defaultErrorHandler(MissingServletRequestParameterException ex) {
        logger.error(ex.getMessage(), ex);
        return getFieldErrorResult("请求参数错误");
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response defaultErrorHandler(TypeMismatchException ex) {
        logger.error(ex.getMessage(), ex);
        return getFieldErrorResult("格式错误");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response defaultErrorHandler(HttpRequestMethodNotSupportedException ex) {
        logger.error(ex.getMessage(), ex);
        return getFieldErrorResult("请求方式错误");
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response defaultErrorHandler(NoHandlerFoundException ex) {
        logger.error(ex.getMessage(), ex);
        return getFieldErrorResult("接口不存在");
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Response defaultErrorHandler(BusinessException ex) {
        logger.error(ex.getMessage(), ex);
        if (ex.getResponseEnum() != null) {
            return getFieldServiceErrorResult(ex.getResponseEnum());
        } else {
            return getFieldErrorResult(ex.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response defaultErrorHandler(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return getFieldErrorResult(ex.getMessage());
    }

    private Response getFieldErrorResult(String errorMsg) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(0);
        response.setMsg(errorMsg);
        return response;
    }

    private Response getFieldServiceErrorResult(ResponseEnum responseEnum) {
        Response response = new Response(responseEnum);
        response.setSuccess(false);
        return response;
    }
}
