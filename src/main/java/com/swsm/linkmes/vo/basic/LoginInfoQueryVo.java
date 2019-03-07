package com.swsm.linkmes.vo.basic;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tinel
 * @Title: LoginInfoQueryVo
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/2613:23
 */
public class LoginInfoQueryVo extends LoginInfoVo implements Serializable{


    private static final long serialVersionUID = 3047917286049411296L;

    private Date loginTimeStart;

    private Date loginTimeEnd;


    public Date getLoginTimeStart() {
        return loginTimeStart;
    }

    public void setLoginTimeStart(Date loginTimeStart) {
        this.loginTimeStart = loginTimeStart;
    }

    public Date getLoginTimeEnd() {
        return loginTimeEnd;
    }

    public void setLoginTimeEnd(Date loginTimeEnd) {
        this.loginTimeEnd = loginTimeEnd;
    }
}
