package com.zhaowk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.AddArticleDTO;
import com.zhaowk.domain.dto.ArticleListDTO;
import com.zhaowk.domain.entity.Article;
import org.springframework.http.ResponseEntity;

public interface ArticleService extends IService<Article>{
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDTO article);

    ResponseResult listArticle(Integer pageNum, Integer pageSize, ArticleListDTO articlelistDTO);

    ResponseResult getArticle(Long id);

    ResponseResult updateArticle(AddArticleDTO article);

    ResponseResult deleteArticle(Long id);
}
