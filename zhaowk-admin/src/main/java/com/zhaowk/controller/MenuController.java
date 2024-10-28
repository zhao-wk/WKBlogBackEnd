package com.zhaowk.controller;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.MenuDTO;
import com.zhaowk.domain.entity.Menu;
import com.zhaowk.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("list")
    public ResponseResult listAllMenus(String menuName, String status){
        return menuService.listAllMenus(menuName, status);
    }

    @PostMapping
    public ResponseResult addMenu(@RequestBody MenuDTO menuDTO){
        return menuService.addMenu(menuDTO);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable("id") Long id){
        return menuService.getMenuById(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody MenuDTO menuDTO){
        return menuService.updateMenu(menuDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable("id") Long id){
        return menuService.deleteById(id);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeSelect(){
        return menuService.treeSelect();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeselect(@PathVariable("id") Long id){
        return menuService.roleMenuTreeselect(id);
    }

}
