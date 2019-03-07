package com.swsm.linkmes.vo.basic;

import java.io.Serializable;

/**
 * @author tinel
 * @Title: ResourceQueryVo
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/1814:20
 */
public class ResourceQueryVo  implements Serializable{

    private static final long serialVersionUID = 7898586940437706319L;


    /**
     * 父节点 ID
     */
    private String parentId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 资源名称
     */
    private String resName;

    /**
     * 资源编码
     */
    private String resCode;

    /**
     * 资源类型
     */
    private String resType;

    /**
     * 是否包含当前节点，true：包含，false不包含
     */
    private boolean isContainCurrentNode;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public boolean isContainCurrentNode() {
        return isContainCurrentNode;
    }

    public void setContainCurrentNode(boolean containCurrentNode) {
        isContainCurrentNode = containCurrentNode;
    }


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
}
