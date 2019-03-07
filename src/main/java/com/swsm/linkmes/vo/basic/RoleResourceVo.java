package com.swsm.linkmes.vo.basic;

import java.io.Serializable;

/**
 * @author tinel
 * @Title: RoleResourceVo
 * @ProjectName mes-sm
 * @Description: 角色与资源关系
 * @date 2018/12/2113:18
 */
public class RoleResourceVo implements Serializable{

    private static final long serialVersionUID = -7948651089728229967L;

    /**
     * 角色ID
     */
    private String roleId;


    /**
     * 资源ID，用，分割
     */
    private String resourceIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}
