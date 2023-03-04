package com.example.lifediary.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifediary.dto.AddDiary;
import com.example.lifediary.dto.DiaryList;
import com.example.lifediary.dto.PageInfo;
import com.example.lifediary.dto.Result;
import com.example.lifediary.entity.Diary;
import com.example.lifediary.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author MoYuXi
 * @since 2023-02-06
 */
@Api(tags = "日记模块")
@RestController
@RequestMapping("/diary")
public class DiaryController extends BaseController {

    @ApiOperation(value = "日记列表", notes = "获取所有日记信息")
    @GetMapping("/list")
    public Result list() {
        return Result.success(diaryService.list());
    }

    @ApiOperation(value = "日记详情", notes = "获取一篇日记信息")
    @GetMapping("/diaryInfo")
    public Result getDiaryInfo(@NotNull @RequestParam Integer id) {
        LambdaQueryWrapper<Diary> wrapper = new LambdaQueryWrapper<>();
        Integer userId = JwtUtils.getUserId(request);
        if (userId == -1) {
            return Result.error(401, "Token验证未通过或已失效，请重新登录获取token！");
        }
        wrapper.eq(Diary::getUseId, userId)
                .eq(Diary::getId, id);
        Diary diary = diaryService.getOne(wrapper);
        return diary != null ? Result.success(diary) : Result.error(userId + " 找不到id为 " + id + " 的日记");
    }

    @ApiOperation(value = "日记列表", notes = "获取用户的所有日记信息")
    @GetMapping("/diaryList")
    public Result useDiaryList() {
        LambdaQueryWrapper<Diary> wrapper = new LambdaQueryWrapper<>();
        Integer userId = JwtUtils.getUserId(request);
        if (userId == -1) {
            return Result.error(401, "Token验证未通过或已失效，请重新登录获取token！");
        }
        wrapper.eq(Diary::getUseId, userId).orderBy(true, false, Diary::getCreateTime, Diary::getUpdateTime);
        List<Diary> list = diaryService.list(wrapper);
        List<DiaryList> result = new ArrayList<>();
        list.forEach(e -> {
            DiaryList diaryList = new DiaryList();

            BeanUtils.copyProperties(e, diaryList);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (e.getCreateTime() != null) {
                diaryList.setCreateTime(format.format(e.getCreateTime()));
            }
            if (e.getUpdateTime() != null) {
                diaryList.setUpdateTime(format.format(e.getUpdateTime()));
            }

            String content = diaryList.getContent().replaceAll("<[^>]+>", "");
            diaryList.setContent(content.length() > 20 ? content.substring(0, 20) + "……" : content);

            result.add(diaryList);
        });
        return Result.success(result);
    }

    @ApiOperation(value = "日记分页", notes = "根据需要将日记列表分页查询")
    @PostMapping("/page")
    public Result findPage(@RequestBody PageInfo pageInfo) {
        LambdaQueryWrapper<Diary> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Diary::getId);
        Page<Diary> page = diaryService.page(new Page<>(pageInfo.getNum(), pageInfo.getSize()), wrapper);
        return Result.success(page);
    }

    @ApiOperation(value = "日记保存", notes = "日记的新增操作")
    @PostMapping("/save")
    public Result save(@Validated @RequestBody AddDiary newDiary) {
        Diary diary = new Diary();
        BeanUtils.copyProperties(newDiary, diary);
        Integer userId = JwtUtils.getUserId(request);
        if (userId == -1) {
            return Result.error(401, "Token验证未通过或已失效，请重新登录获取token！");
        }
        diary.setUseId(userId);
        return diaryService.save(diary) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "日记更新", notes = "日记的更新操作")
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Diary newDiary) {
        Integer userId = JwtUtils.getUserId(request);
        if (userId == -1) {
            return Result.error(401, "Token验证未通过或已失效，请重新登录获取token！");
        }
        LambdaQueryWrapper<Diary> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Diary::getUseId, userId).eq(Diary::getId, newDiary.getId());
        Diary diary = diaryService.getOne(wrapper);
        if (diary != null) {
            diaryService.update(newDiary, wrapper);
            return Result.success();
        } else {
            return Result.error("当前用户未找到该id为 " + newDiary.getId() + " 的日记");
        }
    }

    @ApiOperation(value = "日记删除", notes = "日记的删除")
    @GetMapping("/delete")
    public Result delete(@NotNull @RequestParam Integer id) {
        return diaryService.removeById(id) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "日记删除", notes = "日记的批量删除")
    @PostMapping("/deleteList")
    public Result deleteList(@RequestBody List<Integer> ids) {
        return diaryService.removeByIds(ids) ? Result.success() : Result.error();
    }
}
