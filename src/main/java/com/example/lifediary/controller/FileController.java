package com.example.lifediary.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifediary.dto.Result;
import com.example.lifediary.entity.Resource;
import com.example.lifediary.entity.User;
import com.example.lifediary.utils.JwtUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 描述:文件控制器
 * 日期: 2023/3/4 22:57:06
 *
 * @author MoYuXi
 */
@Api(tags = "文件模块")
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController extends BaseController {
    @PostMapping("/upload")
    public Result upLoadFile(Integer diaryId, @RequestParam("file") MultipartFile file) {
        Integer userId = JwtUtils.getUserId(request);
        if (userId == -1) {
            return Result.error(401, "Token验证未通过或已失效，请重新登录获取token！");
        }
        if (userService.getById(userId) != null) {
            LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
            if (diaryId != null) {
                wrapper.eq(Resource::getUserId, userId).eq(Resource::getDiaryId, diaryId).last("limit 1");
            } else {
                wrapper.eq(Resource::getUserId, userId).eq(Resource::getStatus, "0").last("limit 1");
            }
            Resource one = resourceService.getOne(wrapper);
            String path;
            if (one != null) {
                path = one.getAddress().substring(0, one.getAddress().lastIndexOf("/") + 1);
            } else {
                path = "static/upload/diary/" + userId + "/" + UUID.randomUUID() + "/";
            }
            String fileType = file.getContentType();
            String originalFilename = file.getOriginalFilename();
            String suffixName;
            if (originalFilename != null) {
                suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            } else return Result.error("空文件名");
            String filename = UUID.randomUUID() + suffixName;
            try {
                File iconFile = new File(getPath(path), filename);
                if (!iconFile.exists()) iconFile.mkdirs();
                file.transferTo(iconFile);
                Resource data = new Resource();
                data.setUserId(userId);
                data.setType(fileType);
                data.setAddress(path + filename);
                if (diaryId != null) {
                    data.setStatus(1);
                } else {
                    data.setStatus(0);
                }
                return resourceService.save(data) ? Result.success(200, data.getAddress()) : Result.error("服务器保存数据出错!");
            } catch (IOException e) {
                return Result.error("文件处理异常：" + e.getMessage());
            }
        } else {
            return Result.error("用户" + userId + "不存在");
        }
    }

    @PostMapping("/uploadIcon")
    public Result upLoadIcon(@RequestParam("file") MultipartFile file) {
        Integer userId = JwtUtils.getUserId(request);
        if (userId == -1) {
            return Result.error(401, "Token验证未通过或已失效，请重新登录获取token！");
        }
        String path = "upload/icon/";
        String originalFilename = file.getOriginalFilename();
        String suffixName;
        if (originalFilename != null) {
            suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else return Result.error("空文件名");
        String filename = UUID.randomUUID() + suffixName;
        try {
            User user = userService.getById(userId);
            String icon = user.getIcon();
            File iconFile = new File(getPath(path), filename);
            if (!iconFile.exists()) iconFile.mkdirs();
            file.transferTo(iconFile);
            user.setIcon("static/" + path + filename);
            if (icon != null && !icon.isEmpty() && !icon.equals("static/upload/icon/defaulticon.png")) {
                File oldIconFile = new File(getPath(icon));
                if (oldIconFile.isFile() && oldIconFile.exists()) oldIconFile.delete();
            }
            userService.updateById(user);
            return Result.success(200, user.getIcon());
        } catch (IOException e) {
            return Result.error("文件处理异常：" + e.getMessage());
        }
    }

    /**
     * 文件保存目录至项目同目录下
     *
     * @param subdirectory 添加至目录路径
     * @return 完整的文件路径
     * @throws FileNotFoundException
     */
    public String getPath(String subdirectory) throws FileNotFoundException {
        String property = System.getProperty("user.dir");
        File file = new File(property, subdirectory.startsWith("static/") ? subdirectory : "static/" + subdirectory);
//        if (!file.exists()) file.mkdirs();
        return file.getPath();
    }

}
