package com.tensquare.controller;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tensquare.pojo.Article;
import com.tensquare.service.ArticleService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/atricle")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 增加文章
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article){
        articleService.add(article);
        return new Result(true, StatusCode.OK,"插入成功");
    }

    /**
     * 查询
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/{keyword}/{page}/{size}",method = RequestMethod.GET)
    public Result findByTitleOrContentLike(@PathVariable String keyword ,@PathVariable int page ,@PathVariable int  size ){
        Page<Article> pagelist = articleService.findByTitleOrContentLike(keyword, page, size);
        return  new Result(true,StatusCode.OK,"查询成功",pagelist);
    }

}

