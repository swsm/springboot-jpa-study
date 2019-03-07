package com.swsm.linkmes.domain.repository.basic.custom.impl;

import com.swsm.linkmes.domain.dto.RoleQueryDto;
import com.swsm.linkmes.domain.repository.basic.custom.CustomRoleRepository;
import com.swsm.linkmes.model.basic.Role;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepositoryImpl;
import com.swsm.platform.model.PageFilter;
import com.swsm.platform.util.converter.entity.ConvertUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: CustomRoleRepositoryImpl
 * @ProjectName mes-sm
 * @Description: CustomRoleRepository实现类
 * @date 2018/12/1015:53
 */
public class CustomRoleRepositoryImpl extends BaseCustomRepositoryImpl implements CustomRoleRepository {

    @Override
    public List<Role> getRole(RoleQueryDto roleQueryDto, PageFilter pageFilter) {
        String countHql;
        countHql = "select count(*) from Role ";
        String recordHql;
        recordHql = "from Role ";
        String conditionString;
        conditionString = " where delFlag='0' ";
        Map<String,Object> queryMap = new HashMap<>();
        if (!StringUtils.isEmpty(roleQueryDto.getRoleName())) {
            conditionString += " and roleName like :roleName";
            queryMap.put("roleName","%"+roleQueryDto.getRoleName()+"%");
        }
        if (!StringUtils.isEmpty(roleQueryDto.getRoleType())) {
            conditionString += " and roleType = :roleType";
            queryMap.put("roleType",roleQueryDto.getRoleType());
        }
        if (!StringUtils.isEmpty(roleQueryDto.getIgnoreDecesion())) {
            conditionString += " and ignoreDecesion like :ignoreDecesion";
            queryMap.put("ignoreDecesion","%"+roleQueryDto.getIgnoreDecesion()+"%");
        }
        if (!StringUtils.isEmpty(roleQueryDto.getRemark())) {
            conditionString += " and remark like :remark";
            queryMap.put("remark","%"+roleQueryDto.getRemark()+"%");
        }
        if (roleQueryDto.getCreateDateStart()!=null) {
            conditionString += " and createDate >= :createDate_start";
            queryMap.put("createDate_start",roleQueryDto.getCreateDateStart());
        }
        if (roleQueryDto.getCreateDateEnd()!=null) {
            conditionString += " and createDate <= :createDate_end";
            queryMap.put("createDate_end",roleQueryDto.getCreateDateEnd());
        }
        return super.findPageByJqpl(recordHql + conditionString ,countHql + conditionString,queryMap, pageFilter,Role.class);
    }

    @Override
    public List<Role> getEnableRoles() {
        String hql;
        hql = "from Role  where delFlag='0' and enabled = '1'";
        return super.findByJqpl(hql, Collections.emptyMap(),Role.class);
    }

    @Override
    public List<Map<String, String>> getAllRoleNames(String roleName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t.role_name as \"roleName\" from sys_role t where t.del_flag = '0' ");
        if(StringUtils.isNotBlank(roleName)){
            sql.append("and t.role_name like '%").append(roleName).append("%'");
        }
        List<Map> list = super.findMapsBySql(sql.toString(),Collections.emptyMap());
        return ConvertUtil.toKVMapList(list,String.class,String.class);
    }

    @Override
    public boolean checkRoleUsed(String roleId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from sys_user_role ur,sys_user u where ur.user_id = u.pk_id");
        sql.append(" and u.del_flag = '0' and ur.role_id =:roleId");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("roleId",roleId);
        List<Map> list = super.findMapsBySql(sql.toString(),paramMap);
        if(list.isEmpty()){
            return false;
        }
        return true;
    }
}
