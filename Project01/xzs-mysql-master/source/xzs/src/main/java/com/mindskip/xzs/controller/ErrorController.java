package com.mindskip.xzs.controller;

import com.mindskip.xzs.base.SystemCode;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/*
* SpringBoot的默认异常处理映射为“/error”。BasicErrorController已经默认实现了“text/html”的处理，如果想返回自定义JSON格式信息，则实现“ErrorController ”接口，增加一个produces 为“application/json”的方法即可，如：
* */
@RestController
public class ErrorController extends BasicErrorController {

    private static final String PATH = "/error";

    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> error = new HashMap<>(2);
        error.put("code", SystemCode.InnerError.getCode());
        error.put("message", SystemCode.InnerError.getMessage());
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
