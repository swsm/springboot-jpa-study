package com.swsm.linkmes.domain.repository.basic.custom;


import com.swsm.linkmes.model.basic.Dict;
import com.swsm.linkmes.model.basic.DictIdx;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepository;
import com.swsm.platform.model.PageFilter;

import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: CustomDictRepository
 * @ProjectName mes-sm
 * @Description: 系统管理字典业务数据库访问
 * @date 2018/12/1014:13
 */
 public interface CustomDictRepository extends BaseCustomRepository {

    /**
     * <p>Description: 是否使用缓存</p>
     * @return true or false
     */
     boolean usingCache();

    /**
     *
     * <p>Description: 查找字典索引列表</p>
     * @param paramMap 条件
     * @param pageFilter 分页
     * @return
     */
     List<DictIdx> getDictIdxForGrid(Map<String, Object> paramMap, PageFilter pageFilter);

    /**
     *
     * <p>Description: 删除字典索引</p>
     * @param dictIdx dictIdx对象
     * @return 删除的字典索引集合
     */
     List<Dict> deleteDictIndex(DictIdx dictIdx);
    /**
     *
     * <p>Description: 获取某一字典索引下的所有字典值</p>
     * @param paramMap 过滤器
     * @param pageFilter 分页信息
     * @return 字典列表
     */
     List<Dict> getDictForGrid(Map<String, Object> paramMap, PageFilter pageFilter);

    /**
     *
     * <p>Description: 根据字典名称以及字典key值查找字典的value值</p>
     * @param parentKey 字典名
     * @param dictKey key
     * @return 字典的value值
     */
     List<Dict> getDictValueByKey(String parentKey, String dictKey);
    /**
     *
     * <p>Description: 根据字典名称以及字典value值查找字典的key值</p>
     * @param parentKey 字典名
     * @param dictValue value
     * @return 字典的key值
     */
     List<Dict> getDictKeyByValue(String parentKey, String dictValue);
    /**
     *
     * <p>Description: 通过父key获得所有的字典对象</p>
     * @param parentKey 父key
     * @return 结果集
     */
     List<Dict> getDictListyByParentKey(String parentKey);

    /**
     *
     * <p>
     * Description: 根据字典名以及key值查找相应的value
     * </p>
     *
     * @param parentKey 字典名
     * @param key key值
     * @return value值
     */
     String getDictValueByParentAndKey(String parentKey, String key);

    /**
     * 根据parentKey以及dickkey获取字典数据
     * @param parentKey
     * @param dictKeys 数组
     * @return
     */
     List<Dict> findDictsByParent(String parentKey, String[] dictKeys);
    /**
     * <p>
     * Description: 根据字典名称查询字典key和value
     * </p>
     * @param queryMap  查询条件
     * @return 字典key和value
     */
     List<Dict> getDictByName(Map<String, Object> queryMap);


    /**
     *
     * <p>Description: 查询所有业务字典，无查询条件</p>
     * @return 字典实体列表，包括实体的所有字段
     */
     List<Dict> getAllBusinessDict();

    /**
     * <p>Description: 查询所有字典，程序启动加载到缓存</p>
     * @return 字典实体列表
     */
     List<Dict> getAllDictCache();
}
