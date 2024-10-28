package com.zhaowk.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签关联表(WkArticleTag)表实体类
 *
 * @author makejava
 * @since 2024-10-27 15:43:41
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("wk_article_tag")
public class ArticleTag {
//文章id
    private Long articleId;
//标签id
    private Long tagId;
}

