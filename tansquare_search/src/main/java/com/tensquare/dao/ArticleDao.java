package com.tensquare.dao;

import com.tensquare.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleDao extends ElasticsearchRepository<Article,String>{
  public Page<Article> findByTitleOrContentLike(String tile, String content, Pageable pageable)  ;
}
