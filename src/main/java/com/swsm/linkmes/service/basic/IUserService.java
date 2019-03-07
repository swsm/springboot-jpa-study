/**
 * UserService.java
 * Created at 2014-7-5
 * Created by zhangqiuyi
 * Copyright (C) 2014 SHANGHAI BRODATEXT, All rights reserved.
 */
package com.swsm.linkmes.service.basic;


import com.swsm.linkmes.domain.dto.UserQueryDto;
import com.swsm.linkmes.model.basic.User;
import com.swsm.platform.model.PageFilter;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * ClassName: UserService
 * </p>
 * <p>
 * Description: 用户管理的业务接口
 * </p>
 * <p>
 * Author: zhangqiuyi
 * </p>
 * <p>
 * Date: 2014-7-5
 * </p>
 */
public interface IUserService {

    /**
     * 启用
     */
    final static String ENABLED = "1";

    /**
     * 禁用
     */
    final static String DESIBLED = "0";

    /**
     * <p>Description: 分页查询用户信息，每个查询条件在userQueryDto中</p>
     *
     * @param userQueryDto 查询条件的过滤器 <br/>
     *                     queryMap中的keySet如下：
     * @param pageFilter
     * @return list, user对象包括基本对象、roleNameStr（逗号分割），roleId
     */
    List<User> queryPagedEntityListForUser(UserQueryDto userQueryDto, PageFilter pageFilter);

    /**
     * <p>
     * Description: 根据用户名修改用户密码
     * </p>
     *
     * @param newPassword 新密码
     * @param oldPassword 原始密码
     * @param username    修改用户
     * @return 如果修改成功返回success；如果修改不成功返回fail
     */
    String updatePassword(String newPassword, String oldPassword, String username);

    /**
     * <p>Description: 设置用户为禁用状态</p>
     *
     * @param ids      用户ids，用逗号分割
     * @param username 修改用户
     */
    void disableUser(String ids, String username);

    /**
     * <p>Description: 设置用户为启用状态</p>
     *
     * @param ids      用户ids，用逗号分割
     * @param username 修改用户
     */
    void enableUser(String ids, String username);

    /**
     * <p>Description: 删除用户信息</p>
     *
     * @param ids      用户ids，用逗号分割
     * @param username 修改用户
     */
    void deleteUser(String ids, String username);


    /**
     * <p>Description: 新增用户信息</p>
     *
     * @param user     用户对象，需要包括roleList信息
     * @param username 操作人
     * @return String 操作结果；workNoExist：表示工号存在，usernameExist：用户名存在，success：表示操作成功
     */
    String saveUser(User user, String username);

    /**
     * <p>Description: 更新用户信息</p>
     *
     * @param user     用户对象，需要包括roleList信息
     * @param username 操作人
     * @return String 操作结果；workNoExist：表示工号存在，usernameExist：用户名存在，success：表示操作成功
     */
    String updateUser(User user, String username);

    /**
     * <p>Description: 查找所有的用户信息</p>
     *
     * @return List<User> 用户信息列表，不包括扩展属性
     */
    List<User> getUserAll();

    /**
     * <p>Description: 根据工号或者用户真实名称查询用户信息</p>
     *
     * @param employeeName 用户名称
     * @return List<User> 用户列表信息，User对象只包括id，workNo，truename这些属性
     */
    List<User> getUserByEname(String employeeName);

    /**
     * <p>Description: 根据用户id查找用户信息</p>
     *
     * @param userId 用户id
     * @return 用户信息
     */
    User getUserById(String userId);


    /**
     * <p>Description: 根据组织id、工号或者用户真实名称查询用户信息</p>
     *
     * @param organId      组织id
     * @param employeeName 用户名称
     * @return List<User> 用户列表信息，User对象只包括id，workNo，truename这些属性
     */
    List<User> getUserByOrganIdAndEname(String organId, String employeeName);

    /**
     * <p>Description: 上传附件</p>
     *
     * @param user
     * @param userName
     */
    void uploadPicture(User user, String userName);

    /**
     * 通过用户名或者用户编码进行模糊查询，获取用户信息<
     *
     * @param code
     * @return
     */
    List<Map<String, String>> getUserByCode(String code);

}
