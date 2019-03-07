package com.swsm.linkmes.service.basic;


import com.swsm.linkmes.model.basic.Resource;
import com.swsm.platform.model.PageFilter;

import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: IResourceService</p>
 * <p>Description: 资源管理接口</p>
 * <p>Author: yanshisheng</p>
 * <p>Date: 2016-5-27</p>
 */
 public interface IResourceService {

    /**
     * <p>Description: 查找资源信息，根据父节点ID、本节点ID进行查找</p>
     * @param parentId 上级资源id/本节点ID
     * @param roleId 角色Id，用来判断是否默认选中某资源
     * @return List<Resource>  Resource对象只包括id，childCount，resName，resCode，resType，dbChecked属性
     */
     List<Resource> getRoleResourceTreeByPid(String parentId, String roleId);


    /**
     * <p>Description: 根据条件查询资源信息</p>
     * @param queryMap 查询条件，根据调用方法提供的条件，条件主要封装在queryMap中，其中key为条件
     * @param pageFilter 实体类
     * @return List<Resource>  Resource对象不包括扩展属性
     * @throws Exception 运行时异常
     */
     List<Resource> queryPagedEntityList(Map<String, Object> queryMap, PageFilter pageFilter);

    /**
     * <p>Description: 根据资源名称获取资源树信息，需要排除自身资源</p>
     * @param resName 资源名称,模糊匹配
     * @return List<Map<String, Object>> Map<String, Object>对象中的key包括parentId，id，text
     */
     List<Map<String, Object>> getResourceTreeByResname(String resName);

    /**
     * <p>Description: 新增或修改资源，根据id是否为null确定为新增还是修改</p>
     * @param res 需要操作的资源对象，如果id==null表示为新增，否则表示为修改
     * @param userInfo 登录人用户名
     * @return 新增或保存的校验信息
     */
     String saveOrUpdateResource(Resource res, String[] userInfo);

    /**
     * <p>Description: 删除对象列表</p>
     * @param list 需要删除的对象列表 List<Resource>中的Resource对象至少需要包括资源ID
     */
     void realDeleteEntity(List<Resource> list);

    /**
     * <p>Description: 启用或禁用资源</p>
     * @param enabled 状态
     * @param ids 资源id数组
     */
     void updateResourceEnabled(String enabled, String[] ids);

    /**
     *
     * <p>
     * Description: 根据角色ID查询资源数据（导出专用），SQL比较复杂，尽量少用
     * </p>
     * select t.le ,t.pk_id,t.res_name ,t.res_code ,
     * sd.dict_value as \"resType\" from ( select rownum as num,tt.* from
     * (select level as le,CASE connect_by_isleaf
     * WHEN 0 THEN  case when m.parent_id is null then to_char(connect_by_root m.res_order)
     * else to_char(connect_by_root m.res_order || '.' || m.res_order)
     * end else connect_by_root m.res_order
     * || '.' || get_PreOrder(m.parent_id) || '.' || m.res_order END as xh,m.pk_id,
     * m.res_name,m.res_code,m.res_type,m.parent_id,m.res_order,m.enabled,m.belong_system,
     * to_char(m.create_date,'yyyy-mm-dd hh24:mi:ss') as create_date from sys_resource m
     *  start with m.parent_id is null connect by m.parent_id = prior m.pk_id
     * ORDER SIBLINGS BY m.res_order, m.create_date ) tt
     *       left join sys_role_resource srr  on srr.res_id = tt.pk_id
     * left join sys_role sr  on sr.pk_id = srr.role_id
     *  where sr.pk_id ='").append(roleId).append("') t
     *  left join sys_dict sd on sd.dict_key = t.res_type
     * and sd.parent_key = 'SYS_ZYLBDM' and sd.del_flag = '0' order by t.num
     * @param roleId 角色ID
     * @return org.hibernate.Query
     */
     List<Map<String, Object>> getResourceTreeForExport(String roleId);


    /**
     * <p>Description: 根据工号查询所有资源</p>
     * @param workNo 工号
     * @return List<Map<String, Object>><br/>
     * Map<String, Object>对象中的key为id(资源ID)，text(资源名称)，parentIdStr(资源父ID)，resCode(资源编码)，resType(资源类型)，children(孩子节点set集合)，res_Order(资源顺序)
     */
     List<Map<String, Object>> getAllResourceTree(String workNo);

    /**
     *
     * <p>Description: 根据条件查询资源信息</p>
     * @param map 查询条件
     * @return 资源实体列表，包括所有资源实体字段
     */
     List<Map<String, Object>> getResource(Map<String, Object> map);

    /**
     *
     * <p>Description: 根据id删除资源信息</p>
     * @param id 资源id
     * @return 是否可删除
     */
     boolean deleteResource(String id);

    /**
     * 查询资源表名信息
     * @param tableName 表名
     * @return 资源表名信息
     */
	 List<Map<String, Object>> getResourceTable(String tableName);

	/**
	 * 查询库表的结构
	 * @param tableName 表名
	 * @return 表结构
	 */
	 List<Map<String, Object>> getTableStructure(String tableName);

	/**
	 * 根据表名查询表的描述信息
	 * @param tableName 表名
	 * @return 表的描述信息
	 */
	 List<Map<String, Object>> getTableComments(String tableName);

    /**
     * 根据JPQL、参数查询是否存在对象信息，该方法主要用于唯一性验证
     * @param jpql
     * @param paramsMap
     * @return
     */
    boolean isExists(String jpql, Map<String, Object> paramsMap);


    /**
     * 获取所有的一级模块信息
     * @return
     */
    List<Resource> getAllFirstModules();

    /**
     * 获取所有的资源信息
     * @return
     */
    List<Resource> getAllResources();

    /**
     * 根据ID获取资源薪资
     * @param id
     * @return
     */
    Resource getResourceById(String id);


}
