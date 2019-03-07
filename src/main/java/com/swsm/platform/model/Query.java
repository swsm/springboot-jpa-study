package com.swsm.platform.model;


import java.io.Serializable;

public class Query implements Serializable {

    /**
     * 条件
     */
    private Condition condition;
    /**
     * 值
     */
    private Object value;

    /**
     * 值
     */
    private boolean ignoreCase;

    /**
     * <p>Description: 构造函数，用于快速初始化</p>
     *
     * @param value 值
     */
    public Query(Object value) {
        this.condition = Condition.EQ;
        this.value = value;
    }

    /**
     * <p>Description: 构造函数，用于快速初始化</p>
     *
     * @param condition 条件
     * @param value     值
     */
    public Query(Condition condition, Object value) {
        this.condition = condition;
        this.value = value;
    }


    /**
     * <p>Description: 构造函数，用于快速初始化</p>
     *
     * @param condition  条件
     * @param value      值
     * @param ignoreCase 忽略大小写
     */
    public Query(Condition condition, Object value, boolean ignoreCase) {
        this.condition = condition;
        this.value = value;
        this.ignoreCase = ignoreCase;
    }

    public Condition getCondition() {
        return this.condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


    public boolean isIgnoreCase() {
        return this.ignoreCase;
    }


    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
}
