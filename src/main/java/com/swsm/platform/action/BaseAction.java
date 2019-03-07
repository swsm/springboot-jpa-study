package com.swsm.platform.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author tinel
 * @Title: BaseAction
 * @ProjectName mes-wms
 * @Description: restcont基础抽象列，提供一些公共方法
 * @date 2018/11/89:23
 */
public abstract class BaseAction {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseAction.class);


    /**
     *
     * @param commonJsonVo
     * @param tClass
     * @param <T>
     * @return
     */
    protected <T> List<T> parsorList(CommonJsonVo commonJsonVo,Class<T> tClass){
        String insertJson = commonJsonVo.getInsertJson();
        String updateJson = commonJsonVo.getUpdateJson();
        List<T> insertRoles = Collections.emptyList();
        if(!StringUtils.isEmpty(insertJson)){
            insertRoles = JSON.parseArray(insertJson,tClass);
        }
        List<T> updateRoles =  Collections.emptyList();
        if(!StringUtils.isEmpty(updateJson)){
            updateRoles = JSON.parseArray(updateJson,tClass);
        }
        List<T> list = new ArrayList<>();
        list.addAll(insertRoles);
        list.addAll(updateRoles);
        return list;
    }

    /**
     * 唯一性检查
     * @param uniqueFields
     * @param commonJsonVo
     * @param clazz
     * @return
     */
    protected boolean queryCheckUnique(String[] uniqueFields, CommonJsonVo commonJsonVo,Class clazz){
        String insertJson = commonJsonVo.getInsertJson();
        String updateJson = commonJsonVo.getUpdateJson();
        String deleteJson = commonJsonVo.getDeleteJson();
        List<Map> deletes = new ArrayList<>();
        List<Map> inserts = new ArrayList<>();
        List<Map> updates = new ArrayList<>();
        if(!StringUtils.isEmpty(deleteJson)){
            deletes = this.parsorMapList(deleteJson);
        }
        if(!StringUtils.isEmpty(insertJson)){
            inserts = this.parsorMapList(insertJson);
        }
        if(!StringUtils.isEmpty(updateJson)){
            updates = this.parsorMapList(updateJson);
        }
        return this.queryCheckUnique(uniqueFields,deletes,inserts,updates,clazz);
    }

    private List<Map> parsorMapList(String jsonStr) {
        JSONArray array = JSON.parseArray(jsonStr);
        List<Map> list = new LinkedList();
        if (array != null) {
            for(int i = 0; i < array.size(); ++i) {
                Map rltMap = (Map)JSON.parse(array.get(i).toString());
                list.add(rltMap);
            }
        }

        return list;
    }
    /**
     * 检查唯一性
     * @param uniqueFields
     * @param deleteList
     * @param insertList
     * @param updateList
     * @param clazz
     * @return
     */
    protected boolean queryCheckUnique(String[] uniqueFields, List<Map> deleteList, List<Map> insertList, List<Map> updateList, Class clazz) {
        List<Map> checkList = null;
        Map map;
        Iterator var8;
        if (insertList != null && updateList != null) {
            checkList = insertList;
            var8 = updateList.iterator();
            while(var8.hasNext()) {
                map = (Map)var8.next();
                checkList.add(map);
            }
        } else if (insertList != null) {
            checkList = insertList;
        } else if (updateList != null) {
            checkList = updateList;
        }
        String uniqueField;
        int var11;
        int var12;
        String[] var13;
        for(int i = 0; i < checkList.size(); ++i) {
            for(int j = 1 + i; j < checkList.size(); ++j) {
                boolean b = true;
                var13 = uniqueFields;
                var12 = uniqueFields.length;

                for(var11 = 0; var11 < var12; ++var11) {
                    uniqueField = var13[var11];
                    b = b && ((Map)checkList.get(i)).get(uniqueField).equals(((Map)checkList.get(j)).get(uniqueField));
                }
                if (b) {
                    return false;
                }
            }
        }
        if (checkList != null) {
            var8 = checkList.iterator();
            while(var8.hasNext()) {
                map = (Map)var8.next();
                deleteList.add(map);
            }
        }
        Map<String, Object> queryMap = new HashMap();
        Iterator var18 = checkList.iterator();
        do {
            if (!var18.hasNext()) {
                return true;
            }
            Map checkMap = (Map)var18.next();
            var13 = uniqueFields;
            var12 = uniqueFields.length;

            for(var11 = 0; var11 < var12; ++var11) {
                uniqueField = var13[var11];
                queryMap.put(uniqueField, checkMap.get(uniqueField));
            }
        } while(this.checkExits(queryMap, deleteList, uniqueFields, clazz));
        return false;
    }

    /**
     * 判断是否具存在
     * @param queryMap
     * @param allList
     * @param uniqueFields
     * @param clazz
     * @return
     */
    protected boolean checkExits(Map<String, Object> queryMap, List<Map> allList, String[] uniqueFields, Class clazz) {
        StringBuilder hql = (new StringBuilder("from ")).append(clazz.getName()).append(" clazz").append(" where clazz.delFlag = '0'");
        if (!allList.isEmpty()) {
            hql.append(" and clazz.id not in('");
            int i = 0;
            for(Iterator var8 = allList.iterator(); var8.hasNext(); ++i) {
                Map map = (Map)var8.next();
                if (i < allList.size() - 1) {
                    hql.append(map.get("id")).append("','");
                } else {
                    hql.append(map.get("id")).append("')");
                }
            }
        }
        String[] var9 = uniqueFields;
        int var13 = uniqueFields.length;
        String hqlStr;
        for(int var11 = 0; var11 < var13; ++var11) {
            hqlStr = var9[var11];
            hql.append(" and clazz.").append(hqlStr).append(" =:").append(hqlStr);
        }
        hqlStr = hql.toString();
        return isNotFindEntity(hqlStr,queryMap);
    }

    /**
     * 根据hql以及参数查找是否存在
     * @param jpql
     * @param queryMap
     * @return
     */
    protected  abstract boolean isNotFindEntity(String jpql,Map<String, Object> queryMap);
    



}
