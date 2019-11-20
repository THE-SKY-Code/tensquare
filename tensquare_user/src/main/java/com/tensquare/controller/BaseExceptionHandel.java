package com.tensquare.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseExceptionHandel {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result erro(Exception e){
        e.printStackTrace();
        return new Result(false , StatusCode.ERROR,e.getMessage());
    }
}
