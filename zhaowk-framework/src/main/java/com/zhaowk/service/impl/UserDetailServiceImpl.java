package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhaowk.domain.entity.LoginUser;
import com.zhaowk.domain.entity.User;
import com.zhaowk.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserName, s);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查到用户，如果没查到抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //返回用户信息
        //TODO 查询权限信息封装
        return new LoginUser(user);
    }
}
