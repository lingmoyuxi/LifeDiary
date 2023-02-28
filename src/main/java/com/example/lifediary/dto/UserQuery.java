package com.example.lifediary.dto;

import lombok.Data;

/**
 * 描述:用户查询
 * 日期: 2023/2/5 11:39:38
 *
 * @author MoYuXi
 */
@Data
public class UserQuery extends PageInfo{
    private String name;
}
