package com.swsm.linkmes.vo.basic;


import com.swsm.platform.model.CommonVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @author tinel
 * @Title: ResourceVo
 * @ProjectName mes-sm
 * @Description: 描述资源信息
 * @date 2018/12/1214:59
 */
public class ResourceVo extends CommonVo implements Serializable{

    private static final long serialVersionUID = 6919918393339828931L;

    /**
     * 资源名
     */
    private String resName;

    /**
     * 资源代码
     */
    private String resCode;

    /**
     * 资源类别
     */
    private String resType;

    /**
     * 资源排序
     */
    private Integer resOrder;

    /**
     * 资源状态
     */
    private String enabled;

    /**
     * 所属系统
     */
    private String belongSystem;


    /**
     *
     */
    private String modualFlag;

    /**
     * 子资源（直接孩子）数量，
     */
    private BigDecimal childCount ;

    /**
     * 数据库中查出来的是否选中 1选中0未选中
     */
    private String dbChecked;


    /**
     * 上级菜单名
     */
    private String preResName;

    /**
     * 上级菜单id
     */
    private String parentIdStr;


    /*
     * 菜单图标class
     */
    private String iconCls;

    private Set<ResourceVo> children;


    public Set<ResourceVo> getChildren() {
        return children;
    }

    public void setChildren(Set<ResourceVo> children) {
        this.children = children;
    }

    /**
     * 对应的跳转url地址
     */
    private String resUrl;

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public Integer getResOrder() {
        return resOrder;
    }

    public void setResOrder(Integer resOrder) {
        this.resOrder = resOrder;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getBelongSystem() {
        return belongSystem;
    }

    public void setBelongSystem(String belongSystem) {
        this.belongSystem = belongSystem;
    }

    public String getModualFlag() {
        return modualFlag;
    }

    public void setModualFlag(String modualFlag) {
        this.modualFlag = modualFlag;
    }

    public BigDecimal getChildCount() {
        return childCount;
    }

    public void setChildCount(BigDecimal childCount) {
        this.childCount = childCount;
    }

    public String getDbChecked() {
        return dbChecked;
    }

    public void setDbChecked(String dbChecked) {
        this.dbChecked = dbChecked;
    }

    public String getPreResName() {
        return preResName;
    }

    public void setPreResName(String preResName) {
        this.preResName = preResName;
    }

    public String getParentIdStr() {
        return parentIdStr;
    }

    public void setParentIdStr(String parentIdStr) {
        this.parentIdStr = parentIdStr;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public boolean getChecked() {
        return "1".equals(this.getDbChecked());
    }

    public boolean getLeaf() {
        if(this.childCount ==null){
            return true;
        }
        return this.childCount.equals(new BigDecimal(0));
    }

}
