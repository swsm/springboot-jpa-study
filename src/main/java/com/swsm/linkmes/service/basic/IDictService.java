package com.swsm.linkmes.service.basic;


import com.swsm.linkmes.model.basic.Dict;
import com.swsm.linkmes.model.basic.DictIdx;
import com.swsm.platform.model.PageFilter;

import java.util.List;
import java.util.Map;


/**
 * @author tinel
 * @Title: IDictService
 * @ProjectName mes-sm
 * @Description: 系统管理，数据字典服务接口
 * @date 2018/12/1914:52
 */
 public interface IDictService {


    /**
     * <p>Description: 查询字典索引</p>
     * @param queryMap 过滤器
     * @param pageFilter
     * @return 字典索引列表
     */
     List<DictIdx> getDictIdxForGrid(Map<String, Object> queryMap, PageFilter pageFilter);


    /**
     * <p>Description: 保存或更新字典索引</p>
     * @param objList 需要操作的字典索引对象列表
     * @param userName 登录人用户名
     * @return String 操作结果
     */
     String saveOrUpdateDictIndex(List<DictIdx> objList, String userName);

    /**
     * <p>Description: 删除字典索引</p>
     * @param ids 字典索引id 数组
     * @param username 操作人
     * @return 操作结果
     */
     String deleteDictIndex(String ids, String username);




    /**
     * <p>Description: 获取某一字典索引下的所有字典值</p>
     * @param queryMap
     * @param pageFilter
     * @return 字典列表
     */
     List<Dict> getDictForGrid(Map<String, Object> queryMap, PageFilter pageFilter);

    /**
     * <p>Description: 保存或更新字典</p>
     * @param objList 需要操作的字典对象列表
     * @param userName 登录人用户名
     * @return String 操作结果
     */
     String saveOrUpdateDict(List<Dict> objList, String userName);

    /**
     * <p>Description: 删除字典</p>
     * @param ids 字典索引id 数组
     * @param userName 操作人
     * @return 操作结果
     */
     String deleteDict(String ids, String userName);

    /**
     *
     * <p>Description: 根据字典名称以及字典key值查找字典的value值</p>
     * @param parentKey 字典名
     * @param dictKey key
     * @return value
     */
     String getDictValueByKey(String parentKey, String dictKey);

    /**
     *
     * <p>Description: 根据字典名称以及字典value值查找字典的key值</p>
     * @param parentKey 字典名
     * @param dictValue value
     * @return key
     */
     String getDictKeyByValue(String parentKey, String dictValue);

    /**
     *
     * <p>
     * Description: 通过父key获得所有的字典对象
     * </p>
     *
     * @param parentKey 父key
     * @return 结果集
     */
     List<Dict> getDictListyByParentKey(String parentKey);

    /**
     *
     * <p>Description: 查询所有业务字典，无查询条件</p>
     * @return 字典实体列表，包括实体的所有字段
     */
     List<Dict> getAllBusinessDict();


    /**
     * 根据JPQL、参数查询是否存在对象信息，该方法主要用于唯一性验证
     * @param jpql
     * @param paramsMap
     * @return
     */
    boolean isExistsDict(String jpql, Map<String, Object> paramsMap);
}
