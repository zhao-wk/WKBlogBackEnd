package com.zhaowk.service.impl;

import com.zhaowk.constants.SystemConstants;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.LoginUser;
import com.zhaowk.domain.entity.User;
import com.zhaowk.domain.vo.BlogUserLoginVO;
import com.zhaowk.domain.vo.UserInfoVO;
import com.zhaowk.service.BlogLoginService;
import com.zhaowk.utils.BeanCopyUtils;
import com.zhaowk.utils.JwtUtil;
import com.zhaowk.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //用户信息存入redis
        redisCache.setCacheObject(SystemConstants.BLOG_LOGIN_PREFIX + userId, loginUser);
        //token和userinfo封装返回
        //user -> userInfoVO
        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVO.class);
        BlogUserLoginVO vo = new BlogUserLoginVO(jwt, userInfoVO);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject(SystemConstants.BLOG_LOGIN_PREFIX + userId);
        return ResponseResult.okResult();
    }
}
