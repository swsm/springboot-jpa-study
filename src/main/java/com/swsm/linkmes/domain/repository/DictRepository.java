package com.swsm.linkmes.domain.repository;


import com.swsm.linkmes.domain.repository.basic.custom.CustomDictRepository;
import com.swsm.linkmes.model.basic.Dict;
import com.swsm.platform.domain.jpa.repository.BaseRepository;

/**
 * @author tinel
 * @Title: DictRepository
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/1014:08
 */
public interface DictRepository extends BaseRepository<Dict,String>,CustomDictRepository {
}
