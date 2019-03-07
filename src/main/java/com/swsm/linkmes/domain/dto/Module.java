/**
 * Module.java
 * Created at 2016-5-5
 * Created by TangSanlin
 * Copyright (C) 2016 BROADTEXT SOFTWARE, All rights reserved.
 */
package com.swsm.linkmes.domain.dto;

import java.util.List;

/**
 * 
 * <p>
 * ClassName: Module
 * </p>
 * <p>
 * Description: 模块定义类
 * </p>
 * <p>
 * Author: Administrator
 * </p>
 * <p>
 * Date: 2016-5-17
 * </p>
 */
public class Module implements java.io.Serializable {

    /**
     * <p>
     * Field serialVersionUID: 版本
     * </p>
     */
    private static final long serialVersionUID = 6992217444298403206L;
    /**
     * 模块ID
     */
    private String id;
    /**
     * 模块显示值
     */
    private String text;
    /**
     * 叶子节点标识
     */
    private boolean leaf;
    /**
     * 模块URL
     */
    private String url;
    /**
     * 孩子节点
     */
    private List<Module> children;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Module> getChildren() {
        return this.children;
    }

    public void setChildren(List<Module> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

}
