package com.example.lifediary.controller;

import com.example.lifediary.serviceImpl.DiaryServiceImpl;
import com.example.lifediary.serviceImpl.ResourceServiceImpl;
import com.example.lifediary.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:基础控制器
 * 日期: 2023/2/5 10:55:35
 *
 * @author MoYuXi
 */
@RestController
public class BaseController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    DiaryServiceImpl diaryService;
    @Autowired
    ResourceServiceImpl resourceService;
    @Autowired
    HttpServletRequest request;
}
