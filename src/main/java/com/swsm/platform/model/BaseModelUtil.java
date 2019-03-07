/**
 * BaseUtil.java
 * Created at 2015-12-2
 * Created by Administrator
 * Copyright (C) 2015 BROADTEXT SOFTWARE, All rights reserved.
 */
package com.swsm.platform.model;


import java.util.Date;

/**
 * <p>
 * ClassName: BaseUtil
 * </p>
 * <p>
 * Description: 基础工具类
 * </p>
 * <p>
 * Author: Administrator
 * </p>
 * <p>
 * Date: 2015-12-2
 * </p>
 */
public class BaseModelUtil {

    /**
     * 删除标志
     */
    public final static String DEL_TRUE="1";

    /**
     * 非删除标志
     */
    public final static String DEL_FALSE="0";

    /**
     * <p>
     * Description: 设置对象默认值
     * </p>
     *
     * @param baseModel 基础对象
     * @param isAdd     是否新增
     * @param userInfo  挡墙用户信息
     */
    public static void setDefaultFieldBaseModel(BaseModel baseModel, String[] userInfo, boolean isAdd) {
        baseModel.setDelFlag(DEL_FALSE);
        if (isAdd) {
            baseModel.setCreateDate(new Date());
            baseModel.setCreateUser(userInfo[1]);
        } else {
            baseModel.setUpdateDate(new Date());
            baseModel.setUpdateUser(userInfo[1]);
        }
    }

    /**
     * <p>
     * Description: 设置对象默认值
     * </p>
     *
     * @param baseModel 基础对象
     * @param isAdd     是否新增
     * @param user      挡墙用户信息
     */
    public static void setDefaultFieldBaseModel(BaseModel baseModel, String user, boolean isAdd) {
        baseModel.setDelFlag(DEL_FALSE);
        if (isAdd) {
            baseModel.setCreateDate(new Date());
            baseModel.setCreateUser(user);
        } else {
            baseModel.setUpdateDate(new Date());
            baseModel.setUpdateUser(user);
        }
    }

}
