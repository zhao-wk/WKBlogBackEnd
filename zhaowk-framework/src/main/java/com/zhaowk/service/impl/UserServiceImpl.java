package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.AddUserDTO;
import com.zhaowk.domain.dto.GetUserByIdDTO;
import com.zhaowk.domain.dto.UpdateUserDTO;
import com.zhaowk.domain.dto.UserListDTO;
import com.zhaowk.domain.entity.Role;
import com.zhaowk.domain.entity.User;
import com.zhaowk.domain.vo.PageVO;
import com.zhaowk.domain.vo.UserInfoVO;
import com.zhaowk.enums.AppHttpCodeEnum;
import com.zhaowk.exception.SystemException;
import com.zhaowk.mapper.UserMapper;
import com.zhaowk.service.RoleService;
import com.zhaowk.service.UserRoleService;
import com.zhaowk.service.UserService;
import com.zhaowk.utils.BeanCopyUtils;
import com.zhaowk.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

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

    @Override
    public ResponseResult register(User user) {
        addUserInDatabase(user);

        return ResponseResult.okResult();
    }

    private User addUserInDatabase(User user) {
        //对数据进行非空判断
        //username
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        //password
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        //email
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //nickname
        if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行重复判断
        //username
        if (userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //nickname
        if (nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //email
        if (emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return user;
    }

    @Override
    public ResponseResult listUsers(Integer pageNum, Integer pageSize, UserListDTO userListDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userListDTO.getUserName()), User::getUserName, userListDTO.getUserName());
        queryWrapper.eq(StringUtils.hasText(userListDTO.getPhonenumber()), User::getPhonenumber, userListDTO.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(userListDTO.getStatus()), User::getStatus, userListDTO.getStatus());
        Page<User> page = new Page<>(pageNum, pageSize);
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        PageVO pageVO = new PageVO(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVO);
    }

    @Override
    @Transactional
    public ResponseResult addUser(AddUserDTO addUserDTO) {
        //注册用户表
        User user = BeanCopyUtils.copyBean(addUserDTO, User.class);
        User addedUser = addUserInDatabase(user);
        Long userId = addedUser.getId();
        //添加角色关系
        userRoleService.addByUserId(userId, addUserDTO.getRoleIds());
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        baseMapper.deleteById(id);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserById(Long id) {
        //查询用户
        User user = getById(id);
        //查询所有role
        List<Role> roles = (List<Role>) roleService.listAllNormalRoles().getData();
        //查询用户关联的role
        List<Long> userRoles =  userRoleService.getRolesById(id);

        GetUserByIdDTO getUserByIdDTO = new GetUserByIdDTO(userRoles, roles, user);
        return ResponseResult.okResult(getUserByIdDTO);
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UpdateUserDTO updateUserDTO) {
        //更新user
        User user = BeanCopyUtils.copyBean(updateUserDTO, User.class);
        updateById(user);
        //删除原有UserRole
        userRoleService.deleteById(user.getId());
        //添加新的UserRole
        userRoleService.addByUserId(user.getId(), updateUserDTO.getRoleIds());

        return ResponseResult.okResult();
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }


}
