package com.zhaowk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PathUtils {
    public static String generateFilePath(String fileName){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String dataPath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        int index = fileName.lastIndexOf(".");

        String fileType = fileName.substring(index);
        return new StringBuilder().append(dataPath).append(uuid).append(fileType).toString();
    }
}
