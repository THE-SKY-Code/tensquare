package com.tensquare.Listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;
    @Value("${aliyun.sms.template_code}")
    private  String template_code;
    @Value("${aliyun.sms.sign_nanme}")
    private  String sign_nanme;

    @RabbitHandler
    public  void sendSms(Map<String,String> map){
        String mobile = map.get("mobile");
        String checkcode = map.get("checkcode");
        System.out.println("mobile"+mobile);

        try {
            smsUtil.sendSms(mobile,template_code,sign_nanme,"{\"checkcode\":\""+checkcode+"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
