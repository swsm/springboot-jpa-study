package com.swsm.linkmes.domain.repository.basic.custom.impl;

import com.swsm.linkmes.domain.dto.LoginInfoQueryDto;
import com.swsm.linkmes.domain.repository.basic.custom.CustomLoginInfoRepository;
import com.swsm.linkmes.model.basic.LoginInfo;
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
 * @Title: CustomLoginInfoRepositoryImpl
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/2515:38
 */
public class CustomLoginInfoRepositoryImpl extends BaseCustomRepositoryImpl implements CustomLoginInfoRepository {

    @Override
    public List<LoginInfo> queryLoginInfo(LoginInfoQueryDto loginInfoQueryDto, PageFilter pageFilter) {
        StringBuilder hql;
        hql = new StringBuilder();
        hql.append(" from LoginInfo li");
        hql.append(" where li.loginStatus = '1'");
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(loginInfoQueryDto.getUserName())) {
            hql.append(" and li.userName like :userName ");
            map.put("userName","%"+loginInfoQueryDto.getUserName()+"%");
        }
        if (StringUtils.isNotEmpty(loginInfoQueryDto.getTrueName())) {
            hql.append(" and li.trueName like :trueName ");
            map.put("trueName","%"+loginInfoQueryDto.getTrueName()+"%");
        }
        if (StringUtils.isNotEmpty(loginInfoQueryDto.getIpAddress())) {
            hql.append(" and li.ipAddress like :ipAddress ");
            map.put("ipAddress","%"+loginInfoQueryDto.getIpAddress()+"%");
        }
        if (StringUtils.isNotEmpty(loginInfoQueryDto.getModuleName())) {
            hql.append(" and li.moduleName like :moduleName ");
            map.put("moduleName","%"+loginInfoQueryDto.getModuleName()+"%");
        }
        if (loginInfoQueryDto.getLoginTimeStart()!=null) {
            hql.append(" and li.loginTime >= :loginTime_start ");
            map.put("loginTime_start",loginInfoQueryDto.getLoginTimeStart());
        }
        if (loginInfoQueryDto.getLoginTimeEnd()!=null) {
            hql.append(" and li.loginTime <= :loginTime_end ");
            map.put("loginTime_end",loginInfoQueryDto.getLoginTimeEnd());
        }
        hql.append(" order by li.loginTime desc");
        return super.findPageByJqpl(hql.toString(),"select count(*) " + hql.toString(),map,pageFilter,LoginInfo.class);
    }

    @Override
    public List<LoginInfo> getLoginInfoByUserName(String userName, String loginStatus) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("userName", userName);
        queryMap.put("loginStatus", loginStatus);
        StringBuilder hql;
        hql = new StringBuilder("from LoginInfo l");
        hql.append(" where l.loginStatus = :loginStatus and l.userName = :userName");
        List<LoginInfo> list;
        list = super.findByJqpl(hql.toString(), queryMap,LoginInfo.class);

        return list;
    }

    @Override
    public List<LoginInfo> checkUserIsLogining(String userName, String ipAddress, String status) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("userName", userName);
        queryMap.put("ipAddress",  ipAddress);
        queryMap.put("status", status);
        StringBuilder hql;
        hql = new StringBuilder("from LoginInfo li");
        hql.append(" where li.loginStatus = :status");
        hql.append(" and li.userName = :userName and li.ipAddress = :ipAddress");
        hql.append(" order by li.loginTime desc");
        return super.findByJqpl(hql.toString(), queryMap,LoginInfo.class);
    }

    @Override
    public List<LoginInfo> checkUserIsLogin(String userName, String ipAddress) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("userName", userName);
        queryMap.put("ipAddress",ipAddress);
        StringBuilder hql;
        hql = new StringBuilder("from LoginInfo li");
        hql.append(" where li.loginStatus = '1'");
        hql.append(" and li.userName = :userName and li.ipAddress != :ipAddress");
        return super.findByJqpl(hql.toString(), queryMap,LoginInfo.class);
    }

    @Override
    public List<Map<String, Object>> getUserInfoByUserName(String userName) {
        String sql = " SELECT pk_id \"id\" ,true_name \"trueName\" FROM sys_login_info"
                + " WHERE login_status=1 AND user_name='" + userName + "'";
        List<Map> list = super.findMapsBySql(sql, Collections.emptyMap());
        return ConvertUtil.toKVMapList(list,String.class,Object.class);
    }
}
