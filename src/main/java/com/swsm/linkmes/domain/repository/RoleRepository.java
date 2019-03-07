package com.swsm.linkmes.domain.repository;


import com.swsm.linkmes.domain.repository.basic.custom.CustomRoleRepository;
import com.swsm.linkmes.model.basic.Role;
import com.swsm.platform.domain.jpa.repository.BaseRepository;

/**
 * @author tinel
 * @Title: RoleRepository
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/1014:06
 */
public interface RoleRepository extends BaseRepository<Role,String>, CustomRoleRepository {
}
