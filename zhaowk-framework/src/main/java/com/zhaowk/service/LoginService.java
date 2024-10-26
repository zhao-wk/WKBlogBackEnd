package com.zhaowk.service;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
