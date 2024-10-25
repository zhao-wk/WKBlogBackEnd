package com.zhaowk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaowk.domain.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<String> selectRoleKeyByUserId(Long id);
}
