package com.swsm.linkmes.domain.dto;


import com.swsm.platform.model.BaseModel;

public class OrganDto extends BaseModel {
    /**
     * <p>Field serialVersionUID: 序列化</p>
     */
    private static final long serialVersionUID = 1L;
    /**
     * 机构名称
     */
    private String organName;
    /**
     * 机构编码
     */
    private String organCode;
    /**
     * 负责人名称
     */
    private String dutyUsername;
    /**
     * LDAP server中的Id
     */
    private String ldapOrganId;
    /**
     * 机构排序
     */
    private Integer organOrder;
    /**
     * 父节点id
     */
    private String parentId;

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getDutyUsername() {
        return dutyUsername;
    }

    public void setDutyUsername(String dutyUsername) {
        this.dutyUsername = dutyUsername;
    }

    public String getLdapOrganId() {
        return ldapOrganId;
    }

    public void setLdapOrganId(String ldapOrganId) {
        this.ldapOrganId = ldapOrganId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getOrganOrder() {
        return organOrder;
    }

    public void setOrganOrder(Integer organOrder) {
        this.organOrder = organOrder;
    }
    
}
