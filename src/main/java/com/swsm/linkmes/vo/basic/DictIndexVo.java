package com.swsm.linkmes.vo.basic;


import com.swsm.platform.model.CommonVo;

import java.io.Serializable;

/**
 * @author tinel
 * @Title: DictIndexVo
 * @ProjectName mes-sm
 * @Description: 字典索引表现层对象
 * @date 2018/12/1916:44
 */
public class DictIndexVo extends CommonVo implements Serializable{

    private static final long serialVersionUID = -6329845291593402559L;

    /**
     * 字典标识
     */
    private String dictKey;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private int dictType;


    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public int getDictType() {
        return dictType;
    }

    public void setDictType(int dictType) {
        this.dictType = dictType;
    }
}
