package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.domain.entity.RoleMenu;
import com.zhaowk.mapper.RoleMenuMapper;
import com.zhaowk.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Override
    public List<Long> getMenuByRoleId(Long id) {
        return baseMapper.getMenuByRoleId(id);

    }

    @Override
    public void deleteByRoleId(Long id) {
        baseMapper.deleteByRoleId(id);
    }
}
