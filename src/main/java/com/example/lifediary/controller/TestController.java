package com.example.lifediary.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:测试控制器
 * 日期: 2023/2/3 22:31:16
 *
 * @author MoYuXi
 */
@Api(tags = "测试模块")
@RestController
public class TestController {

//    @Autowired
//    private UserMapper userMapper;

    @GetMapping("/test")
    @ApiOperation(value = "测试用例",notes = "测试用的方法")
    public String test(@RequestParam("test") String test) {
        return "接收到：" + test;
    }

//    @GetMapping("/list")
//    @ApiOperation(value = "用户列表",notes = "返回所有用户的所有信息")
//    public List<Users> list(){
//        return userMapper.selectList(null);
//    }
}
