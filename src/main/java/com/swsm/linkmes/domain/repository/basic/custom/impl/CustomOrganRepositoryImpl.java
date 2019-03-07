package com.swsm.linkmes.domain.repository.basic.custom.impl;

import com.swsm.linkmes.domain.dto.OrganDto;
import com.swsm.linkmes.domain.repository.basic.custom.CustomOrganRepository;
import com.swsm.linkmes.model.basic.Organ;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepositoryImpl;
import com.swsm.platform.util.converter.entity.ConvertUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: CustomOrganRepositoryImpl
 * @ProjectName mes-sm
 * @Description: CustomOrganRepository的实现类
 * @date 2018/12/1015:55
 */
public class CustomOrganRepositoryImpl extends BaseCustomRepositoryImpl implements CustomOrganRepository {

    public boolean userCache() {
        return true;
    }

    @Override
    public List<Organ> findOrganByPanrentId(String parentId) {
        Map<String, Object> map;
        map = new HashMap<>();
        String whr = null;
        if(parentId ==  null){
            whr = " and o.parentOrgan.id is null \n";
        }else{
            whr = " and o.parentOrgan.id=:parentId \n";
            map.put("parentId", parentId);
        }
        String hql = " from Organ o where o.delFlag='0' \n "+whr+" order by o.organOrder asc";
        return super.findByJqpl(hql, map,Organ.class);
    }

    @Override
    public List<Organ> findOrgan(String organCode) {
        return super.findByJqpl("from Organ o where o.delFlag = '0' and o.organCode ='"
                + organCode + "'", Collections.emptyMap(),Organ.class);
    }

    @Override
    public boolean isExistsOrgan(String organId, String organCode) {
        List<Organ> list = super.findByJqpl("from Organ o where o.delFlag='0' and o.id != '"
                + organId+ "' and o.organCode ='" + organCode + "'",Collections.emptyMap(),Organ.class);
        if(list.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public List<OrganDto> getAllOrgan() {
        StringBuilder sql = new StringBuilder();
        sql.append("select t.pk_id as \"id\",t.ldap_organ_id as \"ldapOrganId\",t.organ_name as \"organName\",");
        sql.append("t.organ_code as \"organCode\",t.duty_username as \"dutyUsername\",");
        sql.append("t.parent_id as \"parentId\" from sys_organ t where t.del_flag = '0'");
        List<OrganDto> list = super.findBySql(sql.toString(),Collections.emptyMap(),OrganDto.class);
        return list;
    }

    @Override
    public List<Map<String, String>> getAllOrganNames(String organName) {
        StringBuilder hql = new StringBuilder();
        hql.append("select o.organName as organName from Organ o where o.delFlag = '0' ");
        Map<String, Object> queryMap = new HashMap<>();
        if(StringUtils.isNotBlank(organName)){
            hql.append(" and o.organName like :organName");
            queryMap.put("organName", "%"+organName+"%");
        }
        List<Map> list = super.findMapsBySql(hql.toString(), queryMap);
        return ConvertUtil.toKVMapList(list,String.class,String.class);
    }

    @Override
    public boolean checkOrganUsed(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from sys_user_organ uo,sys_user u where uo.user_id = u.pk_id");
        sql.append(" and u.del_flag = '0' and uo.organ_id =:organId");
        Map<String,Object> map = new HashMap<>();
        map.put("organId",id);
        List list = super.findMapsBySql(sql.toString(),map);
        if(list.isEmpty()){
            return false;
        }
        return true;
    }
}
