package com.swsm.linkmes.service.basic.impl;

import com.swsm.linkmes.domain.repository.ResourceRepository;
import com.swsm.linkmes.model.basic.Resource;
import com.swsm.linkmes.service.basic.IResourceService;
import com.swsm.platform.model.BaseModelUtil;
import com.swsm.platform.model.Condition;
import com.swsm.platform.model.PageFilter;
import com.swsm.platform.model.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author tinel
 * @Title: ResourceServiceImpl
 * @ProjectName mes-sm
 * @Description: IResourceService接口实现类
 * @date 2018/12/1111:31
 */
@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public List<Resource> getRoleResourceTreeByPid(String parentId, String roleId) {
        //执行查询
        List<Resource> resourceList;
        resourceList = new ArrayList<>();
        List<Resource> list;
        list = this.resourceRepository.getResourceTreeByPid(parentId);
        List<String> resIds;
        resIds = this.resourceRepository.getResIdsByRoleId(roleId);
        for (Resource r : list) {
            boolean b;
            b = resIds.contains(r.getId());
            if (b) {
                r.setDbChecked("1");
            } else {
                r.setDbChecked("0");
            }

        }
        getChildResForTree(resourceList, list, null);
        return resourceList;
    }


    @Override
    public List<Resource> queryPagedEntityList(Map<String, Object> queryMap, PageFilter pageFilter) {
        String hql = "from Resource r where 1=1 ";
        if (queryMap.get("resName") != null && !StringUtils.isEmpty(queryMap.get("resName").toString())) {
            hql += " and r.resName like :resName ";
        }
        if (queryMap.get("resCode") != null && !StringUtils.isEmpty(queryMap.get("resCode").toString())) {
            hql += " and r.resCode like :resCode ";
        }
        if (queryMap.get("resType") != null && !StringUtils.isEmpty(queryMap.get("resType").toString())) {
            hql += " and r.resType = :resType ";
        }
        String countHql = "select count(*) " + hql;
        return this.resourceRepository.findPageByJqpl(hql, countHql, queryMap, pageFilter, Resource.class);
    }

    @Override
    public List<Map<String, Object>> getResourceTreeByResname(String resName) {
        List<Resource> resList;
        resList = this.resourceRepository.getResourceTreeByResname(resName);
        List<Resource> lists;
        lists = new ArrayList<>();
        if (StringUtils.isNotEmpty(resName)) {
            for (Resource res : resList) {
                lists.add(res);
                if (res.getParentResource() != null) {
                    this.getResourceByPid(res, lists);
                }
            }
        } else {
            lists = resList;
        }
        List<Map<String, Object>> list;
        list = new ArrayList<>();
        for (Resource res : lists) {
            Map<String, Object> m;
            m = new HashMap<>();
            if (res.getParentResource() != null) {
                m.put("parentId", res.getParentResource().getId());
            } else {
                m.put("parentId", null);
            }
            m.put("id", res.getId());
            m.put("text", res.getResName());
            list.add(m);
        }
        return list;
    }

    @Override
    public String saveOrUpdateResource(Resource res, String[] userInfo) {
        if (StringUtils.isBlank(res.getId())) {
            return this.saveResource(res, userInfo);
        } else {
            Resource resource = this.resourceRepository.getById(res.getId());
            resource.setResName(res.getResName());
            resource.setResCode(res.getResCode());
            resource.setResType(res.getResType());
            resource.setParentResource(res.getParentResource());
            resource.setBelongSystem(res.getBelongSystem());
            resource.setResOrder(res.getResOrder());
            resource.setRemark(res.getRemark());
            resource.setResUrl(res.getResUrl());
            return this.updateResource(resource, userInfo);
        }
    }

    @Override
    public void realDeleteEntity(List<Resource> list) {
        for (Resource resource : list) {
            Resource dbResource;
            dbResource = this.resourceRepository.getById(resource.getId());
            if (dbResource != null) {
                this.resourceRepository.delete(dbResource);
            }
        }
    }

    @Override
    public void updateResourceEnabled(String enabled, String[] ids) {
        for (String id : ids) {
            Resource resource;
            resource = this.resourceRepository.getById(id);
            resource.setEnabled(enabled);
            this.resourceRepository.update(resource);
        }
    }

    @Override
    public List<Map<String, Object>> getResourceTreeForExport(String roleId) {
        List<Map<String, Object>> list;
        org.hibernate.Query query;
        query = this.resourceRepository.getResourceTreeForExport(roleId);
        list = query.list();
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        this.resetNumberList(list);
        return list;
    }

    @Override
    public List<Map<String, Object>> getAllResourceTree(String workNo) {
        //执行查询
        List<Map<String, Object>> resourceList;
        resourceList = new ArrayList<>();

        List<Map<String, Object>> list;
        list = this.resourceRepository.getAllResourceTree(workNo);
        getChildResourceForTree(resourceList, list, null);
        return resourceList;
    }

    @Override
    public List<Map<String, Object>> getResource(Map<String, Object> map) {
        List<Resource> resList;
        resList = this.resourceRepository.getResource(map);
        List<Resource> lists;
        lists = new ArrayList<>();
        if (map.isEmpty()) {
            lists = resList;
        } else {
            for (Resource res : resList) {
                lists.add(res);
                if (res.getParentResource() != null) {
                    this.getResourceByPid(res, lists);
                }
            }
        }
        Set<Resource> set;
        set = new LinkedHashSet<>();
        set.addAll(lists);
        lists.clear();
        lists.addAll(set);
        List<Map<String, Object>> list;
        list = new ArrayList<>();
        for (Resource res : lists) {
            Map<String, Object> m;
            m = new HashMap<>();
            if (res.getParentResource() != null) {
                m.put("parentId", res.getParentResource().getId());
            } else {
                m.put("parentId", null);
            }
            m.put("id", res.getId());
            m.put("resName", res.getResName());
            m.put("resCode", res.getResCode());
            m.put("resType", res.getResType());
            m.put("resOrder", res.getResOrder());
            m.put("enabled", res.getEnabled());
            m.put("belongSystem", res.getBelongSystem());
            m.put("modualFalg", res.getModualFlag());
            m.put("createDate", res.getCreateDate());
            m.put("createUser", res.getCreateUser());
            m.put("remark", res.getRemark());
            m.put("resUrl", res.getResUrl());
            list.add(m);
        }
        return list;
    }

    @Override
    public boolean deleteResource(String id) {
        Resource res = this.resourceRepository.getById(id);
        if (res.getChildResList().size() > 0) {
            return false;
        }
        this.resourceRepository.delete(res);
        return true;
    }

    @Override
    public List<Map<String, Object>> getResourceTable(String tableName) {
        List<Map<String, Object>> list = this.resourceRepository.getResourceTable(tableName);
        Set<String> set = new HashSet<>();
        Map<String, Object> resMap = new HashMap<>();
        List<Map<String, Object>> tableList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> tempMap : list) {
            if (!set.contains(tempMap.get("id"))) {
                resMap = new HashMap<>();
                tableList = new ArrayList<>();
                set.add(tempMap.get("id").toString());
                tempMap.put("id", tempMap.get("id"));
                resMap.put("name", tempMap.get("resName"));
                resMap.put("lev", 1);
                resMap.put("leaf", false);
                resMap.put("children", tableList);
                resList.add(resMap);
            } else {
                for (Map<String, Object> res : resList) {
                    if (res.containsKey(tempMap.get("id"))) {
                        tableList = (List<Map<String, Object>>) res.get("children");
                        break;
                    }
                }
            }
            Map<String, Object> tableMap = new HashMap<String, Object>();
            tableMap.put("name", tempMap.get("tableName"));
            tableMap.put("lev", 2);
            tableMap.put("leaf", true);
            tableList.add(tableMap);
        }
        return resList;
    }

    @Override
    public List<Map<String, Object>> getTableStructure(String tableName) {
        List<Map<String, Object>> list = this.resourceRepository.getTableStructure(tableName);
        return list;
    }

    @Override
    public List<Map<String, Object>> getTableComments(String tableName) {
        List<Map<String, Object>> list = this.resourceRepository.getTableComments(tableName);
        return list;
    }

    @Override
    public boolean isExists(String jpql, Map<String, Object> paramsMap) {
        List<Resource> list = this.resourceRepository.checkExistByJpql(jpql, paramsMap);
        return list.isEmpty();
    }

    @Override
    public List<Resource> getAllFirstModules() {
        Map<String, Query> queryMap = new HashMap<>();
        queryMap.put("delFlag", new Query(Condition.EQ, "0"));
        queryMap.put("resType", new Query(Condition.EQ, "1"));
        queryMap.put("parentResource.id", new Query(Condition.NULL, null));
        return this.resourceRepository.findSortByPropertys(queryMap, new String[]{"resOrder"}, true);
    }

    @Override
    public List<Resource> getAllResources() {
        return this.resourceRepository.findByProperty("delFlag", "0");
    }

    @Override
    public Resource getResourceById(String id) {
        return this.resourceRepository.getById(id);
    }

    /**
     * <p>Description: 设置子资源 开始递归</p>
     *
     * @param resourceList 结果list
     * @param list         原所有资源
     * @param parentId     顶级父资源parentId
     * @return 结果资源
     */
    private List<Resource> getChildResForTree(List<Resource> resourceList, List<Resource> list, String parentId) {
        for (int i = 0; i < list.size(); i++) {
            Resource currentNode = list.get(i);
            String curentNodeId = currentNode.getId();
            boolean isTopNode = parentId == null && list.get(i).getParentIdStr() == null;
            boolean isChildNode = parentId != null && parentId.equals(list.get(i).getParentIdStr());
            if (isTopNode) {
                currentNode.setChildren(getChildResourceSet(curentNodeId, list));
                resourceList.add(currentNode);
            }
            if (isChildNode) {
                currentNode.setChildren(getChildResourceSet(curentNodeId, list));
                resourceList.add(currentNode);
            }
        }
        return resourceList;
    }

    /**
     * <p>Description: 获取子资源set集合 递归</p>
     *
     * @param parentId 父资源id
     * @param list     资源list
     * @return 子资源set集合
     */
    private Set<Resource> getChildResourceSet(String parentId, List<Resource> list) {
        Set<Resource> set = new HashSet<Resource>();
        for (Resource r : list) {
            if (parentId.equals(r.getParentIdStr())) {
                r.setChildren(getChildResourceSet(r.getId(), list));
                set.add(r);
            }
        }
        return set;
    }

    /**
     * <p>Description: 查询上级资源</p>
     *
     * @param resource 资源
     * @param lists    资源list集合
     */
    private void getResourceByPid(Resource resource, List<Resource> lists) {
        if (resource.getParentResource() != null) {
            Resource res;
            res = this.resourceRepository.getById(resource.getParentResource().getId());
            lists.add(res);
            this.getResourceByPid(res, lists);
        }
    }


    private String saveResource(Resource res, String[] userInfo) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("resName", res.getResName());
        List<Resource> nList = this.resourceRepository.getExactRource(queryMap);
        if (nList.size() != 0) {
            return "resNameExist";
        }
        queryMap.clear();
        queryMap.put("resCode", res.getResCode());
        List<Resource> cList = this.resourceRepository.getExactRource(queryMap);
        if (cList.size() != 0) {
            return "resCodeExist";
        }
        BaseModelUtil.setDefaultFieldBaseModel(res, userInfo, true);
        this.resourceRepository.save(res);
        return "success";
    }

    private String updateResource(Resource res, String[] userInfo) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("resName", res.getResName());
        List<Resource> nList = this.resourceRepository.getExactRource(queryMap);
        if (nList.size() != 0 && !res.getId().equals(nList.get(0).getId())) {
            return "ResNameExist";
        }
        queryMap.clear();
        queryMap.put("resCode", res.getResCode());
        List<Resource> cList = this.resourceRepository.getExactRource(queryMap);
        if (cList.size() != 0 && !res.getId().equals(nList.get(0).getId())) {
            return "ResCodeExist";
        }
        if (res.getParentResource() != null) {
            List<Resource> childList = this.getChildsResource(res.getChildren());
            for (Resource r : childList) {
                if (r.getId().equals(res.getParentResource().getId())) {
                    return "NotChild";
                }
            }
        }
        BaseModelUtil.setDefaultFieldBaseModel(res, userInfo, false);
        this.resourceRepository.update(res);
        return "success";
    }

    private List<Resource> getChildsResource(Set<Resource> childsResource) {
        List<Resource> list;
        list = new ArrayList<>();
        if (childsResource != null) {
            list.addAll(childsResource);
            for (Resource res : childsResource) {
                list.addAll(this.getChildsResource(res.getChildren()));
            }
        }
        return list;
    }

    /**
     * <p>
     * Description: 重置list序号问题（序号:1->1.1->1.1.1 来进行展示）
     * </p>
     *
     * @param list list结果集
     */
    private void resetNumberList(List<Map<String, Object>> list) {
        int maxLevel;
        maxLevel = 1;
        int level;
        //进行保存每次的使用值
        Map<String, Integer> mapKey;
        mapKey = new HashMap<>();
        //进行初始化
        mapKey.put("1", 1);
        list.get(0).put("xh", 1);
        for (int i = 1; i < list.size(); i++) {
            //表示存在
            level = Integer.parseInt(list.get(i).get("le").toString());
            //进行数据自动加以
            if (mapKey.containsKey(list.get(i).get("le").toString())) {
                if (level <= maxLevel) {
                    mapKey.put(String.valueOf(level), mapKey.get(String.valueOf(level)) + 1);
                    for (int j = (level + 1); j <= maxLevel; j++) {
                        mapKey.remove(String.valueOf(j));
                    }
                }
            } else {
                //表示不存在
                mapKey.put(list.get(i).get("le").toString(), 1);
                if (maxLevel < Integer.parseInt(list.get(i).get("le").toString())) {
                    maxLevel = Integer.parseInt(list.get(i).get("le").toString());
                }
            }
            String xh = null;
            //序号进行赋值
            for (int j = 1; j <= level; j++) {
                if (level == j) {
                    if (xh == null) {
                        xh = mapKey.get(String.valueOf(j)) + "";
                    } else {
                        xh += mapKey.get(String.valueOf(j)) + "";
                    }
                } else {
                    if (xh == null) {
                        xh = mapKey.get(String.valueOf(j)) + ".";
                    } else {
                        xh += mapKey.get(String.valueOf(j)) + ".";
                    }
                }
            }
            list.get(i).put("xh", xh);
        }

    }

    /**
     * <p>Description: 设置子资源 开始递归</p>
     *
     * @param resourceList 结果list
     * @param list         原所有资源
     * @param parentId     顶级父资源parentId
     * @return 结果资源
     */
    private List<Map<String, Object>> getChildResourceForTree(List<Map<String, Object>> resourceList, List<Map<String, Object>> list, String parentId) {
        for (int i = 0; i < list.size(); i++) {
            if ((parentId == null && list.get(i).get("parentIdStr") == null)
                    || (parentId != null && parentId.equals(list.get(i).get("parentIdStr").toString()))) {
                list.get(i).put("children", getChildResSet(list.get(i).get("id").toString(), list));
                resourceList.add(list.get(i));
            }
        }
        return resourceList;
    }

    /**
     * <p>Description: 获取子资源set集合 递归</p>
     *
     * @param parentId 父资源id
     * @param list     资源list
     * @return 子资源set集合
     */
    private List<Map<String, Object>> getChildResSet(String parentId, List<Map<String, Object>> list) {
        List<Map<String, Object>> set = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> r : list) {
            if (r.get("parentIdStr") != null && parentId.equals(r.get("parentIdStr").toString())) {
                r.put("children", getChildResSet(r.get("id").toString(), list));
                set.add(r);
            }
        }
        return set;
    }

}
