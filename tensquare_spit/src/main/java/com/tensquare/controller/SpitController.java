package com.tensquare.controller;

import com.tensquare.pojo.Spit;
import com.tensquare.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/comment/{parentId}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentId(@PathVariable String parentId,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageList =  spitService.findByParentid(parentId,page,size);
        return  new Result(true, StatusCode.OK,"查询成功",new PageResult<Spit>(pageList.getTotalElements(),pageList.getContent()));

    }
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return  new Result(true,StatusCode.OK,"查询成功",spitService.findAll());
    }
    @RequestMapping(value = "/thumbup/{id}",method = RequestMethod.GET)
    public Result updateThuumbup(@PathVariable String id){
        String userid = "2023";
        if(redisTemplate.opsForValue().get("thumbup_"+userid+"_"+id)!=null){
            return new Result(true,StatusCode.ERROR,"你已经点过赞了");
        }
        spitService.updateThumbup_Mongo(id);
        redisTemplate.opsForValue().set("thumbup_"+userid+"_"+id,"1");

        return new Result(true,StatusCode.OK,"点赞成功");


    }
    @RequestMapping(method = RequestMethod.POST)
    public  Result save(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"插入成功");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public  Result save(@PathVariable String id){
       spitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
