package com.zhaowk.controller;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.domain.dto.ListLinkDTO;
import com.zhaowk.domain.dto.AddLinkDTO;
import com.zhaowk.domain.dto.UpdateLinkDTO;
import com.zhaowk.domain.entity.Link;
import com.zhaowk.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("list")
    public ResponseResult listAllLinks(Integer pageNum, Integer pageSize, ListLinkDTO listLinkDTO){
        return linkService.listAllLinks(pageNum, pageSize, listLinkDTO);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody AddLinkDTO addLinkDTO){
        return linkService.addLink(addLinkDTO);
    }

    @GetMapping("{id}")
    public ResponseResult getLinkById(@PathVariable("id") Long id){
        return linkService.getLinkById(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody UpdateLinkDTO updateLinkDTO){
        return linkService.updateLink(updateLinkDTO);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        return linkService.deleteLink(id);
    }
}
