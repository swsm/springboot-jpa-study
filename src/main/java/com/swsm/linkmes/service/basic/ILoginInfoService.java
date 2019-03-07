package com.swsm.linkmes.service.basic;


import com.swsm.linkmes.domain.dto.LoginInfoQueryDto;
import com.swsm.linkmes.model.basic.LoginInfo;
import com.swsm.platform.model.PageFilter;

import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: ILoginInfoService
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/2515:59
 */
  public interface ILoginInfoService {

    /**
     * 登录状态：LOGIN_STATUS 登录中
     */
     static final String LOGIN_STATUS_TRUE = "1";

    /**
     * 登录状态：LOGIN_STATUS 已注销
     */
     static final String LOGIN_STATUS_FALSE = "0";

    /**
     * 注销原因：LOGOFF_TAG 正常注销
     */
     static final String LOGOFF_TAG_NORMAL = "1";

    /**
     * 注销原因：LOGOFF_TAG session过期
     */
     static final String LOGOFF_TAG_EXPIRE = "2";

    /**
     * 注销原因：LOGOFF_TAG 账号二次登录踢出
     */
     static final String LOGOFF_TAG_DISPLACE = "3";

    /**
     * 注销原因：LOGOFF_TAG 管理界面踢出
     */
     static final String LOGOFF_TAG_REJECT = "4";

    /**
     *
     * <p>Description: 查询用户登录信息</p>
     * @param loginInfoQueryDto
     * @param pageFilter
     * @return 登录信息
     */
     List<LoginInfo> queryLoginInfo(LoginInfoQueryDto loginInfoQueryDto, PageFilter pageFilter);

    /**
     *
     * <p>Description: 新增一条记录</p>
     * @param userName 登录用户名
     * @param ipAddress 登录ip
     */
     void saveLoginInfo(String userName, String ipAddress);

    /**
     *
     * <p>Description: 登录用户注销</p>
     * @param userId 被注销用户id
     * @param logoffTag 注销原因
     */
     void logOffUser(String userId, String logoffTag);

    /**
     *
     * <p>Description: 登录用户注销,根据用户名注销</p>
     * @param userName 被注销用户
     * @param logoffTag 注销原因
     */
     void logOffUserByUserName(String userName, String logoffTag);

    /**
     *
     * <p>Description: 更新登录用户实时访问的模块</p>
     * @param userName 用户名
     * @param modual 访问模块
     */
     void updateLoginInfo(String userName, String modual);

    /**
     *
     * <p>Description: 根据用户名以及用户ip判断用户登录是否有效</p>
     * @param userName 用户名
     * @param localIp ip
     * @return 0:ok;(1.正常注销；2.session过期；)3.账号二次登录踢出；4.管理界面踢出；
     */
     String checkLoginStatus(String userName, String localIp);


    /**
     *
     * <p>Description: 校验该用户是否在其他电脑已经登录</p>
     * @param userName 用户名
     * @param ipAddress ip地址
     * @return 结果，true：标识已经被登录
     */
     boolean checkUserIsLogin(String userName, String ipAddress);

    /**
     *
     * <p>Description: 通过用户名拿到当前用户的信息</p>
     * @param userName 用户名
     * @return 用户信息
     */
    List<Map<String, Object>> getUserInfoByUserName(String userName);
}
