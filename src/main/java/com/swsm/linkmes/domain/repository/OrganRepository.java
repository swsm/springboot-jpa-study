package com.swsm.linkmes.domain.repository;


import com.swsm.linkmes.domain.repository.basic.custom.CustomOrganRepository;
import com.swsm.linkmes.model.basic.Organ;
import com.swsm.platform.domain.jpa.repository.BaseRepository;

/**
 * @author tinel
 * @Title: OrganRepository
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/1014:07
 */
public interface OrganRepository extends BaseRepository<Organ,String>,CustomOrganRepository {
}
