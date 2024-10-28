package com.zhaowk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.AddUserDTO;
import com.zhaowk.domain.dto.UpdateUserDTO;
import com.zhaowk.domain.dto.UserListDTO;
import com.zhaowk.domain.entity.User;

public interface UserService extends IService<User> {
    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult listUsers(Integer pageNum, Integer pageSize, UserListDTO userListDTO);

    ResponseResult addUser(AddUserDTO addUserDTO);

    ResponseResult deleteUser(Long id);

    ResponseResult getUserById(Long id);

    ResponseResult updateUser(UpdateUserDTO updateUserDTO);
}
