package com.swsm.linkmes.domain.repository;


import com.swsm.linkmes.domain.repository.basic.custom.CustomLoginInfoRepository;
import com.swsm.linkmes.model.basic.LoginInfo;
import com.swsm.platform.domain.jpa.repository.BaseRepository;

/**
 * @author tinel
 * @Title: LoginInfoRepository
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/2515:36
 */
public interface LoginInfoRepository extends BaseRepository<LoginInfo,String>,CustomLoginInfoRepository {
}
