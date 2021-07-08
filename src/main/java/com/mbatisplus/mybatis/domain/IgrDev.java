package com.mbatisplus.mybatis.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "集抄设备表")
@TableName("IGR_DEV")
public class IgrDev implements Serializable {

    private static final long serialVersionUID = 1011032422192513874L;

    /**
     * ID
     */
    @TableId(value = "N_A", type = IdType.AUTO)
    @TableField("N_A")
    @ApiModelProperty(value = "ID")
    @ExcelProperty
    private Integer N_A;

    /**
     * 所属公司
     */
    @ApiModelProperty(value = "所属公司")
    @TableField("N_B")
    @ExcelProperty
    private Integer N_B;

    /**
     * 设备类型ID
     */
    @ApiModelProperty(value = "设备类型ID")
    @TableField("N_C")
    @ExcelProperty
    private Integer N_C;

    /**
     * 上级设备
     */
    @ApiModelProperty(value = "上级设备")
    @TableField("N_D")
    private Integer N_D;

    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称")
    @TableField("N_D")
    private String C_A;

    /**
     * 设备编号
     */
    @ApiModelProperty(value = "设备编号")
    @TableField(value = "C_B")
    private String C_B;

    /**
     * 所属通道
     */
    @ApiModelProperty(value = "所属通道")
    @TableField(value = "N_E")
    private Integer N_E;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField(value = "C_C")
    private String C_C;

    /**
     * 坐标X
     */
    @ApiModelProperty(value = "坐标X")
    @TableField(value = "C_D")
    private String C_D;

    /**
     * 坐标Y
     */
    @ApiModelProperty(value = "坐标Y")
    @TableField(value = "C_E")
    private String C_E;

    /**
     * 安装日期
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "安装日期")
    @TableField(value = "D_A")
    private Date D_A;

    /**
     * 状态 1 正常 0 异常 2 停用
     */
    @ApiModelProperty(value = "状态 1 正常 0 异常 2 停用")
    @TableField(value = "N_F")
    private Integer N_F;

    /**
     * 最近一次通讯时间
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "最近一次通讯时间")
    @TableField(value = "D_B")
    private Date D_B;

    /**
     * 关联编号
     */
    @ApiModelProperty(value = "关联编号")
    @TableField(value = "C_F")
    private String C_F;

    /**
     * 可执行命令 多个逗号分割
     */
    @ApiModelProperty(value = "可执行命令 多个逗号分割")
    @TableField(value = "C_G")
    private String C_G;

    /**
     * 关联方式 1 用户编号 2 老用户编号 3 卡号 4 表号
     */
    @ApiModelProperty(value = "关联方式 1 用户编号 2 老用户编号 3 卡号 4 表号")
    @TableField(value = "N_G")
    private Integer N_G;

    /**
     * IMEI号，设备芯片唯一标识
     */
    @ApiModelProperty(value = "IMEI号，设备芯片唯一标识")
    @TableField(value = "C_H")
    private String C_H;

    /**
     * 设备ID，由物联网平台生成
     */
    @ApiModelProperty(value = "设备ID，由物联网平台生成")
    @TableField(value = "C_I")
    private String C_I;

    /**
     * 开户标志 0未开户 1已开户
     */
    @ApiModelProperty(value = "开户标志 0未开户 1已开户")
    @TableField(value = "N_H")
    private Integer N_H;

    /**
     * 阀门状态 0阀开 1阀关 2异常
     */
    @ApiModelProperty(value = "阀门状态 0阀开 1阀关 2异常")
    @TableField(value = "N_I")
    private Integer N_I;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号")
    @TableField(value = "C_J")
    private String C_J;

    /**
     * 读数
     */
    @ApiModelProperty(value = "读数")
    @TableField(value = "N_J")
    private BigDecimal N_J;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    @TableField(value = "N_K")
    private BigDecimal N_K;

    /**
     * 信号
     */
    @ApiModelProperty(value = "信号")
    @TableField(value = "N_L")
    private BigDecimal N_L;

    /**
     * 电压
     */
    @ApiModelProperty(value = "电压")
    @TableField(value = "N_M")
    private BigDecimal N_M;

    /**
     * 状态说明
     */
    @ApiModelProperty(value = "状态说明")
    @TableField(value = "C_K")
    private String C_K;

    /**
     * 压力
     */
    @ApiModelProperty(value = "压力")
    @TableField(value = "N_N")
    private BigDecimal N_N;

    /**
     * IMSI
     */
    @ApiModelProperty(value = "IMSI")
    @TableField(value = "C_L")
    private String C_L;

}
