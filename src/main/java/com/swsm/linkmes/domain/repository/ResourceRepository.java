package com.swsm.linkmes.domain.repository;


import com.swsm.linkmes.domain.repository.basic.custom.CustomResourceRepository;
import com.swsm.linkmes.model.basic.Resource;
import com.swsm.platform.domain.jpa.repository.BaseRepository;

/**
 * @author tinel
 * @Title: ResourceRepository
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/1014:06
 */
public interface ResourceRepository extends BaseRepository<Resource, String>, CustomResourceRepository {
}
