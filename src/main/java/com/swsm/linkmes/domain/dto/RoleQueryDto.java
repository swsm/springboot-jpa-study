package com.swsm.linkmes.domain.dto;


import com.swsm.linkmes.model.basic.Role;

import java.io.Serializable;
import java.util.Date;

public class RoleQueryDto extends Role implements Serializable{

    private static final long serialVersionUID = -7590479543992293719L;


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
