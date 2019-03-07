package com.swsm.linkmes.model.basic;


import com.swsm.platform.model.BaseModel;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sys_user")
public class User extends BaseModel implements java.io.Serializable {

    /**
     * 用户账号
     */
    @Column(name = "username", length = 100)
    private String username;

    /**
     * 用户密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 用户姓名
     */
    @Column(name = "truename", length = 100)
    private String truename;

    /**
     * 用户座机
     */
    @Column(name = "telephone", length = 100)
    private String telephone;

    /**
     * 用户电子邮件
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "mobile", length = 100)
    private String mobile;
    
    /**
     * 用户住址
     */
    @Column(name = "address", length = 200)
    private String address;

    /**
     * 头像
     */
    @Column(name = "PICTURE_URL", length = 200)
    private String pictureUrl;
    
    /**
     * 工号
     */
    @Column(name = "WORK_NO", length = 50)
    private String workNo;

    /**
     * LDAP server中的Id
     */
    @Column(name = "ldap_user_id", length = 50)
    private String ldapUserId;
    
    /**
     * 开启状态
     */
    @Column(name = "enabled", length = 1)
    private String enabled;

    /**
     * 角色名称字符串
     */
    @Column(name = "role_Name_Str", length = 500)
    private String roleNameStr;
    
    @Transient
    private String[] roleId;
    /**
     * 微信号
     */
    @Column(name = "WECHAT_NAME", length = 100)
    private String wechatName;

    /**
     * 所拥有的角色列表
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_USER_ROLE",
    joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "PK_ID") },
    inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "PK_ID") })
    private  Set<Role> roleList = new HashSet<Role>();

    @Transient
    private String authorityNameStr;
    
    @Transient
    private String[] authorityId;
    

    /**
     * 机构名
     */
    @Transient
    private String organName = null;
    /**
     * 机构id
     */
    @Transient
    private String organId = null;
    /**
     * 机构code
     */
    @Transient
    private String organCode = null;

    /**
     * 对应的部门（当前是一个用户一个部门），但是也通过中间表绑定关系
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_USER_ORGAN",
    joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "PK_ID") },
    inverseJoinColumns = {@JoinColumn(name = "ORGAN_ID", referencedColumnName = "PK_ID") })
    private  Set<Organ> organList = new HashSet<Organ>(); 

    public Set<Organ> getOrganList() {
        return this.organList;
    }

    public void setOrganList(Set<Organ> organList) {
        this.organList = organList;
    }

    /**
     * <p>Description: 角色名</p>
     * @return RoleId数组
     */
    public String[] getRoleNames() {
        String[] roleNames;
        roleNames = StringUtils.split(this.getRoleNameStr(), ", ");
        return roleNames;
    }
    
    /**
     * <p>Description: 取得RoleId数组</p>
     * @return RoleId数组
     */
    public String[] getRoleId() {
        if(this.roleId!=null && this.roleId.length > 0){
            return this.roleId;
        }else{
            List<String> roleStringList = new ArrayList<>();
            for (Role role: this.roleList) {
                if ("1".equals(role.getEnabled())) {
                    roleStringList.add(role.getId());
                }
            }
            String[] a;
            a = new String[]{};
            return roleStringList.toArray(a);
        }
    }
    

    /**
     * <p>Description: 所在机构id</p>
     * @return 机构id
     */
    public String getOrganId() {
        if (StringUtils.isNotEmpty(this.organId)) {
            return this.organId;
        }
        if (!this.organList.isEmpty()) {
            Organ[] arr;
            arr = new Organ[]{};
            return (this.organList.toArray(arr))[0].getId();
        }
        return null;
    }
    
    /**
     * <p>Description: 所在机构名称</p>
     * @return 机构名称
     */
    public String getOrganName() {
        if (StringUtils.isNotEmpty(this.organName)) {
            return this.organName;
        }
        
        if (!this.organList.isEmpty()) {
            Organ[] arr;
            arr = new Organ[]{};
            return (this.organList.toArray(arr))[0].getOrganName();
        }
        return null;
    }
    
    /**
     * <p>Description: 所在机构代码</p>
     * @return 机构代码
     */
    public String getOrganCode() {
        if (StringUtils.isNotEmpty(this.organCode)) {
            return this.organCode;
        }
        if (!this.organList.isEmpty()) {
            Organ[] arr;
            arr = new Organ[]{};
            return (this.organList.toArray(arr))[0].getOrganName();
        }
        return null;
    }
    
    public String getLdapUserId() {
        return this.ldapUserId;
    }

    public void setLdapUserId(String ldapUserId) {
        this.ldapUserId = ldapUserId;
    }

    // Property accessors
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoleList() {
        return this.roleList;
    }

    public void setRoleList(Set<Role> roleList) {
        this.roleList = roleList;
    }

    public String getWorkNo() {
        return this.workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getRoleNameStr() {
        return this.roleNameStr;
    }

    public void setRoleId(String[] roleId) {
        this.roleId = roleId;
    }

    public void setRoleNameStr(String roleNameStr) {
        this.roleNameStr = roleNameStr;
    }

    public String getAuthorityNameStr() {
        return authorityNameStr;
    }

    public void setAuthorityNameStr(String authorityNameStr) {
        this.authorityNameStr = authorityNameStr;
    }

    public void setAuthorityId(String[] authorityId) {
        this.authorityId = authorityId;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getTruename() {
        return this.truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getWechatName() {
        return this.wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
    
}
