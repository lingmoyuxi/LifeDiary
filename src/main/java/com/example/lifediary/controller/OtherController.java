package com.example.lifediary.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifediary.dto.Envent;
import com.example.lifediary.dto.Result;
import com.example.lifediary.entity.BaseEntity;
import com.example.lifediary.entity.Diary;
import com.example.lifediary.entity.User;
import com.example.lifediary.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 描述:其他功能控制器
 * 日期: 2023/2/3 22:31:16
 *
 * @author MoYuXi
 */
@Api(tags = "其他功能模块")
@RestController
public class OtherController extends BaseController {
    private static int getWordCount(String string) {
        if (string == null) {
            return 0;
        }
        String englishString = string.replaceAll("[\u4e00-\u9fa5]", "");
        String[] englishWords = englishString.split("[\\p{P}\\p{S}\\p{Z}\\s]+");
        int chineseWordCount = string.length() - englishString.length();
        int otherWordCount = englishWords.length;
        if (englishWords.length > 0 && englishWords[0].length() < 1) {
            otherWordCount--;
        }
        if (englishWords.length > 1 && englishWords[englishWords.length - 1].length() < 1) {
            otherWordCount--;
        }
        return chineseWordCount + otherWordCount;
    }

    private static long getTimeIntervalByDay(Date time1, Date time2) {
        long diff = time1.getTime() - time2.getTime();
        return diff / (1000 * 24 * 60 * 60);
    }

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
            enventList.sort((e1, e2) -> {
                if (e1.timestamp == null) return -1;
                else if (e2.timestamp == null) return 1;
                else return e2.timestamp.compareTo(e1.timestamp);
            });
            return Result.success(enventList);
        }

        Diary diary = diaryList.stream().min(Comparator.comparing(Diary::getCreateTime)).get();
        enventList.add(new Envent(diary.getCreateTime(), "你写下了第一篇日记，开始使用我们的网站记录自己的生活！"));

        Optional<Diary> optional = diaryList.stream().max((e1, e2) -> {
            if (e1.getUpdateTime() == null && e2.getUpdateTime() == null) return 0;
            else if (e1.getUpdateTime() == null) return -1;
            else if (e2.getUpdateTime() == null) return 1;
            else return e1.getUpdateTime().compareTo(e2.getUpdateTime());
        });
        if (optional.isPresent()) {
            diary = optional.get();
            if (diary.getUpdateTime() != null)
                enventList.add(new Envent(diary.getUpdateTime(), "是你最近更新你的日记的时间！"));
        }

        optional = diaryList.stream().max(Comparator.comparing(Diary::getCreateTime));
        if (optional.isPresent()) {
            diary = optional.get();
            enventList.add(new Envent(diary.getCreateTime(), "是你最近添加日记的时间！"));
        }

        List<Diary> diaryListSortByCreateTime = new ArrayList<>(diaryList);
        diaryListSortByCreateTime.sort(Comparator.comparing(BaseEntity::getCreateTime));
        if (diaryListSortByCreateTime.size() >= 10) {
            enventList.add(new Envent(diaryListSortByCreateTime.get(9).getCreateTime(), "你记录的日记达到10篇！"));
        }
        if (diaryListSortByCreateTime.size() >= 20) {
            enventList.add(new Envent(diaryListSortByCreateTime.get(19).getCreateTime(), "你记录的日记达到20篇！"));
        }
        if (diaryListSortByCreateTime.size() >= 50) {
            enventList.add(new Envent(diaryListSortByCreateTime.get(49).getCreateTime(), "你记录的日记达到50篇！"));
        }
        if (diaryListSortByCreateTime.size() >= 100) {
            enventList.add(new Envent(diaryListSortByCreateTime.get(99).getCreateTime(), "你记录的日记达到100篇！"));
        }
        if (diaryListSortByCreateTime.size() >= 500) {
            enventList.add(new Envent(diaryListSortByCreateTime.get(499).getCreateTime(), "你记录的日记达到500篇！"));
        }
        if (diaryListSortByCreateTime.size() >= 1000) {
            enventList.add(new Envent(diaryListSortByCreateTime.get(999).getCreateTime(), "你记录的日记达到1000篇！"));
        }

        long wordCount = 0;
        int wordCountFlag = 0;
        for (Diary item : diaryListSortByCreateTime) {
            wordCount += getWordCount(item.getContent().replaceAll("<[^>]+>", ""));
            if (wordCount >= 100 && wordCountFlag == 0) {
                wordCountFlag++;
                enventList.add(new Envent(item.getCreateTime(), "你写下的日记字数达到100字！"));
            }
            if (wordCount >= 300 && wordCountFlag == 1) {
                wordCountFlag++;
                enventList.add(new Envent(item.getCreateTime(), "你写下的日记字数达到300字！"));
            }
            if (wordCount >= 500 && wordCountFlag == 2) {
                wordCountFlag++;
                enventList.add(new Envent(item.getCreateTime(), "你写下的日记字数达到500字！"));
            }
            if (wordCount >= 1000 && wordCountFlag == 3) {
                wordCountFlag++;
                enventList.add(new Envent(item.getCreateTime(), "你写下的日记字数达到1000字！"));
            }
        }
        String str = String.format("本系统陪伴了你%d天，在这期间，你写下了%d篇日记，共计%d个字，平均每篇%d个字。",
                getTimeIntervalByDay(new Date(), user.getCreateTime()), diaryList.size(), wordCount, wordCount / diaryList.size());
        enventList.add(new Envent("至今", str));

        enventList.sort((e1, e2) -> {
            if (e1.timestamp == null && e2.timestamp == null) return 0;
            else if (e1.timestamp == null) return -1;
            else if (e2.timestamp == null) return 1;
            else return e2.timestamp.compareTo(e1.timestamp);
        });
        return Result.success(enventList);
    }
}
