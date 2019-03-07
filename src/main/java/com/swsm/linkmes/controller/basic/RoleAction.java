package com.swsm.linkmes.controller.basic;

import com.swsm.linkmes.domain.dto.RoleQueryDto;
import com.swsm.linkmes.model.basic.Role;
import com.swsm.linkmes.service.basic.IRoleService;
import com.swsm.linkmes.vo.basic.RoleQueryVo;
import com.swsm.linkmes.vo.basic.RoleResourceVo;
import com.swsm.linkmes.vo.basic.RoleVo;
import com.swsm.platform.action.BaseAction;
import com.swsm.platform.action.CommonJsonVo;
import com.swsm.platform.action.MultiResultResponse;
import com.swsm.platform.model.PageFilter;
import com.swsm.platform.util.converter.entity.ConvertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: RoleAction
 * @ProjectName mes-sm
 * @Description: RoleApi实现类
 * @date 2018/12/1213:19
 */
@RestController
public class RoleAction extends BaseAction {

    @Autowired
    private IRoleService roleService;

    
    @GetMapping("/basic/role/get/{start}/{limit}")
    public MultiResultResponse<RoleVo> getRole(@ModelAttribute RoleQueryVo roleQueryVo,
                                               @PathVariable("start") int start, @PathVariable("limit") int limit) {
        PageFilter pageFilter = PageFilter.of(start,limit);
        RoleQueryDto roleQueryDto = new RoleQueryDto();
        BeanUtils.copyProperties(roleQueryVo,roleQueryDto);
        List<Role> roleList = this.roleService.getRole(roleQueryDto,pageFilter);
        List<RoleVo> resultList = ConvertUtil.toNList(roleList,Role.class,RoleVo.class);
        return new MultiResultResponse(resultList,pageFilter.getTotal());
    }

    
    @PostMapping("/basic/role/save")
    public boolean saveOrUpdateRole(@ModelAttribute CommonJsonVo commonJsonVo) {
        List<Role> list = super.parsorList(commonJsonVo,Role.class);
        this.roleService.saveOrUpdateRole(list,commonJsonVo.getUserDisplayName());
        return true;
    }

    
    @DeleteMapping("/basic/role/delete/{ids}/{userName}")
    public boolean deleteRole(@PathVariable("ids") String ids, @PathVariable("userName") String userName) {
        this.roleService.deleteRole(ids.split(","));
        return true;
    }

    
    @PostMapping("/basic/role/status/update/{ids}/{enabled}/{userName}")
    public boolean updateRoleEnable(@PathVariable("ids") String ids, @PathVariable("enabled") String enabled,
                                    @PathVariable("userName") String userName) {
        this.roleService.updateRoleEnabled(enabled,ids.split(","));
        return true;
    }

    
    @PostMapping("/basic/role/resource/update")
    public boolean updateRoleResource(@ModelAttribute RoleResourceVo roleResourceVo) {
        this.roleService.updateRoleResource(roleResourceVo.getRoleId(),roleResourceVo.getResourceIds());
        return true;
    }

    
    @GetMapping("/basic/role/unique/{uniqueFields}")
    public boolean checkRoleUnique(@ModelAttribute CommonJsonVo commonJsonVo, @PathVariable("uniqueFields") String uniqueFields) {

        return super.queryCheckUnique(uniqueFields.split(","),commonJsonVo,Role.class);
    }

    
    @PostMapping("/basic/role/copy")
    public void copyRole(@ModelAttribute RoleVo roleVo) {
        this.roleService.copyRole(roleVo.getId(),roleVo.getRoleName(),
                roleVo.getRoleType(),roleVo.getIgnoreDecesion(),roleVo.getRemark());
    }

    
    @GetMapping("/basic/role/combox/roles")
    public List<Map<String, String>> getAllRoleNames(@ModelAttribute RoleVo roleVo) {
        return this.roleService.getAllRoleNames(roleVo.getRoleName());
    }

    
    protected boolean isNotFindEntity(String jpql, Map<String, Object> queryMap) {
        return this.roleService.isExists(jpql,queryMap);
    }

    
    @GetMapping("/basic/role/{roleId}")
    public RoleVo getRoleById(@PathVariable("roleId") String roleId) {
        Role role = this.roleService.getRoleById(roleId);
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role,roleVo);
        return roleVo;
    }
}
