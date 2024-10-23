package com.zhaowk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();
}
