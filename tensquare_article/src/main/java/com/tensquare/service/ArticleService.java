package com.tensquare.service;

import com.tensquare.dao.ArticleDao;
import com.tensquare.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据id 查询文章
     * @param id
     * @return
     */
    public Article findById(String id){
        //先从缓存中拿数据
        Article article = (Article)redisTemplate.opsForValue().get("article_"+id);
        //如果拿不到，就去数据库中查询
        if (article==null){
            System.out.println("进入if");
            article = articleDao.findById(id).get();
            //放入缓存中
            redisTemplate.opsForValue().set("article_"+id, article, 10, TimeUnit.SECONDS);
        }
        System.out.println("123"+(Article)redisTemplate.opsForValue().get("article_"+id));
        return article;
    }

    /**
     * 插入文章
     * @param a
     */
    public  void update(Article a){
        redisTemplate.delete("article"+a);
        articleDao.save(a);
    }
    public  void deleteByid(String id){
        redisTemplate.delete("article"+id);
        articleDao.deleteById(id);
    }

    /**
     * 点赞
     * @param id
     * @return
     */
    public  int updateThumbup(String id){
        return articleDao.updateThumbup(id);
    }

    public void  examine(String id){
        articleDao.examine(id);
    }


}
