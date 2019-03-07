package com.swsm.linkmes.service.basic;



import com.swsm.linkmes.domain.dto.RoleQueryDto;
import com.swsm.linkmes.model.basic.Role;
import com.swsm.platform.model.PageFilter;

import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: IRoleService</p>
 * <p>Description: 角色业务服务接口，提供有关角色操作的所有业务服务</p>
 * <p>Author: yanshisheng</p>
 * <p>Date: 2016-5-27</p>
 */
 public interface IRoleService {

    /**
     * <p>Description: 新增或修改角色，根据roleId判断为新增或者修改，roleId==null表示信息，roleId！=null表示修改</p>
     * @param objList 需要操作的角色对象列表
     * @param userName 登录人用户名
     */
     void saveOrUpdateRole(List<Role> objList, String userName);

    /**
     * <p>Description: 删除角色信息</p>
     * @param ids 角色id数组
     */
     void deleteRole(String[] ids);

    /**
     * <p>Description: 启用或禁用角色</p>
     * @param enabled 状态
     * @param ids 角色id数组
     */
     void updateRoleEnabled(String enabled, String[] ids);

    /**
     * <p>
     * Description: 更改资源与角色的对应关系
     * </p>
     * @param roleId 角色id
     * @param resIdStr 资源id字符串
     */
     void updateRoleResource(String roleId, String resIdStr);

    /**
     * <p>Description: 查询角色信息，每个查询条件在roleQueryDto中</p>
     * @param roleQueryDto 过滤器
     * @param pageFilter
     * @return List<Role> 角色列表
     */
     List<Role> getRole(RoleQueryDto roleQueryDto, PageFilter pageFilter) ;

    /**
     * 
     * <p>Description: 复制角色</p>
     * @param id 被复制的角色id
     * @param roleName 角色名
     * @param roleType 角色类别
     * @param ignoreDecesion 忽略决策
     * @param remark 备注
     */
     void copyRole(String id, String roleName, String roleType, String ignoreDecesion, String remark);

    /**
     * 
     * <p>
     * Description: 根据id获得角色信息
     * </p>
     * @param roleId 角色id
     * @return 角色对象
     */
     Role getRoleById(String roleId);
    
    /**
     * 
     * <p>Description: 获取启用的角色信息</p>
     * @return role列表，Role对象只包括id与roleName
     */
     List<Role> getEnableRoles();

    /**
     * 
     * <p>Description: 根据角色名称模糊查询符合条件的角色名称</p>
     * @param roleName 角色名称
     * @return list的map中只有角色名称字段
     */
     List<Map<String, String>> getAllRoleNames(String roleName);


    /**
     * 根据JPQL、参数查询是否存在对象信息，该方法主要用于唯一性验证
     * @param jpql
     * @param paramsMap
     * @return
     */
    boolean isExists(String jpql, Map<String, Object> paramsMap);
    

}
