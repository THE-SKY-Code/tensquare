package com.tensquare.controller;

import com.tensquare.client.LabelClient;
import com.tensquare.pojo.Problem;
import com.tensquare.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/problem")
public class ProblemController {
    @Autowired
    private LabelClient labelClient;
    @Autowired
    private ProblemService problemService;
    @RequestMapping(value = "newlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
    public Result findNewListByLabelId(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){
        Page<Problem> pageList = problemService.findNewListByLabelId(labelid,page,size);
        PageResult pageResult = new PageResult(pageList.getTotalElements(),pageList.getContent());
        return  new Result(true, StatusCode.OK,"查询成功",pageResult);

    }

    @RequestMapping(value = "/label/{labelid}")
    public  Result findById(@PathVariable("labelid") String id){
        return labelClient.findById(id);

    }


}
