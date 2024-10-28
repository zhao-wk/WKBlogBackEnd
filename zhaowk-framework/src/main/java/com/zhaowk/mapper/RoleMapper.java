package com.zhaowk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaowk.domain.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<String> selectRoleKeyByUserId(Long id);

    void changeStatus(@Param("roleId") Long roleId, @Param("status") String status);
}
