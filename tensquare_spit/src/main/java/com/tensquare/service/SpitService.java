package com.tensquare.service;

import com.tensquare.dao.SpitDao;
import com.tensquare.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;


import java.util.Date;
import java.util.List;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询所有记录
     * @return
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据id查询记录
     * @param id
     * @return
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     * 插入数据
     * @param spit
     */
    public  void add(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setPublishtime(new Date());
        spitDao.save(spit);
    }

    /**
     * 修改
     * @param spit
     */
    public void  update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 删除
     * @param id
     */
    public void  deleteById(String id){
        spitDao.deleteById(id);
    }

    /**
     * f根据上级ID查询吐槽列表
     * @param id
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentid(String id,int page,int size){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        return spitDao.findByParentid(id,pageRequest);
    }

    /**
     * 点赞
     * @param id
     */
    public  void  updateThumbup(String id){
        Spit spit =spitDao.findById(id).get();
        spit.setThumbup( spit.getThumbup()+1);
        spitDao.save(spit);
    }

    /**
     * mongodb 点赞
     * @param id
     */
      public  void updateThumbup_Mongo(String id){

          Query query = new Query();
          query.addCriteria(Criteria.where("_id").is(id));
          Update update = new Update();
          update.inc("thumbup",1);
          mongoTemplate.updateFirst(query,update,"spit");
      }


}
