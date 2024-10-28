package com.zhaowk.controller;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.AddUserDTO;
import com.zhaowk.domain.dto.UpdateUserDTO;
import com.zhaowk.domain.dto.UserListDTO;
import com.zhaowk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult listUsers(Integer pageNum, Integer pageSize, UserListDTO userListDTO){
        return userService.listUsers(pageNum, pageSize, userListDTO);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody AddUserDTO addUserDTO){
        return userService.addUser(addUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        return userService.updateUser(updateUserDTO);
    }
}
