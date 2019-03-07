package com.swsm.linkmes.model.basic;



import com.swsm.platform.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_dict_idx")
public class DictIdx extends BaseModel implements java.io.Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 7530202458836884341L;

    // Fields
    /**
     * 字典标识
     */
    @Column(name = "dict_key", length = 50)
    private String dictKey;

    /**
     * 字典名称
     */
    @Column(name = "dict_name", length = 100)
    private String dictName;

    /**
     * 字典类型
     */
    @Column(name = "dict_type", length = 4)
    private int dictType;
    
	/**
     * 默认的构造函数
     */
    public DictIdx() {
    }

    /**
     * <p>
     * Description: 根据键构造一个字典索引类
     * </p>
     * 
     * @param dictKey 键
     */
    public DictIdx(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictKey() {
        return this.dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictName() {
        return this.dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public int getDictType() {
        return this.dictType;
    }

    public void setDictType(int dictType) {
        this.dictType = dictType;
    }
}