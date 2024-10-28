package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.TagDTO;
import com.zhaowk.domain.dto.TagListDTO;
import com.zhaowk.domain.entity.Tag;
import com.zhaowk.domain.vo.PageVO;
import com.zhaowk.domain.vo.TagVO;
import com.zhaowk.mapper.TagMapper;
import com.zhaowk.service.TagService;
import com.zhaowk.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public ResponseResult<PageVO> pageTagList(Integer pageNum, Integer pageSize, TagListDTO tagListDTO) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(tagListDTO.getName()), Tag::getName, tagListDTO.getName());
        queryWrapper.like(StringUtils.hasText(tagListDTO.getRemark()), Tag::getRemark, tagListDTO.getRemark());
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVO pageVO = new PageVO(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVO);
    }

    @Override
    public ResponseResult addTag(TagDTO tagDTO) {
        Tag tag = BeanCopyUtils.copyBean(tagDTO, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Long id) {
        Tag byId = getById(id);
        TagDTO tagDTO = BeanCopyUtils.copyBean(byId, TagDTO.class);

        return ResponseResult.okResult(tagDTO);
    }

    @Override
    public ResponseResult updateTag(TagDTO tagDTO) {
        Tag tag = BeanCopyUtils.copyBean(tagDTO, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getName);
        List<Tag> list = list(queryWrapper);
        List<TagVO> tagVOS = BeanCopyUtils.copyBeanList(list, TagVO.class);

        return ResponseResult.okResult(tagVOS);
    }
}
