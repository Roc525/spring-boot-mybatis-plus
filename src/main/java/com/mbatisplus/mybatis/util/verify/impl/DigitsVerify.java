package com.mbatisplus.mybatis.util.verify.impl;

import com.mbatisplus.mybatis.util.verify.Verify;

import javax.validation.constraints.Digits;
import java.util.List;
import java.util.Map;

/**
 * Created by xinrongli on 2019/6/14.
 */
public class DigitsVerify implements Verify {
    @Override
    public Class annotation() {
        return Digits.class;
    }

    @Override
    public void verify(List<String> errorList, Map<String, String> fieldValueMap, int rowAt, Map<String, Object> fieldAnnotationMap, Map enCnMap) {
        for (String fieldName :
                fieldAnnotationMap.keySet()) {
            Digits digits = (Digits) fieldAnnotationMap.get(fieldName);
            String valueString = fieldValueMap.get(fieldName);
            if (digits != null && !"".equals(valueString)) {
                String[] array = valueString.split("\\.");
                int interLength = digits.integer();
                int fractionLength = digits.fraction();
                boolean correct = true;
                switch (array.length) {
                    case 1:
                        if (array[0].length() > interLength) {
                            correct = false;
                        }
                        break;
                    case 2:
                        if (array[0].length() > interLength || array[1].length() > fractionLength) {
                            correct = false;
                        }
                        break;
                    default:
                        break;
                }
                if (!correct && errorList != null) {
                    errorList.add(String.format("第%s行 %s 有问题！其整数位上限为%s,小数位上限为%s", rowAt, enCnMap.get(fieldName), interLength, fractionLength));
                }
            }
        }
    }
}
