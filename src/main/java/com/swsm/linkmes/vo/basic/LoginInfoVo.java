package com.swsm.linkmes.vo.basic;


import com.swsm.platform.model.CommonVo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tinel
 * @Title: LoginInfoVo
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/2613:21
 */
public class LoginInfoVo extends CommonVo implements Serializable{


    private static final long serialVersionUID = 5795952145390330608L;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户姓名
     */
    private String trueName;

    /**
     * 用户ip
     */
    private String ipAddress;

    /**
     * 最近访问模块
     */
    private String moduleName;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录状态
     */
    private String loginStatus;

    /**
     * 注销原因
     */
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
