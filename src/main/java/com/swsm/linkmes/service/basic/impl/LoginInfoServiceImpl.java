package com.swsm.linkmes.service.basic.impl;

import com.swsm.linkmes.domain.dto.LoginInfoQueryDto;
import com.swsm.linkmes.domain.repository.LoginInfoRepository;
import com.swsm.linkmes.domain.repository.UserRepository;
import com.swsm.linkmes.model.basic.LoginInfo;
import com.swsm.linkmes.model.basic.User;
import com.swsm.linkmes.service.basic.ILoginInfoService;
import com.swsm.platform.model.BaseModelUtil;
import com.swsm.platform.model.PageFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: LoginInfoServiceImpl
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/2515:59
 */
@Service
public class LoginInfoServiceImpl implements ILoginInfoService {

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<LoginInfo> queryLoginInfo(LoginInfoQueryDto loginInfoQueryDto, PageFilter pageFilter) {
        List<LoginInfo> list;
        list = this.loginInfoRepository.queryLoginInfo(loginInfoQueryDto,pageFilter);
        return list;
    }

    @Override
    public void saveLoginInfo(String userName, String ipAddress) {
        User user = this.userRepository.getUserByUserName(userName);
        LoginInfo info = new LoginInfo();
        info.setUserName(userName);
        if (user != null) {
            info.setTrueName(user.getTruename());
        }
        info.setIpAddress(ipAddress);
        info.setLoginTime(new Date());
        info.setLoginStatus(ILoginInfoService.LOGIN_STATUS_TRUE);
        info.setCreateDate(new Date());
        info.setCreateUser(userName);
        info.setDelFlag(BaseModelUtil.DEL_FALSE);
        this.loginInfoRepository.save(info);
    }

    @Override
    public void logOffUser(String userId, String logoffTag) {
        User user;
        user = this.userRepository.getById(userId);
        this.logOffUserByUserName(user.getUsername(), logoffTag);
    }

    /**
     *
     * <p>Description: 查找用户登录信息，根据用户名以及状态</p>
     * @param userName 用户名
     * @param loginStatus 登录状态
     * @return 用户登录信息
     */
    private LoginInfo getLoginInfoByUserName(String userName, String loginStatus) {
        List<LoginInfo> list;
        list = this.loginInfoRepository.getLoginInfoByUserName(userName, loginStatus);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void logOffUserByUserName(String userName, String logoffTag) {
        LoginInfo info;
        info = this.getLoginInfoByUserName(userName, ILoginInfoService.LOGIN_STATUS_TRUE);
        if (info != null) {
            info.setLoginStatus(ILoginInfoService.LOGIN_STATUS_FALSE);
            info.setLogoffTag(logoffTag);
            info.setUpdateDate(new Date());
            info.setUpdateUser(userName);
            this.loginInfoRepository.update(info);
        }
    }

    @Override
    public void updateLoginInfo(String userName, String modual) {
        LoginInfo info;
        info = this.getLoginInfoByUserName(userName, ILoginInfoService.LOGIN_STATUS_TRUE);
        if (info != null) {
            info.setModuleName(modual);
            info.setUpdateDate(new Date());
            info.setUpdateUser(userName);
            this.loginInfoRepository.update(info);
        }
    }

    @Override
    public String checkLoginStatus(String userName, String localIp) {
        LoginInfo info;
        info = this.checkUserIsLogining(userName, localIp, ILoginInfoService.LOGIN_STATUS_TRUE);
        if (info != null) {
            return "0";
        }
        LoginInfo loginInfo;
        loginInfo = this.checkUserIsLogining(userName, localIp, ILoginInfoService.LOGIN_STATUS_FALSE);
        if (loginInfo == null) {
            return null;
        }
        return loginInfo.getLogoffTag();
    }

    /**
     *
     * <p>Description: 根据条件查询用户登录信息</p>
     * @param userName 用户名
     * @param ipAddress ip地址
     * @param status 登录状态
     * @return 结果，true：标识有效
     */
    private LoginInfo checkUserIsLogining(String userName, String ipAddress, String status) {
        List<LoginInfo> list;
        list = this.loginInfoRepository.checkUserIsLogining(userName, ipAddress, status);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public boolean checkUserIsLogin(String userName, String ipAddress) {
        List<LoginInfo> list;
        list = this.loginInfoRepository.checkUserIsLogin(userName, ipAddress);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> getUserInfoByUserName(String userName) {
        List<Map<String, Object>> list;
        list = this.loginInfoRepository.getUserInfoByUserName(userName);
        return list;
    }
}
