package com.swsm.linkmes.controller.basic;

import com.alibaba.fastjson.JSON;
import com.swsm.linkmes.domain.dto.UserQueryDto;
import com.swsm.linkmes.model.basic.Organ;
import com.swsm.linkmes.model.basic.Role;
import com.swsm.linkmes.model.basic.User;
import com.swsm.linkmes.service.basic.IRoleService;
import com.swsm.linkmes.service.basic.IUserService;
import com.swsm.linkmes.vo.basic.RoleVo;
import com.swsm.linkmes.vo.basic.UserQueryVo;
import com.swsm.linkmes.vo.basic.UserVo;
import com.swsm.platform.action.CommonJsonVo;
import com.swsm.platform.action.MultiResultResponse;
import com.swsm.platform.action.UniqueResultResponse;
import com.swsm.platform.model.PageFilter;
import com.swsm.platform.util.MD5Util;
import com.swsm.platform.util.converter.entity.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author tinel
 * @Title: UserAction
 * @ProjectName mes-sm
 * @Description: UserApi实现类
 * @date 2018/12/129:36
 */
@RestController
public class UserAction {

    private static Logger logger = LoggerFactory.getLogger(UserAction.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    
    @GetMapping("/basic/user/get/{start}/{limit}")
    public MultiResultResponse<UserVo> getUser(@ModelAttribute UserQueryVo userQueryVo,
                                               @PathVariable("start") int start, @PathVariable("limit")int limit) {
        PageFilter pageFilter = PageFilter.of(start,limit);
        UserQueryDto userQueryDto = new UserQueryDto();
        BeanUtils.copyProperties(userQueryVo,userQueryDto);
        List<User> list = userService.queryPagedEntityListForUser(userQueryDto,pageFilter);
        List<UserVo> results = ConvertUtil.toNList(list,User.class,UserVo.class);
        return new MultiResultResponse(results,pageFilter.getTotal());
    }


    
    @PostMapping("/basic/user/disable/{ids}/{userName}")
    public boolean disableUser(@PathVariable("ids") String ids,
                               @PathVariable("userName") String userName) {
        this.userService.disableUser(ids,userName);
        return true;
    }


    
    @PostMapping("/basic/user/enable/{ids}/{userName}")
    public boolean enableUser(@PathVariable("ids") String ids,
                              @PathVariable("userName") String userName) {
        this.userService.enableUser(ids,userName);
        return true;
    }

    
    @PostMapping("/basic/user/delete/{ids}/{userName}")
    public boolean deleteUser(@PathVariable("ids") String ids,
                              @PathVariable("userName") String userName) {
        this.userService.deleteUser(ids,userName);
        return true;
    }

    
    @GetMapping("/basic/user/enableroles")
    public List<RoleVo> getEnableRoles() {
        List<Role> list = roleService.getEnableRoles();
        return ConvertUtil.toNList(list,Role.class,RoleVo.class);
    }

    
    @PostMapping("/basic/user/save")
    public UniqueResultResponse<String> saveUser(@ModelAttribute CommonJsonVo commonJsonVo) {
        User user = JSON.parseObject(commonJsonVo.getInsertJson(),User.class);
        String password = user.getPassword();
        user.setPassword(MD5Util.getDigest(password));
        List<String> roleIds = new ArrayList<>();
        for (String s : user.getRoleNameStr().split(",")) {
            roleIds.add(s);
        }
        user.setRoleId(roleIds.toArray(new String[0]));
        this.loadUserInfo(user);
        String result = this.userService.saveUser(user,commonJsonVo.getUserDisplayName());
        return new UniqueResultResponse(result,result);
    }

    
    @PostMapping("/basic/user/update")
    public UniqueResultResponse<String> updateUser(@ModelAttribute CommonJsonVo commonJsonVo) {
        User user = JSON.parseObject(commonJsonVo.getInsertJson(),User.class);
        this.loadUserInfo(user);
        String result = this.userService.updateUser(user,commonJsonVo.getUserDisplayName());
        return new UniqueResultResponse(result,result);
    }

    
    @PostMapping("/basic/user/picture")
    public void uploadPicture(@ModelAttribute UserVo userVo) {
        User user = this.userService.getUserById(userVo.getId());
        user.setPictureUrl(userVo.getPictureUrl());
        this.userService.uploadPicture(user,userVo.getUsername());
    }

    
    @PostMapping("/basic/user/password/update/{userName}/{oldPassword}/{password}")
    public UniqueResultResponse<String> updatePassword(@PathVariable("userName") String userName,
                                                       @PathVariable("oldPassword") String oldPassword,
                                                       @PathVariable("password") String password) {
        String result = this.userService.updatePassword(password,oldPassword,userName);
       return new UniqueResultResponse(result,result);
    }

    
    @GetMapping("/basic/user/{organId}/{employeeName}")
    public List<UserVo> getUserByOrganIdAndEname(@PathVariable("organId") String organId, @PathVariable("employeeName") String employeeName) {
        List<User> list = this.userService.getUserByOrganIdAndEname(organId,employeeName);
        return ConvertUtil.toNList(list,User.class,UserVo.class);
    }

    
    @GetMapping("/basic/user/getUserByCode/{code}")
    public List<Map<String, String>> getUserByCode(@PathVariable("code") String code) {
        return this.userService.getUserByCode(code);
    }

    
    @GetMapping("/basic/user/getAllUsers")
    public List<UserVo> getAllUsers() {
        List<User> list = this.userService.getUserAll();
        return ConvertUtil.toNList(list,User.class,UserVo.class);
    }

    private void loadUserInfo(User user){
        this.loadUserOrgans(user);
        this.loadUserRoles(user);
    }

    private void loadUserOrgans(User user){
        Set<Organ> organSet;
        organSet = new HashSet<>();
        organSet.add(new Organ(user.getOrganId()));
        user.setOrganList(organSet);
    }

    private void loadUserRoles(User user){
        Set<Role> roleSet;
        roleSet = new HashSet<>();
        for (String s : user.getRoleId()) {
            roleSet.add(new Role(s));
        }
        user.setRoleList(roleSet);
    }

}
