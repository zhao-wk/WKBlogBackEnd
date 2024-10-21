package com.zhaowk.service.impl;

import com.zhaowk.domain.entity.Article;
import com.zhaowk.mapper.ArticleMapper;
import com.zhaowk.service.ArticleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{
}
