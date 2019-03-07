package com.swsm.platform.action;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tinel
 * @Title: CommonJsonVo
 * @ProjectName
 * @Description: 抽象需要保存的JSON对象
 * @date 2018/11/2310:59
 */
public class CommonJsonVo implements Serializable{


    private static final long serialVersionUID = 1821186665571402101L;

    /**
     * 新增数据，json数组
     */
    private String insertJson;

    /**
     * 修改数据，json数组
     */
    private String updateJson;

    /**
     * 删除数据，json数组
     */
    private String deleteJson;

    /**
     * 记录时间
     */
    private Date recordDate;

    /**
     * 记录人
     */
    private String userDisplayName;


    public String getInsertJson() {
        return insertJson;
    }

    public void setInsertJson(String insertJson) {
        this.insertJson = insertJson;
    }

    public String getUpdateJson() {
        return updateJson;
    }

    public void setUpdateJson(String updateJson) {
        this.updateJson = updateJson;
    }

    public String getDeleteJson() {
        return deleteJson;
    }

    public void setDeleteJson(String deleteJson) {
        this.deleteJson = deleteJson;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }
}
