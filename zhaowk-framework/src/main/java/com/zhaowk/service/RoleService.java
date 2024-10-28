package com.zhaowk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.RoleAddDTO;
import com.zhaowk.domain.dto.RoleChangeDTO;
import com.zhaowk.domain.dto.RoleListDTO;
import com.zhaowk.domain.dto.RoleUpdateDTO;
import com.zhaowk.domain.entity.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult listAllRoles(Integer pageNum, Integer pageSize, RoleListDTO roleListDTO);

    ResponseResult changeStatus(RoleChangeDTO roleChangeDTO);

    ResponseResult addRole(RoleAddDTO roleAddDTO);

    ResponseResult getRoleById(Long id);

    ResponseResult updateRole(RoleUpdateDTO roleUpdateDTO);

    ResponseResult deleteRole(Long id);

    ResponseResult listAllNormalRoles();
}
