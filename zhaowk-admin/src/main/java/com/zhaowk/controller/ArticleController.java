package com.zhaowk.controller;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.AddArticleDTO;
import com.zhaowk.domain.dto.ArticleListDTO;
import com.zhaowk.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDTO article) {
        return articleService.addArticle(article);
    }

    @GetMapping("/list")
    public ResponseResult listArticle(Integer pageNum, Integer pageSize, ArticleListDTO articleListDTO) {
        return articleService.listArticle(pageNum, pageSize, articleListDTO);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddArticleDTO article){
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id);
    }
}
