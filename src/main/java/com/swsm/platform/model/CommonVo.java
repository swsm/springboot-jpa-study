package com.swsm.platform.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tinel
 * @Title: CommonVo
 * @ProjectName mes-wms
 * @Description: 前台返回的vo的基础对象
 * @date 2018/11/2313:33
 */
public class CommonVo implements Serializable{

    private static final long serialVersionUID = -7772944220657599953L;


    /**
     * 数据模型的唯一标识
     */
    private String id;

    /**
     * 备注
     */
    private String remark = null;
    /**
     * 删除标记
     */
    private String delFlag = null;
    /**
     * 创建人
     */
    private String createUser = null;

    /**
     * 记录创建时间
     */
    private Date createDate = null;

    /**
     * 更新人
     */
    private String updateUser = null;

    /**
     * 记录更新时间
     */
    private Date updateDate = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
