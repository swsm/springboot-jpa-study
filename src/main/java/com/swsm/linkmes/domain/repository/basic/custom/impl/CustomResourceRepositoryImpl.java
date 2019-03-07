package com.swsm.linkmes.domain.repository.basic.custom.impl;

import com.swsm.linkmes.domain.repository.basic.custom.CustomResourceRepository;
import com.swsm.linkmes.model.basic.Resource;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepositoryImpl;
import com.swsm.platform.util.converter.entity.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import java.util.*;

/**
 * @author tinel
 * @Title: CustomResourceRepositoryImpl
 * @ProjectName mes-sm
 * @Description: CustomResourceRepository实现类
 * @date 2018/12/1015:54
 */
public class CustomResourceRepositoryImpl extends BaseCustomRepositoryImpl implements CustomResourceRepository {

    @Override
    public boolean usingCache() {
        return true;
    }

    @Override
    public boolean hasAuthor(String userId, String resCode) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("loginName", userId);
        queryMap.put("resCode", resCode);
        String hql;
        hql = " select count(*) from Resource r "
                + " inner join r.roleList role inner join role.userList user "
                + " where user.username=:loginName and r.resCode=:resCode ";
        List<Object> list = super.findByJqpl(hql, queryMap,Object.class);
        if(!list.isEmpty() && Integer.parseInt(list.get(0).toString())>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Resource> getResourceTreeByPid(String parentId) {
        //执行查询
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append("select t0.PK_ID as \"id\", t0.RES_NAME as \"resName\",t0.PARENT_ID as \"parentIdStr\",")
                .append("(select count(t2.PK_ID) from sys_resource t2 ")
                .append("where t2.parent_id = t0.PK_ID and t2.enabled = '1' and t2.del_flag = '0') as \"childCount\", ")
                .append("t0.RES_CODE as \"resCode\", t0.RES_TYPE as \"resType\" ")
                .append("from SYS_RESOURCE t0 ")
                .append("where t0.del_flag ='0' and t0.enabled ='1' ");
        sb.append("order by t0.res_order, t0.create_date ");
        return super.findBySql(sb.toString(), Collections.emptyMap(),Resource.class);
    }

    @Override
    public List<String> getResIdsByRoleId(String roleId) {
        StringBuilder roleBuf;
        roleBuf = new StringBuilder();
        roleBuf.append("select t.res_id as res_id from SYS_ROLE_RESOURCE t where t.role_id='")
                .append(roleId).append("'");
        List<Map> list = super.findMapsBySql(roleBuf.toString(),Collections.emptyMap());
        List<String> resIds = new LinkedList<String>();
        for(Map<String,Object> map : list){
            resIds.add(map.get("res_id".toUpperCase())==null?null:map.get("res_id".toUpperCase()).toString());
        }
        return resIds;
    }

    @Override
    public List<Resource> getResourceTreeByResname(String resName) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        String sql;
        sql = "from Resource where delFlag='0' and enabled ='1' and resType in ('1','2') ";
        if (StringUtils.isNotEmpty(resName)) {
            queryMap.put("resName", resName);
            sql += " and resName like :resName";
        }
        return super.findByJqpl(sql,queryMap,Resource.class);
    }

    @Override
    public Query getResourceTreeForExport(String roleId) {
        //todo
        return null;
    }

    @Override
    public List<Resource> getResourcesByParentId(String loginName, String parentId) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("loginName", loginName);
        String hql;
        hql = " select distinct r from Resource r inner join fetch r.roleList role"
                + " inner join role.userList user  where user.username=:loginName ";
        //2016-10-16：添加用户的删除标识为0
        hql += " and user.delFlag = '0' and r.belongSystem in ('1','3') ";
        if (parentId == null) {
            hql += " and r.parentResource.id is null and r.resType in ('1','2') and r.enabled='1' ";
        } else {
            queryMap.put("parentId", parentId);
            hql += " and r.parentResource.id=:parentId and r.resType in ('1','2') and r.enabled='1' ";
        }
        hql += "order by r.resOrder";
        return super.findByJqpl(hql,queryMap,Resource.class);
    }

    @Override
    public List<Resource> getChildResource(String parentId) {
        Map<String, Object> map;
        map = new HashMap<>();
        String hql;
        if (parentId == null) {
            hql = "from Resource r where r.parentResource.id is null and r.resType in ('1','2') "
                    + "and r.enabled='1' and r.belongSystem in ('1','3') order by r.resOrder asc ";
        } else {
            hql = "from Resource r where r.parentResource.id = :parentId and r.resType in ('1','2')"
                    + " and r.enabled='1' and r.belongSystem in ('1','3') order by r.resOrder asc ";
            map.put("parentId", parentId);
        }
        return super.findByJqpl(hql,map,Resource.class);
    }

    @Override
    public int getChildCount(String parentId) {
        String hql;
        hql = "from Resource where parentResource.id = :parentId and r.resType in ('1','2') ";
        Map<String, Object> map;
        map = new HashMap<String, Object>();
        map.put("parentId", parentId);
        List<Resource> list = super.findByJqpl(hql,map,Resource.class);
        return list.size();
    }

    @Override
    public List<Resource> getHavResByLoginName(String loginName) {
        if (loginName == null) {
            return super.findByJqpl("from Resource order by resOrder", Collections.emptyMap(),Resource.class);
        } else {
            Map<String, Object> map;
            map = new HashMap<String, Object>();
            map.put("loginName", loginName);
            return super.findByJqpl("select r from Resource r inner join fetch r.roleList role " +
                    "inner join role.userList user where user.username=:loginName order by r.resOrder", map,Resource.class);
        }
    }

    @Override
    public List<Map<String, Object>> getAllResourceTree(String workNo) {
        StringBuilder sb = new StringBuilder();
        sb.append("select DISTINCT 	sr.PK_ID AS \"id\",sr.RES_NAME AS \"text\",sr.PARENT_ID AS \"parentIdStr\",")
                .append("sr.RES_CODE AS \"resCode\",sr.RES_TYPE AS \"resType\",sr.res_Order from SYS_RESOURCE sr ")
                .append("left join SYS_ROLE_RESOURCE rr on RR.RES_ID=SR.PK_ID ")
                .append("left join SYS_USER_ROLE r on r.ROLE_ID=rr.ROLE_ID  ")
                .append("left join SYS_USER su on su.PK_ID=r.USER_ID ")
                .append("where SR.RES_TYPE!=3 and  (su.work_no=:workNo or '04010'=:workNo)");
        sb.append("order BY  sr.parent_id desc,sr.res_Order ");
        Map<String, Object> map;
        map = new HashMap<>();
        map.put("workNo", workNo);
        List<Map> list = super.findMapsBySql(sb.toString(), map);
        return ConvertUtil.toKVMapList(list,String.class,Object.class);
    }

    @Override
    public List<Resource> getResource(Map<String, Object> map) {
        StringBuilder hql = new StringBuilder();
        hql.append("from Resource r where r.delFlag = '0' and r.enabled = '1' ");
        if(checkValid("resName", map)){
            hql.append("and r.resName like :resName ");
            map.put("resName","%"+map.get("resName")+"%");
        }
        if(checkValid("resCode", map)){
            hql.append("and r.resCode like :resCode ");
            map.put("resCode","%"+map.get("resCode")+"%");
        }
        if(checkValid("resType", map)){
            hql.append("and r.resType = :resType ");
        }
        hql.append(" order by r.resOrder");
        List<Resource> list = super.findByJqpl(hql.toString(), map,Resource.class);
        return list;
    }

    @Override
    public List<Resource> getExactRource(Map<String, Object> map) {
        StringBuilder hql = new StringBuilder();
        hql.append("from Resource r where r.delFlag = '0' and r.enabled = '1' ");
        if(checkValid("resName", map)){
            hql.append("and r.resName = :resName ");
        }
        if(checkValid("resCode", map)){
            hql.append("and r.resCode = :resCode ");
        }
        hql.append(" order by r.resOrder");
        List<Resource> list = super.findByJqpl(hql.toString(), map,Resource.class);
        return list;
    }

    @Override
    public List<Map<String, Object>> getResourceTable(String tableName) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> queryMap = new HashMap<>();
        sql.append("select r.pk_id as\"id\",r.res_name as \"resName\",rt.table_name as \"tableName\" ");
        sql.append(" from sys_resource r,sys_resource_table rt where r.pk_id = rt.resource_id");
        if(StringUtils.isNotBlank(tableName)){
            sql.append(" and rt.table_name like :tableName");
            queryMap.put("tableName", "%"+tableName+"%");
        }
        List<Map> list = this.findMapsBySql(sql.toString(), queryMap);
        return ConvertUtil.toKVMapList(list,String.class,Object.class);
    }

    @Override
    public List<Map<String, Object>> getTableStructure(String tableName) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> queryMap = new HashMap<>();
        sql.append("select tc.column_name as \"columnName\",cc.comments as \"comments\",");
        sql.append("tc.data_type as \"dataType\",tc.data_length as \"dataLength\",tc.nullable as \"nullable\",");
        sql.append("(select count(*) from user_cons_columns u ,user_constraints c where u.constraint_name = c.constraint_name ");
        sql.append("and c.constraint_type = 'P' and u.table_name = tc.table_name and u.column_name = tc.column_name) as \"primaryKey\" ");
        sql.append("from user_tab_columns tc ,user_col_comments cc where tc.table_name = cc.table_name ");
        sql.append("and tc.column_name = cc.column_name ");
        if(StringUtils.isNotBlank(tableName)){
            sql.append(" and tc.table_name = :tableName");
            queryMap.put("tableName", tableName);
        }
        List<Map> list = this.findMapsBySql(sql.toString(), queryMap);
        return ConvertUtil.toKVMapList(list,String.class,Object.class);
    }

    @Override
    public List<Map<String, Object>> getTableComments(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t.table_name as \"tableName\",t.comments as \"comments\" ");
        sql.append(" from user_tab_comments t where t.table_name =:tableName ");
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("tableName", tableName);
        List<Map> list = this.findMapsBySql(sql.toString(),queryMap);
        return ConvertUtil.toKVMapList(list,String.class,Object.class);
    }
}
