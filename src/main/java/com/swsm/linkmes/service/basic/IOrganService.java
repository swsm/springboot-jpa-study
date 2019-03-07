/**
 * IOrganService.java
 * Created at 2016-4-24
 * Created by Administrator
 * Copyright (C) 2016 BROADTEXT SOFTWARE, All rights reserved.
 */
package com.swsm.linkmes.service.basic;


import com.swsm.linkmes.domain.dto.OrganDto;
import com.swsm.linkmes.model.basic.Organ;

import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: IOrganService</p>
 * <p>Description: 机构服务接口</p>
 * <p>Author: Administrator</p>
 * <p>Date: 2016-4-24</p>
 */
 public interface IOrganService {

    /**
     * 根节点常量
     */
    static final  String ROOT_NODE_ID = "-1";

    
    /**
     * <p>Description:  根据parentId获取其下所有子机构,如果parentId为null，则为第一层机构信息</p>
     * @param parentId 父机构id，可以为null
     * @return List<Organ> 机构信息列表
     */
     List<Organ> findOrganByPanrentId(String parentId);

    /**
     * <p>Description: 删除某个机构(假删除) 机构管理模块</p>
     * @param id 部门id
     * @param username 操作人
     * @return 查询结果
     */
     String deleteOrgan(String id, String username);

    /**
     * <p>Description: 新增机构信息</p>
     * @param organ 机构对象
     * @param userName 操作人
     * @return String,如果机构编码存在，则返回organCodeExist；新增成功返回success
     */
     String saveOrgan(Organ organ, String userName);
    
    /**
     * <p>Description: 更新机构信息</p>
     * @param organ 机构对象
     * @param username 操作人
     * @return String，如果机构编码存在，则返回organCodeExist<br/>
     * 如果所有的子孙机构中存在该机构的parentId，则返回parentOrganNotChildOrgan<br/>
     * 操作成功返回success
     */
     String updateOrgan(Organ organ, String username);
    
    /**
     * <p>Description: 机构模块 树 移动节点</p>
     * @param sourceNode 被移动节点id
     * @param targetNode 目标节点id
     * @param username 操作人
     * @param dropPosition 在node的操作位置
     * @return 操作结果
     */
     String treeDropMove(String sourceNode, String targetNode, String username, String dropPosition);
    
    /**
     * <p>Description: 机构模块 树 复制节点</p>
     * @param sourceNode 被移动节点id
     * @param targetNode 目标节点id
     * @param username 操作人
     * @param dropPosition 在node的操作位置
     * @return 操作结果
     */
     String treeDropCopy(String sourceNode, String targetNode, String username, String dropPosition);

    /**
     * 
     * <p>Description: 查询所有机构信息，无查询条件</p>
     * @return 机构Dto列表
     */
     List<OrganDto> getAllOrgans();

    /**
     *  根据机构名称模糊查询所有机构名称
     * @param organName 机构名称
     * @return 机构名称
     */
	 List<Map<String, String>> getAllOrganNames(String organName);

	/**
	 * 
	 * <p>Description: 查询全部组织信息</p>
	 * @return
	 */
     List<Organ> getAllOrgan();

    /**
     * 根据JPQL、参数查询是否存在对象信息，该方法主要用于唯一性验证
     * @param jpql
     * @param paramsMap
     * @return
     */
    boolean isExistsDict(String jpql, Map<String, Object> paramsMap);

    /**
     * 根据ID获取机构信息
     * @param organId
     * @return
     */
    Organ getOrganById(String organId);

}
