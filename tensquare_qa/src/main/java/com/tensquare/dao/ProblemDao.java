package com.tensquare.dao;

import com.tensquare.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    @Query(value = "select * from tb_problem where id in (select problemid from tb_pl where labelid=?) order By replytime desc",nativeQuery = true)
    public Page<Problem> findNewListByLabelId(String labelid, Pageable pageable);

}
