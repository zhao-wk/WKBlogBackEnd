package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhaowk.constants.SystemConstants;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.Article;
import com.zhaowk.domain.entity.Category;
import com.zhaowk.domain.vo.ArticleDetailVO;
import com.zhaowk.domain.vo.ArticleListVO;
import com.zhaowk.domain.vo.HotArticleVO;
import com.zhaowk.domain.vo.PageVO;
import com.zhaowk.mapper.ArticleMapper;
import com.zhaowk.service.ArticleService;
import com.zhaowk.service.CategoryService;
import com.zhaowk.utils.BeanCopyUtils;
import com.zhaowk.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;


    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多10条
        Page<Article> page = new Page<>(1, 10);

        page(page, queryWrapper);

        List<Article> articles = page.getRecords();

//        List<HotArticleVO> articleVOS = new ArrayList<>();
//
//        //Bean拷贝
//        for (Article article : articles) {
//            HotArticleVO vo = new HotArticleVO();
//            BeanUtils.copyProperties(article, vo);
//            articleVOS.add(vo);
//        }

        List<HotArticleVO> vos = BeanCopyUtils.copyBeanList(articles, HotArticleVO.class);
        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //如果有category id，查询时需要和传入的相同
        //状态是正式发布
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop降序排序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);
        //查询分类名称
        List<Article> articles = page.getRecords();
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        articles.stream()
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) {
                        //获取分类id，查询分类信息，获取分类名称
                        Category byId = categoryService.getById(article.getCategoryId());
                        //把分类名称设置给article
                        article.setCategoryName(byId.getName());
                        return article;
                    }
                })
                .collect(Collectors.toList());


        //封装
        List<ArticleListVO> articleListVOS = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVO.class);


        PageVO pageVO = new PageVO(articleListVOS, page.getTotal());
        return ResponseResult.okResult(pageVO);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT_PREFIX, id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVO articleDetailVO = BeanCopyUtils.copyBean(article, ArticleDetailVO.class);
        //根据分类id查分类名
        Long categoryId = articleDetailVO.getCategoryId();
        Category byId = categoryService.getById(categoryId);
        if (byId != null){
            articleDetailVO.setCategoryName(byId.getName());
        }
        return ResponseResult.okResult(articleDetailVO);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中 对应id的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT_PREFIX, id.toString(), 1);
        return ResponseResult.okResult();
    }
}
