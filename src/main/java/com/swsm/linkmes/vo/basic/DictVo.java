package com.swsm.linkmes.vo.basic;


import com.swsm.platform.model.CommonVo;

import java.io.Serializable;

/**
 * @author tinel
 * @Title: DictVo
 * @ProjectName mes-sm
 * @Description: 字典对象表现层
 * @date 2018/12/1916:44
 */
public class DictVo extends CommonVo implements Serializable{

    private static final long serialVersionUID = -3599456667939889774L;

    /**
     * 字典键值
     */
    private String dictKey;

    /**
     * 字典显示值
     */
    private String dictValue;

    /**
     * 排序
     */
    private Integer dictSort;

    /**
     * 是否开启
     */
    private String openFlag;

    /**
     * 字典类型
     */
    private String parentKey;

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public Integer getDictSort() {
        return dictSort;
    }

    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }
}
