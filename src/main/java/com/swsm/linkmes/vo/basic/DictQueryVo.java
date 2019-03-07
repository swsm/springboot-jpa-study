package com.swsm.linkmes.vo.basic;

import java.io.Serializable;

/**
 * @author tinel
 * @Title: DictQueryVo
 * @ProjectName mes-sm
 * @Description: 字典查询条件封装类
 * @date 2018/12/1916:43
 */
public class DictQueryVo implements Serializable{

    private static final long serialVersionUID = 844824854302872175L;

    /**
     * 字典键值
     */
    private String dictKey;

    /**
     * 字典显示值
     */
    private String dictValue;


    /**
     * 是否开启
     */
    private String openFlag;


    /**
     * 字典索引标识
     */
    private String dictIndexKey;

    /**
     * 字典索引名称
     */
    private String dictIndexName;

    /**
     * 字典索引类型
     */
    private int dictIndexType;

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

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public String getDictIndexKey() {
        return dictIndexKey;
    }

    public void setDictIndexKey(String dictIndexKey) {
        this.dictIndexKey = dictIndexKey;
    }

    public String getDictIndexName() {
        return dictIndexName;
    }

    public void setDictIndexName(String dictIndexName) {
        this.dictIndexName = dictIndexName;
    }

    public int getDictIndexType() {
        return dictIndexType;
    }

    public void setDictIndexType(int dictIndexType) {
        this.dictIndexType = dictIndexType;
    }
}
