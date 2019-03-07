package com.swsm.linkmes.service.basic.impl;

import com.swsm.linkmes.domain.dto.UserQueryDto;
import com.swsm.linkmes.domain.repository.OrganRepository;
import com.swsm.linkmes.domain.repository.RoleRepository;
import com.swsm.linkmes.domain.repository.UserRepository;
import com.swsm.linkmes.model.basic.Organ;
import com.swsm.linkmes.model.basic.Role;
import com.swsm.linkmes.model.basic.User;
import com.swsm.linkmes.service.basic.IUserService;
import com.swsm.platform.model.BaseModelUtil;
import com.swsm.platform.model.PageFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: UserServiceImpl
 * @ProjectName mes-sm
 * @Description: IUserService接口实现类
 * @date 2018/12/1111:23
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrganRepository organRepository;

    @Override
    public List<User> queryPagedEntityListForUser(UserQueryDto userQueryDto, PageFilter pageFilter) {
        //查询并返回
        List<User> userList;
        userList = this.userRepository.queryPagedEntityListForUser(userQueryDto,pageFilter);
        //处理用户角色 显示问题  (不因角色的名称或者其它可更改字段的改动而出现显示问题)
        List<User> hqlUser;
        hqlUser = this.userRepository.findByProperty("delFlag","0");
        for (int i = 0; i < userList.size(); i++) {
            for (User uRole : hqlUser) {
                if (userList.get(i).getId().equals(uRole.getId())) {
                    String roleNameStr = "";
                    String roleId = "";
                    for (Role role : uRole.getRoleList()) {
                        if(roleNameStr.length() > 0){
                            roleNameStr += "," + role.getRoleName();
                        }else{
                            roleNameStr = role.getRoleName();
                        }
                        if(roleId.length() > 0){
                            roleId += "," + role.getId();
                        }else{
                            roleId = role.getId();
                        }
                    }
                    StringBuilder authorityNameStr = new StringBuilder();
                    StringBuilder authorityId = new StringBuilder();
                    User user = userList.get(i);
                    user.setRoleNameStr(roleNameStr);
                    user.setRoleId(roleId.split(","));
                    user.setAuthorityNameStr(authorityNameStr.toString());
                    user.setAuthorityId(authorityId.toString().split(","));
                }
            }
        }
        return userList;
    }

    @Override
    public String updatePassword(String newPassword, String oldPassword, String username) {
        String flag;
        flag = this.userRepository.updatePassword(newPassword, oldPassword, username);
        return flag;
    }

    @Override
    public void disableUser(String ids, String username) {
        for (String id : ids.split(",")) {
            User u;
            u = this.userRepository.getById(id);
            //0 禁用 1 启用
            u.setEnabled(IUserService.DESIBLED);
            u.setUpdateDate(new Date());
            u.setUpdateUser(username);
            this.userRepository.update(u);
        }
    }

    @Override
    public void enableUser(String ids, String username) {
        for (String id : ids.split(",")) {
            User u;
            u = this.userRepository.getById(id);
            u.setEnabled(IUserService.ENABLED);
            u.setUpdateDate(new Date());
            u.setUpdateUser(username);
            this.userRepository.update(u);
        }
    }

    @Override
    public void deleteUser(String ids, String username) {
        for (String id : ids.split(",")) {
            User u;
            u = this.userRepository.getById(id);
            u.setDelFlag(BaseModelUtil.DEL_TRUE);
            u.setUpdateDate(new Date());
            u.setUpdateUser(username);
            this.cleanUser(u);
            this.userRepository.update(u);
        }
    }

    private void cleanUser(User user){
        user.setRoleId(new String[0]);
        user.setAuthorityId(new String[0]);
        user.setOrganList(Collections.emptySet());
        user.setRoleList(Collections.emptySet());
    }

    @Override
    public String saveUser(User user, String username) {
        //验证员工工号的唯一性
        List<User> userWorkList;
        userWorkList = this.userRepository.checkWorkNo(user,true);
        if (!userWorkList.isEmpty()) {
            return "workNoExist";
        }
        //验证员工用户名的唯一性
        List<User> userNameList;
        userNameList = this.userRepository.checkUserName(user,true);
        if (!userNameList.isEmpty()) {
            return "usernameExist";
        }

        user.setDelFlag(BaseModelUtil.DEL_FALSE);
        user.setCreateDate(new Date());
        user.setCreateUser(username);
        String roleName = "";
        for (Role r : user.getRoleList()) {
            roleName += this.roleRepository.getById(r.getId()).getRoleName();
            roleName += ", ";
        }
        roleName = roleName.substring(0, roleName.length() - 2);
        user.setRoleNameStr(roleName);
        this.userRepository.save(user);
        return "success";
    }

    @Override
    public String updateUser(User user, String username) {
        //验证员工工号的唯一性
        List<User> validateUserList;
        validateUserList = this.userRepository.checkWorkNo(user,false);
        if (!validateUserList.isEmpty()) {
            return "workNoExist";
        }
        //验证员工用户名的唯一性
        validateUserList = this.userRepository.checkUserName(user,false);
        if (!validateUserList.isEmpty()) {
            return "usernameExist";
        }

        user.setDelFlag(BaseModelUtil.DEL_FALSE);
        user.setUpdateDate(new Date());
        user.setUpdateUser(username);
        String roleName = "";
        for (Role r : user.getRoleList()) {
            Role role = this.roleRepository.getById(r.getId());
            BeanUtils.copyProperties(role,r);
            roleName += r.getRoleName();
            roleName += ", ";
        }
        roleName = roleName.substring(0, roleName.length() - 2);
        user.setRoleNameStr(roleName);
        for(Organ organ : user.getOrganList()){
            Organ o = this.organRepository.getById(organ.getId()) ;
            BeanUtils.copyProperties(o,organ);
        }
        this.userRepository.update(user);
        return "success";
    }

    @Override
    public List<User> getUserAll() {
        return this.userRepository.findUserAll();
    }

    @Override
    public List<User> getUserByEname(String employeeName) {
        List<User> list = this.userRepository.getUserByEname(employeeName);
        return list;
    }

    @Override
    public User getUserById(String userId) {
        return this.userRepository.getById(userId);
    }


    @Override
    public List<User> getUserByOrganIdAndEname(String organId, String employeeName) {
        List<User> list = this.userRepository.getUserByOrganIdAndEname(organId,employeeName);
        return list;
    }

    @Override
    public void uploadPicture(User user,String userName) {
        BaseModelUtil.setDefaultFieldBaseModel(user,userName,false);
        this.userRepository.update(user);
    }

    @Override
    public List<Map<String, String>> getUserByCode(String code) {
        return this.userRepository.getUserByCode(code);
    }
}
