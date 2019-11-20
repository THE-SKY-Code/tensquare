package com.tensquare.service;

import com.tensquare.dao.ArticleDao;
import com.tensquare.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    /**
     * 添加文章
     * @param article
     */
    public  void add(Article article){
        articleDao.save(article);
    }

    /**
     * 根据标题内容模糊查询
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByTitleOrContentLike(String keyword,int page,int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findByTitleOrContentLike(keyword,keyword,pageRequest);
    }

}
