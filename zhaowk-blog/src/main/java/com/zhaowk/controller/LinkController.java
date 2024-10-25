package com.zhaowk.controller;

import com.zhaowk.annotation.SystemLog;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@Api(tags = "友链", description = "友链相关接口")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(bussinessName = "查询所有友链")
    @ApiOperation(value = "查询友链", notes = "查询所有友链信息")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
