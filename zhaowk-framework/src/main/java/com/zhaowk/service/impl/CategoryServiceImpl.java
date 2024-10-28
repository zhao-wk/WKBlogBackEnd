package com.zhaowk.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaowk.constants.SystemConstants;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.AddCategoryDTO;
import com.zhaowk.domain.dto.ListAllCategoryDTO;
import com.zhaowk.domain.dto.UpdateCategoryDTO;
import com.zhaowk.domain.entity.Article;
import com.zhaowk.domain.entity.Category;
import com.zhaowk.domain.vo.CategoryVO;
import com.zhaowk.domain.vo.ExcelCategoryVO;
import com.zhaowk.domain.vo.PageVO;
import com.zhaowk.enums.AppHttpCodeEnum;
import com.zhaowk.mapper.CategoryMapper;
import com.zhaowk.service.ArticleService;
import com.zhaowk.service.CategoryService;
import com.zhaowk.utils.BeanCopyUtils;
import com.zhaowk.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id 去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> category.getStatus().equals(SystemConstants.STATUS_NORMAL))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVO> categoryVOS = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);
        return ResponseResult.okResult(categoryVOS);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
        categoryWrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        List<Category> categoryList = list(categoryWrapper);
        List<CategoryVO> categoryVOS = BeanCopyUtils.copyBeanList(categoryList, CategoryVO.class);

        return ResponseResult.okResult(categoryVOS);
    }

    @Override
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader(response, "分类.xlsx");
            //获取需要导出的数据
            List<Category> list = list();
            List<ExcelCategoryVO> excelCategoryVOS = BeanCopyUtils.copyBeanList(list, ExcelCategoryVO.class);
            //数据写入excel
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVO.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVOS);
        } catch (Exception e) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }

    }

    @Override
    public ResponseResult listAllCategories(Integer pageNum, Integer pageSize, ListAllCategoryDTO listAllCategoryDTO) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(listAllCategoryDTO.getName()), Category::getName, listAllCategoryDTO.getName());
        queryWrapper.eq(StringUtils.hasText(listAllCategoryDTO.getStatus()), Category::getStatus, SystemConstants.STATUS_NORMAL);
        Page<Category> page = new Page<>(pageNum, pageSize);
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        PageVO pageVO = new PageVO(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVO);
    }

    @Override
    public ResponseResult addCategory(AddCategoryDTO addCategoryDTO) {
        Category category = BeanCopyUtils.copyBean(addCategoryDTO, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category byId = getById(id);
        return ResponseResult.okResult(byId);
    }

    @Override
    public ResponseResult updateCategory(UpdateCategoryDTO updateCategoryDTO) {
        Category category = BeanCopyUtils.copyBean(updateCategoryDTO, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }
}
