package com.zhaowk.controller;

import com.zhaowk.domain.ResponseResult;
import com.zhaowk.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("upload")
    public ResponseResult uploadImage(@RequestParam("img") MultipartFile multipartFile) {
        try {
            return uploadService.uploadImg(multipartFile);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("文件上传失败");
        }
    }
}
