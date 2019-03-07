/**
 * Dict.java
 * Created at 2014-5-5
 * Created by zhangqiuyi
 * Copyright (C) 2014 SHANGHAI BRODATEXT, All rights reserved.
 */
package com.swsm.linkmes.model.basic;

import com.swsm.platform.model.BaseModel;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>ClassName: Dict</p>
 * <p>Description: 字典明细的Model类</p>
 * <p>Author: zhangqiuyi</p>
 * <p>Date: 2014-5-5</p>
 */
@Entity
@Table(name = "sys_dict")
public class Dict extends BaseModel implements java.io.Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 7530202458836884341L;

    // Fields
    /**
     * 字典键值
     */
    @Column(name = "dict_key", length = 50)
    private String dictKey;

    /**
     * 字典显示值
     */
    @Column(name = "dict_value", length = 100)
    private String dictValue;

    /**
     * 排序
     */
    @Column(name = "dict_sort", columnDefinition = "NUMBER(4)")
    private Integer dictSort;

    /**
     * 是否开启
     */
    @Column(name = "open_flag", length = 1)
    private String openFlag;
   
    /**
     * 字典类型
     */
    @Column(name = "parent_key")
    private String parentKey;

    /**
     * 默认的构造函数
     */
    public Dict() {
    }
    
    /**
     * <p>
     * Description: 构造函数
     * </p>
     * 
     * @param dictKey 字典Key
     * @param dictValue 字典值
     */
    public Dict(String dictKey, String dictValue) {
        this.setDictKey(dictKey);
        this.setDictValue(dictValue);
    }

    // Property accessors

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getDictKey() {
        return this.dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return this.dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public Integer getDictSort() {
        return this.dictSort;
    }

    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }

    public String getOpenFlag() {
        return this.openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    /**
     * 
     * <p>
     * Description: 是否相等
     * </p>
     * 
     * @param obj 比较对象
     * @return true or false
     */
    public boolean equals(Object obj) {
        if (StringUtils.equals(this.dictKey, ((Dict) obj).getDictKey())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 重写hashCode
     * @return int
     */
     public int hashCode() {
         final int  CODE = 7;
         return this.dictKey.hashCode() << CODE;
     }
}