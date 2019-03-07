/**
 * ParserUtil.java
 * Created at 2017-9-22
 * Created by chenhao
 * Copyright (C) 2017 BROADTEXT SOFTWARE, All rights reserved.
 */
package com.swsm.platform.util.converter.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>ClassName: ParserUtil</p>
 * <p>Description: 格式转换工具类</p>
 * <p>Author: chenhao</p>
 * <p>Date: 2017-9-22</p>
 */
public class ParserUtil {
    /**
     * 日志信息
     */
    private static Logger logger = LoggerFactory.getLogger(ParserUtil.class);

    /**
     * <p>Description: bean装换成map</p>
     *
     * @param obj bean对象
     * @return map对象
     */
    public static Map<String, Object> parserBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map;
        map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo;
            beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors;
            propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性  
                if (!key.equals("class")) {
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            logger.error("parserBean2Map error!!!");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * <p>Description: map装换成bean</p>
     *
     * @param obj bean对象
     * @param map map对象
     */
    public static void parserMap2Bean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo;
            beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors;
            propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }
            }
        } catch (Exception e) {
            logger.error("parserMap2Bean error!!!");
            e.printStackTrace();
        }
        return;
    }

}
