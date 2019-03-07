package com.swsm.linkmes.vo.basic;


import com.swsm.platform.model.CommonVo;

import java.io.Serializable;

/**
 * @author tinel
 * @Title: UserVo
 * @ProjectName mes-sm
 * @Description: 描述用户信息
 * @date 2018/12/129:22
 */
public class UserVo extends CommonVo implements Serializable{


    private static final long serialVersionUID = -2409055128114803798L;


    // Fields
    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户姓名
     */
    private String truename;

    /**
     * 用户座机
     */
    private String telephone;

    /**
     * 用户电子邮件
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户住址
     */
    private String address;

    /**
     * 头像
     */
    private String pictureUrl;

    /**
     * 工号
     */
    private String workNo;

    /**
     * LDAP server中的Id
     */
    private String ldapUserId;

    /**
     * 开启状态
     */
    private String enabled;

    /**
     * 角色名称字符串
     */
    private String roleNameStr;

    private String[] roleId;
    /**
     * 微信号
     */
    private String wechatName;

    /**
     *
     */
    private String authorityNameStr;

    /**
     *
     */
    private String[] authorityId;

    /**
     * 机构名
     */
    private String organName = null;
    /**
     * 机构id
     */
    private String organId = null;
    /**
     * 机构code
     */
    private String organCode = null;


    public String[] getRoleId() {
        return roleId;
    }

    public String[] getAuthorityId() {
        return authorityId;
    }

    public String getOrganName() {
        return organName;
    }

    public String getOrganId() {
        return organId;
    }

    public String getOrganCode() {
        return organCode;
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
