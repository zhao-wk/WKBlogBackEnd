package com.zhaowk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaowk.domain.entity.ArticleTag;

public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    void deleteByArticleId(Long id);
}
