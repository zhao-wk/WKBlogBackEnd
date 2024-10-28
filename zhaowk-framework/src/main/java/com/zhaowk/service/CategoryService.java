package com.zhaowk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.AddCategoryDTO;
import com.zhaowk.domain.dto.ListAllCategoryDTO;
import com.zhaowk.domain.dto.UpdateCategoryDTO;
import com.zhaowk.domain.entity.Category;

import javax.servlet.http.HttpServletResponse;

public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    void export(HttpServletResponse response);

    ResponseResult listAllCategories(Integer pageNum, Integer pageSize, ListAllCategoryDTO listAllCategoryDTO);

    ResponseResult addCategory(AddCategoryDTO addCategoryDTO);

    ResponseResult getCategoryById(Long id);

    ResponseResult updateCategory(UpdateCategoryDTO updateCategoryDTO);

    ResponseResult deleteCategory(Long id);
}
