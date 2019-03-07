package com.swsm.linkmes.domain.repository;


import com.swsm.linkmes.domain.repository.basic.custom.CustomUserRepository;
import com.swsm.linkmes.model.basic.User;
import com.swsm.platform.domain.jpa.repository.BaseRepository;

/**
 * @author tinel
 * @Title: UserRepository
 * @ProjectName mes-sm
 * @Description:
 * @date 2018/12/1014:05
 */
public interface UserRepository extends BaseRepository<User, String>, CustomUserRepository {
}
