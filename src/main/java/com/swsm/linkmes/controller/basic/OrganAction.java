package com.swsm.linkmes.controller.basic;

import com.swsm.linkmes.domain.dto.OrganDto;
import com.swsm.linkmes.model.basic.Organ;
import com.swsm.linkmes.service.basic.IOrganService;
import com.swsm.linkmes.vo.basic.OrganVo;
import com.swsm.platform.action.BaseAction;
import com.swsm.platform.action.UniqueResultResponse;
import com.swsm.platform.model.BaseModelUtil;
import com.swsm.platform.util.converter.entity.ConvertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author tinel
 * @Title: OrganAction
 * @ProjectName mes-sm
 * @Description: OrganApi实现类
 * @date 2018/12/219:59
 */
@RestController
public class OrganAction extends BaseAction {

    @Autowired
    private IOrganService organService;

    @Override
    protected boolean isNotFindEntity(String jpql, Map<String, Object> queryMap) {
        return this.organService.isExistsDict(jpql,queryMap);
    }

    @GetMapping("/basic/organ/find/{nodeId}")
    public List<OrganVo> findOrgan(@PathVariable("nodeId") String nodeId) {
        List<Organ> list = this.organService.findOrganByPanrentId(nodeId);
        List<OrganVo>  results = new ArrayList<>();
        for(Organ organ : list){
            OrganVo organVo = new OrganVo();
            BeanUtils.copyProperties(organ,organVo,new String[]{"parentOrgan","children","userList"});
            Set<Organ> children = organ.getChildren();
            boolean isLeaf = true;
            for(Organ o : children){
                if(!o.getDelFlag().equals(BaseModelUtil.DEL_TRUE)){
                    isLeaf = false;
                }
            }
            organVo.setLeaf(isLeaf);
            results.add(organVo);
        }
        return results;
    }

    @DeleteMapping("/basic/organ/delete/{organId}/{userName}")
    public UniqueResultResponse<String> deleteOrgan(@PathVariable("organId") String organId, @PathVariable("userName") String userName) {
        String msg = this.organService.deleteOrgan(organId,userName);
        return new UniqueResultResponse(msg,msg);
    }

    @PostMapping("/basic/organ/save/{userName}")
    public UniqueResultResponse<String> saveOrgan(@ModelAttribute OrganVo organVo,@PathVariable("userName") String userName) {
        Organ organ = new Organ();
        String parentOrganId = organVo.getParentOrgenId();
        BeanUtils.copyProperties(organVo,organ,new String[]{"parentOrgan","children"});
        organ.setParentOrgan(new Organ(parentOrganId));
        String msg = this.organService.saveOrgan(organ,userName);
        return new UniqueResultResponse<>(msg,msg);
    }

    @PostMapping("/basic/organ/update/{userName}")
    public UniqueResultResponse<String> updateOrgan(@ModelAttribute OrganVo organVo,@PathVariable("userName") String userName) {
        Organ organ = new Organ();
        BeanUtils.copyProperties(organVo,organ);
        String msg = this.organService.updateOrgan(organ,userName);
        return new UniqueResultResponse<>(msg,msg);
    }


    @GetMapping("/basic/organ/getAllOrganNames?organName={organName}")
    public List<Map<String, String>> getAllOrganNames(@PathVariable("organName") String organName) {
        return this.organService.getAllOrganNames(organName);
    }

    @GetMapping("/basic/organ/getAllOrgans")
    public List<OrganVo> getAllOrgans() {
        List<OrganDto> list = this.organService.getAllOrgans();
        return ConvertUtil.toNList(list,OrganDto.class,OrganVo.class);
    }

    @GetMapping("/basic/organ/getOrganById/{organId}")
    public OrganVo getOrganById(@PathVariable("organId") String organId) {
        Organ organ = this.organService.getOrganById(organId);
        OrganVo organVo = new OrganVo();
        BeanUtils.copyProperties(organ,organVo,new String[]{"parentOrgan","children","userList"});
        return organVo;
    }
}
