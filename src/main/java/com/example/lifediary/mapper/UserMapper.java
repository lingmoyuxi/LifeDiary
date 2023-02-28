package com.example.lifediary.mapper;

import com.example.lifediary.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MoYuXi
 * @since 2023-02-18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
