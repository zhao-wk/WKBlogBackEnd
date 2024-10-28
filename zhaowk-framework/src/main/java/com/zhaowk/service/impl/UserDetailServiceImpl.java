package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhaowk.constants.SystemConstants;
import com.zhaowk.domain.entity.LoginUser;
import com.zhaowk.domain.entity.User;
import com.zhaowk.mapper.MenuMapper;
import com.zhaowk.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, s);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查到用户，如果没查到抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //返回用户信息

        if (SystemConstants.ADMIN.equals(user.getType())){
            //后台用户需要查询权限信息封装
            List<String> strings = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user, strings);
        }
        return new LoginUser(user, null);
    }
}
