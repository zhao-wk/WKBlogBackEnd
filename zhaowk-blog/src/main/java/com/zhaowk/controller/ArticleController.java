package com.zhaowk.controller;

import com.zhaowk.annotation.SystemLog;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.Article;
import com.zhaowk.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/article")
@CrossOrigin
@Api(tags = "文章", description = "文章相关接口")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @SystemLog(bussinessName = "热门文章列表")
    @ApiOperation(value = "热门文章", notes = "查询前十名热门文章列表")
    public ResponseResult hotArticleList(){
        //查询热门文章，封装成ResponseResult返回
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    @SystemLog(bussinessName = "分页查询文章列表")
    @ApiOperation(value = "获取文章列表", notes = "分页查询所有文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "categoryId", value = "分类id")

    })
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(bussinessName = "获取文章详情")
    @ApiOperation(value = "获取文章详细信息", notes = "根据文章id查询文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id")
    })
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @SystemLog(bussinessName = "更新文章浏览量")
    @ApiOperation(value = "更新浏览量", notes = "在redis中更新当前文章id的浏览量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id")
    })
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }


}
