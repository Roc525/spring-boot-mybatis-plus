package com.mbatisplus.mybatis.util;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.monitorjbl.xlsx.StreamingReader;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelReadUtils {

    private static final Logger log = LoggerFactory.getLogger(ExcelReadUtils.class);

    /**
     * 将数据从excel读入list
     *
     * @param file            要导入的文件
     * @param entityClazz     实体类型，entityClazz中的字段通过@ApiModelProperty与excel中的列对应
     * @param sheetAt         excel sheet index,>=0
     * @param rowBegin        数据开始行，>=0
     * @param formatErrorList 用来记录格式有误的数据
     * @return 第一个List为列名列表，后面各个List为各行的数据
     * @throws Exception
     */
    public static <T> List<T> getDataFromExcel(File file, Class<T> entityClazz, int sheetAt, int rowBegin, List formatErrorList) throws Exception {
        List<List<String>> rowList = new ArrayList<>();
        try {
            //Workbook workBook = StreamingReader.builder().open(file);
            Workbook workBook = null;
            String type = file.getName().split("\\.")[1];
            if ("xlsx".equals(type)) {
                //Excel2007的版本，扩展名是.xlsx
                workBook = new XSSFWorkbook(new FileInputStream(file));
            } else if ("xls".equals(type)) {
                //Excel2003的版本，扩展名是.xlsx
                workBook = new HSSFWorkbook(new FileInputStream(file));
            }
            Sheet sheet = workBook.getSheetAt(sheetAt);
            Iterator rowIterator = sheet.rowIterator();

            //读第一行读出总列数
            int totalCells = 0;
            int rowIndex = 0;
            Row firstRow = null;
            while (rowIterator.hasNext()) {
                if (rowIndex++ == rowBegin) {
                    firstRow = ((Row) rowIterator.next());
                    totalCells = firstRow.getPhysicalNumberOfCells();
                    break;
                } else {
                    rowIterator.next();
                }
            }

            //取得列名的 中文->英文 键值对
            Map<String, String> cnEnTitleMap = getCnEnTitleMap(entityClazz);

            Map<Integer, String> indexNameMap = new HashMap<>();
            List columnNameList = new ArrayList();
            if (firstRow != null) {
                // 第一行为列名，通过其取得excel中能与entityObject中的各字段对应上的各列的 index->英文列名 的键值对
                for (int cellIndex = 0; cellIndex < totalCells; cellIndex++) {
                    Cell cell = firstRow.getCell(cellIndex);
                    if (cell == null) {
                        throw new NullPointerException("rowBegin有误或有空的列名！");
                    }
                    String cellValue = cell.getStringCellValue().trim();
                    cellValue = cellValue.replace("（", "(").replace("）", ")");
                    if (cnEnTitleMap.containsKey(cellValue)) {
                        Object name = cnEnTitleMap.get(cellValue);
                        if (name != null && StringUtils.isNotEmpty((String) name)) {
                            indexNameMap.put(cellIndex, (String) name);
                            columnNameList.add(name + ":" + cellValue);
                        }
                    }
                }
            }

            //把列名(英文名:中文名)列表作为rowList的第一项存入
            rowList.add(columnNameList);

            Cell cell;
            List notEmptyCellValueList;
            List cellValueList;
            while (rowIterator.hasNext()) {
                Row row = (Row) rowIterator.next();
                if (row == null) {
                    break;
                }

                notEmptyCellValueList = new ArrayList();
                cellValueList = new ArrayList(columnNameList.size());//给个初始大小，使其不用在运行时动态扩容
                for (int cellIndex = 0; cellIndex < totalCells; cellIndex++) {
                    String cellValue = "";
                    if (indexNameMap.containsKey(cellIndex) && !indexNameMap.get(cellIndex).equals("")) {
                        cell = row.getCell(cellIndex);
                        cellValue = getCellValue(cell);
                        if (StringUtils.isNotEmpty(cellValue)) {
                            notEmptyCellValueList.add(cellValue);
                        }
                        cellValueList.add(cellValue);
                    }
                }
                if (notEmptyCellValueList.size() == 0) {
                    break;
                }
                rowList.add(cellValueList);
            }
        } catch (OLE2NotOfficeXmlFileException e) {
            e.printStackTrace();
            throw new OLE2NotOfficeXmlFileException("只支持XLSX文件的导入！");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new NumberFormatException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        log.info("获取Excel数据完成,共:" + rowList.size() + "条");

        List entityList = convertToEntity(rowList, entityClazz, formatErrorList, rowBegin);

        return entityList;
    }

    private static Map getCnEnTitleMap(Class entityClazz) {
        Map<String, String> columnMap = new HashMap<>();
        Class clazz = entityClazz;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field :
                fields) {
            ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            if (apiModelProperty != null) {
                String keys = apiModelProperty.value();
                keys = keys.replace("（", "(").replace("）", ")");
                String value = field.getName();
                if (keys.contains(",")) {
                    for (String key : keys.split(",")) {
                        columnMap.put(key, value);
                    }
                } else {
                    columnMap.put(keys, value);
                }
            }
        }
        return columnMap;
    }


    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (null != cell) {
            // 以下是判断数据的类型
            switch (cell.getCellType()) {
                case 0: // 数字 NUMERIC
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                        cellValue = sdf.format(date);
                    } else {
                        DecimalFormat df = new DecimalFormat("#.#########");
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    return cellValue;
                case 1: // 字符串 STRING
                    cellValue = cell.getStringCellValue().trim();
                    return cellValue;
                case 4: // Boolean BOOLEAN
                    cellValue = cell.getBooleanCellValue() + "";
                    return cellValue;
                case 2: // 公式 FORMULA
                    cellValue = cell.getCellFormula() + "";
                    return cellValue;
                case 3: // 空值 BLANK
                    cellValue = "";
                    return cellValue;
                case 5: // 故障 ERROR
                    cellValue = "非法字符";
                    return cellValue;
                default:
                    cellValue = "未知类型";
            }
        }
        return cellValue;
    }


    /**
     * 把原始数据List转换成实体List
     *
     * @param dataList        用来转成实体List的原始List,方法执行完成后，List中的元素全被置为null
     * @param clazz           要转成的实体
     * @param formatErrorList 用来记录格式有误的数据
     * @param rowBegin        数据开始行，>=0
     * @return 实体List
     * @throws Exception
     */
    private static List<Object> convertToEntity(List<List<String>> dataList, Class clazz, List formatErrorList, int rowBegin) throws Exception {
        List<String> fieldNameList = dataList.get(0);
        dataList.set(0, null);
        List<String> fieldTypeList = new ArrayList<>();
        for (String fieldName :
                fieldNameList) {
            String enName = fieldName.split(":")[0];
            String fieldType = clazz.getDeclaredField(enName).getType().getSimpleName();
            fieldTypeList.add(fieldType);
        }

        Object newEntity;
        List entityList = new ArrayList();
        List<String> valueList;

        List enNameList = new ArrayList();
        List cnNameList = new ArrayList();
        String fieldNameTemp;
        String enNameTemp;
        String cnNameTemp;
        for (int i = 0; i < fieldNameList.size(); i++) {
            fieldNameTemp = fieldNameList.get(i);
            enNameTemp = fieldNameTemp.split(":")[0];
            cnNameTemp = fieldNameTemp.split(":")[1];
            enNameList.add(enNameTemp);
            cnNameList.add(cnNameTemp);
        }

        for (int i = 1; i < dataList.size(); i++) {
            valueList = dataList.get(i);
            //使valueList能被尽早gc，同时避免引起dataList大小的变化而造成性能的损失
            dataList.set(i, null);
            newEntity = clazz.newInstance();

            String fieldType;
            for (int j = 0; j < fieldNameList.size(); j++) {
                fieldType = fieldTypeList.get(j);
                try {
                    Object properValue = getProperValue(valueList.get(j), fieldType);
                    Reflections.setFieldValue(newEntity, enNameList.get(j).toString(), properValue);
                } catch (NumberFormatException e) {
                    if (formatErrorList != null) {
                        formatErrorList.add(String.format("第%s行数据中的 %s 数据类型有问题！其值为：%s", rowBegin + i + 1, cnNameList.get(j), valueList.get(j)));
                    }
                    e.printStackTrace();
                } catch (ParseException e) {
                    if (formatErrorList != null) {
                        formatErrorList.add(String.format("第%s行数据中的 %s 数据格式有问题！其值为：%s, 正确格式应为：%s", rowBegin + i + 1, cnNameList.get(j), valueList.get(j), e.getMessage()));
                    }
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            entityList.add(newEntity);
        }

        return entityList;
    }

    private static Object getProperValue(String value, String fieldType) throws Exception {
        Object properValue = StringUtils.isNotEmpty(value) ? value : null;
        if (null != value && !"".equals(value)) {
            try {
                switch (fieldType) {
                    case "Date":
                        properValue = parseDate(value);
                        break;
                    case "Integer":
                    case "int":
                        properValue = Integer.parseInt(value);
                        break;
                    case "Long":
                        properValue = Long.parseLong(value);
                        break;
                    case "Double":
                        properValue = Double.parseDouble(value);
                        break;
                    case "BigDecimal":
                        properValue = new BigDecimal(value);
                        break;
                    case "Boolean":
                        properValue = Boolean.parseBoolean(value);
                        break;
                    default:
                        properValue = value;
                        break;
                }
            } catch (NumberFormatException e) {
                throw e;
            } catch (Exception e) {
                throw e;
            }
        }
        return properValue;
    }

    /**
     * 格式化string为Date
     *
     * @param dateStr
     * @return date
     */
    private static Date parseDate(String dateStr) throws Exception {
        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }
        String fmtStr = null;
        try {
            if (dateStr.indexOf('-') > 0) {
                if (dateStr.indexOf(':') > 0) {
                    fmtStr = "yyyy-MM-dd HH:mm:ss";
                } else {
                    fmtStr = "yyyy-MM-dd";
                }
            } else {
                if (dateStr.indexOf('/') > 0) {
                    fmtStr = "yyyy/MM/dd";
                } else {
                    fmtStr = "yyyyMMdd";
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmtStr, Locale.UK);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new ParseException("YYYY-MM-DD,如2018-09-01", e.getErrorOffset());
        } catch (Exception e) {
            throw e;
        }
    }

}
