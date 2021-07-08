package com.mbatisplus.mybatis.controller;


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import com.mbatisplus.mybatis.domain.ErpDeclareElements;
import com.mbatisplus.mybatis.domain.IgrDev;
import com.mbatisplus.mybatis.domain.Provider;
import com.mbatisplus.mybatis.service.IIgrDevService;
import com.mbatisplus.mybatis.service.IProviderService;
import com.mbatisplus.mybatis.util.ExcelBean;
import com.mbatisplus.mybatis.util.ExcelExportUtils;
import com.mbatisplus.mybatis.util.ExcelImportUtils;
import com.mbatisplus.mybatis.util.ExcelReadUtils;
import com.mbatisplus.mybatis.util.verify.VerifyHolder;
import com.mbatisplus.mybatis.util.verify.impl.DecimalMinVerify;
import com.mbatisplus.mybatis.util.verify.impl.DigitsVerify;
import com.mbatisplus.mybatis.util.verify.impl.MaxVerify;
import com.mbatisplus.mybatis.util.verify.impl.NotEmptyVerify;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Echo42
 * @since 2020-12-12
 */
@RestController
@RequestMapping("/sys/provider")
@Api(description = "供应商 操作接口", tags = {"ProviderController"})
@Slf4j
public class ProviderController {

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IIgrDevService igrDevService;

    @ApiOperation(value = "根据条件查询供应商信息")
    @GetMapping("/getProviders")
    public void getProvider(Provider provider) {
        EntityWrapper<Provider> wrapper = new EntityWrapper<>();
        wrapper.eq("pid", 1);
        List<Provider> providers = providerService.selectList(wrapper);
        System.out.println(providers);

    }

    @ApiOperation(value = "根据条件查询供应商信息")
    @GetMapping("/getIgrdevs")
    public void getIgrDev(IgrDev igrDev) {
        EntityWrapper<IgrDev> wrapper = new EntityWrapper<>();
        wrapper.eq("N_A", 124);
        List<IgrDev> igrDevs = igrDevService.selectList(wrapper);
        for (IgrDev dev : igrDevs) {
            String s1 = dev.toString();
            Gson gson = new Gson();
            String s = gson.toJson(dev);
            System.out.println(s);
        }
        System.out.println(igrDevs);
    }


    @PostMapping("/exportOne")
    @ApiOperation("导出第一种方式")
    public void exportOneExcel(HttpServletResponse response, @RequestBody IgrDev igrDev) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            int sheetNo = 0;
            Sheet sheet = new Sheet(++sheetNo, 0);
            sheet.setSheetName("申请要素历史");
            //导出查询信息
            EntityWrapper<IgrDev> wrapper = new EntityWrapper<>();
            wrapper.eq("N_A", 124);
            List<IgrDev> igrDevs = igrDevService.selectList(wrapper);
            ExcelExportUtils.write0(IgrDev.class, igrDevs, writer, sheet);

            String fileName = "申报要素统计报表" + System.currentTimeMillis();
            response.reset();
            response.setContentType("application/x-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" +
                    URLEncoder.encode(fileName + ".xlsx", "utf-8"));
            writer.finish();
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    @PostMapping("/exportTwo")
    @ApiOperation("导出第二种方式")
    public void exportTwoExcel(HttpServletResponse response, ErpDeclareElements erpDeclareElements) {
        try {
            //数据
            //导出查询信息
            EntityWrapper<IgrDev> wrapper = new EntityWrapper<>();
            wrapper.eq("N_A", 124);
            List<IgrDev> igrDevs = igrDevService.selectList(wrapper);

            //表头标题
            List<ExcelBean> titleList = new ArrayList<>();
            titleList.add(new ExcelBean("物资编码", "N_A", 2, 20));
            titleList.add(new ExcelBean("商品编码", "N_B", 2, 20));
            titleList.add(new ExcelBean("规范申报要素", "N_C", 2, 60));
            titleList.add(new ExcelBean("是否有效", "N_D", 2, 10));

            ExcelExportUtils utils = new ExcelExportUtils(titleList, igrDevs);
            String fileName = "申报异常要素报表";
            //设置excel名字
            utils.setExcelName(URLEncoder.encode(fileName, "utf-8"));
            utils.runExportTask(response);
        } catch (Exception e) {
            log.error("exportTwoExcel()-error", e);
        }
    }

    @PostMapping("/import")
    @ApiOperation("导入")
    public static void importExcel(@RequestParam("file") MultipartFile file) {
        try {
            // File importFile = ExcelImportUtils.multipartToFile(file);
            int row = 0;
            File file1 = new File("C:\\Users\\Administrator\\Desktop\\file\\a1.xlsx");
            //导入Excel文件
            List errorList = new ArrayList();
            List<ErpDeclareElements> dataFromExcel = ExcelReadUtils.getDataFromExcel(file1, ErpDeclareElements.class, 0, 0, errorList);
            VerifyHolder verifyHolder = new VerifyHolder(ErpDeclareElements.class, errorList);
            verifyHolder.addVerify(new NotEmptyVerify());
            for (ErpDeclareElements erpDeclareElements : dataFromExcel) {
                ++row;
                verifyHolder.check(erpDeclareElements, row);
            }
            for (int i = 0; i < errorList.size(); i++) {
                Object o = errorList.get(i);
                System.out.println(o.toString());
            }
        } catch (Exception e) {
            log.error("importExcel()-error", e);
        }
    }

    public static void main(String[] args) {
        importExcel(null);
    }
}

