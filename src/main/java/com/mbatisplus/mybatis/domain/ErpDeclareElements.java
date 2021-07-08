package com.mbatisplus.mybatis.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: erp_declare_elements申报要素库实体表
 * @author: pengjiang@powerbridge.com
 * @Date: 2020年09月04日 11:18:29
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ErpDeclareElements对象", description = "申报预归素表 ")
@TableName("erp_declare_elements")
public class ErpDeclareElements implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @TableId("OID")
    @ApiModelProperty(value = "主键OID", name = "oid", example = "")
    private String oid;

    @TableField("BIZOP_ETPSNO")
    @ApiModelProperty(value = "经营企业编号", name = "bizopEtpsno", example = "")
    private String bizopEtpsno;

    @ExcelProperty
    @TableField("GDS_SEQNO")
    @ApiModelProperty(value = "预归类编号", name = "gdsSeqno", example = "")
    private BigDecimal gdsSeqno;

    @ExcelProperty
    @TableField("GDS_MTNO")
    @ApiModelProperty(value = "物资编码", name = "gdsMtno", example = "")
    private String gdsMtno;

    @ExcelProperty
    @TableField("GDECD")
    @ApiModelProperty(value = "商品编码", name = "gdecd", example = "")
    private String gdecd;

    @NotEmpty
    @ExcelProperty
    @TableField("GDS_NM")
    @ApiModelProperty(value = "商品名称", name = "gdsNm", example = "")
    private String gdsNm;

    @ExcelProperty
    @TableField("NORM_GDS_SPCF_MODEL_DESC")
    @ApiModelProperty(value = "规范申报要素", name = "normGdsSpcfModelDesc", example = "")
    private String normGdsSpcfModelDesc;

    @ExcelProperty
    @TableField("GDS_SPCF_MODEL_DESC")
    @ApiModelProperty(value = "规格型号", name = "gdsSpcfModelDesc", example = "")
    private String gdsSpcfModelDesc;

    @ExcelProperty
    @TableField("BRAND_NAME")
    @ApiModelProperty(value = "品牌1", name = "brandName", example = "")
    private String brandName;

    @ExcelProperty
    @TableField("BRAND_NAME_TWO")
    @ApiModelProperty(value = "品牌2", name = "brandNameTwo", example = "")
    private String brandNameTwo;

    @TableField("ORIGIN_COUNTRY")
    @ApiModelProperty(value = "原产地", name = "originCountry", example = "")
    private String originCountry;

    @ExcelProperty
    @Max(value = 32, message = "参数值最大值为32")
    @TableField("DICT_PER")
    @ApiModelProperty(value = "归类操作人", name = "dictPer", example = "")
    private String dictPer;

    @ExcelProperty
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("DICT_DATE")
    @ApiModelProperty(value = "归类日期", name = "dictDate", example = "")
    private Date dictDate;

    @TableField(exist = false)
    @ApiModelProperty(value = "归类日期开始", name = "dictDateStart", example = "")
    private String dictDateStart;

    @TableField(exist = false)
    @ApiModelProperty(value = "归类日期结束", name = "dictDateEnd", example = "")
    private String dictDateEnd;

    @ExcelProperty
    @TableField("IS_USEFULE")
    @ApiModelProperty(value = "是否有效", name = "isUsefule", example = "")
    private String isUsefule;

    @TableField("INPUT_COP_NO")
    @ApiModelProperty(value = "申报（录入）企业代码", name = "inputCopNo", example = "")
    private String inputCopNo;

    @TableField("INPUT_COP_NAME")
    @ApiModelProperty(value = "申报（录入）企业名称", name = "inputCopName", example = "")
    private String inputCopName;

    @TableField("CREATE_BY")
    @ApiModelProperty(value = "创建人代码", name = "createBy", example = "")
    private String createBy;

    @TableField("CREATE_NAME")
    @ApiModelProperty(value = "创建人名称", name = "createName", example = "")
    private String createName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("CREATE_TIME")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "")
    private Date createTime;

    @TableField("UPDATE_BY")
    @ApiModelProperty(value = "修改人代码", name = "updateBy", example = "")
    private String updateBy;

    @TableField("UPDATE_NAME")
    @ApiModelProperty(value = "修改人名称", name = "updateName", example = "")
    private String updateName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField("UPDATE_TIME")
    @ApiModelProperty(value = "修改时间", name = "updateTime", example = "")
    private Date updateTime;

    @TableField("G_MODEL")
    @ApiModelProperty(value = "规范要素", name = "gmodel", example = "")
    private String gmodel;

    @ExcelProperty
    @TableField("ENT_UNITCD")
    @ApiModelProperty(value = "企业计量单位", name = "entUnitcd", example = "")
    private String entUnitcd;

    @ExcelProperty
    @TableField("DCL_UNITCD")
    @ApiModelProperty(value = "申报计量单位", name = "dclUnitcd", example = "")
    private String dclUnitcd;

    @ExcelProperty
    @TableField("ENT_SF_VAL")
    @ApiModelProperty(value = "企业单位比例因子", name = "entSfVal", example = "")
    private BigDecimal entSfVal;

    @ExcelProperty
    @TableField("WT_SF_VAL")
    @ApiModelProperty(value = "重量比例因子", name = "wtSfVal", example = "")
    private BigDecimal wtSfVal;

    @TableField(exist = false)
    @ApiModelProperty(value = "申报要素项", name = "element", example = "")
    private String element;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否必填", name = "elementNull", example = "")
    private String elementNull;

    @TableField(exist = false)
    @ApiModelProperty(value = "OID集合", name = "idList", example = "")
    private List<String> idList;

    @TableField(exist = false)
    @ApiModelProperty(value = "关联企业海关编码集合", name = "relateTradeCodeList", example = "")
    private List<String> relateTradeCodeList;

}