package com.mbatisplus.mybatis.util.verify.impl;

import com.mbatisplus.mybatis.util.verify.Verify;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by xinrongli on 2019/6/14.
 */
@Slf4j
public class SizeVerify implements Verify {
    @Override
    public Class annotation() {
        return Size.class;
    }

    @Override
    public void verify(List<String> errorList, Map<String, String> fieldValueMap, int rowAt, Map<String, Object> fieldAnnotationMap, Map enCnMap){
        for (String fieldName :
                fieldAnnotationMap.keySet()) {
            Size size = (Size) fieldAnnotationMap.get(fieldName);
            String valueString = fieldValueMap.get(fieldName);
            if (size != null && !"".equals(valueString)) {
                boolean correct = true;
                int length = 0;
                try {
                    length = valueString.getBytes("gb2312").length;
                }catch (UnsupportedEncodingException e){
                    log.error("verify()-err",e);
                }
                if (length > size.max() || length < size.min()) {
                    correct = false;
                }
                if (!correct && errorList != null) {
                    errorList.add(String.format("第%s行 %s 长度有问题！下限为%s,上限为%s", rowAt, enCnMap.get(fieldName), size.min(), size.max()));
                }
            }
        }
    }
}
