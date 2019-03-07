package com.swsm.linkmes.domain.dto;


import com.swsm.linkmes.model.basic.User;

import java.io.Serializable;
import java.util.Date;

public class UserQueryDto extends User implements Serializable{

    /**
     * 新建时间-开始
     */
    private Date createDate_start;

    /**
     * 新建时间-结束
     */
    private Date createDate_end;

    public Date getCreateDate_start() {
        return createDate_start;
    }

    public void setCreateDate_start(Date createDate_start) {
        this.createDate_start = createDate_start;
    }

    public Date getCreateDate_end() {
        return createDate_end;
    }

    public void setCreateDate_end(Date createDate_end) {
        this.createDate_end = createDate_end;
    }
}
