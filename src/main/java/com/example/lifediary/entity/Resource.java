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
 * @since 2023-03-07
 */
@Getter
@Setter
@TableName("resource")
@ApiModel(value = "Resource对象", description = "")
public class Resource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("日记id")
    @TableField("diary_id")
    private Integer diaryId;

    @ApiModelProperty("资源类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("存放位置")
    @TableField("address")
    private String address;
}
