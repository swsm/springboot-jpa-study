package com.swsm.linkmes.vo.basic;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tinel
 * @Title: UserQueryVo
 * @ProjectName mes-sm
 * @Description: 用户查询条件封装类
 * @date 2018/12/1413:35
 */
public class UserQueryVo extends UserVo implements Serializable{

    private static final long serialVersionUID = -2448996558543778354L;

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
