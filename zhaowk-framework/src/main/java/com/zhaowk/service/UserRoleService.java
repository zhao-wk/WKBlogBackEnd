package com.zhaowk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaowk.domain.dto.AddUserDTO;
import com.zhaowk.domain.entity.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {
    void addByUserId(Long id, List<Long> roleIds);

    List<Long> getRolesById(Long id);

    void deleteById(Long id);
}
