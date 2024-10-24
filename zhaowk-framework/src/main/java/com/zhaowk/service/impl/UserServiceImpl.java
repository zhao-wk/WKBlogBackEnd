package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.User;
import com.zhaowk.domain.vo.UserInfoVO;
import com.zhaowk.mapper.UserMapper;
import com.zhaowk.service.UserService;
import com.zhaowk.utils.BeanCopyUtils;
import com.zhaowk.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //查询用户信息
        User user = getById(userId);
        //封装UserInfoVO
        UserInfoVO vo = BeanCopyUtils.copyBean(user, UserInfoVO.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }
}
