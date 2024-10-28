package com.zhaowk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryDTO {
    private Long id;
    private String name;
    //描述
    private String description;
    //状态0:正常,1禁用
    private String status;
}
