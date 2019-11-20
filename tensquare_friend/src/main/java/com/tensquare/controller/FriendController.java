package com.tensquare.controller;

import com.tensquare.pojo.Friend;
import com.tensquare.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable("friendid") String friendid, @PathVariable("type") String type, HttpServletRequest request){
        Claims user_claims = (Claims) request.getAttribute("admin_claims");
        if(user_claims== null){
            return  new Result(false, StatusCode.ACCESSERROR,"权限不足");
        }
        if(type.equals("1")){
           if(friendService.addFriend(user_claims.getId(),friendid)==0){
               return  new Result(false,StatusCode.REMOTEERROR,"已经添加过该好友");
           }
        }
        return  new Result(true ,StatusCode.OK,"添加成功");
    }
}
