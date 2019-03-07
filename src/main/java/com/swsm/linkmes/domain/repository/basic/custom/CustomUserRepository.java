package com.swsm.linkmes.domain.repository.basic.custom;


import com.swsm.linkmes.domain.dto.UserQueryDto;
import com.swsm.linkmes.model.basic.User;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepository;
import com.swsm.platform.model.PageFilter;

import java.util.List;
import java.util.Map;


/**
 * @author tinel
 * @Title: CustomUserRepository
 * @ProjectName mes-sm
 * @Description: 系统用户数据库访问层。
 * 提供对系统用户的客制化查询功能，例如分页查询，查询出特定的DTO数据列表
 * @date 2018/12/1014:10
 */
 public interface CustomUserRepository extends BaseCustomRepository {
    /**
     *
     * <p>Description: 根据用户名查找User</p>
     * @param userName 用户名
     * @return User对象（不包括扩展属性）,如果不存在则返回null
     */
     User getUserByUserName(String userName);

    /**
     *
     * <p>Description: 根据用户工号查找用户信息</p>
     * @param workNo 工号
     * @return User对象（不包括扩展属性）,如果不存在则返回null
     */
     User getUserByWorkNo(String workNo);
    /**
     * <p>Description: 分页查询用户信息，查询条件封装在filter对象中，每个查询条件在userQueryDto中</p>
     * @param userQueryDto 查询条件的过滤器 <br/>
     * @return list,user对象只包括基本对象
     */
     List<User> queryPagedEntityListForUser(UserQueryDto userQueryDto, PageFilter pageFilter);
    /**
     *
     * <p>Description: 根据用户的工号与ID确认是否存在工号相同但ID不同的用户，返回其用户信息列表</p>
     * @param user 必须包括workNo与id
     * @param isAdd 如果为true则不需要验证id是否不一致
     * @return User列表，User对象（不包括扩展属性）
     */
     List<User> checkWorkNo(User user, boolean isAdd);
    /**
     *
     * <p>Description: 根据用户的名称（username）与ID确认是否存在名称相同但ID不同的用户，返回其用户信息列表</p>
     * @param user 必须包括username与id
     * @param isAdd 如果为true则不需要验证id是否不一致
     * @return User列表，User对象（不包括扩展属性）
     */
     List<User> checkUserName(User user, boolean isAdd);
    /**
     *
     * <p>Description: 根据登录用户名称查找该用户信息</p>
     * @param loginName 登录名称
     * @return 用户对象 ，User对象只包括基本对象
     */
     List<User> findUser(String loginName);

    /**
     * <p>
     * Description: 根据用户名修改用户密码
     * </p>
     * @param newPassword 新密码
     * @param oldPassword 原始密码
     * @param username 用户名称
     * @return 如果修改成功返回success；如果修改不成功返回fail
     */
     String updatePassword(String newPassword, String oldPassword, String username);
    /**
     *
     * <p>Description: 通过用户名或者用户编码进行模糊查询，获取用户信息</p>
     * @param value 用户名或者用户编码
     * @return List<Map<String, String>> 用户信息<br/>
     * Map<String, String>对象的keySet如下：
     * userCode,userName,id,organId,organName
     */
     List<Map<String, String>> getUserByCode(String value);

    /**
     *
     * <p>Description: 查找所有的用户信息</p>
     * @return List<User> 用户信息列表，不包括扩展属性
     */
     List<User> findUserAll();

    /**
     * <p>Description: 根据角色URL获取用户信息列表</p>
     * @param roleUrl 角色URL
     * @return List<User> 用户信息列表，User对象只包括id，workNo，username，truename，email这些属性
     */
     List<User> getUserByRoleUrl(String roleUrl);

    /**
     *
     * <p>Description: 根据工号或者用户真实名称查询用户信息</p>
     * @param employeeName 用户名称
     * @return List<User> 用户列表信息，User对象只包括id，workNo，truename这些属性
     */
     List<User> getUserByEname(String employeeName);



    /**
     *
     * <p>Description: 根据组织id、工号或者用户真实名称查询用户信息</p>
     * @param organId 组织id
     * @param employeeName 用户名称
     * @return List<User> 用户列表信息，User对象只包括id，workNo，truename这些属性
     */
     List<User> getUserByOrganIdAndEname(String organId, String employeeName);

    /**
     *
     * <p>Description: 根据根据用户id获得用户所属组织信息</p>
     * @param userId 用户id
     */
     List<Map<String, Object>> getUserDepartById(String userId);

    /**
     *
     * <p>Description: 获取全部用户</p>
     * @return
     */
     List<Map<String, Object>> getAllUser();

}
