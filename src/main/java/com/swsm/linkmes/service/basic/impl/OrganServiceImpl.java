package com.swsm.linkmes.service.basic.impl;

import com.swsm.linkmes.domain.dto.OrganDto;
import com.swsm.linkmes.domain.repository.OrganRepository;
import com.swsm.linkmes.model.basic.Organ;
import com.swsm.linkmes.service.basic.IOrganService;
import com.swsm.platform.model.BaseModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author tinel
 * @Title: OrganServiceImpl
 * @ProjectName mes-sm
 * @Description: IOrganService实现类
 * @date 2018/12/1111:35
 */
@Service
public class OrganServiceImpl implements IOrganService {

    @Autowired
    private OrganRepository organRepository;

    @Override
    @Cacheable(value="entityCache",key="#parentId")
    public List<Organ> findOrganByPanrentId(String parentId) {
        if (IOrganService.ROOT_NODE_ID.equals(parentId)) {
            List<Organ> list;
            list = this.organRepository.findOrganByPanrentId(null);
            return list;
        } else {
            List<Organ> list;
            list = this.organRepository.findOrganByPanrentId(parentId);
            return list;
        }
    }

    @Override
    public String deleteOrgan(String id, String username) {
        Organ organ = this.organRepository.getById(id);
        this.checkOrganDelete(organ);
        //1.子部门删除标记设为 true
        List<Organ> childOrganList;
        childOrganList = this.getChildsOrgan(organ.getChildren());
        for (Organ o : childOrganList) {
            o.setUpdateDate(new Date());
            o.setUpdateUser(username);
            o.setDelFlag(BaseModelUtil.DEL_TRUE);
            this.organRepository.update(o);
        }
        //2.父部门删除标记设为 true
        organ.setUpdateDate(new Date());
        organ.setUpdateUser(username);
        organ.setDelFlag(BaseModelUtil.DEL_TRUE);
        this.organRepository.update(organ);
        return "success";
    }

    @Override
    public String saveOrgan(Organ organ, String userName) {
        //验证机构编码的唯一性
        List<Organ> list;
        list = this.organRepository.findOrgan(organ.getOrganCode());
        if (!list.isEmpty()) {
            return "organCodeExist";
        }
        organ.setCreateUser(userName);
        organ.setCreateDate(new Date());
        organ.setDelFlag(BaseModelUtil.DEL_FALSE);
        Organ parentOrgan = organ.getParentOrgan();
        parentOrgan = this.organRepository.getById(parentOrgan.getId());
        organ.setParentOrgan(parentOrgan);
        this.organRepository.save(organ);
        return "success";
    }

    @Override
    public String updateOrgan(Organ organ, String username) {
        //验证机构编码的唯一性
        boolean isExists =  this.organRepository.isExistsOrgan(organ.getId(),organ.getOrganCode());
        if (isExists) {
            return "organCodeExist";
        }
        Organ o = this.organRepository.getById(organ.getId());
        List<Organ> childOrganList;
        childOrganList = this.getChildsOrgan(o.getChildren());
        for (Organ childOrgan : childOrganList) {
            if (organ.getParentOrgan() != null && childOrgan.getId().equals(organ.getParentOrgan().getId())) {
                return "parentOrganNotChildOrgan";
            }
        }
        organ.setUpdateUser(username);
        organ.setUpdateDate(new Date());
        this.organRepository.update(organ);
        return "success";
    }

    @Override
    public String treeDropMove(String sourceNode, String targetNode, String username, String dropPosition) {
        Organ organ;
        organ = this.organRepository.findUniqueByProperty("id", sourceNode);
        this.loadOrganParentNode(dropPosition,targetNode,organ);
        organ.setUpdateDate(new Date());
        organ.setUpdateUser(username);
        this.organRepository.update(organ);
        return "success";
    }

    @Override
    public String treeDropCopy(String sourceNode, String targetNode, String username, String dropPosition) {
        Organ organSource;
        //获得要复制的节点
        organSource = this.organRepository.findUniqueByProperty("id", sourceNode);
        Organ organNew;
        organNew = new Organ();
        organNew.setOrganName(organSource.getOrganName());
        organNew.setOrganCode(System.currentTimeMillis() + "");
        organNew.setOrganOrder(organSource.getOrganOrder());
        organNew.setDutyUsername(organSource.getDutyUsername());
        organNew.setRemark(organSource.getRemark());
        organNew.setDelFlag(BaseModelUtil.DEL_FALSE);
        organNew.setCreateDate(new Date());
        organNew.setCreateUser(username);
        this.loadOrganParentNode(dropPosition,targetNode,organNew);
        this.organRepository.save(organNew);
        return "success";
    }

    @Override
    public List<OrganDto> getAllOrgans() {
        return this.organRepository.getAllOrgan();
    }

    @Override
    public List<Map<String, String>> getAllOrganNames(String organName) {
        List<Map<String, String>> list = this.organRepository.getAllOrganNames(organName);
        return list;
    }

    @Override
    public List<Organ> getAllOrgan() {
        return this.organRepository.findByProperty("delFlag",BaseModelUtil.DEL_FALSE);
    }


    @Override
    public boolean isExistsDict(String jpql, Map<String, Object> paramsMap) {
        List<Organ> list = this.organRepository.checkExistByJpql(jpql,paramsMap);
        return list.isEmpty();
    }

    /**
     * 校验机构和其子机构是否可以删除
     * @param organ
     */
    private void checkOrganDelete(Organ organ) {
        boolean flag = this.organRepository.checkOrganUsed(organ.getId());
        if(flag){
            throw new RuntimeException("当前机构或其子部门已使用，无法删除！");
        }else{
            Set<Organ> set = organ.getChildren();
            if(!set.isEmpty()){
                for(Organ o :set){
                    this.checkOrganDelete(o);
                }
            }
        }
    }

    /**
     * <p>Description: 获取所有子 部门</p>
     * @param childsOrgan 子部门
     * @return 子部门列表
     */
    private List<Organ> getChildsOrgan(Set<Organ> childsOrgan) {
        List<Organ> list;
        list = new ArrayList<Organ>();
        list.addAll(childsOrgan);
        for (Organ o : childsOrgan) {
            list.addAll(this.getChildsOrgan(o.getChildren()));
        }
        return list;
    }


    /**
     * 加载parantNode属性
     * @param dropPosition
     * @param targetNode
     * @param organ
     */
    private void loadOrganParentNode(String dropPosition,String targetNode,Organ organ){
        if ("append".equals(dropPosition)) {
            //在树显示的是在父节点中插入子节点
            organ.setParentOrgan(this.organRepository.findUniqueByProperty("id", targetNode));
        } else {
            //在targetNode节点旁边插入兄弟节点
            if (this.organRepository.findUniqueByProperty("id", targetNode).getParentOrgan() != null) {
                //拖拽子节点不在根目录时
                organ.setParentOrgan(this.organRepository.findUniqueByProperty("id",
                        this.organRepository.findUniqueByProperty("id", targetNode).getParentOrgan().getId()));
            } else {
                organ.setParentOrgan(null);
            }
        }
    }

    @Override
    public Organ getOrganById(String organId) {
        return this.organRepository.getById(organId);
    }
}
