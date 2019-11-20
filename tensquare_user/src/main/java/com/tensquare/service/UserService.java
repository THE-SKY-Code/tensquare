package com.tensquare.service;

import com.tensquare.dao.UserDao;
import com.tensquare.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private  IdWorker idWorker;

    public  void sendSms(String mobile){
        //随机生成6位随机数
//        Random random =new Random();
//        int max = 999999;
//        int min = 10000;
//        int code = random.nextInt(max);//生成随机数
//        if(code<min){
//            code = code + min ;
//        }
        String cheeckcode = RandomStringUtils.randomNumeric(6);
        //将验证码放入redis
        redisTemplate.opsForValue().set("checkcode_"+mobile,cheeckcode,5, TimeUnit.MINUTES);

        //将验证码和手机号发到rabbitmq
        Map map = new HashMap();
        map.put("mobile",mobile);
        map.put("checkcode",cheeckcode);
        rabbitTemplate.convertAndSend("sms",map);
        System.out.println("进入到最后一步");

    }

    public void add(User user,String code){
        String syscode = (String)redisTemplate.opsForValue().get("checkcode_"+user.getMobile());
        if(syscode==null){
                throw new RuntimeException("请点击获取短信验证码");
        }
        if(!syscode.equals(code)){
            throw new RuntimeException("验证码输入不争取我");
        }
        user.setId(idWorker.nextId()+"");
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date());//注册日期
        user.setUpdatedate(new Date());//更新日期
        user.setLastdate(new Date());//最后登录日期
        userDao.save(user);

    }

}
