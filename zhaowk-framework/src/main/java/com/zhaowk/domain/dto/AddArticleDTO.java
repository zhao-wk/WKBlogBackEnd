package com.zhaowk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleDTO {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private Long categoryId;
    private String thumbnail;
    //是否置顶 0否1是
    private String isTop;
    //状态 0发布1草稿
    private String status;
    //访问量
    private Long viewCount;
    //是否允许评论 1是0否
    private String isComment;
    private List<Long> tags;
}
