package com.swsm.linkmes.model.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swsm.platform.model.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SYS_RESOURCE")
public class Resource extends BaseModel implements java.io.Serializable {

    /**
     * 资源名
     */
    @Column(name = "RES_NAME", length = 100)
    private String resName;

    /**
     * 资源代码
     */
    @Column(name = "RES_CODE", length = 100)
    private String resCode;

    /**
     * 资源类别
     */
    @Column(name = "RES_TYPE", length = 4)
    private String resType;

    /**
     * 资源排序
     */
    @Column(name = "RES_ORDER", columnDefinition = "NUMBER(6)")
    private Integer resOrder;
    
    /**
     * 资源状态
     */
    @Column(name = "ENABLED", length = 1)
    private String enabled;
    
    /**
     * 所属系统
     */
    @Column(name = "BELONG_SYSTEM", length = 1)
    private String belongSystem;
    
    
    /**
     * 
     */
    @Column(name = "MODUAL_FLAG")
    private String modualFlag;
    
    /**
     * 子资源（直接孩子）数量，
     */
    @Transient
    private BigDecimal childCount ;
   
    /**
     * 数据库中查出来的是否选中 1选中0未选中
     */
    @Transient
    private String dbChecked;
    
    /**
     * 被哪些角色引用
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_ROLE_RESOURCE",
    joinColumns = { @JoinColumn(name = "RES_ID", referencedColumnName = "PK_ID") },
    inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "PK_ID") })
    @JsonIgnore
    private Set<Role> roleList = new HashSet<Role>();  
    
    /**
     * 上级资源
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "PK_ID")
    @JsonIgnore
    private Resource parentResource; 
    
    /**
     * 下级资源列表
     */
    @OneToMany(mappedBy = "parentResource", cascade = {CascadeType.REMOVE }, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Resource> childResList;
    
    /**
     * 下级资源列表
     */
    @Transient
    private Set<Resource> children;
    
    /**
     * 上级菜单名
     */
    @Transient
    private String preResName;
    
    /**
     * 上级菜单id
     */
    @Transient
    private String parentIdStr;
    
    
    /*
     * 菜单图标class
     */
    @Column(name = "icon_cls", length = 50)
    private String iconCls;
    
    /**
     * 对应的跳转url地址
     */
    @Column(name="RES_URL",length=255)
    private String resUrl;
    

    /**
     * 默认构造函数
     */
    public Resource() {
    }

    /**
     * <p>Description: 构造函数</p>
     * @param id 资源Id
     * @param resName 资源名
     */
    public Resource(String id, String resName) {
        this.setId(id);
        this.setResName(resName);
    }

    public boolean getChecked() {
        return "1".equals(this.getDbChecked());
    }
    

    public String getDbChecked() {
        return this.dbChecked;
    }

    public void setDbChecked(String dbChecked) {
        this.dbChecked = dbChecked;
    }

    public Resource getParentResource() {
        return this.parentResource;
    }

    public void setParentResource(Resource parentResource) {
        this.parentResource = parentResource;
    }

    public String getParentId() {
        return this.parentResource == null ? "" : this.parentResource.getId();
    }

    public String getParentResName() {
        return this.parentResource == null ? "" : this.parentResource.getResName();
    }

    public BigDecimal getChildCount() {
        return this.childCount;
    }

    public void setChildCount(BigDecimal childCount) {
        this.childCount = childCount;
    }

    public boolean getLeaf() {
        if(this.childCount ==null){
            return true;
        }
        return this.childCount.equals(new BigDecimal(0));
    }

    public Set<Resource> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Resource> children) {
        this.children = children;
    }

    public Set<Resource> getChildResList() {
        return this.childResList;
    }

    public void setChildResList(Set<Resource> childResList) {
        this.childResList = childResList;
    }

    public String getResName() {
        return this.resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }
    
    public String getResCode() {
        return this.resCode;
    }
    
    public void setResCode(String resCode) {
        this.resCode = resCode;
    }
    
    public String getResType() {
        return this.resType;
    }
    
    public void setResType(String resType) {
        this.resType = resType;
    }
    
    public Set<Role> getRoleList() {
        return this.roleList;
    }
    
    public void setRoleList(Set<Role> roleList) {
        this.roleList = roleList;
    }
    
    public Integer getResOrder() {
        return this.resOrder;
    }

    public void setResOrder(Integer resOrder) {
        this.resOrder = resOrder;
    }

    public String getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * <p>Description: 移除子节点</p>
     * @param resId 资源Id
     */
    public void removeChild(String resId) {
        for (Resource child : this.children) {
            if (child.getId().equals(resId)) {
                this.children.remove(child);
                break;
            }
            child.removeChild(resId);
        }
    }

    public String getModualFlag() {
        return modualFlag;
    }

    public void setModualFlag(String modualFlag) {
        this.modualFlag = modualFlag;
    }

    public String getBelongSystem() {
        return this.belongSystem;
    }

    public void setBelongSystem(String belongSystem) {
        this.belongSystem = belongSystem;
    }

    public String getPreResName() {
        return this.preResName;
    }

    public void setPreResName(String preResName) {
        this.preResName = preResName;
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

	public String getParentIdStr() {
		return this.parentIdStr;
	}

	public void setParentIdStr(String parentIdStr) {
		this.parentIdStr = parentIdStr;
	}
    
    
    
}