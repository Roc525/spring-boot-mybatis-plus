package com.mbatisplus.mybatis.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Echo42
 * @since 2020-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("provider")
@ApiModel(value = "Provider对象", description = "供应商对象")
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pid", type = IdType.AUTO)
    @ApiModelProperty(value = "主键", name = "pid", example = "")
    private Integer pid;

    private String providerCode;

    @TableField("providerName")
    private String providerName;

    private String people;

    private String phone;

    private String address;

    private String fax;

    private String describe;

    private LocalDateTime createDate;


}
