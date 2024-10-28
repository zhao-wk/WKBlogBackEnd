package com.zhaowk.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuTreeselectVO {
    private List<MenuTreeVO> menus;
    private List<Long> checkedKeys;
}
