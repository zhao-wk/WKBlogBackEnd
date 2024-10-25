package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.domain.entity.Role;
import com.zhaowk.mapper.RoleMapper;
import com.zhaowk.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是超级管理员 返回集合中只需要admin
        if (id == 1L){
            List<String> list = new ArrayList<>();
            list.add("admin");
            return list;
        }
        //否则查询当前用户所具有的角色信息

        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}
