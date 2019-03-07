package com.swsm.platform.domain.jpa.repository;


import com.swsm.platform.model.Filter;
import com.swsm.platform.model.PageFilter;
import com.swsm.platform.model.Query;
import com.swsm.platform.util.converter.entity.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Array;
import java.util.*;

public class BaseCustomRepositoryImpl implements BaseCustomRepository {


    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean userCache() {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findPageByJqpl(String jpql, String countJpql, Map<String, Object> parameters, PageFilter pageFilter, Class<T> type) {
        if (StringUtils.isBlank(jpql)) {
            throw new RuntimeException("sql must not be empty!");
        }
        javax.persistence.Query query = em.createQuery(jpql);
        query.setHint("org.hibernate.cacheable", this.userCache());
        javax.persistence.Query countQuery = em.createQuery(countJpql);
        countQuery.setHint("org.hibernate.cacheable", this.userCache());
        if (parameters != null) {
            parameters.forEach((k, v) -> {
                if (v != null) {
                    query.setParameter(k, v);
                    countQuery.setParameter(k, v);
                }
            });
        }
        Object count = countQuery.getSingleResult();
        // set total count
        pageFilter.setTotal(count == null ? 0L : Long.valueOf(count.toString()));
        query.setFirstResult(pageFilter.getStart())
                .setMaxResults(pageFilter.getLimit());
        return (List<T>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findByJqpl(String jpql, Map<String, Object> parameters, Class<T> type) {
        if (StringUtils.isBlank(jpql)) {
            throw new RuntimeException("sql must not be empty!");
        }
        javax.persistence.Query query = em.createQuery(jpql);
        if (parameters != null) {
            parameters.forEach((k, v) -> {
                if (v != null) query.setParameter(k, v);
            });
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findBySql(String sql, Map<String, Object> parameters, Class<T> type) {
        if (StringUtils.isBlank(sql)) {
            throw new RuntimeException("sql must not be empty!");
        }
        javax.persistence.Query query = em.createNativeQuery(sql);
        if (parameters != null) {
            parameters.forEach((k, v) -> {
                if (v != null) query.setParameter(k, v);
            });
        }
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(type));
        return (List<T>) query.getResultList();
    }

    @Override
    public List<Object[]> findObjsBySql(String sql, Map<String, Object> parameters) {
        javax.persistence.Query query = em.createNativeQuery(sql);
        if (parameters != null) {
            parameters.forEach((k, v) -> {
                if (v != null) query.setParameter(k, v);
            });
        }
        List list = query.unwrap(NativeQuery.class).getResultList();
        List<Object[]> results = new ArrayList<>();
        for(Object obj : list){
            if(obj instanceof Array){
                Object[] array = (Object[]) obj;
                results.add(array);
            }else{
                Object[] array = new Object[1];
                array[0] = obj;
                results.add(array);
            }
        }
        return results;
    }

    @Override
    public List<Map> findMapsBySql(String sql, Map<String, Object> parameters) {
        javax.persistence.Query query = em.createNativeQuery(sql);
        if (parameters != null) {
            parameters.forEach((k, v) -> {
                if (v != null) query.setParameter(k, v);
            });
        }
        return query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T findUniqueBySql(String sql, Map<String, Object> parameters, Class<T> type) {
        if (StringUtils.isBlank(sql)) {
            throw new RuntimeException("sql must not be empty!");
        }
        javax.persistence.Query query = em.createNativeQuery(sql);
        parameters.forEach(query::setParameter);
        query.unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(type));
        // edit by zhl getSingleResult()如果找不到结果会抛出异常
        //return (M)query.getSingleResult();
        List<T> list = (List<T>) query.getResultList();
        if (list != null && list.size() > 0) {
            if (list.size() == 1) {
                return list.get(0);
            } else {
                throw new IncorrectResultSizeDataAccessException(list.size());
            }
        }
        return null;
    }

    @Transactional
    @Override
    public Integer executeUpdate(String sql, Map<String, Object> parameters) {
        if (StringUtils.isBlank(sql)) {
            throw new RuntimeException("sql must not be empty!");
        }
        javax.persistence.Query query = em.createNativeQuery(sql);
        parameters.forEach(query::setParameter);
        return query.executeUpdate();
    }


    /**
     * @param sql    sql语句
     * @param filter 查询filter
     * @deprecated {@link #findPagesListBySql(String, Map, PageFilter, Class)} instead.
     */
    @SuppressWarnings("unchecked")
    @Override
    @Deprecated
    public List<Map<String, Object>> findPagesListBySql(String sql, Filter filter) {
        if (StringUtils.isBlank(sql)) {
            throw new RuntimeException("sql must not be empty!");
        }
        String countSql = "select count(*) from (" + sql + ") t0";
        // total count query sql
        javax.persistence.Query countQuery = em.createNativeQuery(countSql);
        countQuery.setHint("org.hibernate.cacheable", this.userCache());
        // query sql
        javax.persistence.Query query = em.createNativeQuery(sql);
        query.setHint("org.hibernate.cacheable", this.userCache());
        Map<String, Query> queryMap = filter.getQueryMap();
        if (queryMap != null && queryMap.size() > 0) {
            queryMap.forEach((k, v) -> {
                countQuery.setParameter(k, v.getValue());
                query.setParameter(k, v.getValue());
            });
        }
        Object count = countQuery.unwrap(NativeQuery.class).getSingleResult();
        // set total count
        filter.setTotal(count == null ? 0L : Long.valueOf(count.toString()));

        query.unwrap(NativeQuery.class).setFirstResult(filter.getStart())
                .setMaxResults(filter.getLimit())
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (List<Map<String, Object>>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> findPagesListBySql(String sql, Map<String, Object> paramters, PageFilter pageFilter, Class<E> type) {
        String countSql = "select count(*) from (" + sql + ") t0";
        return this.findPagesListBySql(sql,countSql,paramters,pageFilter,type);
    }

    @Override
    public <T> List<T> findPageByJqpl(String jpql, Map<String, Object> parameters, PageFilter pageFilter, Class<T> type) {
        String countJpql = null;
        if(jpql.trim().toUpperCase().startsWith("FROM")){
            countJpql = "select count(*)  "+jpql;
        }else{
            countJpql = "select count(*) from ("+jpql+")";
        }
        return this.findPageByJqpl(jpql,countJpql,parameters,pageFilter,type);
    }

    @Override
    public List<Map<String, Object>> findPageMapsByJqpl(String jpql, String countJpql, Map<String, Object> parameters, PageFilter pageFilter,String[] keys) {
        List<Object> list = this.findPageByJqpl(jpql,countJpql,parameters,pageFilter,Object.class);
        return this.toMapList(list,keys);
    }

    @Override
    public List<Map<String, Object>> findPageMapsByJqpl(String jpql, Map<String, Object> parameters, PageFilter pageFilter,String[] keys) {
        List<Object> list = this.findPageByJqpl(jpql,parameters,pageFilter,Object.class);
        return this.toMapList(list,keys);
    }

    @Override
    public List<Object[]> findPageObjsByJqpl(String jpql, String countJpql, Map<String, Object> parameters, PageFilter pageFilter) {
        List<Object> list = this.findPageByJqpl(jpql,countJpql,parameters,pageFilter,Object.class);
        List<Object[]> results = new LinkedList<>();
        for(Object o : list){
            Object[] e = (Object[])o;
            results.add(e);
        }
        return results;
    }

    @Override
    public List<Object[]> findPageObjsByJqpl(String jpql, Map<String, Object> parameters, PageFilter pageFilter) {
        List<Object> list = this.findPageByJqpl(jpql,parameters,pageFilter,Object.class);
        List<Object[]> results = new LinkedList<>();
        for(Object o : list){
            Object[] e = (Object[])o;
            results.add(e);
        }
        return results;
    }

    @Override
    public List<Map<String, Object>> findObjectMapsByJpql(String jpql, Map<String, Object> parameters,String[] keys) {
        List<Object> list = this.findByJqpl(jpql,parameters,Object.class);
        return this.toMapList(list,keys);
    }

    @Override
    public List<Object[]> findObjsByJpql(String jpql, Map<String, Object> parameters) {
        List<Object> list = this.findByJqpl(jpql,parameters,Object.class);
        List<Object[]> results = new LinkedList<>();
        for(Object o : list){
            Object[] e = (Object[])o;
            results.add(e);
        }
        return results;
    }

    @Override
    public List<Map<String, Object>> findObjectMapsBySql(String sql, Map<String, Object> parameters) {
        List<Map> list = this.findMapsBySql(sql,parameters);
        return ConvertUtil.toKVMapList(list,String.class,Object.class);
    }

    @Override
    public <E> List<E> findPagesListBySql(String sql, String countSql, Map<String, Object> paramters, PageFilter pageFilter, Class<E> type) {
        if (StringUtils.isBlank(sql)) {
            throw new RuntimeException("sql must not be empty!");
        }
        javax.persistence.Query countQuery = em.createNativeQuery(countSql);
        countQuery.setHint("org.hibernate.cacheable", this.userCache());
        javax.persistence.Query query = em.createNativeQuery(sql);
        query.setHint("org.hibernate.cacheable", this.userCache());
        if (paramters != null && paramters.size() > 0) {
            paramters.forEach((k, v) -> {
                countQuery.setParameter(k, v);
                query.setParameter(k, v);
            });
        }
        Object count = countQuery.unwrap(NativeQuery.class).getSingleResult();
        pageFilter.setTotal(count == null ? 0L : Long.valueOf(count.toString()));
        query.unwrap(NativeQuery.class).setFirstResult(pageFilter.getStart())
                .setMaxResults(pageFilter.getLimit())
                .setResultTransformer(Transformers.aliasToBean(type));
        return (List<E>) query.getResultList();
    }


    @Override
    public List<Object[]> findPageObjsListBySql(String sql, Map<String, Object> paramters, PageFilter pageFilter) {
        String countSql = "select count(*) from (" + sql + ") t0";
        return this.findPageObjsListBySql(sql,countSql,paramters,pageFilter);
    }

    @Override
    public List<Object[]> findPageObjsListBySql(String sql, String countSql, Map<String, Object> paramters, PageFilter pageFilter) {
        List<Object> list = this.findPagesListBySql(sql,countSql,paramters,pageFilter,Object.class);
        List<Object[]> results = new LinkedList<>();
        for(Object object : list){
            Object[] array = (Object[])object;
            results.add(array);
        }
        return results;
    }

    @Override
    public List<Map<String,Object>> findPageMapsListBySql(String sql, Map<String, Object> paramters, PageFilter pageFilter) {
        String countSql = "select count(*) from (" + sql + ") t0";
        return this.findPageMapsListBySql(sql,countSql,paramters,pageFilter);
    }

    @Override
    public List<Map<String,Object>> findPageMapsListBySql(String sql, String countSql, Map<String, Object> paramters, PageFilter pageFilter) {
        javax.persistence.Query countQuery = em.createNativeQuery(countSql);
        countQuery.setHint("org.hibernate.cacheable", this.userCache());
        javax.persistence.Query query = em.createNativeQuery(sql);
        query.setHint("org.hibernate.cacheable", this.userCache());
        if (paramters != null && paramters.size() > 0) {
            paramters.forEach((k, v) -> {
                countQuery.setParameter(k, v);
                query.setParameter(k, v);
            });
        }
        Object count = countQuery.unwrap(NativeQuery.class).getSingleResult();
        pageFilter.setTotal(count == null ? 0L : Long.valueOf(count.toString()));
        query.unwrap(NativeQuery.class).setFirstResult(pageFilter.getStart())
                .setMaxResults(pageFilter.getLimit())
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return  query.getResultList();
    }

    private List<Map<String,Object>> toMapList(List<Object> list, String[] keys){
        List<Map<String, Object>> results  = new LinkedList<>();
        for(Object o : list){
            int i = 0;
            Map<String, Object> ele = new HashMap<>();
            Object[] objects = (Object[])o;
            for(String key :  keys){
                ele.put(key,objects[i]);
                i++;
            }
            results.add(ele);
        }
        return results;
    }
    /**
     * 判断fieldName是否在queryMap中
     * @param fieldName
     * @param queryMap
     * @return
     */
    protected boolean checkValid(String fieldName, Map<String, Object> queryMap) {
        if (queryMap.get(fieldName) == null) {
            return false;
        } else {
            return queryMap.get(fieldName) != null;
        }
    }

    protected boolean checkQueryMapValid(String fieldName, Map<String, Query> queryMap) {
        if (queryMap.get(fieldName) == null) {
            return false;
        } else {
            Query query = queryMap.get(fieldName) ;
            if(query==null ||  query.getValue()==null || StringUtils.isEmpty(query.getValue().toString())){
                return false;
            }
            return true;
        }
    }
}
