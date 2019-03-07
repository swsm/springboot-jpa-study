package com.swsm.linkmes.domain.repository.basic.custom;


import com.swsm.linkmes.model.basic.Resource;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepository;

import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: CustomResourceRepository
 * @ProjectName mes-sm
 * @Description: 系统管理中资源管理数据访问层
 * @date 2018/12/1014:12
 */
 public interface CustomResourceRepository extends BaseCustomRepository {

    /**
     *
     * <p>Description: 是否用缓存</p>
     * @return true表示是，false表示否
     */
     boolean usingCache();


    /**
     *
     * <p>Description: 根据用户登录名以及资源编码判断是否存在权限</p>
     * @param userId 用户登录名
     * @param resCode 资源编码 
     * @return true表示有权限，false表示没有权限
     */
     boolean hasAuthor(String userId, String resCode);

    /**
     * <p>Description: 查找资源信息，根据父节点ID、本节点ID进行查找</p>
     * @param parentId 上级资源id/本节点ID
     * @return List<Resource>  Resource对象只包括id，childCount，resName，resCode，resType属性
     */
     List<Resource> getResourceTreeByPid(String parentId);

    /**
     * <p>Description: 根据角色ID获取资源ID信息列表</p>
     * @param roleId 角色ID
     * @return List<String> 资源id列表
     */
     List<String> getResIdsByRoleId(String roleId);


    /**
     *
     * <p>Description: 根据资源名称模糊查询资源信息列表</p>
     * @param resName  资源名称
     * @return List<Resource> Resource对象不包括扩展属性
     */
     List<Resource> getResourceTreeByResname(String resName);

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
     org.hibernate.Query getResourceTreeForExport(String roleId);

    /**
     *
     * <p>Description: 根据用户登录名称以及资源ID获取该用户的孩子节点信息</p>
     * @param loginName 用户登录名称
     * @param parentId 资源ID ,如果为null则获取第一层节点信息
     * @return List<Resource> Resource对象不包括扩展属性
     */
     List<Resource> getResourcesByParentId(String loginName, String parentId);

    /**
     *
     * <p>Description: 根据节点ID获取孩子节点信息列表</p>
     * @param parentId 节点ID ，如果为null则获取第一层节点信息
     * @return List<Resource> Resource对象不包括扩展属性
     */
     List<Resource> getChildResource(String parentId);

    /**
     *
     * <p>Description: 根据资源ID获取所有的孩子数量</p>
     * @param parentId 资源ID
     * @return int 孩子节点数量 
     */
     int getChildCount(String parentId);

    /**
     *
     * <p>Description: 根据用户登录名称获取该用户对应的资源信息列表</p>
     * @param loginName 登录名称，如果为null则获取所有的资源信息
     * @return List<Resource> Resource对象不包括扩展属性
     */
     List<Resource> getHavResByLoginName(String loginName);

    /**
     * <p>Description: 根据工号查询所有资源</p>
     * @param workNo 工号
     * @return List<Map<String, Object>><br/> 
     * Map<String, Object>对象中的key为id(资源ID)，text(资源名称)，parentIdStr(资源父ID)，resCode(资源编码)，resType(资源类型)，res_Order(资源顺序)
     */
     List<Map<String, Object>> getAllResourceTree(String workNo);

    /**
     *
     * <p>Description: 根据条件模糊查询资源信息</p>
     * @param map 查询条件
     * @return 资源实体列表，包括所有资源实体字段
     */
     List<Resource> getResource(Map<String, Object> map);

    /**
     *
     * <p>Description: 根据条件精确查询资源信息</p>
     * @param map 查询条件
     * @return 资源实体列表，包括所有资源实体字段
     */
     List<Resource> getExactRource(Map<String, Object> map);

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
}
