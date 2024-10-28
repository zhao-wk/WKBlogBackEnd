package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.domain.dto.AddUserDTO;
import com.zhaowk.domain.entity.UserRole;
import com.zhaowk.mapper.UserMapper;
import com.zhaowk.mapper.UserRoleMapper;
import com.zhaowk.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Override
    public void addByUserId(Long userId, List<Long> roleIds) {
        List<UserRole> userRoles = roleIds.stream()
                .map(roleId -> new UserRole(userId, roleId))
                .collect(Collectors.toList());

        saveBatch(userRoles);
    }

    @Override
    public List<Long> getRolesById(Long id) {
        return baseMapper.getRolesById(id);
    }

    @Override
    public void deleteById(Long id) {
        baseMapper.deleteByUserId(id);
    }
}
