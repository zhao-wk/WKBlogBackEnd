package com.zhaowk.controller;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.AddCategoryDTO;
import com.zhaowk.domain.dto.ListAllCategoryDTO;
import com.zhaowk.domain.dto.UpdateCategoryDTO;
import com.zhaowk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    public void export(HttpServletResponse response){
        categoryService.export(response);
    }

    @GetMapping("list")
    public ResponseResult listAllCategories(Integer pageNum, Integer pageSize, ListAllCategoryDTO listAllCategoryDTO) {
        return categoryService.listAllCategories(pageNum, pageSize, listAllCategoryDTO);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody AddCategoryDTO addCategoryDTO) {
        return categoryService.addCategory(addCategoryDTO);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody UpdateCategoryDTO updateCategoryDTO) {
        return categoryService.updateCategory(updateCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
