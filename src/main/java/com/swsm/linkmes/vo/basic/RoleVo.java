package com.swsm.linkmes.vo.basic;


import com.swsm.platform.model.CommonVo;

import java.io.Serializable;

/**
 * @author tinel
 * @Title: RoleVo
 * @ProjectName mes-sm
 * @Description: 描述角色信息
 * @date 2018/12/129:59
 */
public class RoleVo extends CommonVo implements Serializable{

    private static final long serialVersionUID = -6231935010096433659L;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色类别
     */
    private String roleType;

    /**
     * 是否可以忽略决策
     */
    private String ignoreDecesion;

    /**
     * 状态
     */
    private String enabled;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getIgnoreDecesion() {
        return ignoreDecesion;
    }

    public void setIgnoreDecesion(String ignoreDecesion) {
        this.ignoreDecesion = ignoreDecesion;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
