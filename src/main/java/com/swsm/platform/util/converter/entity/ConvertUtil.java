package com.swsm.platform.util.converter.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 对象转换工具
 *
 * @author dlb
 */
public class ConvertUtil {

    private static Logger logger = LoggerFactory.getLogger(ConvertUtil.class);


    /**
     * 对象转换
     * 针对相同的属性类型和属性名称将直接赋值
     * 要求
     * 1、类有无参构造方法
     * 2、参数名称一致
     *
     * @param object 转换对象
     * @param clazz  转换后对象
     * @return 转换后对象
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T convertClass(Class<T> clazz, Object object) throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {


        if (null == object) {
            return null;
        }

        Map<String, Object> objMaps = new HashMap<>();

        //反切获取传入对象的属性名称和属性值
        Class<?> objClazz = object.getClass();
        do {
            //方法
            Method[] methods = objClazz.getDeclaredMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (!methodName.startsWith("get")) {
                    continue;
                }
                methodName = methodName.substring(3, methodName.length());
                String fieldName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1, methodName.length());
                if (objMaps.containsKey(fieldName)) {
                    continue;
                }

                Object mObj = method.invoke(object);
                objMaps.put(fieldName, mObj);
            }


            objClazz = objClazz.getSuperclass();

        } while (objClazz != null && !objClazz.getName().toLowerCase().equals("java.lang.object"));


        //获取需要转换对象
        T dataBean = clazz.newInstance();
        Class<?> superClass = clazz;
        do {

            Method[] sMethods = superClass.getDeclaredMethods();
            for (Method method : sMethods) {
                String methodName = method.getName();
                if (!methodName.startsWith("set")) {
                    continue;
                }
                methodName = methodName.substring(3, methodName.length());
                String fieldName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1, methodName.length());
                if (objMaps.containsKey(fieldName)) {
                    method.invoke(dataBean, objMaps.get(fieldName));
                }
            }


            superClass = superClass.getSuperclass();

        } while (superClass != null && !superClass.getName().toLowerCase().equals("java.lang.object"));

        return dataBean;
    }

    /**
     * 普通Map对象转换成范型Map对象
     *
     * @param map        转换的Map
     * @param keyClass   key对应的类名
     * @param valueClass value对应的类名
     * @return 范型Map
     */
    public static <K, V> Map<K, V> toKVMap(Map map, Class<K> keyClass, Class<V> valueClass) {
        Map<K, V> kvMap = new HashMap<>();
        map.forEach((key, value) -> {
            K k = keyClass.cast(key);
            V v = valueClass.cast(value);
            kvMap.put(k, v);
        });
        return kvMap;
    }

    /**
     * 把普通的Map类型的List转化成范型Map List
     *
     * @param list
     * @param keyClass
     * @param valueClass
     * @return
     */
    public static <K, V> List<Map<K, V>> toKVMapList(List<Map> list, Class<K> keyClass, Class<V> valueClass) {
        List<Map<K, V>> destList = new ArrayList<>();
        list.forEach((map) -> {
            destList.add(toKVMap(map, keyClass, valueClass));
        });
        return destList;
    }


    public static <M, N> List<N> toNList(List<M> mList, Class<M> mClass, Class<N> nClass) {
        if (mList.isEmpty()) {
            return Collections.emptyList();
        }
        List<N> results = new ArrayList<>();
        for (M m : mList) {
            try {
                N n = nClass.newInstance();
                BeanUtils.copyProperties(m, n);
                results.add(n);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return Collections.emptyList();
            }
        }
        return results;
    }

}
