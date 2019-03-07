package com.swsm.linkmes.model.basic;


import com.swsm.platform.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "SYS_LOGIN_INFO")
public class LoginInfo extends BaseModel implements java.io.Serializable {


    /**
     * 用户账号
     */
    @Column(name = "USER_NAME", length = 100)
    private String userName;

    /**
     * 用户姓名
     */
    @Column(name = "TRUE_NAME", length = 100)
    private String trueName;

    /**
     * 用户ip
     */
    @Column(name = "IP_ADDRESS", length = 50)
    private String ipAddress;

    /**
     * 最近访问模块
     */
    @Column(name = "MODULE_NAME", length = 100)
    private String moduleName;

    /**
     * 登录时间
     */
    @Column(name = "LOGIN_TIME")
    private Date loginTime;

    /**
     * 登录状态
     */
    @Column(name = "LOGIN_STATUS", length = 1)
    private String loginStatus;

    /**
     * 注销原因
     */
    @Column(name = "LOGOFF_TAG", length = 1)
    private String logoffTag;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return this.trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginStatus() {
        return this.loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getLogoffTag() {
        return this.logoffTag;
    }

    public void setLogoffTag(String logoffTag) {
        this.logoffTag = logoffTag;
    }
}
