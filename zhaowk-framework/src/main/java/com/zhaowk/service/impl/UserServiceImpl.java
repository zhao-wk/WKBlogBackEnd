package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.domain.entity.User;
import com.zhaowk.mapper.UserMapper;
import com.zhaowk.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
