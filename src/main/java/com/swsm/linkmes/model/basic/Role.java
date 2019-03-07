package com.swsm.linkmes.model.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swsm.platform.model.BaseModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SYS_ROLE")
public class Role extends BaseModel implements java.io.Serializable {

    /**
     * 角色名
     */
    @Column(name = "ROLE_NAME", length = 100)
    private String roleName;

    /**
     * 角色类别
     */
    @Column(name = "ROLE_TYPE", length = 4)
    private String roleType;
    
    /**
     * 是否可以忽略决策
     */
    @Column(name = "IGNORE_DECESION", length = 2)
    private String ignoreDecesion;

    /**
     * 状态
     */
    @Column(name = "ENABLED", length = 1)
    private String enabled;

    /**
     * 被哪些用户引用
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_USER_ROLE", joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "PK_ID") },
        inverseJoinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "PK_ID") })
    @JsonIgnore
    private  Set<User> userList = new HashSet<User>();

    /**
     * 对应哪些资源
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_ROLE_RESOURCE", joinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "PK_ID")
        }, inverseJoinColumns = { @JoinColumn(name = "RES_ID", referencedColumnName = "PK_ID") })
    @JsonIgnore
    private  Set<Resource> resList = new HashSet<Resource>();

    // Constructors

    /**
     *  默认构造函数
     */
    public Role() {
    }

    /**
     * <p>
     * Description: 根据角色id构造一个对象
     * </p>
     * 
     * @param id 角色id
     */
    public Role(String id) {
        super.setId(id);
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Set<User> getUserList() {
        return this.userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    public Set<Resource> getResList() {
        return this.resList;
    }

    public void setResList(Set<Resource> resList) {
        this.resList = resList;
    }

    public String getIgnoreDecesion() {
        return this.ignoreDecesion;
    }

    public void setIgnoreDecesion(String ignoreDecesion) {
        this.ignoreDecesion = ignoreDecesion;
    }

}