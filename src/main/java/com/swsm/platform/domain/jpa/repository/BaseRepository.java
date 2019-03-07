package com.swsm.platform.domain.jpa.repository;


import com.swsm.platform.model.PageFilter;
import com.swsm.platform.model.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {




    /**
     * 更新实体不更新null值
     *
     * @param entity 实体
     */
    <S extends T> void update(S entity);

    /**
     * 批量更新实体不更新null值
     *
     * @param entities 实体
     */
    void update(Iterable<? extends T> entities);

    /**
     * 更新实体,所有值都更新，null也会被更新
     *
     * @param entity 实体
     */
    <S extends T> void updateAllColumn(S entity);

    /**
     * 删除实体
     *
     * @param id 主键
     */
    void deleteEntityById(ID id);

    /**
     * 删除实体
     *
     * @param ids 实体id集合
     */
    void deleteAllEntityById(Iterable<? extends ID> ids);

    /**
     * 删除实体
     *
     * @param entities 实体集合
     */
    void deleteAllEntity(Iterable<? extends T> entities);

    /**
     * <p>Description: 通过某字段查询model对象列表</p>
     *
     * @param propertyName 属性
     * @param value        值
     * @return model对象
     */
    List<T> findByProperty(String propertyName, Object value);

    /**
     * <p>Description: 通过某字段查询model对象分页列表</p>
     *
     * @param propertyName 属性
     * @param value        值
     * @param pageable     JPA分页对象
     * @return model对象
     */
    Page<T> findPageByProperty(String propertyName, Object value, Pageable pageable);


    /**
     * <p>Description: 通过某字段查询model对象列表</p>
     *
     * @param propertyName 属性
     * @param value        值
     * @return model对象
     */
    List<T> findSortByProperty(String propertyName, Object value, String[] sorts, boolean isAsc);



    /**
     * <p>Description: 通过某字段查询model对象列表,过滤掉DEL_FLAG='1'</p>
     *
     * @param propertyName 属性
     * @param value        值
     * @return model对象
     */
    List<T> findNotDeleteByProperty(String propertyName, Object value);

    /**
     * <p>Description: 通过某字段查询model对象列表,过滤掉DEL_FLAG='1'</p>
     *
     * @param propertyName 属性
     * @param value        值
     * @param sorts
     * @param isAsc
     * @return model对象
     */
    List<T> findNotDeleteSortByProperty(String propertyName, Object value, String[] sorts, boolean isAsc);

    /**
     * <p>Description: 通过某字段查询model对象分页列表,过滤掉DEL_FLAG='1'</p>
     *
     * @param propertyName 属性
     * @param value        值
     * @param pageable     JPA分页对象
     * @return model对象
     */
    Page<T> findNotDeletePageByProperty(String propertyName, Object value, Pageable pageable);

    /**
     * <p>Description: 通过多个字段查询model对象列表</p>
     *
     * @param queryMap key 字段名 value Query对象
     * @return model对象
     */
    List<T> findByPropertys(Map<String, Query> queryMap);

    /**
     * <p>Description: 通过多个字段查询model对象列表</p>
     *
     * @param queryMap key 字段名 value Query对象
     * @param sorts
     * @param isAsc
     * @return model对象
     */
    List<T> findSortByPropertys(Map<String, Query> queryMap, String[] sorts, boolean isAsc);

    /**
     * <p>Description: 通过一些字段查询model对象分页列表</p>
     *
     * @param queryMap key 字段名 value Query对象
     * @param pageable JPA分页对象
     * @return model对象
     */
    Page<T> findPageByPropertys(Map<String, Query> queryMap, Pageable pageable);

    /**
     * <p>Description: 通过一些字段查询model对象分页列表</p>
     *
     * @param queryMap   key 字段名 value Query对象
     * @param pageFilter 自定义分页对象
     * @return model对象
     */
    Page<T> findPageByPropertys(Map<String, Query> queryMap, PageFilter pageFilter);



    /**
     * <p>Description: 通过一些字段查询model对象分页列表</p>
     *
     * @param queryMap   key 字段名 value Query对象
     * @param pageFilter 自定义分页对象
     * @return model对象
     */
    List<T> findPageListByPropertys(Map<String, Query> queryMap, PageFilter pageFilter);



    /**
     * <p>Description: 通过其他唯一标识查询model对象</p>
     *
     * @param propertyName 唯一标识属性
     * @param value        值
     * @return model对象
     */
    T findUniqueByProperty(String propertyName, Object value);

    /**
     * <p>Description: 通过其他唯一标识查询model对象,过滤掉DEL_FLAG='1'</p>
     *
     * @param propertyName 唯一标识属性
     * @param value        值
     * @return model对象
     */
    T findUniqueNotDeleteByProperty(String propertyName, Object value);

    /**
     * <p>Description: 通过其他唯一标识查询model对象</p>
     *
     * @param queryMap key 字段名 value Query对象
     * @return model对象
     */
    T findUniqueByPropertys(Map<String, Query> queryMap);

    /**
     *
     * @param id 主键
     * @return 实体对象
     */
    T getById(ID id);

    /**
     * @param queryMap queryMap key 字段名 value Query对象
     * @return 记录数
     */
    Long findCountByPropertys(Map<String, Query> queryMap);


    /**
     * 提供唯一性校验的方法
     *
     * @param jpql   jpql
     * @param params 参数
     * @return list
     */
    List<T> checkExistByJpql(String jpql, Map<String, Object> params);


}
