package com.zhaowk.controller;

import com.zhaowk.annotation.SystemLog;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.User;
import com.zhaowk.enums.AppHttpCodeEnum;
import com.zhaowk.exception.SystemException;
import com.zhaowk.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登陆", description = "登陆相关接口")
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(bussinessName = "登陆")
    @ApiOperation(value = "登陆")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())){
            //提示必须要传用户名
//            throw new RuntimeException()
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @SystemLog(bussinessName = "登出")
    @ApiOperation(value = "登出")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }
}
