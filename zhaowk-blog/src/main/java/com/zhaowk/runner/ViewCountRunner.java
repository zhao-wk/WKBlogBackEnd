package com.zhaowk.runner;

import com.zhaowk.constants.SystemConstants;
import com.zhaowk.domain.entity.Article;
import com.zhaowk.mapper.ArticleMapper;
import com.zhaowk.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        //存储到redis中
        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEW_COUNT_PREFIX, viewCountMap);
    }
}
