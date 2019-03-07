package com.swsm.linkmes.vo.basic;


import com.swsm.platform.model.CommonVo;

import java.util.Set;

/**
 * @author tinel
 * @Title: OrganVo
 * @ProjectName mes-sm
 * @Description: 机构信息
 * @date 2018/12/1914:25
 */
public class OrganVo extends CommonVo {

    private static final long serialVersionUID = -6158709166919242628L;

    /**
     * 机构名
     */
    private String organName;

    /**
     * 机构代码
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
     * 上级机构
     */
    private OrganVo parentOrgan;
    /**
     * 下级机构列表
     */
    private Set<OrganVo> children;

    /**
     * 上级ID
     */
    private String parentOrgenId;

    private boolean leaf;

    public String getParentOrgenId() {
        return parentOrgenId;
    }

    public void setParentOrgenId(String parentOrgenId) {
        this.parentOrgenId = parentOrgenId;
    }

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

    public Integer getOrganOrder() {
        return organOrder;
    }

    public void setOrganOrder(Integer organOrder) {
        this.organOrder = organOrder;
    }

    public OrganVo getParentOrgan() {
        return parentOrgan;
    }

    public void setParentOrgan(OrganVo parentOrgan) {
        this.parentOrgan = parentOrgan;
    }

    public Set<OrganVo> getChildren() {
        return children;
    }

    public void setChildren(Set<OrganVo> children) {
        this.children = children;
    }

    /**
     * <p>Description: 判断是否是叶子节点</p>
     * @return boolean 是 true 否 false
     */
    public boolean getLeaf() {
       return this.leaf;
    }

    public void setLeaf(boolean leaf){
        this.leaf=leaf;
    }
}
