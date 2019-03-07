package com.swsm.linkmes.service.basic.impl;

import com.swsm.linkmes.domain.repository.ResourceRepository;
import com.swsm.linkmes.model.basic.Resource;
import com.swsm.linkmes.service.basic.IMainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tinel
 * @Title: MainServiceImpl
 * @ProjectName mes-sm
 * @Description: IMainService接口实现类
 * @date 2018/12/1111:37
 */
@Service
public class MainServiceImpl implements IMainService {

    private static Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Resource[] getChildResource(String parent) {
        List<Resource> list;
        if (IMainService.ROOT_NODE_ID.equals(parent)) {
            list = this.resourceRepository.getChildResource(null);
        }else{
            list = this.resourceRepository.getChildResource(parent);
        }
        return list.toArray(new Resource[0]);
    }

    @Override
    public Resource[] getResourcesByParentId(String parentId, String loginName) {
        if (loginName == null) {
            logger.debug("loginName is null");
            return new Resource[0];
        }
        if (!IMainService.ADMIN_USER.equals(loginName)) {
            List<Resource> list;
            if (IMainService.ROOT_NODE_ID.equals(parentId)) {
                list = this.resourceRepository.getResourcesByParentId(loginName, null);
            }else{
                list = this.resourceRepository.getResourcesByParentId(loginName, parentId);
            }
            Resource[] array;
            array = new Resource[list.size()];
            int i = 0;
            for (Object o : list) {
                Resource r;
                r = (Resource) o;
                array[i] = r;
                i++;
            }
            return array;
        } else {
            return this.getChildResource(parentId);
        }
    }

    @Override
    public Resource[] getHavResByLoginName(String loginName) {
        if (IMainService.ADMIN_USER.equals(loginName)) {
            List<Resource> list;
            list = this.resourceRepository.getHavResByLoginName(null);
            return list.toArray(new Resource[0]);
        }
        List<Resource> list;
        list = this.resourceRepository.getHavResByLoginName(loginName);
        return list.toArray(new Resource[0]);
    }
}
