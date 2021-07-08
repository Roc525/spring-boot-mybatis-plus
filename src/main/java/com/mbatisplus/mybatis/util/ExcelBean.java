package com.mbatisplus.mybatis.util;


import java.util.Date;

/**
 * 项目名称：bssp Maven webapp
 * 类名称：ExcelBean
 * 类描述：Excel导出实体
 * 创建人：danagao
 * 创建时间：2017/12/13 11:45
 */

public class ExcelBean {

    //标题名称；
    private String titleName;

    //属性名称；
    private String fieldName;

    //属性格式
    private String fieldFormat;

    //属性样式:1 标题，2 文字，3 数字
    private int fieldStyle;

    //表格宽度,单号20，序号5
    private int fieldWidth;
    //横
    private int row;
    //列
    private int cell;
    //打印日期
    private Date date;

    public ExcelBean(String titleName, String fieldName, int fieldStyle, int fieldWidth) {
        this.titleName = titleName;
        this.fieldName = fieldName;
        this.fieldWidth = fieldWidth;
        this.fieldStyle = fieldStyle;
    }

    public ExcelBean(String titleName, String fieldName, int fieldStyle, int fieldWidth, String fieldFormat) {
        this.titleName = titleName;
        this.fieldName = fieldName;
        this.fieldWidth = fieldWidth;
        this.fieldStyle = fieldStyle;
        this.fieldFormat = fieldFormat;
    }

    public ExcelBean(String fieldName, int row, int cell) {
        this.fieldName = fieldName;
        this.row = row;
        this.cell = cell;
    }

    public ExcelBean(Date date, int row, int cell) {
        this.date = date;
        this.row = row;
        this.cell = cell;
    }

    public ExcelBean(String titleName, String fieldName) {
        this.titleName = titleName;
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldFormat() {
        return fieldFormat;
    }

    public void setFieldFormat(String fieldFormat) {
        this.fieldFormat = fieldFormat;
    }

    public int getFieldStyle() {
        return fieldStyle;
    }

    public void setFieldStyle(int fieldStyle) {
        this.fieldStyle = fieldStyle;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
