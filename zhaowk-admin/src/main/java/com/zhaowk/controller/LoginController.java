package com.zhaowk.controller;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.entity.LoginUser;
import com.zhaowk.domain.entity.Menu;
import com.zhaowk.domain.entity.User;
import com.zhaowk.domain.vo.AdminUserInfoVO;
import com.zhaowk.domain.vo.RoutersVO;
import com.zhaowk.domain.vo.UserInfoVO;
import com.zhaowk.enums.AppHttpCodeEnum;
import com.zhaowk.exception.SystemException;
import com.zhaowk.service.LoginService;
import com.zhaowk.service.MenuService;
import com.zhaowk.service.RoleService;
import com.zhaowk.service.UserService;
import com.zhaowk.utils.BeanCopyUtils;
import com.zhaowk.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }

    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVO> getInfo() {
        //获取当前登陆的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(user, UserInfoVO.class);
        //封装返回
        AdminUserInfoVO adminUserInfoVO = new AdminUserInfoVO(perms, roleKeyList,userInfoVO);
        return ResponseResult.okResult(adminUserInfoVO);
    }

    @GetMapping("getRouters")
    public ResponseResult<RoutersVO> getRouters() {
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVO(menus));
    }

}
