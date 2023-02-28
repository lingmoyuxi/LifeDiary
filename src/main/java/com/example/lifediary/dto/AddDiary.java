package com.example.lifediary.dto;

import lombok.Data;

/**
 * 描述:新增日记参数模型
 * 日期: 2023/2/19 14:05:56
 *
 * @author MoYuXi
 */
@Data
public class AddDiary {
    private String title;
    private String content;
    private String type;
}
