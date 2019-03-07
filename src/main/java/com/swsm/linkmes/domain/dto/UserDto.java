package com.swsm.linkmes.domain.dto;


import com.swsm.linkmes.model.basic.User;

import java.io.Serializable;

public class UserDto extends User implements Serializable{

    private static final long serialVersionUID = -5338991337170679092L;

    /**
     * 机构ID
     */
    private String organId;

    /**
     * 机构名称
     */
    private String organName;

    @Override
    public String getOrganId() {
        return organId;
    }

    @Override
    public void setOrganId(String organId) {
        this.organId = organId;
    }

    @Override
    public String getOrganName() {
        return organName;
    }

    @Override
    public void setOrganName(String organName) {
        this.organName = organName;
    }
}
