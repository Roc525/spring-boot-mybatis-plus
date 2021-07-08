package com.mbatisplus.mybatis.util.verify.impl;

import com.mbatisplus.mybatis.util.verify.Verify;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * Created by xinrongli on 2019/6/14.
 */
public class NotEmptyVerify implements Verify {
    @Override
    public Class annotation() {
        return NotEmpty.class;
    }

    @Override
    public void verify(List<String> errorList, Map<String, String> fieldValueMap, int rowAt, Map<String, Object> fieldAnnotationMap, Map enCnMap) {
        for (String fieldName :
                fieldAnnotationMap.keySet()) {
            NotEmpty notEmpty = (NotEmpty) fieldAnnotationMap.get(fieldName);
            String valueString = fieldValueMap.get(fieldName);
            if (notEmpty != null) {
                if (errorList != null && "".equals(valueString)) {
                    errorList.add(String.format("第%s行 %s 不能为空！", rowAt, enCnMap.get(fieldName)));
                }
            }
        }
    }
}
