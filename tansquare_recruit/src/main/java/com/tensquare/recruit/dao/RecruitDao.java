package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecruitDao extends JpaRepository<Recruit,String >,JpaSpecificationExecutor<Recruit> {
    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);
}
