package com.swsm.linkmes.domain.repository.basic.custom.impl;


import com.swsm.linkmes.domain.repository.basic.custom.CustomDictRepository;
import com.swsm.linkmes.model.basic.Dict;
import com.swsm.linkmes.model.basic.DictIdx;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepositoryImpl;
import com.swsm.platform.model.PageFilter;
import com.swsm.platform.util.converter.string.StringHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: CustomDictRepositoryImpl
 * @ProjectName mes-sm
 * @Description: CustomDictRepository的实现类
 * @date 2018/12/1015:56
 */
public class CustomDictRepositoryImpl extends BaseCustomRepositoryImpl implements CustomDictRepository {

    @Override
    public boolean usingCache() {
        return true;
    }

    @Override
    public List<DictIdx> getDictIdxForGrid(Map<String, Object> paramMap, PageFilter pageFilter) {
        String countHql;
        countHql = "select count(*) from DictIdx ";
        String recordHql;
        recordHql = "from DictIdx ";
        String conditionString;
        conditionString = " where delFlag='0' ";
        if (paramMap.get("dictName")!=null && !"".equals(paramMap.get("dictName"))) {
            conditionString += " AND dictName like :dictName";
            paramMap.put("dictName","%"+paramMap.get("dictName")+"%");
        }
        if (paramMap.get("dictKey")!=null && !"".equals(paramMap.get("dictKey"))) {
            conditionString += " AND dictKey like :dictKey";
            paramMap.put("dictKey","%"+paramMap.get("dictKey")+"%");
        }
        if (paramMap.get("dictType")!=null) {
            conditionString += " AND dictType = :dictType";
        }
        return super.findPageByJqpl( recordHql + conditionString, countHql + conditionString,paramMap,pageFilter,DictIdx.class);
    }

    @Override
    public List<Dict> deleteDictIndex(DictIdx dictIdx) {
        return super.findByJqpl("from Dict t where t.parentKey=\'" + dictIdx.getDictKey() + "'" +
                " and t.delFlag = '0'", Collections.emptyMap(),Dict.class);
    }

    @Override
    public List<Dict> getDictForGrid(Map<String, Object> paramMap, PageFilter pageFilter) {
        String recordHql;
        recordHql = "from Dict ";
        String conditionString;
        conditionString = " where delFlag='0' ";
        if(paramMap.get("parentKey")!=null && !"".equals(paramMap.get("parentKey"))){
            conditionString += " AND parentKey = :parentKey";
        }
        String orderString;
        orderString = " order by dictSort asc";
        String countHql = "select count(*) from Dict";
        return super.findPageByJqpl(recordHql + conditionString + orderString,
                countHql + conditionString + orderString,paramMap,pageFilter,Dict.class);
    }

    @Override
    public List<Dict> getDictValueByKey(String parentKey, String dictKey) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("parentKey", parentKey);
        queryMap.put("dictKey", dictKey);
        StringBuilder hql;
        hql = new StringBuilder("from Dict dct");
        hql.append(" where dct.delFlag = '0' and dct.parentKey =:parentKey and dct.dictKey =:dictKey");
        return super.findByJqpl(hql.toString(), queryMap,Dict.class);
    }

    @Override
    public List<Dict> getDictKeyByValue(String parentKey, String dictValue) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("parentKey", parentKey);
        queryMap.put("dictValue", dictValue);
        StringBuilder hql;
        hql = new StringBuilder("from Dict dct");
        hql.append(" where dct.delFlag = '0' and dct.parentKey =:parentKey and dct.dictValue =:dictValue");
        return super.findByJqpl(hql.toString(), queryMap,Dict.class);
    }

    @Override
    public List<Dict> getDictListyByParentKey(String parentKey) {
        Map<String, Object> queryMap;
        queryMap = new HashMap<>();
        queryMap.put("parentKey", parentKey);
        StringBuilder hql;
        hql = new StringBuilder("from Dict dct");
        hql.append(" where dct.delFlag = '0' and dct.parentKey =:parentKey");
        return super.findByJqpl(hql.toString(), queryMap,Dict.class);
    }

    @Override
    public String getDictValueByParentAndKey(String parentKey, String key) {
        StringBuilder hql;
        hql = new StringBuilder("select dict");
        hql.append(" from Dict dict");
        hql.append(" where dict.parentKey = '").append(parentKey).append("'");
        hql.append(" and dict.dictKey = '").append(key).append("'");
        List<Dict> list = super.findByJqpl(hql.toString(),Collections.emptyMap(),Dict.class);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0).getDictValue();
    }

    @Override
    public List<Dict> findDictsByParent(String parentKey, String[] dictKeys) {
        StringBuilder builder = new StringBuilder();
        builder.append("from Dict d where d.delFlag = '0' and d.parentKey = '");
        builder.append(parentKey);
        builder.append("'");
        if(dictKeys!=null && dictKeys.length>0){
            String inWhr = StringHelper.createInWhr("d.dictKey", dictKeys);
            builder.append(inWhr);
        }
        List<Dict> dictList=super.findByJqpl(builder.toString(),Collections.emptyMap(),Dict.class);
        return dictList;
    }

    @Override
    public List<Dict> getDictByName(Map<String, Object> queryMap) {
        String sql;
        sql = "select t.dict_key as dictKey,t.dict_value as dictValue from SYS_DICT t where  t.parent_key=:parentKey";
        return super.findByJqpl(sql, queryMap, Dict.class);
    }

    @Override
    public List<Dict> getAllBusinessDict() {
        StringBuilder hql = new StringBuilder();
        hql.append("select d from Dict d,DictIdx di where d.parentKey = di.dictKey and di.dictType = '1' ");
        hql.append(" and d.delFlag = '0' and di.delFlag = '0'");
        List<Dict> list = super.findByJqpl(hql.toString(),Collections.emptyMap(),Dict.class);
        return list;
    }

    @Override
    public List<Dict> getAllDictCache() {
        StringBuilder hql = new StringBuilder();
        hql.append("select d from Dict d,DictIdx di where d.parentKey = di.dictKey ");
        hql.append(" and d.delFlag = '0' and di.delFlag = '0'");
        List<Dict> list = super.findByJqpl(hql.toString(),Collections.emptyMap(),Dict.class);
        return list;
    }
}
