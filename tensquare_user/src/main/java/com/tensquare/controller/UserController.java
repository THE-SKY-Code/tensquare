package com.tensquare.controller;

import com.tensquare.pojo.User;
import com.tensquare.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile){
        userService.sendSms(mobile);
        System.out.println("helloworld");
        System.out.println("helloworld");
        return new Result(true, StatusCode.OK,"发送成功");

    }

    @RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
    public Result register(@RequestBody User user,@PathVariable String code){
        userService.add(user,code);
        return  new Result(true,StatusCode.OK,"注册成功");


    }

}
