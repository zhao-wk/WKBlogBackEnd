package com.zhaowk.domain.vo;

import com.zhaowk.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVO {
    private List<Menu> menus;
}
