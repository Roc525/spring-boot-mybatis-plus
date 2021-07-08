package com.mbatisplus.mybatis.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目名称：bssp Maven webapp
 * 类名称：ExcelExportUtils
 * 类描述：Excel导出
 *
 * @author ：danagao
 * 创建时间：2017/12/13 11:45
 */

public class ExcelExportUtils {
    /**
     * 每一次写入的行数
     */
    public static int PER_WRITE_ROW_COUNT = 10000;

    private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtils.class);

    /**
     * 标题列表
     */
    private List titleList;
    /**
     * 数据列表
     */
    private List dataList;
    private HSSFWorkbook wb = new HSSFWorkbook();
    /**
     * 2007
     */
    private Workbook wb2007 = new XSSFWorkbook();
    /**
     * 表头对象
     */
    private Object headObject;
    /**
     * 表头列表
     */
    private List headTitle;
    /**
     * 表体
     */
    private List dataAndList;
    /**
     * 表体开始行
     */
    private int listStartRow;
    /**
     * 表体标题集合
     */
    private List bodyTitleList;
    /**
     * 打印日期
     */
    private Object time;
    /**
     * 大数据 默认200行保存内存中
     */
    private SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(200);

    /**
     * 备用
     */
    private String col1;

    /**
     * cellStyle1 标题
     * cellStyle2 文字
     * cellStyle3 数字
     */
    private CellStyle cellStyle1 = getExcelCellStyleHead(sxssfWorkbook, 3, true);
    private CellStyle cellStyle2 = getExcelCellStyleHead(sxssfWorkbook, 2, true);
    private CellStyle cellStyle3 = getExcelCellStyleHead(sxssfWorkbook, 1, true);

    /**
     * excel表文件名字
     */
    private String excelName = "excel" + MD5Utils.getMD5(String.valueOf(Math.random()));


    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }


    public static <T> void write0(Class clazz, List list, ExcelWriter writer, com.alibaba.excel.metadata.Sheet sheet) throws Exception {
        List<List<String>> titles = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldNameList = new ArrayList<>();
        List<String> fieldTypeList = new ArrayList<>();
        for (Field field : fields) {
            ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (apiModelProperty != null && excelProperty != null) {
                String nameCn = apiModelProperty.value();
                titles.add(Arrays.asList(nameCn));
                fieldNameList.add(field.getName());
                fieldTypeList.add(field.getType().getTypeName());
            }
        }

        List dataList = new ArrayList();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        SimpleDateFormat df2 = new SimpleDateFormat(pattern, Locale.US);

        for (Object data : list) {
            List<String> list1 = new ArrayList<>();
            for (int i = 0; i < fieldNameList.size(); i++) {
                String fieldName = fieldNameList.get(i);
                String type = fieldTypeList.get(i);
                Object value = BeanKit.getProperty(data, fieldName);
                String txt = value == null ? null : value.toString();
                switch (type) {
                    case "java.math.BigDecimal":
                    case "java.lang.Integer":
                    case "java.lang.Double":
                    case "java.lang.Float":
                        if (StringUtils.isNotEmpty(txt)) {
                            NumberFormat nf = NumberFormat.getInstance();
                            // 设置此格式中不使用分组
                            nf.setGroupingUsed(false);
                            // 设置数的小数部分所允许的最大位数。
                            nf.setMaximumFractionDigits(9);
                            txt = nf.format(Double.parseDouble(txt));
                        }
                        break;
                    case "java.util.Date":
                        //获取时间的String 值 转成Date。Date格式化为指定格式
                        if (StringUtils.isNotEmpty(txt)) {
                            Date date = df2.parse(txt);
                            txt = df.format(date);
                        }
                        break;
                    default:
                        break;
                }
                list1.add(txt);
            }
            dataList.add(list1);
        }
        sheet.setHead(titles);
        writer.write0(dataList, sheet);
    }

    //单个sheet
    public ExcelExportUtils(List titleList, List dataList) {
        this.titleList = titleList;
        this.dataList = dataList;

        cellStyle1.setFont(getExcelFontHead(sxssfWorkbook));
        cellStyle2.setFont(getExcelFontDetail(sxssfWorkbook));
        cellStyle3.setFont(getExcelFontDetail(sxssfWorkbook));
    }

    /**
     * 取得报表样式
     *
     * @param wb    工作表
     * @param align 对齐方式　 1 右对齐  2 左对齐 3 居中
     * @param isBor 是否有边框
     * @return CellStyle
     */
    private CellStyle getExcelCellStyleHead(Workbook wb, int align, boolean isBor) {
        CellStyle cellStyle = wb.createCellStyle();
        if (align == 1) {
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        } else if (align == 2) {
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
        } else if (align == 3) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        }
        if (isBor) {
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
            cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
            cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
            cellStyle.setTopBorderColor(IndexedColors.BLACK.index);
            cellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        return cellStyle;
    }

    /**
     * 报头的字体
     *
     * @param wb 工作表
     * @return HSSFFont;
     */
    private Font getExcelFontHead(Workbook wb) {
        Font hsFont = wb.createFont();
        hsFont.setFontHeightInPoints((short) 10);
        hsFont.setFontName("宋体");
        hsFont.setColor(IndexedColors.BLACK.index);
//        hsFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        hsFont.setBold(true);
        return hsFont;
    }

    /**
     * 表身字体
     *
     * @param wb 工作表
     * @return Font
     */
    private Font getExcelFontDetail(Workbook wb) {
        Font hsFont = wb.createFont();
        hsFont.setFontHeightInPoints((short) 10);
        hsFont.setFontName("宋体");
        hsFont.setColor(IndexedColors.BLACK.index);
//        hsFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        hsFont.setBold(true);
        return hsFont;
    }

    /**
     * 导出excel文件
     *
     * @param response 响应
     * @throws Exception 异常
     */
    public void runExportTask(HttpServletResponse response) throws Exception {


        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ServletOutputStream out = null;
        try {
            response.reset();
            response.setContentType("application/x-excel;charset=UTF-8");
            //导出Excel文件
            ba = export2Excel(ba);
            out = response.getOutputStream();
            response.setHeader("Content-Length", "" + ba.size());
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(excelName.getBytes(), "iso-8859-1") + ".xlsx");
        } catch (Exception ex) {
            logger.error("导出Excel异常", ex);
            throw ex;
        } finally {
            try {
                if (out != null) {
                    ba.writeTo(out);
                    out.close();
                }
            } catch (Exception ex) {
                logger.error("输出流异常", ex);
                ex.printStackTrace();
            }
        }
    }

    /**
     * 生成excel文件
     *
     * @param ba 输出流
     * @return ByteArrayOutputStream
     */
    private ByteArrayOutputStream export2Excel(ByteArrayOutputStream ba) throws Exception {

        Sheet s = sxssfWorkbook.createSheet();


        sxssfWorkbook.setSheetName(0, "Sheet1");
        int rowNum = 0;
        Cell cell;
        ExcelBean excelBean;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        SimpleDateFormat df2 = new SimpleDateFormat(pattern, Locale.US);

        //打印标题
        Row row = s.createRow(rowNum);
        for (int i = 0; i < titleList.size(); i++) {
            excelBean = (ExcelBean) titleList.get(i);
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle1);
            s.setColumnWidth(i, getWidth(excelBean.getFieldWidth()));
            cell.setCellValue(excelBean.getTitleName());
        }

        //打印内容
        for (Object data : dataList) {
            row = s.createRow(++rowNum);

            for (int j = 0; j < titleList.size(); j++) {
                excelBean = (ExcelBean) titleList.get(j);
                cell = row.createCell(j);
                cell.setCellStyle(getStyle(excelBean.getFieldStyle()));
                String type = BeanKit.getMethodReturnType(data, excelBean.getFieldName());
                String txt = "";
                switch (type) {
                    case "java.math.BigDecimal":
                    case "java.lang.Integer":
                    case "java.lang.Double":
                    case "java.lang.Float":
                        String bigDecimal1 = getStringValue(BeanKit.getProperty(data, excelBean.getFieldName()), excelBean.getFieldFormat());
                        if (StringUtils.isNotEmpty(bigDecimal1)) {
                            NumberFormat nf = NumberFormat.getInstance();
                            // 设置此格式中不使用分组
                            nf.setGroupingUsed(false);
                            // 设置数的小数部分所允许的最大位数。
                            nf.setMaximumFractionDigits(9);
                            txt = nf.format(Double.parseDouble(bigDecimal1));
                            cell.setCellValue(new Double(txt));
//                            cell.setCellType(CellType.NUMERIC);
                        }
                        break;
                    case "java.util.Date":
                        //获取时间的String 值 转成Date。Date格式化为指定格式
                        txt = BeanKit.getProperty(data, excelBean.getFieldName()) == null ? "" : BeanKit.getProperty(data, excelBean.getFieldName()).toString();
                        if (StringUtils.isNotEmpty(txt)) {
                            Date date = df2.parse(txt);
                            txt = df.format(date);
                        }
                        cell.setCellValue(txt);
                        break;
                    default:
                        txt = getStringValue(BeanKit.getProperty(data, excelBean.getFieldName()), excelBean.getFieldFormat());
                        cell.setCellValue(txt);
                        break;
                }
                //txt = getStringValue(BeanKit.getProperty(data, excelBean.getFieldName()),excelBean.getFieldFormat());
//                cell.setCellValue(txt);
            }
        }
        //输出流
        sxssfWorkbook.write(ba);

        return ba;
    }

    /**
     * 将对象转换为字符串
     *
     * @param value 值
     * @return String
     */
    private String getStringValue(Object value) {
        return getStringValue(value, null);
    }

    /**
     * 将对象转换为字符串
     *
     * @param value 值
     * @return String
     */
    private String getStringValue(Object value, String fieldFormat) {
        if (value == null) {
            return null;
        }
        if (StringUtils.isNotEmpty(fieldFormat)) {
            DecimalFormat df = new DecimalFormat(fieldFormat);
            value = df.format(value);
        }
        return value.toString();
    }

    /**
     * 取得表格样式
     *
     * @param type 字体样式 1 标题  2 文字 3 数字
     * @return HSSFCellStyle
     */
    private CellStyle getStyle(int type) {
        switch (type) {
            case 1:
                return cellStyle1;
            case 2:
                return cellStyle2;
            case 3:
                return cellStyle3;
            default:
                return cellStyle2;
        }

    }

    /**
     * 获取表格宽度
     *
     * @param wid 宽度
     * @return int
     */
    private int getWidth(int wid) {
        if (wid == 0) {
            wid = 8;
        }
        return wid * 280;
    }

}
