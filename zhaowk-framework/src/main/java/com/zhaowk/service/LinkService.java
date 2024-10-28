package com.zhaowk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.ListLinkDTO;
import com.zhaowk.domain.dto.AddLinkDTO;
import com.zhaowk.domain.dto.UpdateLinkDTO;
import com.zhaowk.domain.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();

    ResponseResult listAllLinks(Integer pageNum, Integer pageSize, ListLinkDTO listLinkDTO);

    ResponseResult addLink(AddLinkDTO addLinkDTO);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(UpdateLinkDTO updateLinkDTO);

    ResponseResult deleteLink(Long id);
}
