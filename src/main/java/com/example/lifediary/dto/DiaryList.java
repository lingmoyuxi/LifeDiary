package com.example.lifediary.dto;

import lombok.Data;

/**
 * 描述:日记列表 字段内容
 * 日期: 2023/2/22 15:03:39
 *
 * @author MoYuXi
 */
@Data
public class DiaryList {
    private Integer id;
    private String title;
    private String content;
    private String type;
    private Integer useId;
    private String createTime;
    private String updateTime;
}
