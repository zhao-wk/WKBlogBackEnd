package com.zhaowk.service;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
