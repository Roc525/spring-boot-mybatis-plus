package com.mbatisplus.mybatis.util.verify.impl;

import com.mbatisplus.mybatis.util.verify.Verify;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by xinrongli on 2019/8/29.
 */
@Slf4j
public class DecimalMinVerify implements Verify {
    @Override
    public Class annotation() {
        return DecimalMin.class;
    }

    @Override
    public void verify(List<String> errorList, Map<String, String> fieldValueMap, int rowAt, Map<String, Object> fieldAnnotationMap, Map enCnMap) {
        for (String fieldName :
                fieldAnnotationMap.keySet()) {
            try {
                DecimalMin annotation = (javax.validation.constraints.DecimalMin) fieldAnnotationMap.get(fieldName);
                String valueString = fieldValueMap.get(fieldName);
                if (annotation != null && !"".equals(valueString)) {
                    BigDecimal min = new BigDecimal(annotation.value());
                    BigDecimal value = new BigDecimal(valueString);
                    if (errorList != null && value.compareTo(min) == -1) {
                        errorList.add(String.format("第%s行 %s 不能小于%s！", rowAt, enCnMap.get(fieldName), min.toPlainString()));
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
