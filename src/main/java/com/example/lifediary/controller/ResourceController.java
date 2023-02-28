package com.example.lifediary.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifediary.common.Result;
import com.example.lifediary.dto.PageInfo;
import com.example.lifediary.entity.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MoYuXi
 * @since 2023-02-06
 */
@Api(tags = "资源模块")
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {
    @ApiOperation(value = "资源列表",notes = "获取所有资源信息")
    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(resourceService.list());
    }
    @ApiOperation(value = "资源分页",notes = "根据需要将资源列表分页查询")
    @PostMapping("/page")
    public Result<?> findPage(@RequestBody PageInfo pageInfo) {
        LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Resource::getId);
        Page<Resource> page = resourceService.page(new Page<>(pageInfo.getNum(), pageInfo.getSize()), wrapper);
        return Result.success(page);
    }
    @ApiOperation(value = "资源保存",notes = "资源的新增与更新操作")
    @PostMapping("/save")
    public Result<?> save(@Validated @RequestBody Resource resource) {
        return resourceService.save(resource)?Result.success():Result.error();
    }
    @ApiOperation(value = "资源删除",notes = "资源的批量删除")
    @PostMapping("/delete")
    public Result<?> save(@RequestBody List<Integer> ids) {
        return resourceService.removeByIds(ids) ? Result.success() : Result.error();
    }
}
