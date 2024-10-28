package com.zhaowk.controller;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.RoleAddDTO;
import com.zhaowk.domain.dto.RoleChangeDTO;
import com.zhaowk.domain.dto.RoleListDTO;
import com.zhaowk.domain.dto.RoleUpdateDTO;
import com.zhaowk.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    public ResponseResult listAllRoles(Integer pageNum, Integer pageSize, RoleListDTO roleListDTO) {
        return roleService.listAllRoles(pageNum, pageSize, roleListDTO);
    }

    @GetMapping("listAllRole")
    public ResponseResult listAllNormalRole() {
        return roleService.listAllNormalRoles();
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleChangeDTO roleChangeDTO) {
        return roleService.changeStatus(roleChangeDTO);
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody RoleAddDTO roleAddDTO){
        return roleService.addRole(roleAddDTO);
    }

    @GetMapping("/{id}")
    public ResponseResult getRoleById(@PathVariable("id") Long id){
        return roleService.getRoleById(id);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody RoleUpdateDTO roleUpdateDTO){
        return roleService.updateRole(roleUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long id){
        return roleService.deleteRole(id);
    }
}
