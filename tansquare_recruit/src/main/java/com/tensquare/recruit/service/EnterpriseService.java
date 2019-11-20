package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.EnterpriseDao;
import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EnterpriseService {
    @Autowired
    private EnterpriseDao enterpriseDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 热门列表
     * @return
     */
    public List<Enterprise> hotList(){
        List<Enterprise> list = enterpriseDao.findByIshot("1");
        return list;
    }

    public Page<Enterprise> findSearch(Map whereMap, int page, int size) {
        Specification<Enterprise> specification = createSpecification(whereMap);
        PageRequest pageRequest =  PageRequest.of(page-1, size);
        return enterpriseDao.findAll(specification, pageRequest);
    }
    public List<Enterprise> findSearch(Map whereMap) {
        Specification<Enterprise> specification = createSpecification(whereMap);
        return enterpriseDao.findAll(specification);
    }

    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<Enterprise> createSpecification(Map searchMap) {

        return new Specification<Enterprise>() {

            @Nullable
            @Override
            public Predicate toPredicate(Root<Enterprise> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 企业名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                    predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 企业简介
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                    predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 企业地址
                if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
                    predicateList.add(cb.like(root.get("address").as(String.class), "%"+(String)searchMap.get("address")+"%"));
                }
                // 标签列表
                if (searchMap.get("labels")!=null && !"".equals(searchMap.get("labels"))) {
                    predicateList.add(cb.like(root.get("labels").as(String.class), "%"+(String)searchMap.get("labels")+"%"));
                }
                // 坐标
                if (searchMap.get("coordinate")!=null && !"".equals(searchMap.get("coordinate"))) {
                    predicateList.add(cb.like(root.get("coordinate").as(String.class), "%"+(String)searchMap.get("coordinate")+"%"));
                }
                // 是否热门
                if (searchMap.get("ishot")!=null && !"".equals(searchMap.get("ishot"))) {
                    predicateList.add(cb.like(root.get("ishot").as(String.class), "%"+(String)searchMap.get("ishot")+"%"));
                }
                // LOGO
                if (searchMap.get("logo")!=null && !"".equals(searchMap.get("logo"))) {
                    predicateList.add(cb.like(root.get("logo").as(String.class), "%"+(String)searchMap.get("logo")+"%"));
                }
                // URL
                if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
                    predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

    }
}
