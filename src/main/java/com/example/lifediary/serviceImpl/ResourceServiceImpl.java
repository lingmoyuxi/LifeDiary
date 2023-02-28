package com.example.lifediary.serviceImpl;

import com.example.lifediary.entity.Resource;
import com.example.lifediary.mapper.ResourceMapper;
import com.example.lifediary.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MoYuXi
 * @since 2023-02-18
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

}
