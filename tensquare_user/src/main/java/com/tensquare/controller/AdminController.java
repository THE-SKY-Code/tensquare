package com.tensquare.controller;

import com.tensquare.pojo.Admin;
import com.tensquare.service.AdminService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> map){
        Admin admin = adminService.findByLoginAndPassword(map.get("loginname"), map.get("password"));
        if (admin != null){
            String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
            HashMap hashMap = new HashMap();
            hashMap.put("token",token);
            hashMap.put("name",admin.getLoginname());
            return new Result(true , StatusCode.OK,"登录成功",hashMap);
        }else {
            return new Result(false,StatusCode.ERROR,"用户名和密码错误");
        }

    }

    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Admin admin  ){
        adminService.add(admin);
        return new Result(true,StatusCode.OK,"增加成功");
    }

@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id,HttpServletRequest request){
 /*   String authorization = request.getHeader("Authorization");
    if(authorization == null){
        return new Result(false,StatusCode.ACCESSERROR,"权限不足");
    }
    if(!authorization.startsWith("Bearer ")){
        return new Result(false,StatusCode.ACCESSERROR,"权限不足");
    }
    String token = authorization.substring(7);
    Claims claims = jwtUtil.parseJWT(token);
    if(claims == null){
        return new Result(false,StatusCode.ACCESSERROR,"权限不足");
    }
    if(!"admin".equals(claims.get("roles"))){
        return new Result(false,StatusCode.ACCESSERROR,"权限不足");
    }
    return new Result(true,StatusCode.OK,"删除成功");*/

    Claims claims = (Claims) request.getAttribute("admin_claims");
    if(claims == null){
        return new Result(false,StatusCode.ACCESSERROR,"权限不足");
    }
    //userService.deleteById(id);
    return new Result(true,StatusCode.OK,"删除成功");
}

}
