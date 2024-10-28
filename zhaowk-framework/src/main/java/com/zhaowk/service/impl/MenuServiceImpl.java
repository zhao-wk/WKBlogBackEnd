package com.zhaowk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.constants.SystemConstants;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.MenuDTO;
import com.zhaowk.domain.entity.Menu;
import com.zhaowk.domain.vo.MenuTreeVO;
import com.zhaowk.domain.vo.RoleMenuTreeselectVO;
import com.zhaowk.enums.AppHttpCodeEnum;
import com.zhaowk.exception.SystemException;
import com.zhaowk.mapper.MenuMapper;
import com.zhaowk.service.MenuService;
import com.zhaowk.service.RoleMenuService;
import com.zhaowk.utils.BeanCopyUtils;
import com.zhaowk.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    private final MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是超级管理员，返回所有权限
        if (SecurityUtils.isAdmin()){
            //管理员
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if (SecurityUtils.isAdmin()){
            //管理员返回所有符合要求的menu
            menus = menuMapper.selectAllRouterMenu();
        }else {
            //否则返回当前用户具有的menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建Tree
        //先找出第一层菜单，找出子菜单，设置到children属性中
        List<Menu> menuTree = buildMenuTree(menus ,0L);

        return menuTree;
    }

    @Override
    public ResponseResult listAllMenus(String menuName, String status) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.hasText(status), Menu::getStatus, status);
        queryWrapper.like(StringUtils.hasText(menuName), Menu::getMenuName, menuName);
        queryWrapper.orderByDesc(Menu::getOrderNum);

        List<Menu> menus = list(queryWrapper);

        return ResponseResult.okResult(menus);
    }

    @Override
    public ResponseResult addMenu(MenuDTO menuDTO) {
        Menu menu = BeanCopyUtils.copyBean(menuDTO, Menu.class);
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuById(Long id) {
        Menu menu = getById(id);
        MenuDTO menuDTO = BeanCopyUtils.copyBean(menu, MenuDTO.class);
        return ResponseResult.okResult(menuDTO);
    }

    @Override
    public ResponseResult updateMenu(MenuDTO menuDTO) {
        if (menuDTO.getParentId().equals(menuDTO.getId())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Menu menu = BeanCopyUtils.copyBean(menuDTO, Menu.class);
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteById(Long id) {
        //所有menus
        List<Menu> menus = list();
        Menu menu = getById(id);
        List<Menu> children = getChildren(menu, menus);
        if (!children.isEmpty()){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<List<MenuTreeVO>> treeSelect() {
        List<Menu> menus = menuMapper.selectAllMenus();
        List<MenuTreeVO> menuTreeVOS = menus.stream()
                .map(menu -> new MenuTreeVO(menu.getId(), menu.getMenuName(), menu.getParentId(), null))
                .collect(Collectors.toList());
        List<MenuTreeVO> menuTree = buildTreeSelect(menuTreeVOS ,0L);
        return ResponseResult.okResult(menuTree);

    }

    @Override
    public ResponseResult<RoleMenuTreeselectVO> roleMenuTreeselect(Long id) {
        List<MenuTreeVO> menus = treeSelect().getData();
        List<Long> menuIds = roleMenuService.getMenuByRoleId(id);
        RoleMenuTreeselectVO roleMenuTreeselectVO = new RoleMenuTreeselectVO(menus, menuIds);

        return ResponseResult.okResult(roleMenuTreeselectVO);
    }

    private List<MenuTreeVO> buildTreeSelect(List<MenuTreeVO> menus, long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getTreeSelectChildren(menu, menus)))
                .collect(Collectors.toList());
    }

    private List<MenuTreeVO> getTreeSelectChildren(MenuTreeVO menu, List<MenuTreeVO> menus) {
        return menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getTreeSelectChildren(m, menus)))
                .collect(Collectors.toList());
    }

    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
    }

    /**
     * 获取传入menu的子menu
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        return menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
    }

}
