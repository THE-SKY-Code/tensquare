package com.tensquare.client.impl;

import com.tensquare.client.LabelClient;
import entity.Result;
import entity.StatusCode;

public class LabelClientImpl extends LabelClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"熔断器启动");
    }
}
