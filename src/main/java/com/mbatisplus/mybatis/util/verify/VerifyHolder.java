package com.mbatisplus.mybatis.util.verify;

import com.mbatisplus.mybatis.util.BeanKit;
import io.swagger.annotations.ApiModelProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 持有校验接口和校验数据的类
 * Created by xinrongli on 2019/6/14.
 */
public class VerifyHolder<T> {
    private Class entityClazz;
    private Map<String, String> cnEnMap;
    private Map<String, String> enCnMap;
    private Map<String, String> fieldValueMap;
    private List<String> errorList;
    private Map<Class, Map<String, Object>> annotationMap = new HashMap<>();
    private List<Verify> verifyList = new ArrayList<>();

    /**
     * @param entityClazz 需要校验的实体类
     * @param errorList   用来保存校验结果
     */
    public VerifyHolder(Class<T> entityClazz, List errorList) {
        this.entityClazz = entityClazz;
        this.errorList = errorList;
        cnEnMap = getCnEnTitleMap();

        enCnMap = new HashMap<>();
        cnEnMap.forEach((cn, en) -> {
            enCnMap.put(en, cn);
        });
    }

    private Map getCnEnTitleMap() {
        Map<String, String> cnEnMap = new HashMap<>();
        Field[] fields = entityClazz.getDeclaredFields();
        for (Field field :
                fields) {
            ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
            if (apiModelProperty != null) {
                String keys = apiModelProperty.value();
                String value = field.getName();
                if (keys.contains(",")) {
                    for (String key : keys.split(",")) {
                        cnEnMap.put(key, value);
                    }
                } else {
                    cnEnMap.put(keys, value);
                }
            }
        }
        return cnEnMap;
    }

    /**
     * 增加校验
     *
     * @param verify
     * @return
     */
    public VerifyHolder<T> addVerify(Verify verify) {
        Class annotationClass = verify.annotation();
        verifyList.add(verify);
        annotationMap.put(annotationClass, getAnnotationMap(annotationClass));
        return this;
    }

    private <E extends Annotation> Map getAnnotationMap(Class<E> annotationClass) {
        Map<String, E> map = new HashMap<>();
        Field[] fields = entityClazz.getDeclaredFields();
        for (Field field :
                fields) {
            if (enCnMap.get(field.getName()) == null) {
                continue;
            }

            E annotation = field.getAnnotation(annotationClass);
            if (annotation != null) {
                map.put(field.getName(), annotation);
            }
        }
        return map;
    }

    /**
     * 执行校验
     *
     * @param entity
     * @param rowAt
     * @throws Exception
     */
    public void check(T entity, int rowAt) throws Exception {
        if (!entityClazz.isInstance(entity)) {
            throw new Exception("错误的对象类型");
        }

        fieldValueMap = new HashMap<>();
        enCnMap.keySet().forEach(fieldName -> {
            Object value = BeanKit.getProperty(entity, fieldName);
            String valueString = value == null ? "" : value.toString().trim();
            fieldValueMap.put(fieldName, valueString);
        });

        verifyList.forEach(e -> {
            Map<String, Object> map = annotationMap.get(e.annotation());
            e.verify(errorList, fieldValueMap, rowAt, map, enCnMap);
        });
    }

//    public static void main(String[] args) throws Exception {
//        List errorList = new ArrayList();
//        VerifyHolder verifyHolder = new VerifyHolder<EmsPutrecImg1>(EmsPutrecImg1.class, errorList);
//        verifyHolder.addVerify(new NotEmptyVerify());
//        verifyHolder.addVerify(new DigitsVerify());
//        verifyHolder.addVerify(new SizeVerify());
//        verifyHolder.addVerify(new MinVerify());
//        verifyHolder.addVerify(new MaxVerify());
//
//        EmsPutrecImg1 emsPutrecImg = new EmsPutrecImg1();
//        emsPutrecImg.setChgTmsCnt(BigDecimal.valueOf(123456));
//        //        emsPutrecImg.setChgTmsCnt(BigDecimal.valueOf(12345));
//        emsPutrecImg.setGdsSeqno(BigDecimal.valueOf(999999999999999999l));
//        emsPutrecImg.setRmk("123456");
//        emsPutrecImg.setGdecd(" ");
//        emsPutrecImg.setGdsMtno("abc");
//        emsPutrecImg.setGdsMtno("");
//
//        verifyHolder.check(emsPutrecImg, 1);
//
//        errorList.forEach(e -> System.out.println(e));
//    }
}
