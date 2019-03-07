package com.swsm.linkmes.service.basic.impl;

import com.swsm.linkmes.domain.dto.RoleQueryDto;
import com.swsm.linkmes.domain.repository.ResourceRepository;
import com.swsm.linkmes.domain.repository.RoleRepository;
import com.swsm.linkmes.model.basic.Resource;
import com.swsm.linkmes.model.basic.Role;
import com.swsm.linkmes.service.basic.IRoleService;
import com.swsm.platform.model.BaseModelUtil;
import com.swsm.platform.model.PageFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author tinel
 * @Title: RoleServiceImpl
 * @ProjectName mes-sm
 * @Description: IRoleService实现类
 * @date 2018/12/1111:27
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceRepository resourceRepository;


    @Override
    public void saveOrUpdateRole(List<Role> objList, String userName) {
        for (Role role : objList) {
            if (StringUtils.isEmpty(role.getId())) {
                //新增
                role.setDelFlag(BaseModelUtil.DEL_FALSE);
                role.setCreateDate(new Date());
                role.setCreateUser(userName);
                this.roleRepository.save(role);
            } else {
                //更新
                role.setDelFlag(BaseModelUtil.DEL_FALSE);
                role.setUpdateDate(new Date());
                role.setUpdateUser(userName);
                Role r;
                r = this.roleRepository.getById(role.getId());
                role.setUserList(r.getUserList());
                role.setResList(r.getResList());
                this.roleRepository.save(role);
            }
        }
    }

    @Override
    public void deleteRole(String[] ids) {
        for (String id : ids) {
            boolean flag = this.roleRepository.checkRoleUsed(id);
            if(flag){
                throw new RuntimeException("所选记录存在已使用的角色，无法删除！");
            }
            this.roleRepository.deleteById(id);
        }
    }

    @Override
    public void updateRoleEnabled(String enabled, String[] ids) {
        for (String id : ids) {
            Role role;
            role = this.roleRepository.getById(id);
            role.setEnabled(enabled);
            this.roleRepository.update(role);
        }
    }

    @Override
    public void updateRoleResource(String roleId, String resIdStr) {
        Role role;
        role = this.roleRepository.getById( roleId);
        String[] resIdArr;
        resIdArr = StringUtils.split(resIdStr, "@");
        List<Resource> list = resourceRepository.findByProperty("delFlag","0");
        Set<Resource> resSet = new HashSet<>();
        List<String> resIdList = Arrays.asList(resIdArr);
        for(Resource resource :list){
            if(resIdList.contains(resource.getId())){
                resSet.add(resource);
            }
        }
        role.setResList(resSet);
        this.roleRepository.update(role);
    }

    @Override
    public List<Role> getRole(RoleQueryDto roleQueryDto, PageFilter pageFilter) {
        // 执行查询
        List<Role> list;
        list = this.roleRepository.getRole(roleQueryDto,pageFilter);
        return list;
    }

    @Override
    public void copyRole(String id, String roleName, String roleType, String ignoreDecesion, String remark) {
        Role oldRole;
        oldRole = this.roleRepository.getById(id);

        Role role;
        role = new Role();
        BeanUtils.copyProperties(role, oldRole);
        role.setId(null);
        Set<Resource> resList;
        resList = new HashSet<>();
        for (Resource res : oldRole.getResList()) {
            resList.add(new Resource(res.getId(), ""));
        }
        role.setResList(resList);
        role.setCreateDate(new Date());
        role.setUserList(null);
        role.setRoleName(roleName);
        role.setRoleType(roleType);
        role.setIgnoreDecesion(ignoreDecesion);
        role.setRemark(remark);
        this.roleRepository.save(role);
    }

    @Override
    public Role getRoleById(String roleId) {
        return this.roleRepository.getById(roleId);
    }

    @Override
    public List<Role> getEnableRoles() {
        return this.roleRepository.getEnableRoles();
    }

    @Override
    public List<Map<String, String>> getAllRoleNames(String roleName) {
        return this.roleRepository.getAllRoleNames(roleName);
    }

    @Override
    public boolean isExists(String jpql, Map<String, Object> paramsMap) {
        List<Role> list = this.roleRepository.checkExistByJpql(jpql,paramsMap);
        return list.isEmpty();
    }
}
