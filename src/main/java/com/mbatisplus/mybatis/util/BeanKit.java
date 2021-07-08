package com.mbatisplus.mybatis.util;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class BeanKit {
    public BeanKit() {
    }

    public static boolean propertyExists(Object object, String propertyName) {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            if (field.getName().equals(propertyName)) {
                return true;
            }
        }

        return false;
    }

    public static List selectRedisDropDown(Object tableService) {
        Class clazz = tableService.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        try {
            Method[] var3 = methods;
            int var4 = methods.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Method method = var3[var5];
                if (method.getName().equals("selectRedisDropDown")) {
                    return (List) method.invoke(tableService);
                }
            }
        } catch (IllegalAccessException var7) {
            var7.printStackTrace();
        } catch (InvocationTargetException var8) {
            var8.printStackTrace();
        }

        return null;
    }

    public static void setProperty(Object object, String propertyName, Object propertyValue) {
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        try {
            Method[] var5 = methods;
            int var6 = methods.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Method method = var5[var7];
                if (method.getName().equalsIgnoreCase("set" + propertyName)) {
                    method.invoke(object, propertyValue);
                }
            }
        } catch (IllegalAccessException var9) {
            var9.printStackTrace();
        } catch (InvocationTargetException var10) {
            var10.printStackTrace();
        }

    }

    public static Object getProperty(Object object, String propertyName) {
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        try {
            Method[] var4 = methods;
            int var5 = methods.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Method method = var4[var6];
                if (method.getName().equalsIgnoreCase("get" + propertyName)) {
                    return method.invoke(object);
                }
            }
        } catch (IllegalAccessException var8) {
            var8.printStackTrace();
        } catch (InvocationTargetException var9) {
            var9.printStackTrace();
        }

        return null;
    }

    public static String getMethodReturnType(Object classInstance, String propertyName) {
        Class clzz = classInstance.getClass();
        String methodName = "get" + propertyName;
        Method[] methods = clzz.getMethods();
        Method[] var5 = methods;
        int var6 = methods.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            Method m = var5[var7];
            if (m.getName().equalsIgnoreCase(methodName)) {
                Type type = m.getGenericReturnType();
                return type.getTypeName();
            }
        }

        return "";
    }

    public static Method[] getMethod(Object classInstance) {
        Class clzz = classInstance.getClass();
        return clzz.getMethods();
    }

    public static String getDateTimePattern(Object beanObject, String properties) {
        Class clazz = beanObject.getClass();
        Field[] fs = clazz.getDeclaredFields();
        Field[] var4 = fs;
        int var5 = fs.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Field f = var4[var6];
            String fieldName = f.getName();
            if (fieldName.equalsIgnoreCase(properties)) {
                Annotation annotation = f.getAnnotation(JsonFormat.class);
                if (annotation != null) {
                    JsonFormat jsonFormat = (JsonFormat) annotation;
                    return jsonFormat.pattern();
                }
            }
        }

        return null;
    }

    public static Object convertMap(Class type, Map map) {
        BeanInfo beanInfo = null;
        Object obj = null;

        try {
            beanInfo = Introspector.getBeanInfo(type);
            obj = type.newInstance();
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (int i = 0; i < propertyDescriptors.length; ++i) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                Class propertytype = descriptor.getPropertyType();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    if ("java.math.BigDecimal".equals(propertytype.getName()) && value != null) {
                        value = new BigDecimal(String.valueOf(value));
                    } else if ("java.util.Date".equals(propertytype.getName()) && value != null) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        value = simpleDateFormat.parse(String.valueOf(value));
                    }

                    Object[] args = new Object[]{value};
                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return obj;
    }
}
