package com.zhaowk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaowk.domain.entity.UserRole;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    List<Long> getRolesById(Long id);

    void deleteByUserId(Long id);
}
