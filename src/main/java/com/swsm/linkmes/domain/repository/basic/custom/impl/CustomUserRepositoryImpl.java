package com.swsm.linkmes.domain.repository.basic.custom.impl;

import com.swsm.linkmes.domain.dto.UserQueryDto;
import com.swsm.linkmes.domain.repository.basic.custom.CustomUserRepository;
import com.swsm.linkmes.model.basic.User;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepositoryImpl;
import com.swsm.platform.model.Condition;
import com.swsm.platform.model.PageFilter;
import com.swsm.platform.model.Query;
import com.swsm.platform.util.converter.entity.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: CustomUserRepositoryImpl
 * @ProjectName mes-sm
 * @Description: CustomUserRepository接口实现
 * @date 2018/12/1014:48
 */
public class CustomUserRepositoryImpl extends BaseCustomRepositoryImpl implements CustomUserRepository {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CustomUserRepositoryImpl.class);

    @Override
    public User getUserByUserName(String userName) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("userName", userName);
        StringBuilder hql;
        hql = new StringBuilder("from User u");
        hql.append(" where u.username = :userName and u.delFlag = '0'");
        List<User> list;
        list = super.findByJqpl(hql.toString(), queryMap,User.class);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public User getUserByWorkNo(String workNo) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("workNo", workNo);
        StringBuilder hql;
        hql = new StringBuilder("from User u");
        hql.append(" where u.workNo = :workNo and u.delFlag = '0'");
        List<User> list;
        list = super.findByJqpl(hql.toString(), queryMap,User.class);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<User> queryPagedEntityListForUser(UserQueryDto userQueryDto, PageFilter pageFilter) {
        StringBuilder sql;
        sql = new StringBuilder("select distinct u.PK_ID as \"id\", u.work_no as \"workNo\", ");
        sql.append(" u.username as \"username\",u.password as \"password\", u.ldap_user_id as \"ldapUserId\", ");
        sql.append(" u.truename as \"truename\", u.mobile as \"mobile\", ");
        sql.append(" u.telephone as \"telephone\", u.email as \"email\",u.picture_url as \"pictureUrl\",");
        sql.append(" u.address as \"address\", u.remark as \"remark\", u.WECHAT_NAME as \"wechatName\",");
        sql.append(" u.DEL_FLAG as \"delFlag\", u.enabled as \"enabled\",organ.pk_id as \"organId\",");
        sql.append(" u.role_name_str as \"roleNameStr\",organ.organ_name as \"organName\",");
        sql.append(" u.CREATE_USER as \"createUser\", u.UPDATE_DATE as \"updateDate\", ");
        sql.append(" u.CREATE_DATE as \"createDate\", u.UPDATE_USER as \"updateUser\" ");
        sql.append(" from sys_user u");
        sql.append(" left join sys_user_organ userOrgan on u.pk_id = userOrgan.user_id");
        sql.append(" left join sys_organ organ on userOrgan.organ_id = organ.PK_ID ");
        sql.append(" left join sys_user_role ur on u.pk_id = ur.user_id ");
        sql.append(" left join sys_role r on ur.role_id = r.pk_id ");
        sql.append(" where u.DEL_FLAG = '0'");
        Map<String,Object> queryMap = new HashMap<>();
        if (!StringUtils.isEmpty(userQueryDto.getUsername())) {
            sql.append(" AND u.username like :username ");
            queryMap.put("username",userQueryDto.getUsername());
            this.addLikeChar("username",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getTruename())) {
            sql.append(" AND u.truename like :truename ");
            queryMap.put("truename",userQueryDto.getTruename());
            this.addLikeChar("truename",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getWorkNo())) {
            sql.append(" AND u.work_no like :workNo ");
            queryMap.put("workNo",userQueryDto.getWorkNo());
            this.addLikeChar("workNo",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getOrganName())) {
            sql.append(" AND organ.organ_name like :organName ");
            queryMap.put("organName",userQueryDto.getOrganName());
            this.addLikeChar("organName",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getRoleNameStr())) {
            sql.append(" AND r.role_name like :roleNameStr ");
            queryMap.put("roleNameStr",userQueryDto.getRoleNameStr());
            this.addLikeChar("roleNameStr",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getAuthorityNameStr())) {
            sql.append(" AND exists(select 1 from sys_user_authority ua,sys_authority a where u.pk_id = ua.user_id ");
            sql.append(" and ua.authority_id = a.pk_id and a.authority_name like :authorityNameStr) ");
            queryMap.put("authorityNameStr",userQueryDto.getAuthorityNameStr());
            this.addLikeChar("authorityNameStr",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getMobile())) {
            sql.append(" AND u.mobile like :mobile ");
            queryMap.put("mobile",userQueryDto.getMobile());
            this.addLikeChar("mobile",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getTelephone())) {
            sql.append(" AND u.telephone like :telephone ");
            queryMap.put("telephone",userQueryDto.getTelephone());
            this.addLikeChar("telephone",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getAddress())) {
            sql.append(" AND u.address like :address ");
            queryMap.put("address",userQueryDto.getAddress());
            this.addLikeChar("address",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getWechatName())) {
            sql.append(" AND u.wechat_name like :wechatName ");
            queryMap.put("wechatName",userQueryDto.getWechatName());
            this.addLikeChar("wechatName",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getEmail())) {
            sql.append(" AND u.email like :email ");
            queryMap.put("email",userQueryDto.getEmail());
            this.addLikeChar("email",queryMap);
        }
        if (!StringUtils.isEmpty(userQueryDto.getRemark())) {
            sql.append(" AND u.remark like :remark ");
            queryMap.put("remark",userQueryDto.getRemark());
            this.addLikeChar("remark",queryMap);
        }
        if (userQueryDto.getCreateDate_start()!=null) {
            queryMap.put("createDate_start",userQueryDto.getCreateDate_start());
            sql.append(" AND u.create_date >= :createDate_start ");
        }
        if (userQueryDto.getCreateDate_end()!=null) {
            queryMap.put("createDate_end",userQueryDto.getCreateDate_end());
            sql.append(" AND u.create_date <= :createDate_end ");
        }
        if (!StringUtils.isEmpty(userQueryDto.getEnabled())) {
            sql.append(" AND u.enabled = :enabled ");
        }
        return super.findPagesListBySql(sql.toString(), queryMap,pageFilter,  User.class);
    }

    private void addLikeChar(String key,Map<String,Object> map){
        Object value = map.get(key);
        map.put(key,"%"+value.toString()+"%");
    }

    @Override
    public List<User> checkWorkNo(User user, boolean isAdd) {
        String hql = "from User u where u.delFlag = '0' and u.workNo = '"+ user.getWorkNo() + "'";
        if(!isAdd){
            hql += " and u.id != '"+ user.getId() + "'";
        }
        return super.findByJqpl(hql, Collections.emptyMap(),User.class);
    }

    @Override
    public List<User> checkUserName(User user, boolean isAdd) {
        String hql = "from User u where u.delFlag = '0' and u.username = '"+ user.getUsername() + "'";
        if(!isAdd){
            hql += " and u.id != '"+ user.getId() + "'";
        }
        return super.findByJqpl(hql, Collections.emptyMap(),User.class);
    }

    @Override
    public List<User> findUser(String loginName) {
        String hql;
        hql = "from User user where user.username=:username and user.delFlag='0'";
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("username", loginName);
        return super.findByJqpl(hql, queryMap,User.class);
    }

//    @Override
//    public List<User> checkLoginFlag(String workNo) {
//        String sql;
//        sql = "select pk_id from TM_EMPLOYEE  where HIS_FLAG=0 and del_flag=0 "
//                + "and LOGIN_FLAG=0 and EMPLOYEE_NO=:workNo";
//        Map<String, Object> queryMap;
//        queryMap = new HashMap<>();
//        queryMap.put("workNo", workNo);
//        return super.findBySql(sql, queryMap).list();
//    }

    @Override
    public String updatePassword(String newPassword, String oldPassword, String username) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("username", username);
        String sql;
        sql = "select u.password from sys_user u where u.username =:username and del_flag='0'";
        List<Object[]> list  = super.findObjsBySql(sql, queryMap);
        if(list.isEmpty()){
            return "fail";
        }
        Object opwObject = list.get(0)[0];
        String opw = opwObject==null?null:opwObject.toString();
        if (opw.equals(oldPassword)) {
            sql = "update sys_user A set A.password ='" + newPassword + "' " +
                    "where A.username =:username and del_flag='0'";
            super.executeUpdate(sql, Collections.emptyMap());
            return "success";
        } else {
            return "fail";
        }
    }

    @Override
    public List<Map<String, String>> getUserByCode(String value) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        StringBuilder sql;
        sql = new StringBuilder("select usr.work_no as \"userCode\", usr.truename as \"userName\"");
        sql.append(" ,usr.pk_id as \"id\", org.pk_id as \"organId\", org.organ_name as \"organName\",usr.username \"loginName\" ");
        sql.append(" from sys_user usr, SYS_USER_ORGAN uo, sys_organ org");
        sql.append(" where usr.pk_id = uo.user_id and uo.organ_id = org.pk_id and usr.del_flag = '0'");
        if (StringUtils.isNotEmpty(value)) {
            queryMap.put("value", new Query(Condition.LIKE, value));
            sql.append(" and (usr.work_no like :value or usr.truename like :value)");
        }
        List<Map> list = super.findMapsBySql(sql.toString(), queryMap);
        return ConvertUtil.toKVMapList(list,String.class,String.class);
    }

    @Override
    public List<User> findUserAll() {
        String hql;
        hql = "from User user where user.delFlag='0'";
        return super.findByJqpl(hql,Collections.emptyMap(),User.class);
    }

    @Override
    public List<User> getUserByRoleUrl(String roleUrl) {
        StringBuilder sql;
        sql = new StringBuilder();
        sql.append("select distinct su.pk_id as id,su.WORK_NO as workNo,su.USERNAME as username,su.truename as truename,su.EMAIL as email");
        sql.append(" from sys_user su,SYS_USER_ROLE sur,SYS_ROLE sr");
        sql.append(" where su.pk_id = sur.user_id ");
        sql.append(" and sur.role_id = sr.pk_id");
        sql.append(" and sr.role_url = '").append(roleUrl).append("'");
        sql.append(" and su.del_flag = '0'");
        return this.findBySql(sql.toString(), Collections.emptyMap(),  User.class);
    }

    @Override
    public List<User> getUserByEname(String employeeName) {
        StringBuilder sql;
        sql = new StringBuilder("select pk_id as id,truename as truename,work_no as workNo ");
        sql.append(" from sys_user where del_Flag=0 ");
        sql.append(" and (truename like :employeeName or work_No like :employeeName) ");
        sql.append(" and enabled='1'");
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("employeeName", employeeName);
        return super.findBySql(sql.toString(),Collections.emptyMap(),User.class);
    }


    @Override
    public List<User> getUserByOrganIdAndEname(String organId, String employeeName) {
        StringBuilder sql;
        Map<String,Object> queryMap = new HashMap<>();
        sql = new StringBuilder("select u.pk_id as id,u.truename as truename,u.work_no as workNo ");
        sql.append("from sys_user u left join sys_user_organ uo on u.pk_id =uo.user_id ");
        sql.append("where u.del_Flag=0 ");
        sql.append("and enabled='1' ");
        if (!StringUtils.isEmpty(organId)) {
            sql.append("and uo.organ_id in (SELECT o.pk_id FROM sys_organ o START WITH o.pk_id = :organId CONNECT BY o.parent_id = PRIOR o.pk_id and o.del_flag = '0') ");
            queryMap.put("organId", organId);
        }
        if(!StringUtils.isBlank(employeeName)){
            sql.append("and (u.truename like :employeeName or u.work_No like :employeeName) ");
            queryMap.put("employeeName", employeeName);
        }
        return super.findBySql(sql.toString(),queryMap,User.class);
    }

    @Override
    public List<Map<String, Object>> getUserDepartById(String userId) {
        StringBuilder sql;
        sql = new StringBuilder("select o.pk_id as \"id\" ");
        sql.append("from sys_organ o,sys_user_organ uo  ");
        sql.append("where o.pk_id = uo.organ_id and uo.user_id = :userId ");
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("userId", userId);
        List<Map> list = super.findMapsBySql(sql.toString(),queryMap);
        return ConvertUtil.toKVMapList(list,String.class,Object.class);
    }

    @Override
    public List<Map<String, Object>> getAllUser() {
        StringBuilder sql = new StringBuilder();
        sql.append("select u.username as \"username\",u.truename as \"truename\" from sys_user u");
        List<Map> list = super.findMapsBySql(sql.toString(),Collections.emptyMap());
        return ConvertUtil.toKVMapList(list,String.class,Object.class);
    }
}
