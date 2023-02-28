package com.example.lifediary.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifediary.common.Result;
import com.example.lifediary.dto.PageInfo;
import com.example.lifediary.entity.User;
import com.example.lifediary.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author MoYuXi
 * @since 2023-02-05
 */
@Api(tags = "用户模块")
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController extends BaseController {

    @ApiOperation(value = "用户列表", notes = "获取所有用户信息")
    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(userService.list());
    }

    @ApiOperation(value = "用户分页", notes = "根据需要将用户列表分页查询")
    @PostMapping("/page")
    public Result<?> findPage(@RequestBody PageInfo pageInfo) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(User::getId);
//        if (!StringUtils.isBlank(pageInfo.getName())) {
//            wrapper.like(User::getName, pageInfo.getName());
//        }
        Page<User> page = userService.page(new Page<>(pageInfo.getNum(), pageInfo.getSize()), wrapper);
        return Result.success(page);
    }

    @ApiOperation(value = "用户保存", notes = "用户的新增操作")
    @PostMapping("/save")
    private Result<?> save(@Validated @RequestBody User user) {
        return userService.save(user) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "用户更新", notes = "用户的更新操作")
    @PostMapping("/update")
    public Result<?> update(@Validated @RequestBody User user) {
        Integer userId = JwtUtils.getUserId(request);
        if (user.getId().equals(userId)) {
            return userService.updateById(user) ? Result.success() : Result.error();
        } else {
            return Result.error("token与用户id不匹配，请确认id是否正确，或者重新登录获取token！");
        }
    }

    @ApiOperation(value = "用户删除", notes = "用户的批量删除")
    @PostMapping("/delete")
    public Result<?> save(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids) ? Result.success() : Result.error();
    }

    @ApiOperation(value = "用户登录", notes = "用户登录操作")
    @GetMapping("/login")
    public Result<?> login(@Max(999999999) @RequestParam("account") Integer account, @NotBlank @RequestParam("password") String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, account)
                .eq(User::getPassword, password)
                .last("limit 1");
        User user = userService.getOne(wrapper);
        if (user != null) {
            return Result.success(getReturnUser(user));
        } else {
            return Result.error("账户或密码不正确!");
        }
    }

    @ApiOperation(value = "用户注册", notes = "用户注册操作")
    @GetMapping("/registry")
    public Result<?> registry(@Max(999999999) @RequestParam("account") Integer account, @NotBlank @RequestParam("password") String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, account)
                .last("limit 1");
        if (userService.getOne(wrapper) == null) {
            User user = new User();
            user.setAccount(account);
            user.setName("用户" + (new Random().nextInt(90000) + 10000));
            user.setPassword(password);
            userService.save(user);
            return Result.success(getReturnUser(user));
        } else {
            return Result.error("账号已存在！");
        }
    }

    @NotNull
    private HashMap<Object, Object> getReturnUser(User user) {
        HashMap<Object, Object> returnInfo = new HashMap<>();
        HashMap<Object, Object> map = new HashMap<>();
        String token = JwtUtils.generateToken(user);
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("account", user.getAccount());
        map.put("token", token);
        returnInfo.put("userInfo", map);
        returnInfo.put("menuList", "");
        return returnInfo;
    }
}
