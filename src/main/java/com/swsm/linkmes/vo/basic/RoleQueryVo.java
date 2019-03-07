package com.swsm.linkmes.vo.basic;


import java.io.Serializable;
import java.util.Date;

/**
 * @author tinel
 * @Title: RoleQueryVo
 * @ProjectName mes-sm
 * @Description: 角色查询封装类
 * @date 2018/12/1715:56
 */
public class RoleQueryVo extends RoleVo implements Serializable{



    private static final long serialVersionUID = -3024755155127627051L;


    /**
     * 新建时间-开始
     */
    private Date createDateStart;

    /**
     * 新建时间-结束
     */
    private Date createDateEnd;


    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }
}
