package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 查询热门列表
     * @return
     */
    @RequestMapping(value = "/search/hotlist", method= RequestMethod.GET)
    public Result hotlist(){
        List<Enterprise> list = enterpriseService.hotList();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }
    /**
     * 分页+多条件查询
     * @param searchMap 查询条件封装
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
        Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
        return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Enterprise>(pageList.getTotalElements(), pageList.getContent()) );
    }

    /**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",enterpriseService.findSearch(searchMap));
    }

}
