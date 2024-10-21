package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.Article;
import com.zhaowk.mapper.ArticleMapper;
import com.zhaowk.service.ArticleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //正式文章
        queryWrapper.eq(Article::getStatus, 0);
        //按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多10条
        Page<Article> page = new Page<>(1, 10);

        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

        return ResponseResult.okResult(articles);
    }
}
