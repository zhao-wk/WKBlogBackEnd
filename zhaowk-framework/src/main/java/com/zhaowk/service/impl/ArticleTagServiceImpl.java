package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.domain.entity.ArticleTag;
import com.zhaowk.mapper.ArticleTagMapper;
import com.zhaowk.service.ArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
