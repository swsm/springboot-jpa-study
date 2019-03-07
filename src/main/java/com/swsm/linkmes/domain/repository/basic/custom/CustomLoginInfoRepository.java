package com.swsm.linkmes.domain.repository.basic.custom;


import com.swsm.linkmes.domain.dto.LoginInfoQueryDto;
import com.swsm.linkmes.model.basic.LoginInfo;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepository;
import com.swsm.platform.model.PageFilter;

import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: CustomLoginInfoRepository
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/2515:37
 */
public interface CustomLoginInfoRepository extends BaseCustomRepository {

    /**
     *
     * <p>Description: 查询用户登录信息</p>
     * @param loginInfoQueryDto
     * @param pageFilter
     * @return 登录信息
     */
    public List<LoginInfo> queryLoginInfo(LoginInfoQueryDto loginInfoQueryDto, PageFilter pageFilter);

    /**
     *
     * <p>Description: 查找用户登录信息，根据用户名以及状态</p>
     * @param userName 用户名
     * @param loginStatus 登录状态
     * @return 用户登录信息
     */
    public List<LoginInfo> getLoginInfoByUserName(String userName, String loginStatus);
    /**
     *
     * <p>Description: 根据条件查询用户登录信息</p>
     * @param userName 用户名
     * @param ipAddress ip地址
     * @param status 登录状态
     * @return 结果，true：标识有效
     */
    /**
     *
     * <p>Description: 根据条件查询用户登录信息</p>
     * @param userName 用户名
     * @param ipAddress ip地址
     * @param status 登录状态
     * @return 结果，true：标识有效
     */
    public List<LoginInfo> checkUserIsLogining(String userName, String ipAddress, String status);
    /**
     *
     * <p>Description: 校验该用户是否在其他电脑已经登录</p>
     * @param userName 用户名
     * @param ipAddress ip地址
     * @return 结果，true：标识已经被登录
     */
    public List<LoginInfo> checkUserIsLogin(String userName, String ipAddress);
    /**
     *
     * <p>Description: 根据用户名称获取用户信息</p>
     * @param userName
     * @return
     */
    public List<Map<String, Object>> getUserInfoByUserName(String userName);
}
