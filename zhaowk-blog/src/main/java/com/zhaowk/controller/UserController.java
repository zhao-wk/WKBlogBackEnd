package com.zhaowk.controller;

import com.zhaowk.annotation.SystemLog;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.User;
import com.zhaowk.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(bussinessName = "获取用户信息")
    @ApiOperation(value = "获取用户信息", notes = "查询用户个人信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(bussinessName = "更新用户信息")
    @ApiOperation(value = "更新用户", notes = "更新用户个人信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("register")
    @SystemLog(bussinessName = "用户注册")
    @ApiOperation(value = "用户注册", notes = "添加注册用户信息")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
