package com.zhaowk.controller;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.TagDTO;
import com.zhaowk.domain.dto.TagListDTO;
import com.zhaowk.domain.vo.PageVO;
import com.zhaowk.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVO> list(Integer pageNum, Integer pageSize, TagListDTO tagListDTO){
        return tagService.pageTagList(pageNum, pageSize, tagListDTO);

    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagDTO tagDTO){
        return tagService.addTag(tagDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

   @GetMapping("/{id}")
   public ResponseResult getTagById(@PathVariable Long id){
        return tagService.getTagById(id);
   }

   @PutMapping()
    public ResponseResult updateTag(@RequestBody TagDTO tagDTO){
        return tagService.updateTag(tagDTO);
   }

   @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
   }
}
