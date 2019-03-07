package com.swsm.linkmes.controller.basic;

import com.swsm.linkmes.model.basic.Resource;
import com.swsm.linkmes.service.basic.IResourceService;
import com.swsm.linkmes.vo.basic.ResourceQueryVo;
import com.swsm.linkmes.vo.basic.ResourceVo;
import com.swsm.platform.action.BaseAction;
import com.swsm.platform.action.CommonJsonVo;
import com.swsm.platform.action.UniqueResultResponse;
import com.swsm.platform.model.PageFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author tinel
 * @Title: ResourceAction
 * @ProjectName mes-sm
 * @Description: ResourceApi实现类
 * @date 2018/12/1213:21
 */
@RestController
public class ResourceAction extends BaseAction {

    @Autowired
    private IResourceService resourceService;


    @Override
    protected boolean isNotFindEntity(String jpql, Map<String, Object> queryMap) {
        return this.resourceService.isExists(jpql,queryMap);
    }

    @GetMapping("/basic/resource/getResourceTree")
    public List<ResourceVo> getResourceTree(@ModelAttribute ResourceQueryVo resourceQueryVo) {
        List<Resource> list = this.resourceService.getRoleResourceTreeByPid(resourceQueryVo.getParentId(),resourceQueryVo.getRoleId());
        return this.toResourceVoList(list);
    }


    private List<ResourceVo> toResourceVoList(List<Resource> list){
        List<ResourceVo> results = new ArrayList<>();
        for(Resource resource : list){
            ResourceVo resourceVo = new ResourceVo();
            BeanUtils.copyProperties(resource,resourceVo,new String[]{"children"});
            this.loadChildren(resource,resourceVo);
            results.add(resourceVo);
        }
        return results;
    }


    private void loadChildren(Resource resource,ResourceVo resourceVo){
        Set<Resource> childs = resource.getChildren();
        if(childs!=null){
            Set<ResourceVo> voChilds = new HashSet<>();
            for(Resource child : childs){
                ResourceVo childResourceVo = new ResourceVo();
                BeanUtils.copyProperties(child,childResourceVo,new String[]{"children"});
                voChilds.add(childResourceVo);
                this.loadChildren(child,childResourceVo);
            }
            resourceVo.setChildren(voChilds);
        }
    }

    @PostMapping("/basic/resource/getResource")
    public List<Map<String, Object>> getResource(@ModelAttribute ResourceQueryVo resourceQueryVo) {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("resCode",resourceQueryVo.getResCode());
        queryMap.put("resName",resourceQueryVo.getResName());
        queryMap.put("resType",resourceQueryVo.getResType());
        List<Map<String, Object>> list = this.resourceService.getResource(queryMap);
        this.loadResourceTree(list);
        return list;
    }



    @GetMapping("/basic/resource/tree")
    public List<Map<String, Object>> queryTreeForParent(String resName) {
        if(StringUtils.isEmpty(resName)){
            resName="";
        }
        List<Map<String, Object>>  list = this.resourceService.getResourceTreeByResname(resName);
        this.loadResourceTree(list);
        return list;
    }



    @PostMapping("/basic/resource/save/{userName}")
    public UniqueResultResponse<String> saveOrUpdateResource(@ModelAttribute ResourceVo resourceVo, @PathVariable("userName") String userName) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceVo,resource);
        String parentId = resourceVo.getParentIdStr();
        if(!StringUtils.isEmpty(parentId)){
            Resource parentResource = new Resource();
            parentResource.setId(parentId);
            resource.setParentResource(parentResource);
        }
        String msg = this.resourceService.saveOrUpdateResource(resource,new String[]{"",userName});
        return new UniqueResultResponse(msg,msg);
    }

    @GetMapping("/basic/resource/unique/{uniqueFields}")
    public boolean getResourceUnique(@ModelAttribute CommonJsonVo commonJsonVo, @PathVariable("uniqueFields") String uniqueFields) {
        return super.queryCheckUnique(uniqueFields.split(","),commonJsonVo,Resource.class);
    }

    @DeleteMapping("/basic/resource/delete/{id}")
    public boolean deleteResource(@PathVariable("id") String id) {
        return this.resourceService.deleteResource(id);
    }

    @PostMapping("/basic/resource/status/{ids}/{enable}")
    public boolean updateResourceEnable(@PathVariable("ids") String ids, @PathVariable("enable") String enable) {
        this.resourceService.updateResourceEnabled(enable,ids.split(","));
        return true;
    }

    @PostMapping("/basic/resource/exportResource")
    public List<ResourceVo> exportResource(@ModelAttribute ResourceQueryVo resourceQueryVo) {
        PageFilter pageFilter = PageFilter.of(0,Integer.MAX_VALUE);
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("resCode",resourceQueryVo.getResCode());
        queryMap.put("resName",resourceQueryVo.getResName());
        queryMap.put("resType",resourceQueryVo.getResType());
        List<Resource> list = this.resourceService.queryPagedEntityList(queryMap,pageFilter);
        return this.toResourceVoList(list);
    }

    @GetMapping("/basic/resource/exportResourceTree/{roleId}")
    public List<Map<String, Object>> exportResourceTree(@PathVariable("roleId") String roleId) {
       return this.resourceService.getResourceTreeForExport(roleId);
    }

    @GetMapping("/basic/resource/getAllResourceTree/{workNo}")
    public List<Map<String, Object>> getAllResourceTree(@PathVariable("workNo") String workNo) {
        return this.resourceService.getAllResourceTree(workNo);
    }

    
    @GetMapping("/basic/getAllResourceTable")
    public List<Map<String, Object>> getAllResourceTable() {
        return this.resourceService.getResourceTable(null);
    }


    @GetMapping("/basic/getResourceTable/{tableName}")
    public List<Map<String, Object>> getResourceTable(@PathVariable(value="tableName") String tableName) {
        return this.resourceService.getResourceTable(tableName);
    }


    @GetMapping("/basic/getTableStructure/{tableName}")
    public List<Map<String, Object>> getTableStructure(@PathVariable(value="tableName") String tableName) {
        return this.resourceService.getTableStructure(tableName);
    }


    @GetMapping("/basic/getTableComments/{tableName}")
    public List<Map<String, Object>> getTableComments(@PathVariable(value="tableName") String tableName) {
        return this.resourceService.getTableComments(tableName);
    }


    @GetMapping("/basic/getAllFirstModules")
    public List<ResourceVo> getAllFirstModules() {
        List<Resource> list = this.resourceService.getAllFirstModules();
        List<ResourceVo> results = new LinkedList<>();
        for(Resource resource : list){
            ResourceVo resourceVo = new ResourceVo();
            resourceVo.setResCode(resource.getResCode());
            resourceVo.setResName(resource.getResName());
            results.add(resourceVo);
        }
        return results;
    }


    @GetMapping("/basic/getAllResources")
    public List<ResourceVo> getAllResources() {
        List<Resource> list = this.resourceService.getAllResources();
        List<ResourceVo> results = new LinkedList<>();
        for(Resource resource : list){
            ResourceVo resourceVo = new ResourceVo();
            resourceVo.setResCode(resource.getResCode());
            resourceVo.setResName(resource.getResName());
            resourceVo.setId(resource.getId());
            results.add(resourceVo);
        }
        return results;
    }


    @GetMapping("/basic/getResourceById/{id}")
    public ResourceVo getResourceById(@PathVariable(value = "id") String id) {
        Resource resource = this.resourceService.getResourceById(id);
        ResourceVo vo = new ResourceVo();
        if(resource!=null){
            vo.setResCode(resource.getResCode());
            vo.setResName(resource.getResName());
            vo.setId(resource.getId());
            vo.setParentIdStr(resource.getParentId());
        }
        return vo;
    }

    /**
     * <p>Description: 查询一级资源</p>
     * @param list 机构树
     */
    private void findFirstNode(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            if (map.get("parentId") == null) {
                map.put("parentId", "-1");
            }
        }
    }

    /**
     * <p>Description: 递归非叶子节点</p>
     * @param currendNode 当前节点
     * @param list 机构树
     */
    private void loadNodeChildren(Map<String, Object> currendNode, List<Map<String, Object>> list) {
        String parentId;
        parentId = currendNode.get("id").toString();
        List<Map<String, Object>> returnList;
        returnList = this.getChildrenNodes(parentId, list);
        if (returnList.isEmpty()) {
            currendNode.put("leaf", true);
        } else {
            currendNode.put("leaf", false);
            currendNode.put("expanded", false);
            currendNode.put("children", returnList);
            for (Map<String, Object> map : returnList) {
                this.loadNodeChildren(map, list);
            }
        }
    }

    /**
     * <p>Description: 查询当前节点下的节点</p>
     * @param parentId 当前节点id
     * @param list 机构树
     * @return returnList 当前节点下的节点
     */
    private List<Map<String, Object>> getChildrenNodes(String parentId, List<Map<String, Object>> list) {
        List<Map<String, Object>> returnList;
        returnList = new LinkedList<>();
        for (Map<String, Object> map : list) {
            if ((map.get("parentId") + "").equals(parentId)) {
                returnList.add(map);
            }
        }
        return returnList;
    }

    private Map<String,Object> createTreeRootNode(){
        Map<String, Object> map;
        map = new HashMap<>();
        map.put("parentId", null);
        map.put("id", "-1");
        map.put("text", "根节点");
        return map;
    }

    private void loadResourceTree(List<Map<String, Object>> list){
        Map<String,Object> map = this.createTreeRootNode();
        this.findFirstNode(list);
        if (!list.isEmpty()) {
            this.loadNodeChildren(map, list);
        }
    }
}
