package com.zhaowk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaowk.domain.entity.RoleMenu;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    List<Long> getMenuByRoleId(Long id);

    void deleteByRoleId(Long id);
}
