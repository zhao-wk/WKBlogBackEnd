package com.zhaowk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.TagDTO;
import com.zhaowk.domain.dto.TagListDTO;
import com.zhaowk.domain.entity.Tag;
import com.zhaowk.domain.vo.PageVO;

public interface TagService extends IService<Tag> {
    ResponseResult<PageVO> pageTagList(Integer pageNum, Integer pageSize, TagListDTO tagListDTO);

    ResponseResult addTag(TagDTO tagDTO);

    ResponseResult deleteTag(Long id);

    ResponseResult getTagById(Long id);

    ResponseResult updateTag(TagDTO tagDTO);

    ResponseResult listAllTag();
}
