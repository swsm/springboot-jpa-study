package com.swsm.linkmes.service.basic.impl;

import com.swsm.linkmes.domain.repository.UserRepository;
import com.swsm.linkmes.model.basic.Organ;
import com.swsm.linkmes.model.basic.Role;
import com.swsm.linkmes.model.basic.User;
import com.swsm.linkmes.service.basic.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author tinel
 * @Title: LoginServiceImpl
 * @ProjectName mes-sm
 * @Description: ILoginService接口实现类
 * @date 2018/12/1111:43
 */
@Service
public class LoginServiceImpl implements ILoginService {

    private static Logger logger= LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUser(String loginName) {
        List<User> list;
        list = this.userRepository.findUser(loginName);
        if (list.isEmpty()) {
            return null;
        } else {
            for(User u:list){
                if("1".equals(u.getEnabled())){
                    return u;
                }
            }
            return list.get(0);
        }
    }

    @Override
    public Map<String, Object> findUserFullInfo(String loginName) {
        User user;
        user = this.findUser(loginName);
        Map<String, Object> map;
        map = new HashMap<>();
        if (user == null ||"0".equals(user.getEnabled())) {
            return Collections.EMPTY_MAP;
        }
        map.put("user", user);
        Set<Organ> organSet;
        organSet = user.getOrganList();
        Set<Role> roleSet;
        roleSet = user.getRoleList();
        logger.debug(organSet.size() + "" + roleSet.size());
        map.put("organs", organSet);
        map.put("roles", roleSet);
        return map;
    }

    @Override
    public User getUserByWorkNo(String workNo) {
        return this.userRepository.getUserByWorkNo(workNo);
    }
}
