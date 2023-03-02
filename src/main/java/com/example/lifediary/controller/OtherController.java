package com.example.lifediary.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifediary.dto.Envent;
import com.example.lifediary.dto.Result;
import com.example.lifediary.entity.Diary;
import com.example.lifediary.entity.User;
import com.example.lifediary.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 描述:其他功能控制器
 * 日期: 2023/2/3 22:31:16
 *
 * @author MoYuXi
 */
@Api(tags = "其他功能模块")
@RestController
public class OtherController extends BaseController {
    @GetMapping("/course")
    @ApiOperation(value = "历程功能", notes = "用户时光轴功能")
    public Result course() {
        Integer userId = JwtUtils.getUserId(request);
        if (userId == -1) {
            return Result.error(401, "Token验证未通过或已失效，请重新登录获取token！");
        }
        User user = userService.getById(userId);
        if (user == null) {
            return Result.error(401, "Token验证未通过或已失效，请重新登录获取token！");
        }
        List<Envent> enventList = new ArrayList<>();

        enventList.add(new Envent(user.getCreateTime(), "您成为我们的用户。"));

        List<Diary> diaryList = diaryService.list(new LambdaQueryWrapper<Diary>().eq(Diary::getUseId, userId));

        if (diaryList.isEmpty()) {
            enventList.add(new Envent("至今", "您还没留下什么足迹呢！"));
            return Result.success(enventList);
        }

        Diary diary = diaryList.stream().min(Comparator.comparing(Diary::getCreateTime)).get();
        enventList.add(new Envent(diary.getCreateTime(), "你写下了第一篇日记，开始使用我们的网站记录自己的生活"));


        enventList.add(new Envent("至今", "你已经写下了" + diaryList.size() + "篇日记"));

        return Result.success(enventList);
    }

}
