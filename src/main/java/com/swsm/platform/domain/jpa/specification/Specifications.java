package com.swsm.platform.domain.jpa.specification;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@SuppressWarnings("unchecked")
public class Specifications {

    /**
     * like条件
     * @param attribute 属性
     * @param value 值
     * @return Specification
     */
    public static Specification containsLike(String attribute, String value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(attribute),value));
    }

    /**
     * equal条件
     * @param attribute 属性
     * @param value 值
     * @return Specification
     */
    public static Specification equal(String attribute, Object value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(attribute),value));
    }

    /**
     * between
     * @param attribute 属性
     * @param min 最大值
     * @param max 最小值
     * @return Specification
     */
    public static Specification between(String attribute, Integer min, Integer max) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(attribute),min,max));
    }

    public static Specification between(String attribute, Double min, Double max) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(attribute),min,max));
    }

    public static Specification between(String attribute, Date min, Date max) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(attribute),min,max));
    }

    /**
     * in
     * @param attribute 属性
     * @param collection 集合
     * @return Specification
     */
    public static Specification in(String attribute, Collection collection) {
        return ((root, query, criteriaBuilder) -> root.get(attribute).in(collection));
    }

    /**
     * 大于
     * @param attribute 属性
     * @param value 值
     * @return Specification
     */
    public static Specification greaterThan(String attribute, BigDecimal value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(attribute),value));
    }

    public static Specification greaterThan(String attribute, Long value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(attribute),value));
    }

    public static Specification greaterThan(String attribute, Integer value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(attribute),value));
    }

    public static Specification greaterThan(String attribute, Date value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(attribute),value));
    }

    /**
     * 大于等于
     * @param attribute 属性
     * @param value 值
     * @return Specification
     */
    public static Specification greaterThanOrEqualTo(String attribute, BigDecimal value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(attribute),value));
    }

    public static Specification greaterThanOrEqualTo(String attribute, Long value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(attribute),value));
    }

    public static Specification greaterThanOrEqualTo(String attribute, Integer value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(attribute),value));
    }

    public static Specification greaterThanOrEqualTo(String attribute, Date value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(attribute),value));
    }

    /**
     * 小于
     * @param attribute 属性
     * @param value 值
     * @return Specification
     */
    public static Specification lessThan(String attribute, BigDecimal value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(attribute),value));
    }

    public static Specification lessThan(String attribute, Long value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(attribute),value));
    }

    public static Specification lessThan(String attribute, Integer value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(attribute),value));
    }

    public static Specification lessThan(String attribute, Date value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(attribute),value));
    }

    /**
     * 小于等于
     * @param attribute 属性
     * @param value 值
     * @return Specification
     */
    public static Specification lessThanOrEqualTo(String attribute, BigDecimal value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(attribute),value));
    }

    public static Specification lessThanOrEqualTo(String attribute, Long value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(attribute),value));
    }

    public static Specification lessThanOrEqualTo(String attribute, Integer value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(attribute),value));
    }

    public static Specification lessThanOrEqualTo(String attribute, Date value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(attribute),value));
    }

    /**
     * 为null
     * @param attribute 属性
     * @return Specification
     */
    public static Specification isNull(String attribute) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(attribute)));
    }

    /**
     * 不为null
     * @param attribute 属性
     * @return Specification
     */
    public static Specification isNotNull(String attribute) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(attribute)));
    }


}
