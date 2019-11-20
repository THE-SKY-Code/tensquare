package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.RecruitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitService {
    @Autowired
    private RecruitDao recruitDao;

    /**
     * 查询前4的标签

     * @return
     */
    public List findTop4(){
        return  recruitDao.findTop4ByStateOrderByCreatetimeDesc("2");
    }
}
