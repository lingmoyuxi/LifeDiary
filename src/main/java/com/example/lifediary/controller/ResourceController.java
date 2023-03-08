package com.example.lifediary.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifediary.dto.PageInfo;
import com.example.lifediary.dto.Result;
import com.example.lifediary.entity.Diary;
import com.example.lifediary.entity.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author MoYuXi
 * @since 2023-02-06
 */
@Api(tags = "资源模块")
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {
    @Autowired
    FileController fileController;

    @ApiOperation(value = "资源列表", notes = "获取所有资源信息")
    @GetMapping("/list")
    public Result list() {
        return Result.success(resourceService.list());
    }

    @ApiOperation(value = "资源分页", notes = "根据需要将资源列表分页查询")
    @PostMapping("/page")
    public Result findPage(@RequestBody PageInfo pageInfo) {
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Resource::getId);
        Page<Resource> page = resourceService.page(new Page<>(pageInfo.getNum(), pageInfo.getSize()), wrapper);
        return Result.success(page);
    }

    @ApiOperation(value = "资源保存", notes = "资源的新增与更新操作")
    @PostMapping("/save")
    public Result save(@Validated @RequestBody Resource resource) {
        return resourceService.save(resource) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "资源删除", notes = "资源的批量删除")
    @PostMapping("/delete")
    public Result save(@RequestBody List<Integer> ids) {
        return resourceService.removeByIds(ids) ? Result.success() : Result.error();
    }

    /**
     * 保存日记时绑定资源表
     *
     * @param userId  用户ID
     * @param diaryId 日记ID
     */
    public void bindDiary(Integer userId, Integer diaryId) {
        List<Resource> updatelist = new ArrayList<>(), removeList = new ArrayList<>();
        List<String> addressList = updateResourceByDiaryId(diaryId);
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Resource::getUserId, userId)
                .eq(Resource::getStatus, "1")
                .eq(Resource::getDiaryId, diaryId);
        List<Resource> diartlist = resourceService.list(wrapper);
        diartlist.forEach(e -> {
            AtomicInteger index = new AtomicInteger(-1);
            addressList.forEach(o -> {
                int i = o.lastIndexOf(e.getAddress());
                if (i != -1) index.set(i);
            });
            if (index.get() == -1) removeList.add(e);
        });
        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Resource::getUserId, userId).eq(Resource::getStatus, "0");
        List<Resource> resourceList = resourceService.list(wrapper);
        resourceList.forEach(e -> {
            AtomicInteger index = new AtomicInteger(-1);
            addressList.forEach(o -> {
                int i = o.lastIndexOf(e.getAddress());
                if (i != -1) index.set(i);
            });
            if (index.get() != -1) {
                e.setStatus(1);
                e.setDiaryId(diaryId);
                updatelist.add(e);
            } else removeList.add(e);
        });
        resourceService.updateBatchById(updatelist);
        resourceService.removeByIds(removeList);
        deleteFile(removeList);
    }

    public void unBindDiary(Integer userId, Integer diaryId) {
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Resource::getUserId, userId).eq(Resource::getDiaryId, diaryId);
        List<Resource> removeList = resourceService.list(wrapper);
        resourceService.removeByIds(removeList);
        deleteFile(removeList);
    }

    private void deleteFile(List<Resource> removeList) {
        removeList.forEach(e -> {
            File oldIconFile = null;
            try {
                oldIconFile = new File(fileController.getPath(e.getAddress()));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            if (oldIconFile.isFile() && oldIconFile.exists()) oldIconFile.delete();
            File parentFile = oldIconFile.getParentFile();
            if (parentFile != null && parentFile.listFiles().length == 0) {
                parentFile.delete();
            }
        });
    }

    public List<String> updateResourceByDiaryId(Integer diaryId) {
        LambdaQueryWrapper<Diary> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Diary::getId, diaryId);
        Diary diary = diaryService.getOne(wrapper);
        List<String> addressList = new ArrayList<>();
        if (diary != null) {
            final String regex = "<.*?src=\"(.*?)\".*?\\/?>";
            final String string = diary.getContent();
            final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(string);
            while (matcher.find()) {
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    addressList.add(matcher.group(i));
                }
            }
        }
        return addressList;
    }
}
