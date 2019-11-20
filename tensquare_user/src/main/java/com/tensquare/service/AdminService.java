package com.tensquare.service;

import com.tensquare.dao.AdminDao;
import com.tensquare.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import util.IdWorker;
@Service
public class AdminService {
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private AdminDao adminDao;

    /**
     * 添加管理员并对密码进行加密
     * @param admin
     */
    public  void  add(Admin admin){
        admin.setId(idWorker.nextId()+"");
        String newpassword = encoder.encode(admin.getPassword());
        admin.setPassword(newpassword);
        adminDao.save(admin);
    }

    /**
     * 根据登录名和密码查询
     * @param loginnname
     * @param password
     * @return
     */
    public  Admin findByLoginAndPassword(String loginnname,String password){
        Admin admin = adminDao.findByLoginname(loginnname);
        if(admin != null && encoder.matches(password,admin.getPassword())){
            return  admin;
        }else{
            return null;
        }
    }

}
