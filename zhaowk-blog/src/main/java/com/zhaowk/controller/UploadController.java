package com.zhaowk.controller;

import com.zhaowk.annotation.SystemLog;
import com.zhaowk.domain.ResponseResult;
import com.zhaowk.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "上传", description = "上传相关接口")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @SystemLog(bussinessName = "OSS上传图片")
    @ApiOperation(value = "上传图片", notes = "向oss上传一张图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "img", value = "上传的MultipartFile图片", paramType = "MultipartFile"),
    })
    public ResponseResult uploadImg(MultipartFile img) {
        return uploadService.uploadImg(img);
    }
}
