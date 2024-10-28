package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.RoleAddDTO;
import com.zhaowk.domain.dto.RoleChangeDTO;
import com.zhaowk.domain.dto.RoleListDTO;
import com.zhaowk.domain.dto.RoleUpdateDTO;
import com.zhaowk.domain.entity.Role;
import com.zhaowk.domain.entity.RoleMenu;
import com.zhaowk.domain.vo.PageVO;
import com.zhaowk.mapper.RoleMapper;
import com.zhaowk.mapper.RoleMenuMapper;
import com.zhaowk.service.RoleMenuService;
import com.zhaowk.service.RoleService;
import com.zhaowk.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMenuService roleMenuService;

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

    @Override
    public ResponseResult<PageVO> listAllRoles(Integer pageNum, Integer pageSize, RoleListDTO roleListDTO) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleListDTO.getRoleName()), Role::getRoleName, roleListDTO.getRoleName());
        queryWrapper.eq(StringUtils.hasText(roleListDTO.getStatus()), Role::getStatus, roleListDTO.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>(pageNum, pageSize);
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        PageVO pageVO = new PageVO(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVO);
    }

    @Override
    public ResponseResult changeStatus(RoleChangeDTO roleChangeDTO) {
        baseMapper.changeStatus(roleChangeDTO.getRoleId(), roleChangeDTO.getStatus());

        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addRole(RoleAddDTO roleAddDTO) {
        //添加Role
        Role role = BeanCopyUtils.copyBean(roleAddDTO, Role.class);
        save(role);
        //添加RoleMenu
        List<Long> menuIdList = roleAddDTO.getMenuIds();

        List<RoleMenu> roleMenus = menuIdList.stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());

        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        return ResponseResult.okResult(role);
    }

    @Override
    @Transactional
    public ResponseResult updateRole(RoleUpdateDTO roleUpdateDTO) {
        Role role = BeanCopyUtils.copyBean(roleUpdateDTO, Role.class);
        updateById(role);
        //删除role_menu中原有
        roleMenuService.deleteByRoleId(role.getId());
        //添加新的menu_id
        List<RoleMenu> roleMenus = roleUpdateDTO.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<List<Role>> listAllNormalRoles() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, "0");

        List<Role> list = list(queryWrapper);

        return ResponseResult.okResult(list);
    }

}
