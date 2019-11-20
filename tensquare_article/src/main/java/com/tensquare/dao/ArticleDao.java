package com.tensquare.dao;

import com.tensquare.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{
    /**
     * 审核文章
     * @param id
     */
    @Modifying
    @Query(value = "update tb_article set state='1' where id=?",nativeQuery = true)
    public void examine(String id);

    /**
     * 点赞加一
     * @param id
     * @return
     */
    @Modifying
    @Query(value = "update tb_article set thumbup=thumbup+1 where id=?",nativeQuery = true)
    public int updateThumbup(String id);

}
