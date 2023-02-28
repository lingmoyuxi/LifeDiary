package com.example.lifediary.serviceImpl;

import com.example.lifediary.entity.User;
import com.example.lifediary.mapper.UserMapper;
import com.example.lifediary.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
