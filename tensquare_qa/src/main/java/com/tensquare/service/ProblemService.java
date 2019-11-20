package com.tensquare.service;

import com.tensquare.dao.ProblemDao;
import com.tensquare.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public class ProblemService {
    @Autowired
    private ProblemDao problemDao;

    public Page<Problem> findNewListByLabelId(String labelid,int page,int size){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        return problemDao.findNewListByLabelId(labelid,pageRequest);


    }
}
