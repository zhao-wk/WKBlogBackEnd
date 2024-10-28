package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.constants.SystemConstants;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.ListLinkDTO;
import com.zhaowk.domain.dto.AddLinkDTO;
import com.zhaowk.domain.dto.UpdateLinkDTO;
import com.zhaowk.domain.entity.Link;
import com.zhaowk.domain.vo.LinkVO;
import com.zhaowk.domain.vo.PageVO;
import com.zhaowk.mapper.LinkMapper;
import com.zhaowk.service.LinkService;
import com.zhaowk.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);

        List<Link> links = list(queryWrapper);
        List<LinkVO> linkVOS = BeanCopyUtils.copyBeanList(links, LinkVO.class);
        return ResponseResult.okResult(linkVOS);
    }

    @Override
    public ResponseResult listAllLinks(Integer pageNum, Integer pageSize, ListLinkDTO listLinkDTO) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(listLinkDTO.getName()), Link::getName, listLinkDTO.getName());
        queryWrapper.eq(StringUtils.hasText(listLinkDTO.getStatus()), Link::getStatus, listLinkDTO.getStatus());

        Page<Link> page = new Page<>(pageNum, pageSize);
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        PageVO pageVO = new PageVO(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVO);
    }

    @Override
    public ResponseResult addLink(AddLinkDTO addLinkDTO) {
        Link link = BeanCopyUtils.copyBean(addLinkDTO, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkById(Long id) {
        Link link = getById(id);
        return ResponseResult.okResult(link);
    }

    @Override
    public ResponseResult updateLink(UpdateLinkDTO updateLinkDTO) {
        Link link = BeanCopyUtils.copyBean(updateLinkDTO, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }

}
