package com.swsm.platform.domain.jpa.repository;




import com.swsm.platform.model.Filter;
import com.swsm.platform.model.PageFilter;

import java.util.List;
import java.util.Map;

public interface BaseCustomRepository {


    /**
     *
     * @return
     */
    boolean userCache();


    /**根据JPQL分页查询，返回类型不能是Map集合
     * @param jpql       jpql语句
     * @param countJpql  jpql语句
     * @param parameters 查询参数
     * @param pageFilter
     * @param type       类型
     * @return list
     */
    <T> List<T> findPageByJqpl(String jpql, String countJpql, Map<String, Object> parameters, PageFilter pageFilter, Class<T> type);

    /**根据JPQL分页查询，返回类型不能是Map集合
     * @param jpql       jpql语句
     * @param parameters 查询参数
     * @param pageFilter
     * @param type       类型
     * @return list
     */
    <T> List<T> findPageByJqpl(String jpql, Map<String, Object> parameters, PageFilter pageFilter, Class<T> type);

    /**根据JPQL分页查询
     * @param jpql       jpql语句
     * @param countJpql  jpql语句
     * @param parameters 查询参数
     * @param pageFilter
     * @param keys key对应jpql中的字段位置
     * @return list
     */
    List<Map<String,Object>> findPageMapsByJqpl(String jpql, String countJpql, Map<String, Object> parameters, PageFilter pageFilter, String[] keys);

    /**根据JPQL分页查询
     * @param jpql       jpql语句
     * @param parameters 查询参数
     * @param pageFilter
     * @param keys key对应jpql中的字段位置
     * @return list
     */
    List<Map<String,Object>> findPageMapsByJqpl(String jpql, Map<String, Object> parameters, PageFilter pageFilter, String[] keys);

    /**根据JPQL分页查询
     * @param jpql       jpql语句
     * @param countJpql  jpql语句
     * @param parameters 查询参数
     * @param pageFilter
     * @return list
     */
    List<Object[]> findPageObjsByJqpl(String jpql, String countJpql, Map<String, Object> parameters, PageFilter pageFilter);

    /**根据JPQL分页查询
     * @param jpql       jpql语句
     * @param parameters 查询参数
     * @param pageFilter
     * @return list
     */
    List<Object[]> findPageObjsByJqpl(String jpql, Map<String, Object> parameters, PageFilter pageFilter);

    /**
     * 根据jpql查找返回Map对象列表-Map<String,Object>
     * @param jpql
     * @param parameters
     * @param keys
     * @return
     */
    List<Map<String,Object>> findObjectMapsByJpql(String jpql, Map<String, Object> parameters, String[] keys);

    /**
     * 根据jpql查找返回object[] 数组对象集合
     * @param jpql
     * @param parameters
     * @return
     */
    List<Object[]> findObjsByJpql(String jpql, Map<String, Object> parameters);

    /**根据jpql查找，不能是Map对象集合
     * @param jpql       jpql语句
     * @param parameters 查询参数
     * @param type       类型
     * @return list
     */
    <T> List<T> findByJqpl(String jpql, Map<String, Object> parameters, Class<T> type);


    /**
     * 根据sql查找返回object[] 数组对象集合
     * @param sql
     * @param parameters
     * @return
     */
    List<Object[]> findObjsBySql(String sql, Map<String, Object> parameters);


    /**
     * 根据sql查找返回Map对象列表Map
     * @param sql
     * @param parameters
     * @return
     */
    @Deprecated
    List<Map> findMapsBySql(String sql, Map<String, Object> parameters);

    /**
     * 根据sql查找返回Map对象列表-Map<String,Object>
     * @param sql
     * @param parameters
     * @return
     */
    List<Map<String,Object>> findObjectMapsBySql(String sql, Map<String, Object> parameters);

    /**
     * 原生sql查询，不能是Map对象集合
     *
     * @param sql        原生sql 语句
     * @param parameters 查询参数
     * @param type       返回对象类型Class
     * @return T
     */
    <T> List<T> findBySql(String sql, Map<String, Object> parameters, Class<T> type);

    /**
     * 原生sql查询 不要使用*查询
     *
     * @param sql        原生sql 语句
     * @param parameters 查询参数
     * @param type       返回对象类型Class
     * @return M
     */
    <T> T findUniqueBySql(String sql, Map<String, Object> parameters, Class<T> type);

    /**
     * Execute an update or delete statement.
     *
     *
     * @param sql        sql语句
     * @param parameters 参数
     *
     * @return the number of entities updated or deleted
     */
    Integer executeUpdate(String sql, Map<String, Object> parameters);

    /**
     * @param sql    sql语句
     * @param filter 查询filter
     * @return list
     * @deprecated {@link #findPagesListBySql(String, Map, PageFilter, Class)} instead.
     */
    @Deprecated
    List<Map<String, Object>> findPagesListBySql(String sql, Filter filter);

    /**
     * 根据SQL分页查询，返回类型不能是Map集合
     * @param sql    sql语句
     * @param paramters
     * @param pageFilter
     * @return list
     */
    <E> List<E> findPagesListBySql(String sql, Map<String, Object> paramters, PageFilter pageFilter, Class<E> type);

    /**
     * 根据SQL分页查询，返回类型不能是Map集合
     * @param sql    sql语句
     * @param countSql
     * @param paramters
     * @param pageFilter
     * @param type 不能为Map
     * @return list
     */
    <E> List<E> findPagesListBySql(String sql, String countSql, Map<String, Object> paramters, PageFilter pageFilter, Class<E> type);


    /**
     * 根据SQL分页查询，返回类型不能是Map集合
     * @param sql    sql语句
     * @param filter 查询filter
     * @return list
     */
    List<Object[]> findPageObjsListBySql(String sql, Map<String, Object> paramters, PageFilter filter);

    /**
     * 根据SQL分页查询，返回类型不能是Map集合
     * @param sql    sql语句
     * @param countSql
     * @param paramters
     * @param pageFilter
     * @return list
     */
    List<Object[]> findPageObjsListBySql(String sql, String countSql, Map<String, Object> paramters, PageFilter pageFilter);


    /**
     * 根据SQL分页查询
     * @param sql    sql语句
     * @param paramters
     * @param filter 查询filter
     * @return list
     */
    List<Map<String,Object>> findPageMapsListBySql(String sql, Map<String, Object> paramters, PageFilter filter);

    /**
     * 根据SQL分页查询
     * @param sql    sql语句
     * @param countSql
     * @param paramters
     * @param pageFilter
     * @return list
     */
    List<Map<String,Object>> findPageMapsListBySql(String sql, String countSql, Map<String, Object> paramters, PageFilter pageFilter);

}
