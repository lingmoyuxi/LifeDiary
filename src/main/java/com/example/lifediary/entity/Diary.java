package com.example.lifediary.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author MoYuXi
 * @since 2023-02-18
 */
@Getter
@Setter
@TableName("diary")
@ApiModel(value = "Diary对象", description = "")
public class Diary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("日记标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("日记正文")
    @TableField("content")
    private String content;

    @ApiModelProperty("日记分类")
    @TableField("type")
    private String type;

    @ApiModelProperty("用户id")
    @TableField("use_id")
    private Integer useId;
}
