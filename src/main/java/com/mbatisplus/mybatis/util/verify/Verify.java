package com.mbatisplus.mybatis.util.verify;

import java.util.List;
import java.util.Map;

/**
 * 数据校验接口
 * Created by xinrongli on 2019/6/14.
 */
public interface Verify {
    /**
     * 获取对应的注解类
     * @return 对应的注解类
     */
    public Class annotation();

    /**
     * 执行具体的校验逻辑
     * @param errorList 用来保存校验结果
     * @param fieldValueMap 字段名：字段值
     * @param rowAt 行号
     * @param fieldAnnotationMap 字段名：注解
     * @param enCnMap 字段名：字段中文名
     */
    public void verify(List<String> errorList, Map<String, String> fieldValueMap, int rowAt, Map<String, Object> fieldAnnotationMap, Map enCnMap);
}
