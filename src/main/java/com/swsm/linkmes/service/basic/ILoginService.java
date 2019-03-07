/**
 * ILoginService.java
 * Created at 2016-5-5
 * Created by TangSanlin
 * Copyright (C) 2016 BROADTEXT SOFTWARE, All rights reserved.
 */
package com.swsm.linkmes.service.basic;


import com.swsm.linkmes.model.basic.User;

import java.util.Map;

/**
 * 
 * <p>
 * ClassName: ILoginService
 * </p>
 * <p>
 * Description: 用户登录服务接口
 * </p>
 * <p>
 * Author: Administrator
 * </p>
 * <p>
 * Date: 2016-5-17
 * </p>
 */
public interface ILoginService {
    /**
     * 
     * <p>
     * Description: 根据登录用户查找该用户信息，如果不存在该用户，返回null
     * </p>
     * 
     * @param loginName 登录名称
     * @return 用户对象
     */
    public User findUser(String loginName);



    /**
     * 
     * <p>
     * Description: 根据登录用户查找该用户全部信息，包括用户基本信息，机构信息，角色信息，如果不存在该用户，返回null
     * </p>
     * 
     * @param loginName 登录名称
     * @return 用户对象
     */
    public Map<String, Object> findUserFullInfo(String loginName);
    
    
    /**
     * 
     * <p>Description: 根据用户工号查找User</p>
     * @param workNo 用户名
     * @return User,如果不存在则返回null
     */
    public User getUserByWorkNo(String workNo) ;
    

    


}
