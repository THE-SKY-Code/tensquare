package com.tensquare.recruit.controller;

import com.tensquare.recruit.service.RecruitService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/recruit")
public class RecruitController {
    @Autowired
    private RecruitService recruitService;
    @RequestMapping(value = "/search/newlist",method = RequestMethod.GET)
    public Result newlist(){
        return new Result(true, StatusCode.OK,"查询成功",recruitService.findTop4());
    }
}
