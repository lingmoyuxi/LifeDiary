package com.example.lifediary.controller;

import com.example.lifediary.dto.Result;
import io.swagger.annotations.Api;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 描述:文件控制器
 * 日期: 2023/3/4 22:57:06
 *
 * @author MoYuXi
 */
@Api(tags = "文件模块")
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {
    @PostMapping("/upload")
    public Result upLoadFile(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
//        return Result.success(file.getOriginalFilename());
        return Result.success(ResourceUtils.getURL("classpath:").getPath());
    }

    public String getPath() throws FileNotFoundException {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");
            return path.getAbsolutePath();
        }
        return null;
    }

}
