package com.swsm.linkmes.domain.repository.basic.custom;


import com.swsm.linkmes.domain.dto.RoleQueryDto;
import com.swsm.linkmes.model.basic.Role;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepository;
import com.swsm.platform.model.PageFilter;

import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: CustomRoleRepository
 * @ProjectName mes-sm
 * @Description: 系统管理中的角色业务数据访问层
 * @date 2018/12/1014:11
 */
 public interface CustomRoleRepository extends BaseCustomRepository {

    /**
     * <p>Description: 查询角色信息</p>
     * @param roleQueryDto
     * @param pageFilter
     * @return List<Role> 角色列表
     */
     List<Role> getRole(RoleQueryDto roleQueryDto, PageFilter pageFilter);
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
     * 校验角色是否被使用
     * @param roleId 角色id
     * @return 是否使用 true：已使用  false：未使用
     */
     boolean checkRoleUsed(String roleId);

}
