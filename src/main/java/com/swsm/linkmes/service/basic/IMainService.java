package com.swsm.linkmes.service.basic;


import com.swsm.linkmes.model.basic.Resource;

/**
 * <p>
 * ClassName: IMainService
 * </p>
 * <p>
 * Description: 提供系统资源服务
 * </p>
 * <p>
 * Author: Tangsanlin
 * </p>
 * <p>
 * Date: 2016-4-8
 * </p>
 */
public interface IMainService {

    /**
     * 根节点常量
     */
    static final  String ROOT_NODE_ID = "-1";

    /**
     * 管理用户
     */
    static final  String ADMIN_USER = "test1";
    /**
     * 
     * <p>
     * Description: 根据parentId获取所有的孩子资源
     * </p>
     * 
     * @param parnetId 父节点
     * @return 资源列表
     */
    public Resource[] getChildResource(String parnetId);

    /**
     * 
     * <p>
     * Description: 根据parentId获取指定用户的资源列表，admin用户拥有所有的权限
     * </p>
     * 
     * @param parnetId 父节点
     * @param loginName 当前用户信息
     * @return Resource[] 拥有权限的资源集合
     */
    public Resource[] getResourcesByParentId(String parnetId, String loginName);

    /**
     * 
     * <p>
     * Description: 根据用户登录名获取用户有权限的资源列表
     * </p>
     * 
     * @param loginName 登录用户名
     * @return 拥有权限的资源集合
     */
    public Resource[] getHavResByLoginName(String loginName);
}
