package com.swsm.linkmes.domain.repository.basic.custom;


import com.swsm.linkmes.domain.dto.OrganDto;
import com.swsm.linkmes.model.basic.Organ;
import com.swsm.platform.domain.jpa.repository.BaseCustomRepository;

import java.util.List;
import java.util.Map;

/**
 * @author tinel
 * @Title: CustomOrganRepository
 * @ProjectName mes-sm
 * @Description: 系统管理中机构管理数据访问层
 * @date 2018/12/1014:12
 */
 public interface CustomOrganRepository extends BaseCustomRepository {

    /**
     *
     * <p>Description:  根据parentId获取其下所有子机构,如果parentId为null，则为第一层机构信息</p>
     * @param parentId 父机构id，可以为null
     * @return List<Organ> 机构信息列表
     */
     List<Organ> findOrganByPanrentId(String parentId);
    /**
     *
     * <p>Description: 根据机构编码获取机构信息</p>
     * @param organCode 机构编码 ，不允许为null
     * @return List<Organ> 机构信息列表
     */
     List<Organ> findOrgan(String organCode);
    /**
     *
     * <p>Description: 根据机构编码与机构ID查找是否存在非该机构ID的机构编码</p>
     * @param organId 机构ID
     * @param organCode 机构编码
     * @return boolean，存在则返回true，不存在则放回false
     */
     boolean isExistsOrgan(String organId, String organCode);
    /**
     *
     * <p>Description: 查询所有机构信息，无查询条件</p>
     * @return list 机构Dto列表
     */
     List<OrganDto> getAllOrgan();
    /**
     *
     * <p>Description: 根据机构名称模糊查询符合条件的机构名称</p>
     * @param organName 机构名称
     * @return list的map中只有机构名称字段
     */
     List<Map<String, String>> getAllOrganNames(String organName);
    /**
     * 校验机构是否被使用
     * @param id 机构id
     * @return 使用情况 true：已使用  false：未使用
     */
     boolean checkOrganUsed(String id);
}
