package com.example.lifediary.dto;

import lombok.Data;

/**
 * 描述:用户更新
 * 日期: 2023/3/6 23:42:37
 *
 * @author MoYuXi
 */
@Data
public class UserUpdate {
    private String name;
    private String oldPassword;
    private String newPassword;

}
