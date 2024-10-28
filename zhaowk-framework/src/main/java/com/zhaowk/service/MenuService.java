package com.zhaowk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.MenuDTO;
import com.zhaowk.domain.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult listAllMenus(String menuName, String status);

    ResponseResult addMenu(MenuDTO menuDTO);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(MenuDTO menuDTO);

    ResponseResult deleteById(Long id);

    ResponseResult treeSelect();

    ResponseResult roleMenuTreeselect(Long id);
}
