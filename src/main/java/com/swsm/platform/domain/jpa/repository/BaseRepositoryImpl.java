package com.swsm.platform.domain.jpa.repository;

import com.swsm.platform.domain.jpa.specification.Specifications;
import com.swsm.platform.model.Condition;
import com.swsm.platform.model.PageFilter;
import com.swsm.platform.model.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.beans.PropertyDescriptor;
import java.util.*;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private static final Logger log = LoggerFactory.getLogger(BaseRepositoryImpl.class);

    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";

    /**
     * 属性分隔符
     */
    private static final String PROPERTY_SEPARATOR = ".";

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }



    /**
     * 更新实体不更新null值
     *
     * @param entity 实体
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public <S extends T> void update(S entity) {
        //获取ID
        ID entityId = (ID) entityInformation.getId(entity);
        Assert.notNull(entityId, ID_MUST_NOT_BE_NULL);
        //获取空属性并处理成null
        String[] nullProperties = getNullProperties(entity);
        //1.获取最新对象
        //T target = optionalT.orElseThrow(()->new RuntimeException("you want to update an entity by id,but not found in the repository"));
        T target = getOne(entityId);
        //2.将非空属性覆盖到最新对象
        BeanUtils.copyProperties(entity, target, nullProperties);
        //3.更新非空属性
        em.merge(target);
        //flush();
    }

    @Override
    @Transactional
    public void update(Iterable<? extends T> entities) {
        for (T entity : entities) {
            update(entity);
        }
    }

    /**
     * 更新实体,所有值都更新，null也会被更新
     *
     * @param entity 实体
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public <S extends T> void updateAllColumn(S entity) {
        //获取ID
        ID entityId = (ID) entityInformation.getId(entity);
        Assert.notNull(entityId, ID_MUST_NOT_BE_NULL);
        save(entity);
    }

    @Override
    @Transactional
    public void deleteEntityById(ID id) {
        if (existsById(id)) {
            deleteById(id);
        } else {
            log.warn("No {} entity with id {} exists!", entityInformation.getJavaType(), id);
        }
    }

    @Override
    @Transactional
    public void deleteAllEntityById(Iterable<? extends ID> ids) {
        for (ID id : ids) {
            deleteEntityById(id);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void deleteAllEntity(Iterable<? extends T> entities) {
        for (T entity : entities) {
            deleteEntityById((ID) entityInformation.getId(entity));
        }
    }

    /**
     * <p>Description: 通过某字段查询model对象列表</p>
     *
     * @param propertyName 属性
     * @param value        值
     * @return model对象
     */
    @Override
    public List<T> findByProperty(String propertyName, Object value) {
        return findAll(
                (root, query, criteriaBuilder) -> {
                    if (propertyName.indexOf(".") > 0) {
                        String[] propertys = propertyName.split("\\.");
                        return criteriaBuilder.equal(root.join(propertys[0]).get(propertys[1]), value);
                    } else {
                        return criteriaBuilder.equal(root.get(propertyName), value);
                    }
                }
        );
    }

    @Override
    public Page<T> findPageByProperty(String propertyName, Object value, Pageable pageable) {
        return findAll(
                (root, query, criteriaBuilder) -> {
                    if (propertyName.indexOf(".") > 0) {
                        String[] propertys = propertyName.split("\\.");
                        return criteriaBuilder.equal(root.join(propertys[0]).get(propertys[1]), value);
                    } else {
                        return criteriaBuilder.equal(root.get(propertyName), value);
                    }
                }
                , pageable);
    }

    /**
     * <p>Description: 通过多个字段查询model对象列表</p>
     *
     * @param queryMap key 字段名 value Query对象
     * @return model对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByPropertys(Map<String, Query> queryMap) {
        if (queryMap == null)
            return findAll();
        else
            return findAll((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = this.createPredicates(root,criteriaBuilder,queryMap);
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            });
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<T> findPageByPropertys(Map<String, Query> queryMap, Pageable pageable) {
        if (queryMap == null)
            return findAll(pageable);
        else
            return findAll((root,query,criteriaBuilder)->{
                List<Predicate> predicates = this.createPredicates(root,criteriaBuilder,queryMap);
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }, pageable);
    }

    @Override
    public Page<T> findPageByPropertys(Map<String, Query> queryMap, PageFilter pageFilter) {
        PageRequest pageRequest = PageRequest.of(pageFilter.getStart() / pageFilter.getLimit(), pageFilter.getLimit());
        //组装分页条件
        if (org.apache.commons.lang3.StringUtils.isNotBlank(pageFilter.getOrder())) {
            Sort.Direction direction = PageFilter.ASC.equals(pageFilter.getSort()) ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageRequest = PageRequest.of(pageFilter.getStart() / pageFilter.getLimit(), pageFilter.getLimit(), direction, pageFilter.getOrder().split(","));
        }
        Page<T> page = findPageByPropertys(queryMap, pageRequest);
        pageFilter.setTotal(page.getTotalElements());
        return page;
    }

    /**
     * <p>Description: 通过其他唯一标识查询model对象</p>
     *
     * @param propertyName 唯一标识属性
     * @param value        值
     * @return model对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findUniqueByProperty(String propertyName, Object value) {
        return (T) findOne(Specifications.equal(propertyName, value)).orElse(null);
    }

    /**
     * <p>Description: 通过其他唯一标识查询model对象</p>
     *
     * @param queryMap key 字段名 value Query对象
     * @return model对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public T findUniqueByPropertys(Map<String, Query> queryMap) {
        if (queryMap == null)
            return null;
        else
            return findOne((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = this.createPredicates(root,criteriaBuilder,queryMap);
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }).orElse(null);
    }

    @Override
    public T getById(ID id) {
        Optional<T> optional = findById(id);
        return optional.orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long findCountByPropertys(Map<String, Query> queryMap) {
        if (queryMap == null)
            return 0L;
        else
            return count((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = this.createPredicates(root,criteriaBuilder,queryMap);
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            });
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> checkExistByJpql(String jpql, Map<String, Object> parameters) {
        if (org.apache.commons.lang3.StringUtils.isBlank(jpql)) {
            throw new RuntimeException("sql must not be empty!");
        }
        javax.persistence.Query query = em.createQuery(jpql);
        if (parameters != null) {
            parameters.forEach((k, v) -> {
                if (v != null) query.setParameter(k, v);
            });
        }
        return (List<T>) query.getResultList();
    }


    @Override
    public List<T> findSortByProperty(String propertyName, Object value, String[] sorts, boolean isAsc) {
        Map<String, Query> queryMap = new HashMap<>();
        queryMap.put(propertyName,new Query(Condition.EQ,value));
        return this.findSortByPropertys(queryMap,sorts,isAsc);
    }

    @Override
    public List<T> findNotDeleteByProperty(String propertyName, Object value) {
        Map<String, Query> queryMap = new HashMap<>();
        queryMap.put("delFlag",new Query(Condition.EQ,"0"));
        queryMap.put(propertyName,new Query(Condition.EQ,value));
        return this.findByPropertys(queryMap);
    }

    @Override
    public List<T> findNotDeleteSortByProperty(String propertyName, Object value, String[] sorts, boolean isAsc) {
        Map<String, Query> queryMap = new HashMap<>();
        queryMap.put("delFlag",new Query(Condition.EQ,"0"));
        queryMap.put(propertyName,new Query(Condition.EQ,value));
        return this.findSortByPropertys(queryMap,sorts,isAsc);
    }

    @Override
    public Page<T> findNotDeletePageByProperty(String propertyName, Object value, Pageable pageable) {
        Map<String, Query> queryMap = new HashMap<>();
        queryMap.put("delFlag",new Query(Condition.EQ,"0"));
        queryMap.put(propertyName,new Query(Condition.EQ,value));
        return this.findPageByPropertys(queryMap,pageable);
    }

    @Override
    public List<T> findSortByPropertys(Map<String, Query> queryMap, String[] sorts, boolean isAsc) {
        if (queryMap == null || queryMap.isEmpty()){
            return findAll();
        }
        return findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = this.createPredicates(root,criteriaBuilder,queryMap);
            if(isAsc){
                List<Order> orderList = new ArrayList<>();
                for(String sort : sorts){
                    orderList.add(criteriaBuilder.asc(root.get(sort)));
                }
                query.orderBy(orderList);
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        });
    }



    @Override
    public T findUniqueNotDeleteByProperty(String propertyName, Object value) {
        Map<String,Query> queryMap = new HashMap<>();
        queryMap.put("delFlag",new Query(Condition.EQ,"0"));
        queryMap.put(propertyName,new Query(Condition.EQ,value));
        return this.findUniqueByPropertys(queryMap);
    }

    @Override
    public List<T> findPageListByPropertys(Map<String, Query> queryMap, PageFilter pageFilter) {
        Page<T> page = this.findPageByPropertys(queryMap,pageFilter);
        return page.getContent();
    }


    /**
     * 获取Path
     *
     * @param path         Path
     * @param propertyPath 属性路径
     * @return Path
     */
    private <X> Path<X> getPath(Path<?> path, String propertyPath) {
        if (path == null || org.apache.commons.lang3.StringUtils.isEmpty(propertyPath)) {
            return (Path<X>) path;
        }
        String property = org.apache.commons.lang3.StringUtils.substringBefore(propertyPath, PROPERTY_SEPARATOR);
        return getPath(path.get(property), org.apache.commons.lang3.StringUtils.substringAfter(propertyPath, PROPERTY_SEPARATOR));
    }

    /**
     * 获取对象的空属性
     */
    private static String[] getNullProperties(Object src) {
        //1.获取Bean
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        //2.获取Bean的属性描述
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        //3.获取Bean的空属性
        Set<String> properties = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : pds) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = srcBean.getPropertyValue(propertyName);
            if (StringUtils.isEmpty(propertyValue)) {
                srcBean.setPropertyValue(propertyName, null);
                properties.add(propertyName);
            }
        }
        return properties.toArray(new String[0]);
    }


    private List<Predicate> createPredicates(Root<T> root, CriteriaBuilder criteriaBuilder,Map<String, Query> queryMap){
        List<Predicate> predicates = new ArrayList<>();
        queryMap.forEach((k, v) -> {
            if (v != null && v.getValue() != null) {
                Path<?> path = getPath(root, k);
                switch (v.getCondition()) {
                    case EQ:
                        predicates.add(criteriaBuilder.equal(path, v.getValue()));
                        break;
                    case NE:
                        predicates.add(criteriaBuilder.notEqual(path, v.getValue()));
                        break;
                    case LIKE:
                        predicates.add(criteriaBuilder.like((Path<String>) path, (String) v.getValue()));
                        break;
                    case NOTLIKE:
                        predicates.add(criteriaBuilder.notLike((Path<String>) path, (String) v.getValue()));
                        break;
                    case GT:
                        if (Number.class.isAssignableFrom(path.getJavaType()) && v.getValue() instanceof Number) {
                            predicates.add(criteriaBuilder.gt((Path<Number>) path, (Number) v.getValue()));
                        }
                        if (v.getValue() instanceof Date) {
                            predicates.add(criteriaBuilder.greaterThan((Path<Date>) path, (Date) v.getValue()));
                        }
                        break;
                    case GE:
                        if (Number.class.isAssignableFrom(path.getJavaType()) && v.getValue() instanceof Number) {
                            predicates.add(criteriaBuilder.ge((Path<Number>) path, (Number) v.getValue()));
                        }
                        if (v.getValue() instanceof Date) {
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo((Path<Date>) path, (Date) v.getValue()));
                        }
                        break;
                    case LT:
                        if (Number.class.isAssignableFrom(path.getJavaType()) && v.getValue() instanceof Number) {
                            predicates.add(criteriaBuilder.lt((Path<Number>) path, (Number) v.getValue()));
                        }
                        if (v.getValue() instanceof Date) {
                            predicates.add(criteriaBuilder.lessThan((Path<Date>) path, (Date) v.getValue()));
                        }
                        break;
                    case LE:
                        if (Number.class.isAssignableFrom(path.getJavaType()) && v.getValue() instanceof Number) {
                            predicates.add(criteriaBuilder.le((Path<Number>) path, (Number) v.getValue()));
                        }
                        if (v.getValue() instanceof Date) {
                            predicates.add(criteriaBuilder.lessThanOrEqualTo((Path<Date>) path, (Date) v.getValue()));
                        }
                        break;
                    case IN:
                        predicates.add(path.in(v.getValue()));
                        break;
                    case NULL:
                        predicates.add(criteriaBuilder.isNull(path));
                        break;
                    case NOTNULL:
                        predicates.add(criteriaBuilder.isNotNull(path));
                        break;
                }
            }
        });
        return predicates;
    }


}
